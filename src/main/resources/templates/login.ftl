<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->
    <title>登录</title>

    <link rel="shortcut icon" href="${rc.contextPath}/static/img/favicon.ico">

    <link href="${rc.contextPath}/static/appcore/bootstrap/bootstrap.min.css?v=3.3.7" rel="stylesheet">
    <link href="${rc.contextPath}/static/css/font-awesome.css?v=4.7.0" rel="stylesheet">
    <link href="${rc.contextPath}/static/css/animate.css" rel="stylesheet">
    <link href="${rc.contextPath}/static/appcore/hplus/style.css" rel="stylesheet">
    <link href="${rc.contextPath}/static/css/login.css" rel="stylesheet">

    <script>
        if (window.top !== window.self) {
            window.top.location = window.location;
        }
    </script>
</head>

<body class="signin">
<div class="signinpanel">
    <div class="row">
        <div class="col-sm-7">
            <div class="signin-info">
                <div class="logopanel m-b">
                    <h1>励图开发框架</h1>
                </div>
                <div class="m-b"></div>
                <h4>欢迎使用 </h4>
                <ul class="m-b">
                    <#-- 框架说明信息-->
                    <#-- <li><i class="fa fa-arrow-circle-o-right m-r-xs"></i> 优势一</li>-->
                </ul>
            </div>
        </div>
        <div class="col-sm-5">
            <form id="loginForm">
                <h4 class="no-margins text-center">登录</h4>
                <p class="m-t-md">欢迎登录励图开发框架</p>
                <input type="text" id="account" name="account" class="form-control uname" required="required" placeholder="用户名"/>
                <input type="password" id="password" name="password" class="form-control pword m-b" required="required" placeholder="密码"/>
                <div class="row">
                    <div class="col-xs-5 form-group">
                        <input type="text" id="verifyCode" name="verifyCode" class="form-control verify" placeholder="验证码"/>
                    </div>
                    <div class="col-xs-7">
                        <img id="verifyCodeImg" title="点击切换" style="height: 32px;width:110px;margin-top: 15px;" src="/public/vcode"/>
                    </div>
                </div>
                <a id="btnlogin" class="btn btn-success btn-block">登录</a>
            </form>
        </div>
    </div>
    <div class="signup-footer">
        <div class="pull-left">
            科技大学 &copy; 2015 All Rights Reserved.
        </div>
    </div>
</div>
<script type="text/javascript" src="${rc.contextPath}/static/js/jquery/jquery.min.js?v=2.1.4"></script>
<script type="text/javascript" src="${rc.contextPath}/static/appcore/bootstrap/bootstrap.min.js?v=3.3.7"></script>

<script type="text/javascript" src="${rc.contextPath}/static/js/jquery/jquery.md5.js"></script>
<#--jquery校验-->
<script type="text/javascript" src="${rc.contextPath}/static/js/jquery/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/static/js/jquery/validate/messages_zh.min.js"></script>

<script type="text/javascript" src="${rc.contextPath}/static/js/layer/layer.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/static/js/lt.util.js"></script>

<script type="text/javascript">
    // 判断是否在iframe中,在进行未登录验证，跳转时使用到，防止跳转到iframe界面中。
    if (self != top) {
        parent.window.location.replace(window.location.href);
    }

    $(function () {
        $('#loginForm').validate({
            submitHandler: function () {
                login();
            }
        });

        // 切换验证码操作。 其中支持一个切换验证码的按钮
        $('#verifyCodeImg').click(function (event) {
            $('#verifyCodeImg').attr('src', '${rc.contextPath}/public/vcode');
        });

        // 登录按钮的操作
        $("#btnlogin").click(function (event) {
            $('#loginForm').submit();
        });

        // 录入框的回车按钮的事件，直接登录系统
        $("#account,#password,#verifyCode").keyup(function (event) {
            if (event.keyCode == 13) {
                $('#loginForm').submit();
            }
        });

        // 登录验证的方法，在进行强制登录时，需要通过data来传输标志值
        function login(data) {
            //TODO  录入信息验证
            var param = $('#loginForm').serializeJson();
            param.password = $.md5(param.password);

            lt.loadShow(1);
            $.post("${rc.contextPath}/public/login", param, function (res) {
                if (res.code == 200) {
                    window.location.href = '${rc.contextPath}/index';
                } else {
                    lt.msgError(res.msg);
                }
                lt.loadClose();
            });
        }
    });
</script>
</body>

</html>
