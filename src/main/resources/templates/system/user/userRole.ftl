<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="赋予用户角色" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <div class="row">
            <div class="col-sm-12">
                <input id="userId" type="hidden" value="${nowUser}"/>
                <ul id="roleList" class="list-group">
                    <#list  roleList as role >
                        <li class="list-group-item">
                            <#if userRoleIds?seq_contains("${role.id}")>
                                <input type="checkbox" value="${role.id}" class="i-checks" checked/><span class="m-l-xs">${role.name}</span>
                            <#else >
                                <input type="checkbox" value="${role.id}" class="i-checks"/><span class="m-l-xs">${role.name}</span>
                            </#if>
                        </li>
                    </#list>
                </ul>
                <div class="row text-center">
                    <button id="btnSubmit" type="submit" class="btn btn-success"><span class="fa fa-check"></span>提交</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<@mc.ltfooter type="view" >
    <script type="text/javascript">
        var userRolePrefix = '${rc.contextPath}/userRole';

        $(function () {
            //提交按钮的操作
            $("#btnSubmit").click(function () {
                var roleIds = [];
                $('#roleList input:checked').each(function (i, el) {
                    roleIds.push($(el).val());
                });

                var userId = $('#userId').val();
                $.post(userRolePrefix + '/saveUserRole', {'roleIds': roleIds.join(','), 'userId': userId}, function (res) {
                    if (res.code == 200) {
                        lt.alertSuccess(res.msg, function () {
                            lt.closeThis();
                        });
                    } else {
                        lt.alertWarning(res.msg);
                    }
                });
            });
        });
    </script>
</@mc.ltfooter>
</html>