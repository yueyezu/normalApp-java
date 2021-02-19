<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" import="tree" title="系统管理"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-title" style="border-width:1px 0 0;">部门信息</div>
                <div class="ibox-content">
                    <div id="deptTree"></div>
                </div>
            </div>
        </div>
        <div class="col-sm-9">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <@shiro.hasPermission name="funcUser-btnQuery">
                        <form role="form" class="form-inline query-form">
                            <div class="input-group">
                                <input type="text" class="form-control" id="queryKeyword" placeholder="账号、姓名"/>
                                <span class="input-group-btn">
			                        <button type="button" class="btn btn-primary" onclick="refreshTable()"><i class="fa fa-search"></i>查询</button>
			                    </span>
                            </div>
                        </form>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="funcUser-btnAdd">
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
</div>
<@mc.ltfooter  type="list" import="tree">
    <script type="text/javascript">
        // 本功能所有请求的根路径
        var prefix = '${rc.contextPath}/user';
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
                url: prefix + '/page',
                toolbar: '#tableToolbar',            //工具按钮用哪个容器
                queryParams: function (params) {
                    if (!nowDept) return false; // 当部门未选择时，不进行查询操作

                    params.keyword = $('#queryKeyword').val();
                    params.deptId = nowDept;

                    return params;
                },
                columns: [{field: 'ck', checkbox: true},
                    {
                        title: '帐号', field: 'account',
                        formatter: function (value, row, index) {
                            return '<a href="javascript:view(\'' + row.id + '\')" >' + value + '</a>';
                        }
                    },
                    {title: '姓名', field: 'realName'},
                    {title: '呢称', field: 'nickName'},
                    {
                        title: '性别', field: 'sex', align: 'center',
                        formatter: function (value, row, index) {
                            //TODO 性别改到字典表中
                            return value == 2 ? "女" : "男";
                        }
                    },
                    {title: '联系方式', field: 'phone'},
                    // {title: '岗位', field: 'roleId'},
                    {
                        title: "操作", field: "id",
                        formatter: function (value, row) {
                            var e = '<@shiro.hasPermission name="funcUser-btnEdit"><a class="btn btn-success btn-xs m-r-xs" onclick="edit(\'' + value + '\');return false;"><i class="fa fa-edit"></i>编辑</a></@shiro.hasPermission>';
                            var d = '<@shiro.hasPermission name="funcUser-btnDelete"><a class="btn btn-danger btn-xs m-r-xs" onclick="del(\'' + value + '\');return false;"><i class="fa fa-remove"></i>删除</a></@shiro.hasPermission>';
                            var r = '<@shiro.hasPermission name="funcUser-btnUserRole"><a class="btn btn-info btn-xs m-r-xs" onclick="role(\'' + value + '\');return false;"><i class="fa fa-user-secret"></i>赋予角色</a></@shiro.hasPermission>';
                            var rs = '<@shiro.hasPermission name="funcUser-btnReset"><a class="btn btn-info btn-xs m-r-xs" onclick="reset(\'' + value + '\');return false;"><i class="fa fa-refresh"></i>重置密码</a></@shiro.hasPermission>';
                            return e + d + r + rs;
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
            lt.open("查看用户信息", prefix + "/view/" + id);
        }

        // 添加代码
        function add() {
            if (!nowDept) {
                lt.alertWarning("请选择要添加用户的部门！")
            }
            lt.open("添加用户信息", prefix + "/form?deptId=" + nowDept, {
                end: function (index) {
                    refreshTable();
                }
            });
        }

        // 修改的代码
        function edit(id) {
            lt.open("修改用户信息", prefix + "/form/" + id, {
                end: function () {
                    refreshTable();
                }
            });
        }

        // 删除的代码
        function del(id) {
            lt.confirm("确定要删除选中数据？", function () {
                $.post(prefix + '/logicalDelete', {id: id}, function (res) {
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

        //重置密码
        function reset(id) {
            lt.confirm("确定要重置选择项的密码信息吗？", function () {
                $.post(prefix + '/resetPwd', {userId: id}, function (res) {
                    if (res.code == 200) {
                        lt.alertSuccess(res.msg);
                    } else {
                        lt.alertError(res.msg);
                    }
                });
            });
        }

        //赋予角色的操作
        function role(userId) {
            lt.open("赋予用户角色", prefix + "/userRole?userId=" + userId, {width: '400px', height: '400px'});
        }
    </script>
</@mc.ltfooter>
</body>
</html>