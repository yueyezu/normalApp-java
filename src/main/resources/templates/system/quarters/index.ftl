<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="岗位管理"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <form role="form" class="form-inline query-form">
                    <div class="input-group">
                        <input type="text" class="form-control" id="queryKeyword" placeholder="请输入编号或名称"/>
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-primary" onclick="refreshTable()"><i class="fa fa-search"></i>查询</button>
                        </span>
                    </div>
                </form>
                <@shiro.hasPermission name="funcQuarters-btnAdd">
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
<@mc.ltfooter  type="list">
    <script type="text/javascript">
        // 本功能所有请求的根路径
        var prefix = '${rc.contextPath}/role';
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
                url: prefix + "/list",
                showExport: true,
                exportDataType: 'all',               //导出checkbox选中的行数: all\selected\
                toolbar: '#tableToolbar',            //工具按钮用哪个容器
                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                pagination: false,                    //是否显示分页（*）
                queryParams: function (params) {
                    params.keyword = $('#queryKeyword').val();
                    params.type = 2;
                    return params;
                },
                columns: [{field: 'ck', checkbox: true},
                    {
                        title: '岗位编号', field: 'code',
                        formatter: function (value, row, index) {
                            return '<a onclick="view(\'' + row.id + '\')">' + value + '</a>';
                        }
                    },
                    {title: '岗位名称', field: 'name'},
                    {title: '排序码', field: 'sortNum'},
                    {
                        title: "操作", field: "id",
                        formatter: function (value, row) {
                            var e = '<@shiro.hasPermission name="funcQuarters-btnEdit"><a class="btn btn-success btn-xs m-r-xs" onclick="edit(\'' + value + '\');return false;"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                            var d = '<@shiro.hasPermission name="funcQuarters-btnDelete"><a class="btn btn-danger btn-xs m-r-xs" onclick="del(\'' + value + '\');return false;"><i class="fa fa-remove"></i>删除</a></@shiro.hasPermission>';
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
            lt.open("查看岗位信息", prefix + "/quarters/view/" + id);
        }

        // 添加代码
        function add() {
            lt.open("添加岗位信息", prefix + "/quarters/form", {
                end: function (index) {
                    refreshTable();
                }
            });
        }

        // 修改的代码
        function edit(id) {
            lt.open("修改岗位信息", prefix + "/quarters/form/" + id, {
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
                        lt.alertSuccess(res.msg, function () {
                            refreshTable();
                        });
                    } else {
                        lt.alertError(res.msg);
                    }
                });
            });
        }
    </script>
</@mc.ltfooter>
</body>
</html>

