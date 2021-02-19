<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="授权列表"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <@shiro.hasPermission name="funcAccessToken-btnQuery">
                    <form role="form" class="form-inline query-form">
                        <div class="input-group">
                            <input type="text" class="form-control" id="queryKeyword" placeholder="请输入账号"/>
                            <span class="input-group-btn">
	                            <button type="button" class="btn btn-primary" onclick="refreshTable()"><i class="fa fa-search"></i>查询</button>
	                        </span>
                        </div>
                    </form>
                </@shiro.hasPermission>
                <table id="dataTable" data-mobile-responsive="true"></table>
            </div>
        </div>
    </div>
</div>
<@mc.ltfooter  type="list">
    <script type="text/javascript">
        // 本功能所有请求的根路径
        var prefix = '${rc.contextPath}/accessToken';
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
                url: prefix + "/page",
                showExport: true,
                exportDataType: 'all',               //导出checkbox选中的行数: all\selected\
                toolbar: '#tableToolbar',            //工具按钮用哪个容器
                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                pagination: true,                    //是否显示分页（*）
                queryParams: function (params) {
                    params.keyword = $('#queryKeyword').val();
                    return params;
                },
                columns: [{field: 'ck', checkbox: true},
                    {
                        title: '账号', field: 'userId',
                        formatter: function (value, row, index) {
                            return '<a onclick="view(\'' + row.id + '\')">' + value + '</a>';
                        }
                    },
                    {title: '客户端机器码', field: 'clientMcode'},
                    {title: '客户端IP', field: 'clientIp'},
                    {title: '客户端MAC', field: 'clientMac'},
                    {
                        title: '有效标志', field: 'enableFlag',
                        formatter: function (value, row, index) {
                            return '<@shiro.hasPermission name="funcAccessToken-btnDisable"><a href="javascript:enableFlag(\'' + row.id + '\',' + value + ')" >' + (value == 1 ? '<i id="btnDisable" class="fa fa-toggle-on"></i>' : '<i class="fa fa-toggle-off"></i>') + '</a></@shiro.hasPermission>';
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
            lt.open("查看授权详细信息", prefix + "/view/" + id);
        }

        function enableFlag(id, value) {
            debugger
            if (value == 1) {
                lt.confirm("确定要禁用选中数据？", function () {
                    $.post(prefix + '/disable', {id: id}, function (res) {
                        if (res.code == 200) {
                            lt.alertSuccess("禁用成功！");
                            refreshTable();
                        }
                    });
                });
            }
            if (value == 0) {
                lt.confirm("确定要启动选中数据？", function () {
                    $.post(prefix + '/enable', {id: id}, function (res) {
                        if (res.code == 200) {
                            lt.alertSuccess("启用成功！");
                            refreshTable();
                        }
                    });
                });
            }
        }
    </script>
</@mc.ltfooter>
</body>
</html>