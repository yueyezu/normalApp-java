<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" import="tree"  title="用户信息选择" />
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-title h5">部门信息</div>
                <div class="ibox-content">
                    <div id="deptTree"></div>
                </div>
            </div>
        </div>
        <div class="col-sm-9">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <table id="dataTable" data-mobile-responsive="true"></table>
                </div>
            </div>
        </div>
    </div>
    <!--TODO 后续完善，存放已选择的内容。-->
    <#--    <div class="row">-->
    <#--    </div>-->
</div>
<@mc.ltfooter  type="list" import="tree">
    <script type="text/javascript">
        // 本功能所有请求的根路径
        var prefix = '${rc.contextPath}/user';
        var selectType = '${selectType}';

        var nowDept = null;
        $(function () {
            initTree();
            initTable();
        });

        function initTree() {
            $.get("${rc.contextPath}/organize/tree", function (data) {
                lt.tree.init("#deptTree", {
                    multiple: false,
                    data: data,
                    openAll: true, // 默认展开全部
                    ready: function (e, data) {    // 默认选中第一项
                        var inst = data.instance;
                        var obj = inst.get_node(e.target.firstChild.firstChild.lastChild);
                        inst.select_node(obj);
                    },
                    click: function (e, data) {
                        if (nowDept != data.selected[0]) {
                            nowDept = data.selected[0];
                            refreshTable();
                        }
                    }
                });
            });
        }

        // 初始化查询列表
        function initTable() {
            lt.dataTable.init("#dataTable", {
                url: prefix + '/list',
                // search: true,
                showToggle: false,
                showExport: false,
                showColumns: false,
                sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                pagination: false,                   //是否显示分页（*）
                singleSelect: selectType == 'single',
                queryParams: function (params) {
                    if (!nowDept) return false; // 当部门未选择时，不进行查询操作
                    params.deptId = nowDept;
                    return params;
                },
                columns: [{field: 'ck', checkbox: true},
                    {title: '帐号', field: 'account'},
                    {title: '姓名', field: 'realName'},
                    {
                        title: '性别', field: 'sex', align: 'center',
                        formatter: function (value, row, index) {
                            return value == 0 ? "女" : "男";
                        }
                    },
                    {title: '联系方式', field: 'phone'}
                ]
            });
        }

        // 刷新查询列表
        function refreshTable() {
            $('#dataTable').bootstrapTable("refresh");
        }

        //关闭时，供外部调用的方法
        function getSelected() {
            var sel = $('#dataTable').bootstrapTable('getSelections');
            return sel;
        }
    </script>
</@mc.ltfooter>
</body>
</html>