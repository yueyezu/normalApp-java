<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="系统版本管理"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
            	<@shiro.hasPermission name="funcSystem-btnAddVersion">
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
        var prefix = '${rc.contextPath}/systemVersion';
        var sysCode = "${sysCode}";

        $(function () {
            initQuery();
            initTable();
        });

        // 初始化查询条件
        function initQuery() {
        }

        // 初始化查询列表
        function initTable() {
            lt.dataTable.init("#dataTable", {
                url: prefix + "/list?fSystemcode=" + sysCode,
                showRefresh: true,
                search: false,
                toolbar: '#tableToolbar',            //工具按钮用哪个容器
                pagination: false,                    //是否显示分页（*）
                columns: [{field: 'ck', checkbox: true},
                    {
                        title: '版本号', field: 'fVersion',
                        formatter: function (value, row, index) {
                            return '<a onclick="view(\'' + row.fId + '\')">' + value + '</a>';
                        }
                    },
                    {title: '更新时间', field: 'fUpdatedate'},
                    {title: '更新地址', field: 'fDownloadpath'},
                   {
                        title: "操作", field: "fId",
                        formatter: function (value, row) {
                            var e = '<@shiro.hasPermission name="funcSystem-btnEditVersion"><a class="btn btn-success btn-xs m-r-xs" onclick="edit(\'' + value + '\');return false;"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                            var d = '<@shiro.hasPermission name="funcSystem-btnDeleteVersion"><a class="btn btn-danger btn-xs m-r-xs" onclick="del(\'' + value + '\');return false;"><i class="fa fa-remove"></i>删除</a></@shiro.hasPermission>';
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
            lt.open("查看版本信息", prefix + "/view/" + id);
        }

        // 添加代码
        function add() {
            lt.open("添加版本信息", prefix + "/form/" + sysCode, {
                end: function () {
                    refreshTable();
                }
            });
        }

        // 修改的代码
        function edit(id) {
            lt.open("修改版本信息", prefix + "/form/" + sysCode + "/" + id, {
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