<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" import="tree" title="字典管理"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
	<div class="row">
    <div class="col-sm-3">
        <div class="ibox">
            <div class="ibox-title" style="border-width:1px 0px 0;">字典信息</div>
            <div class="ibox-content">
                <div id="dictTree"></div>
            </div>
        </div>
    </div>
    <div class="col-sm-9">
        <div class="ibox">
            <div class="ibox-content">
            	<@shiro.hasPermission name="funcDictItem-btnQuery">
	                <form role="form" class="form-inline query-form">
	                    <div class="input-group">
	                        <input type="text" class="form-control" id="queryKeyword" placeholder="编号或名称"/>
	                        <span class="input-group-btn">
		                        <button type="button" class="btn btn-primary" onclick="refreshTable()"><i class="fa fa-search"></i>查询</button>
		                    </span>
	                    </div>
	                </form>
                </@shiro.hasPermission>
                <div id="tableToolbar" role="group" class="t-bar">
                	<@shiro.hasPermission name="funcDictItem-dictSet">
	                    <a class="btn btn-primary" title="字典设置" onclick="set()">
	                        <i class="fa fa-book" aria-hidden="true"></i>字典设置
	                    </a>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="funcDictItem-btnAdd">
	                    <a class="btn btn-primary" title="添加" onclick="add()">
	                        <i class="fa fa-plus" aria-hidden="true"></i>添加
	                    </a>
                    </@shiro.hasPermission>
                </div>
                <table id="dataTable" data-mobile-responsive="true"></table>
            </div>
        </div>
    </div>
</div>


<@mc.ltfooter  type="list" import="tree">
    <script type="text/javascript">
        // 本功能所有请求的根路径
        var prefix = '${rc.contextPath}/dictItem';
        var nowDict = null;
        $(function () {
            initTree();
            initTable();

        });

        function initTree() {
            $.get("${rc.contextPath}/dict/tree", function (data) {
                $('#dictTree').on('select_node.jstree', function (e, data) {    // 点击切换事件
                    if (nowDict != data.selected[0]) {
                        nowDict = data.selected[0];
                        refreshTable();
                    }
                }).on('ready.jstree', function (e, data) {
                    // 加载完成后，将所有的树展开
                    $('#dictTree').jstree(true).open_all();
                    // 默认选中第一项
                    var inst = data.instance;
                    var obj = inst.get_node(e.target.firstChild.firstChild.lastChild);
                    inst.select_node(obj);
                }).jstree({     // 初始化
                    'plugins': ["search"],
                    'core': {	//core主要功能是控制树的形状，单选多选等等
                        data: data
                    }
                });
            });
        }

        function initTable() {
            lt.dataTable.init("#dataTable", {
                url: prefix + '/list',
                showExport: true,
                exportDataType: 'all',               //导出checkbox选中的行数: all\selected\
                toolbar: '#tableToolbar',            //工具按钮用哪个容器
                uniqueId: "fId",                     //每一行的唯一标识，一般为主键列
                pagination: false,
                queryParams: function (params) {
                    if (!nowDict) return false; // 当字典未选择时，不进行查询操作
                    params.keyword = $('#queryKeyword').val();
                    params.fDictid = nowDict;
                    return params;
                },
                columns: [{field: 'ck', checkbox: true},
                    {
                        title: '名称', field: 'fName',
                        formatter: function (value, row, index) {
                            return '<a href="javascript:view(\'' + row.fId + '\')" >' + value + '</a>';
                        }
                    },
                    {title: '编号', field: 'fCode', width: 140, align: 'center'},
                    {title: '排序码', field: 'fSortnum', width: 100, align: 'center'},
                    {
                        title: "操作", field: "fId",
                        formatter: function (value, row) {
                            var e = '<@shiro.hasPermission name="funcDictItem-btnEdit"><a class="btn btn-success btn-xs m-r-xs" onclick="edit(\'' + value + '\');return false;"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                            var d = '<@shiro.hasPermission name="funcDictItem-btnDelete"><a class="btn btn-danger btn-xs m-r-xs" onclick="del(\'' + value + '\');return false;"><i class="fa fa-remove"></i>删除</a></@shiro.hasPermission>';
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

        //字典设置
        function set() {
            lt.open("字典设置", "/dict/index", {
                end: function (index) {
                    window.location.reload();
                    //$("#dictTree").load(location.href + "#dictTree")
                }
            });
        }

        // 详情查看的操作方法
        function view(id) {
            lt.open("查看字典值信息", prefix + "/view/" + id);
        }

        // 添加代码
        function add() {
            lt.open("添加字典值信息", prefix + "/form?fDictid=" + nowDict, {
                end: function (index) {
                    refreshTable();
                }
            });
        }

        // 修改的代码
        function edit(id) {
            lt.open("修改字典值信息", prefix + "/form/" + id, {
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
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/treeview/bootstrap-treeview.js"></script>
</@mc.ltfooter>
</body>
</html>