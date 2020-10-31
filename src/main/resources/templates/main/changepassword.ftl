<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader title="修改密码" type="form" ></@mc.ltheader>
</head>
<body class="bg-gray">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <div class="col-sm-6">
            <form class="form-horizontal m-t">
                <div class="form-group">
                    <label class="col-sm-3 control-label">旧密码:</label>
                    <div class="col-sm-8">
                        <input id="oldPwd" name="oldPwd" type="password" class="form-control required"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">新密码:</label>
                    <div class="col-sm-8">
                        <input id="newPwd" name="newPwd" type="password" class="form-control required"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">确认密码:</label>
                    <div class="col-sm-8">
                        <input id="confirmPwd" name="confirmPwd" type="password" class="form-control required"/>
                    </div>
                </div>
            </form>
            <div class="row text-center">
                <a onclick="submit()" class="btn btn-primary"><span class="fa fa-check"></span>提交</a>
            </div>
        </div>
    </div>
</div>

<@mc.ltfooter type="form" import="md5">
    <script type="text/javascript">
        function submit() {
            var oldPwd = $('#oldPwd').val().trim();
            var newPwd = $('#newPwd').val().trim();
            var confirmPwd = $('#confirmPwd').val().trim();

            if (oldPwd == "" || newPwd == "" || confirmPwd == "") {
                lt.alertWarning("密码不能为空！")
                return;
            }
            if (newPwd != confirmPwd) {
                lt.alertWarning("两次输入的密码不一致！")
                return;
            }
            lt.confirm("确定修改密码吗？", function () {
                $.post("${rc.contextPath}/user/modifyPwd", {
                    oldPwd: $.md5(oldPwd),
                    newPwd: $.md5(confirmPwd)
                }, function (res) {
                    if (res.code == 200) {
                        lt.msgSuccess(res.msg);
                    } else {
                        lt.alertError(res.msg)
                    }
                });
            });
        }
    </script>
</@mc.ltfooter>
</body>
</html>

