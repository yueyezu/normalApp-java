$(function () {
    // 图片点击看大图的实现：
    // 方法一： 需要img标签，加样式： can-big
    // $.initCanBig();
    // 方法二：
    $('#userPhoto').click(function (e) {
        lt.openPhoto(e.target.src);
    });

    //菜单切换事件
    $('.menuShow ul li').click(function () {
        $('.menuShow ul li').each(function () {
            $(this).removeClass('liSelected');
        });
        $(this).addClass('liSelected');
    });

    $(".dropdown-menu li").click(function () {
        $('.menuShow ul li').each(function () {
            $(this).removeClass('liSelected');
        });
        $(this).parent().parent().addClass('liSelected');
    });

    // 退出登录操作
    $('#logout').click(function () {
        lt.confirm("确定要退出系统？", function () {
            $.post("/logout", function () {
                lt.cache.clear();
                lt.alertSuccess("注销成功！", function () {
                    window.location = '/index'
                });
            });
        });
    });
    $('.firstLevel').click(function () {
        $(this).find('ul').css('width', $(this).css('width'));
    });
});