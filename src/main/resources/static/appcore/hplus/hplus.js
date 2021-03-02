// 顶级命名空间下添加cache对象
var lt = window.lt || {};
lt.cache = (function ($) {
    var cache = {
        isInit: false,
        token: null,
        userId: '0',
        dictItems: {},
        dictItemArr: {}
    };
    // 初始化方法
    cache.init = function (token) {
        // 如果缓存信息存在了，则直接返回
        if (lt.cache.isInit) {
            return;
        }
        lt.cache.token = token;
        lt.cache.isInit = true;

        //初始化缓存信息
        $.ajax({
            url: "/data",
            type: 'get',
            async: false,
            headers: {auth: token},
            success: function (res) {
                if (res.code == 200) {
                    var data = res.data;
                    cache.userId = data.userId;
                    cache.dictItems = data.dictItems;
                    cache.dictItemArr = data.dictItemArr;
                }
            }
        });
    }

    cache.clear = function () {
        cache.isInit = false;
        cache.userId = '0';
        cache.dictItems = {};
        cache.dictItemArr = {};
        cache.token = null;
    }

    return cache;
})(jQuery);

$(function () {
    debugger
    var params = $.requestParams();
    // 初始化换成信息
    lt.cache.init(params.token);
    // 侧边栏高度
    var heightWithoutNavbar = $("body > #wrapper").height() - 61;
    $(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");

    //ios浏览器兼容性处理
    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        $('#content-main').css('overflow-y', 'auto');
    }

    // MetsiMenu
    $('#side-menu').metisMenu();

    // 右侧边栏使用slimscroll
    $('.sidebar-container').slimScroll({
        height: '100%',
        railOpacity: 0.4,
        wheelStep: 10
    });

    //固定菜单栏
    $('.sidebar-collapse').slimScroll({
        height: '100%',
        railOpacity: 0.9,
        alwaysVisible: false
    });

    // 打开右侧边栏
    $('.right-sidebar-toggle').click(function () {
        $('#right-sidebar').toggleClass('sidebar-open');
    });

    // 菜单切换
    $('.navbar-minimalize').click(function () {
        $("body").toggleClass("mini-navbar");
        SmoothlyMenu();
    });

    // 左侧菜单操作
    $('#side-menu>li').click(function () {
        if ($('body').hasClass('mini-navbar')) {
            NavToggle();
        }
    });
    $('#side-menu>li li a').click(function () {
        if ($(window).width() < 769) {
            NavToggle();
        }
    });

    // 左侧关闭按钮事件--以隐藏
    $('.nav-close').click(NavToggle);

    //侧边栏滚动
    $(window).scroll(function () {
        if ($(window).scrollTop() > 0 && !$('body').hasClass('fixed-nav')) {
            $('#right-sidebar').addClass('sidebar-top');
        } else {
            $('#right-sidebar').removeClass('sidebar-top');
        }
    });

    $(window).bind("load resize click scroll", function () {
        if (!$("body").hasClass('body-small')) {
            var heightWithoutNavbar = $("body > #wrapper").height() - 61;
            $(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");
        }
    });

    $(window).bind("load resize", function () {
        if ($(this).width() < 769) {
            $('body').addClass('mini-navbar');
            $('.navbar-static-side').fadeIn();
        }
    });
});

// 主题设置
$(function () {
    // 顶部菜单固定
    $('#fixednavbar').click(function () {
        if ($('#fixednavbar').is(':checked')) {
            $(".navbar-static-top").removeClass('navbar-static-top').addClass('navbar-fixed-top');
            $("body").removeClass('boxed-layout');
            $("body").addClass('fixed-nav');
            $('#boxedlayout').prop('checked', false);

            if (lt.localStorageSupport) {
                localStorage.setItem("boxedlayout", 'off');
            }

            if (lt.localStorageSupport) {
                localStorage.setItem("fixednavbar", 'on');
            }
        } else {
            $(".navbar-fixed-top").removeClass('navbar-fixed-top').addClass('navbar-static-top');
            $("body").removeClass('fixed-nav');

            if (lt.localStorageSupport) {
                localStorage.setItem("fixednavbar", 'off');
            }
        }
    });

    // 收起左侧菜单
    $('#collapsemenu').click(function () {
        if ($('#collapsemenu').is(':checked')) {
            $("body").addClass('mini-navbar');
            SmoothlyMenu();

            if (lt.localStorageSupport) {
                localStorage.setItem("collapse_menu", 'on');
            }
        } else {
            $("body").removeClass('mini-navbar');
            SmoothlyMenu();

            if (lt.localStorageSupport) {
                localStorage.setItem("collapse_menu", 'off');
            }
        }
    });

    // 固定宽度
    $('#boxedlayout').click(function () {
        if ($('#boxedlayout').is(':checked')) {
            $("body").addClass('boxed-layout');
            $('#fixednavbar').prop('checked', false);
            $(".navbar-fixed-top").removeClass('navbar-fixed-top').addClass('navbar-static-top');
            $("body").removeClass('fixed-nav');
            if (lt.localStorageSupport) {
                localStorage.setItem("fixednavbar", 'off');
            }

            if (lt.localStorageSupport) {
                localStorage.setItem("boxedlayout", 'on');
            }
        } else {
            $("body").removeClass('boxed-layout');

            if (lt.localStorageSupport) {
                localStorage.setItem("boxedlayout", 'off');
            }
        }
    });

    // 默认主题
    $('.s-skin-0').click(function () {
        $("body").removeClass("skin-1");
        $("body").removeClass("skin-2");
        $("body").removeClass("skin-3");
        return false;
    });

    // 蓝色主题
    $('.s-skin-1').click(function () {
        $("body").removeClass("skin-2");
        $("body").removeClass("skin-3");
        $("body").addClass("skin-1");
        return false;
    });

    // 黄色主题
    $('.s-skin-3').click(function () {
        $("body").removeClass("skin-1");
        $("body").removeClass("skin-2");
        $("body").addClass("skin-3");
        return false;
    });

    if (lt.localStorageSupport) {
        var collapse = localStorage.getItem("collapse_menu");
        var fixednavbar = localStorage.getItem("fixednavbar");
        var boxedlayout = localStorage.getItem("boxedlayout");

        if (collapse == 'on') {
            $('#collapsemenu').prop('checked', 'checked')
        }
        if (fixednavbar == 'on') {
            $('#fixednavbar').prop('checked', 'checked')
        }
        if (boxedlayout == 'on') {
            $('#boxedlayout').prop('checked', 'checked')
        }
    }

    if (lt.localStorageSupport) {
        var collapse = localStorage.getItem("collapse_menu");
        var fixednavbar = localStorage.getItem("fixednavbar");
        var boxedlayout = localStorage.getItem("boxedlayout");

        var body = $('body');

        if (collapse == 'on') {
            if (!body.hasClass('body-small')) {
                body.addClass('mini-navbar');
            }
        }

        if (fixednavbar == 'on') {
            $(".navbar-static-top").removeClass('navbar-static-top').addClass('navbar-fixed-top');
            body.addClass('fixed-nav');
        }

        if (boxedlayout == 'on') {
            body.addClass('boxed-layout');
        }
    }
});

function NavToggle() {
    $('.navbar-minimalize').trigger('click');
}

function SmoothlyMenu() {
    if (!$('body').hasClass('mini-navbar')) {
        $('#side-menu').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(500);
            }, 100);
    } else if ($('body').hasClass('fixed-sidebar')) {
        $('#side-menu').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(500);
            }, 300);
    } else {
        $('#side-menu').removeAttr('style');
    }
}

// 自定义添加的
$(function () {
    // 退出登录操作
    $('#btnLogout,#liLogout').click(function () {
        lt.confirm("确定要退出系统？", function () {
            $.post("/public/logoutShiro", function () {
                lt.cache.clear();
                lt.alertSuccess("注销成功！", function () {
                    window.location = '/index'
                });
            });
        });
    });
});
