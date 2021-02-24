<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="菜单管理" import="treegrid" />
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <!--系统列表显示位置-->
                <ul id="systemPnl" class="nav nav-tabs m-b-sm">
                    <#list systemList as system>
                        <li <#if system_index ==0>class="active"</#if>><a code="${system.code}" href="javascript:void(0)">${system.name}</a></li>
                    </#list>
                </ul>
                <!--菜单列表信息-->
                <div style="height: 538px;overflow-y: auto;">
                    <@shiro.hasPermission name="funcMenu-btnQuery">
                        <form role="form" class="form-inline query-form">
                            <div class="input-group">
                                <label class="input-group-addon control-label">关键字：</label>
                                <input type="text" class="form-control" id="queryKeyword" placeholder="编号或名称"/>
                                <span class="input-group-btn">
	                            <button type="button" class="btn btn-primary" onclick="refreshTable()"><i class="fa fa-search"></i>查询</button>
	                        </span>
                            </div>
                        </form>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="funcMenu-btnAdd">
                        <div id="tableToolbar" role="group" class="t-bar">
                            <a class="btn btn-primary" title="添加模块" onclick="add(1)">
                                <i class="fa fa-plus" aria-hidden="true"></i>添加模块
                            </a>
                        </div>
                    </@shiro.hasPermission>
                    <table id="dataTable" data-mobile-responsive="true"></table>
                </div>
            </div>
        </div>
    </div>
</div>

<@mc.ltfooter type="list" import="treegrid">
    <script type="text/javascript">
        // 本功能所有请求的根路径
        var prefix = '${rc.contextPath}/menu';
        var nowSystem = '';
        $(function () {
            nowSystem = $('#systemPnl li.active a').attr('code');   // 初始化当前系统的值
            initQuery();
            initTable();
        });

        // 初始化查询条件
        function initQuery() {
            // 系统选择的事件
            $('#systemPnl a').click(function (e) {
                e.preventDefault()
                var $this = $(this);
                var clickSystem = $this.attr('code');
                if (clickSystem != nowSystem) {
                    $(this).tab('show');
                    nowSystem = clickSystem;
                    refreshTable();
                }
            });

            $("#queryType").initSelect(top.lt.cache.dictItemArr.systemType, {
                disable_search: true
            });
        }

        // 初始化查询列表
        function initTable() {
            lt.dataTable.init("#dataTable", {
                url: prefix + "/list",
                showExport: false,
                toolbar: '#tableToolbar',            //工具按钮用哪个容器
                pagination: false,                    //是否显示分页（*）
                idField: "id",                     //每一行的唯一标识，一般为主键列
                treeShowField: 'name',
                parentIdField: 'parentId',
                queryParams: function (params) {
                    params.keyword = $('#queryKeyword').val();
                    params.systemCode = nowSystem;
                    return params;
                },
                columns: [{field: 'ck', checkbox: true},
                    {
                        title: '名称', field: 'name',
                        formatter: function (value, row, index) {
                            return '<a onclick="view(\'' + row.id + '\')">' + value + '</a>';
                        }
                    },
                    {title: '编号', field: 'code'},
                    {
                        title: '图标', field: 'icon', align: 'center',
                        formatter: function (value, row, index) {
                            return '<i class="' + value + '"></i>';
                        }
                    },
                    {title: '连接', field: 'url'},
                    {
                        title: '类型', field: 'type',
                        formatter: function (value, row, index) {
                            return top.lt.cache.dictItems["menuType"][value];
                        }
                    },
                    {title: '排序码', field: 'sortNum'},
                    {
                        title: "操作", field: "id",
                        formatter: function (value, row) {
                            var e = '';
                            if (row.type == 3) { //按钮
                                e += '<@shiro.hasPermission name="funcMenu-btnEditButton"><a class="btn btn-success btn-xs m-r-xs" onclick="edit(\'' + value + '\');return false;"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                            } else { //模块和功能
                                e += '<@shiro.hasPermission name="funcMenu-btnEdit"><a class="btn btn-success btn-xs m-r-xs" onclick="edit(\'' + value + '\');return false;"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                            }
                            var d = '';
                            if (row.type == 3) { //按钮
                                d += '<@shiro.hasPermission name="funcMenu-btnDeleteButton"><a class="btn btn-danger btn-xs m-r-xs" onclick="del(\'' + value + '\');return false;"><i class="fa fa-remove"></i>删除</a></@shiro.hasPermission>';
                            } else { //模块和功能
                                d += '<@shiro.hasPermission name="funcMenu-btnDelete"><a class="btn btn-danger btn-xs m-r-xs" onclick="del(\'' + value + '\');return false;"><i class="fa fa-remove"></i>删除</a></@shiro.hasPermission>';
                            }
                            var af = '<@shiro.hasPermission name="funcMenu-btnAdd"><a class="btn btn-info btn-xs m-r-xs" onclick="add(2,\'' + value + '\');return false;"><i class="fa fa-plus"></i>添加菜单</a></@shiro.hasPermission>';
                            var ab = '<@shiro.hasPermission name="funcMenu-btnAddButton"><a class="btn btn-info btn-xs m-r-xs" onclick="add(3,\'' + value + '\');return false;"><i class="fa fa-plus"></i>添加按钮</a></@shiro.hasPermission>';

                            if (row.type == 1)
                                return e + d + af;
                            else if (row.type == 2)
                                return e + d + ab;
                            else
                                return e + d;
                        }
                    }
                ]
            });
        }

        // 刷新查询列表
        function refreshTable() {
            $('#dataTable').bootstrapTable("refresh");
        }

        // 详情查看的操作方法
        function view(id) {
            lt.open("查看菜单信息", prefix + "/view/" + id);
        }

        // 添加代码
        // type 1-模块；2-功能；3-按钮
        function add(type, pId) {
            var url = prefix + '/form?systemCode=' + nowSystem + '&type=' + type;
            if (pId) {
                url += "&parentId=" + pId
            }
            lt.open("添加菜单信息", url, {
                end: function (index) {
                    refreshTable();
                }
            });
        }

        // 修改的代码
        function edit(id) {
            lt.open("修改菜单信息", prefix + "/form/" + id + "?systemCode=" + nowSystem, {
                end: function () {
                    refreshTable();
                }
            });
        }

        // 删除的代码
        function del(id) {
            lt.confirm("确定要删除选中数据？", function () {
                $.post(prefix + '/delete', {id: id}, function (res) {
                    if (res.code == 200) {
                        lt.msgSuccess(res.msg, function () {
                            refreshTable();
                        });
                    } else {
                        // 强制删除部分逻辑去除掉，后续有需要再说。
                        // lt.confirm('存在关联数据，是否强制删除？<br/>强制删除，会将关联数据一并删除！', function () {
                        //     $.post(prefix + '/delete', {'id': id, 'force': true}, function () {
                        //         if (res.code == 200) {
                        //             lt.msgSuccess(res.msg, function () {
                        //                 refreshTable();
                        //             });
                        //         } else {
                        lt.alertWarning(res.msg);
                        //         }
                        //     })
                        // });
                    }
                });
            });
        }
    </script>
</@mc.ltfooter>
</body>

