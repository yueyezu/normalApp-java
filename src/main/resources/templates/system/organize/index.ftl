<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="组织架构" import="treegrid" />
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="col-sm-12">
        <div class="ibox">
            <div class="ibox-content">
                <!--菜单列表信息-->
                <form role="form" class="form-inline query-form">
                    <div class="input-group">
                        <input type="text" class="form-control" id="queryKeyword" placeholder="请输入编号、名称"/>
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-primary" onclick="refreshTable()"><i class="fa fa-search"></i>查询</button>
                        </span>
                    </div>
                </form>
                <@shiro.hasPermission name="funcOrganize-btnAdd">
                    <div id="tableToolbar" role="group" class="t-bar">
                        <a class="btn btn-primary" title="添加" onclick="add()">
                            <i class="fa fa-plus" aria-hidden="true"></i>添加
                        </a>
                    </div>
                </@shiro.hasPermission>
                <table id="dataTable" data-mobile-responsive="true"></table>
            </div>
        </div>
    </div>
</div>
<@mc.ltfooter type="list" import="treegrid">
    <script type="text/javascript">
        // 本功能所有请求的根路径
        var prefix = '${rc.contextPath}/organize';
        $(function () {
            initQuery();
            initTable();
        });

        // 初始化查询条件
        function initQuery() {
            $("#queryType").initSelect(top.lt.cache.dictItemArr.systemType, {
                disable_search: true
            });
        }

        // 初始化查询列表
        function initTable() {
            lt.dataTable.init("#dataTable", {
                url: prefix + "/treeList",
                showExport: false,
                toolbar: '#tableToolbar',            //工具按钮用哪个容器
                pagination: false,
                idField: "id",                     //每一行的唯一标识，一般为主键列
                treeShowField: 'name',
                parentIdField: 'parentId',
                queryParams: function (params) {
                    params.keyword = $('#queryKeyword').val();
                    return params;
                },
                columns: [{field: 'ck', checkbox: true},
                    {
                        title: '名称', field: 'name',
                        formatter: function (value, row, index) {
                            return '<a onclick="view(\'' + row.id + '\')" >' + value + '</a>';
                        }
                    },
                    {title: '编号', field: 'code'},
                    {title: '负责人', field: 'manager'},
                    {title: '电话', field: 'phone'},
                    {title: '部门说明', field: 'description', hidden: true},
                    {title: '排序码', field: 'sortNum'},
                    {
                        title: "操作", field: "id",
                        formatter: function (value, row) {
                            var e = '<@shiro.hasPermission name="funcOrganize-btnEdit"><a class="btn btn-success btn-xs m-r-xs" onclick="edit(\'' + value + '\');return false;"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                            var d = '<@shiro.hasPermission name="funcOrganize-btnDelete"><a class="btn btn-danger btn-xs m-r-xs" onclick="del(\'' + value + '\');return false;"><i class="fa fa-remove"></i>删除</a></@shiro.hasPermission>';
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
            lt.open("查看组织架构信息", prefix + "/view/" + id);
        }

        // 添加代码
        function add() {
            lt.open("添加组织架构信息", prefix + "/form", {
                end: function (index) {
                    refreshTable();
                }
            });
        }

        // 修改的代码
        function edit(id) {
            lt.open("修改组织架构信息", prefix + "/form/" + id, {
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
                        lt.alertWarning(res.msg);
                    }
                });
            });
        }
    </script>
</@mc.ltfooter>
</body>