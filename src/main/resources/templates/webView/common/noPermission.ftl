<!doctype html>
<html lang="en">
<head>
    <@ltviewmc.ltheader title="暂无权限"/>
    <style type="text/css">

        .imgDiv{
            width: 100%;
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }
        .imgDiv img{
            width: 510px;
            height: 330px;
        }
        .p1{
            width: 100%;
            text-align: center;
            font-size: 22px;
            font-family: PingFang SC;
            font-weight: 500;
            color: #00478B;
            margin-top: 28px;
        }
        .p2{
            height: 26px;
            display: flex;
            justify-content: center;
            line-height:35px;
            font-size: 16px;
            font-family: PingFang SC;
            font-weight: 500;
            color: #00478B;
        }
        .p2 a{
            margin-left: 20px;
            width: 56px;
            height: 26px;
            font-size: 14px;
            background: #EBF4FF;
            border: 1px solid #00478B;
            opacity: 0.7;
            border-radius: 4px;
            font-weight: 500;
            font-family: PingFang SC;
            text-align: center;
            line-height: 26px;
            color: #00478B;
        }
    </style>
</head>
<body class="gray-bg top-navigation">
<@ltviewmc.ltmaster>
    <div class="container" style="height: 100%;background-color: #FFFFFF">
        <div class="row" style="min-height: 500px">
            <div class="imgDiv">
                <img  src="../../../static/img/jurisdiction.png">
            </div>

            <p class="p1">暂无访问权限</p>
            <p class="p2">
                <b id="second">5</b>秒后自动跳转首页... <a href="javascript:goBack();">返回 <i class="fa fa-angle-right" style="font-size: 18px" aria-hidden="true"></i></a>
            </p>

        </div>
    </div>
</@ltviewmc.ltmaster>

<@ltviewmc.ltfooter>
    <script type="text/javascript">
        var sec = document.getElementById("second");
        var i=5;
        var timer = setInterval(function(){
            i--;
            sec.innerHTML = i;
            if(i ==1){
                window.location.href="http://localhost:801/webView/index";
            }
        },1000)

        //通过window的location和history对象来控制网页的跳转。
        function goBack(){
            window.history.go(-1);
        }
    </script>
</@ltviewmc.ltfooter>
</body>
</html>
