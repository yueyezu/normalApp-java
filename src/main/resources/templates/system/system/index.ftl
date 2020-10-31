<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="系统管理"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
            	<@shiro.hasPermission name="funcSystem-btnQuery">
	                <form role="form" class="form-inline query-form">
	                    <div class="form-group">
	                        <label class="control-label">系统类型：</label>
	                        <select class="form-control" data-placeholder="系统类型" id="queryType">
	                            <option value="">全部</option>
	                        </select>
	                        <div class="input-group">
	                            <input type="text" class="form-control" id="queryKeyword" placeholder="系统名称"/>
	                            <span class="input-group-btn">
	                                <button type="button" class="btn btn-primary" style="margin-bottom:0;" onclick="refreshTable()"><i class="fa fa-search"></i>查询</button>
	                            </span>
	                        </div>
	                    </div>
	                </form>
                </@shiro.hasPermission>
                <@shiro.hasPermission name="funcSystem-btnAdd">
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
        var prefix = '${rc.contextPath}/system';
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
                uniqueId: "fId",                     //每一行的唯一标识，一般为主键列
                pagination: true,                    //是否显示分页（*）
                queryParams: function (params) {
                    params.keyword = $('#queryKeyword').val();
                    var type = $('#queryType ').val();
                    params.fType = type;
                    return params;
                },
                columns: [{field: 'ck', checkbox: true},
                    {
                        title: '系统编号', field: 'fCode',
                        formatter: function (value, row, index) {
                            return '<a onclick="view(\'' + row.fId + '\')">' + value + '</a>';
                        }
                    },
                    {title: '系统名称', field: 'fName'},
                    {
                        title: '系统类型', field: 'fType',
                        formatter: function (value, row, index) {
                            return top.lt.cache.dictItems["systemType"][value];
                        }
                    },
                    {title: '当前版本', field: 'fVersion'},
                    {title: '访问密码', field: 'fSecret'},
                    {title: '建设单位', field: 'fDevorg'},
                    {title: '建设时间', field: 'fDevtime'},
                    {
                        title: "操作", field: "fId",
                        formatter: function (value, row) {
                            var e = '<@shiro.hasPermission name="funcSystem-btnEdit"><a class="btn btn-success btn-xs m-r-xs" onclick="edit(\'' + value + '\');return false;"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                            var d = '<@shiro.hasPermission name="funcSystem-btnDelete"><a class="btn btn-danger btn-xs m-r-xs" onclick="del(\'' + value + '\');return false;"><i class="fa fa-remove"></i>删除</a></@shiro.hasPermission>';
                            var v = '<@shiro.hasPermission name="funcSystem-btnVersionManage"><a class="btn btn-info btn-xs m-r-xs" onclick="version(\'' + row.fCode + '\');return false;"><i class="fa fa-code-fork"></i>版本管理</a></@shiro.hasPermission>';
                            return e + d + v;
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
            lt.open("查看系统信息", prefix + "/view/" + id);
        }

        // 添加代码
        function add() {
            lt.open("添加系统信息", prefix + "/form", {
                end: function (index) {
                    refreshTable();
                }
            });
        }

        // 修改的代码
        function edit(id) {
            lt.open("修改系统信息", prefix + "/form/" + id, {
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

        //系统版本管理的操作
        function version(fCode) {
            lt.open("系统版本管理", "/systemVersion/index/" + fCode);
        }
    </script>
</@mc.ltfooter>
</body>
</html>