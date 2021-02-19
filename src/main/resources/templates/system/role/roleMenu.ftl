<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" import="tree" title="赋予用户角色" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <div class="tabs-container">
            <div class="tabs-left">
                <!--系统列表显示位置-->
                <ul id="systemPnl" class="nav nav-tabs m-b-sm">
                    <#list systemList as system>
                        <li <#if system_index ==0>class="active"</#if>><a code="${system.code}" href="javascript:void(0)">${system.name}</a></li>
                    </#list>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active">
                        <div class="panel-body">
                            <div style="height: 400px;overflow-y: auto;">
                                <div id="menuTree">
                                    <div class="text-center m-t-lg m-b-lg">当前系统还未维护权限信息</div>
                                </div>
                            </div>
                            <div class="row text-center">
                                <button id="btnSubmit" type="button" onclick="submit()" class="btn btn-success" style="display: none;"><span class="fa fa-check"></span>提交</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<@mc.ltfooter type="view" import="tree">
    <script type="text/javascript">
        var prefix = '${rc.contextPath}/role';
        var menuPrefix = '${rc.contextPath}/menu'
        var nowSystem = '';
        var roleId = '${roleId}';
        $(function () {
            nowSystem = $('#systemPnl li.active a').attr('code');   // 初始化当前系统的值

            initTree();

            // 系统选择
            $('#systemPnl a').click(function (e) {
                e.preventDefault()
                var $this = $(this);
                var clickSystem = $this.attr('code');
                if (clickSystem != nowSystem) {
                    $(this).tab('show');
                    nowSystem = clickSystem;
                }
                initTree();
            });
        });

        function initTree() {
            $.get(menuPrefix + "/tree", {'systemCode': nowSystem}, function (data) {
                lt.tree.destroy();
                $('#menuTree').empty();
                if (data.length > 0) {
                    lt.tree.init("#menuTree", {
                        checkbox: true, data: data,
                        ready: function (e, data) {
                            // 初始化角色选中的菜单信息
                            $.get(prefix + "/roleMenuIds", {roleId: roleId}, function (res) {
                                if (res.code == 200) {
                                    // 获取树
                                    var menuTrees = $('#menuTree').jstree(true);
                                    // 清空级联关系
                                    var oldCascade = menuTrees.settings.checkbox.cascade;
                                    menuTrees.settings.checkbox.cascade = '';
                                    // 重置级联关系
                                    setTimeout(function () {
                                        menuTrees.settings.checkbox.cascade = oldCascade;
                                    }, 500);
                                    lt.tree.initSelect("#menuTree", res.data);
                                }
                                $('#btnSubmit').show();
                            });
                        }
                    });
                } else {
                    var emptyHtml = '<div class="text-center m-t-lg m-b-lg">当前系统还未维护权限信息</div>';
                    $('#menuTree').html(emptyHtml);
                    $('#btnSubmit').hide();
                }
            });
        }

        // 系统提交的方法
        function submit() {
            var sels = lt.tree.getSelects("#menuTree");
            $.each($("#menuTree").jstree("get_undetermined"), function (index, item) {//获取所有半选节点
                sels.push(item);
            });
            $.post(prefix + "/saveRoleMenus", {roleId: roleId, menuIds: sels.join(',')}, function (res) {
                if (res.code == 200) {
                    lt.alertSuccess(res.msg, function () {
                        lt.closeThis();
                    });
                } else {
                    lt.alertWarning(res.msg);
                }
            });
        }

    </script>
</@mc.ltfooter>
</body>
</html>

