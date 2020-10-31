<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="系统配置"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
            	<@shiro.hasPermission name="funSet-btnQuery">
	                <form role="form" class="form-inline query-form">
	                    <div class="input-group">
	                        <input type="text" class="form-control" id="queryKeyword" placeholder="关键词"/>
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
        var prefix = '${rc.contextPath}/config';
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
                uniqueId: "fId",                     //每一行的唯一标识，一般为主键列
                pagination: false,                    //是否显示分页（*）
                queryParams: function (params) {
                    params.keyword = $('#queryKeyword').val();
                    return params;
                },
                columns: [{field: 'ck', checkbox: true},
                    {title: '属性', field: 'fName'},
                    {title: '配置分组', field: 'fGroup'},
                    {title: '属性值', field: 'fValue'},
                    {title: '属性值类型', field: 'fValuetype'},
                    {title: '描述', field: 'fDescription'},
                    {title: '备注', field: 'fRemark'},
                    {title: 'Key', field: 'fKey'},
                    {title: '最后修改人', field: 'fLastmodifyuserid'},
                    {title: '最后修改时间', field: 'fLastmodifytime'},
                    {title: '排序码', field: 'fSortnum'},
                    {
                        title: "操作", field: "fId",
                        formatter: function (value, row) {
                            var e = '<@shiro.hasPermission name="funSet-btnSubmit"><a class="btn btn-success btn-xs m-r-xs" onclick="edit(\'' + value + '\');return false;"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                            return e ;
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
            lt.open("查看配置信息", prefix + "/view/" + id);
        }

        // 修改的代码
        function edit(id) {
            lt.open("修改配置信息", prefix + "/form/" + id, {
                end: function () {
                    refreshTable();
                }
            });
        }
    </script>
</@mc.ltfooter>
</body>
</html>