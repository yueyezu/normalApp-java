/*
 *
 *   H+ - 后台主题UI框架
 *   version 4.5
 *
 */
$(function () {
    // 通过遍历给菜单项加上data-index属性
    $(".J_menuItem").each(function (index) {
        if (!$(this).attr('data-index')) {
            $(this).attr('data-index', index);
        }
    });

    $('.J_menuItem').on('click', menuItem);

    // 关闭标签
    $('.J_menuTabs').on('click', '.J_menuTab i', closeTab);
    // 打开到标签
    $('.J_menuTabs').on('click', '.J_menuTab', activeTab);
    // 刷新标签页
    $('.J_menuTabs').on('dblclick', '.J_menuTab', refreshTab);
    // 左移按扭
    $('.J_tabLeft').on('click', scrollTabLeft);
    // 右移按扭
    $('.J_tabRight').on('click', scrollTabRight);
    // 刷新当前界面
    $('.J_tabRefresh').on('click', refreshTab);
    // 关闭其他
    $('.J_tabCloseOther').on('click', closeOtherTabs);
    // 定位到标签
    $('.J_tabShowActive').on('click', showActiveTab);
    // 关闭全部
    $('.J_tabCloseAll').on('click', closeAll);
});

// 计算元素集合的总宽度
function calSumWidth(elements) {
    var width = 0;
    $(elements).each(function () {
        width += $(this).outerWidth(true);
    });
    return width;
}

// 滚动到指定选项卡
function scrollToTab(element) {
    var marginLeftVal = calSumWidth($(element).prevAll()), marginRightVal = calSumWidth($(element).nextAll());
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    // 可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    // 实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").outerWidth() < visibleWidth) {
        scrollVal = 0;
    } else if (marginRightVal <= (visibleWidth
        - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
        if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
            scrollVal = marginLeftVal;
            var tabElement = element;
            while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                scrollVal -= $(tabElement).prev().outerWidth();
                tabElement = $(tabElement).prev();
            }
        }
    } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
        scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
    }
    $('.page-tabs-content').animate({
        marginLeft: 0 - scrollVal + 'px'
    }, "fast");
}

// 查看左侧隐藏的选项卡
function scrollTabLeft() {
    var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    // 可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    // 实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").width() < visibleWidth) {
        return false;
    } else {
        var tabElement = $(".J_menuTab:first");
        var offsetVal = 0;
        while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) { // 找到离当前tab最近的元素
            offsetVal += $(tabElement).outerWidth(true);
            tabElement = $(tabElement).next();
        }
        offsetVal = 0;
        if (calSumWidth($(tabElement).prevAll()) > visibleWidth) {
            while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).prev();
            }
            scrollVal = calSumWidth($(tabElement).prevAll());
        }
    }
    $('.page-tabs-content').animate({
        marginLeft: 0 - scrollVal + 'px'
    }, "fast");
}

// 查看右侧隐藏的选项卡
function scrollTabRight() {
    var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    // 可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    // 实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").width() < visibleWidth) {
        return false;
    } else {
        var tabElement = $(".J_menuTab:first");
        var offsetVal = 0;
        while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) { // 找到离当前tab最近的元素
            offsetVal += $(tabElement).outerWidth(true);
            tabElement = $(tabElement).next();
        }
        offsetVal = 0;
        while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
            offsetVal += $(tabElement).outerWidth(true);
            tabElement = $(tabElement).next();
        }
        scrollVal = calSumWidth($(tabElement).prevAll());
        if (scrollVal > 0) {
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        }
    }
}

// 关闭选项卡菜单
function closeTab() {
    var closeTabId = $(this).parents('.J_menuTab').data('id');
    var currentWidth = $(this).parents('.J_menuTab').width();

    // 当前元素处于活动状态
    if ($(this).parents('.J_menuTab').hasClass('active')) {
        // 当前元素后面有同辈元素，使后面的一个元素处于活动状态
        if ($(this).parents('.J_menuTab').next('.J_menuTab').length) {

            var activeId = $(this).parents('.J_menuTab').next('.J_menuTab:eq(0)').data('id');
            $(this).parents('.J_menuTab').next('.J_menuTab:eq(0)').addClass('active');

            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == activeId) {
                    $(this).show().siblings('.J_iframe').hide();
                    return false;
                }
            });

            var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
            if (marginLeftVal < 0) {
                $('.page-tabs-content').animate({
                    marginLeft: (marginLeftVal + currentWidth) + 'px'
                }, "fast");
            }

            // 移除当前选项卡
            $(this).parents('.J_menuTab').remove();

            // 移除tab对应的内容区
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }

        // 当前元素后面没有同辈元素，使当前元素的上一个元素处于活动状态
        if ($(this).parents('.J_menuTab').prev('.J_menuTab').length) {
            var activeId = $(this).parents('.J_menuTab').prev('.J_menuTab:last').data('id');
            $(this).parents('.J_menuTab').prev('.J_menuTab:last').addClass('active');
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == activeId) {
                    $(this).show().siblings('.J_iframe').hide();
                    return false;
                }
            });

            // 移除当前选项卡
            $(this).parents('.J_menuTab').remove();

            // 移除tab对应的内容区
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }
    }
    // 当前元素不处于活动状态
    else {
        // 移除当前选项卡
        $(this).parents('.J_menuTab').remove();

        // 移除相应tab对应的内容区
        $('.J_mainContent .J_iframe').each(function () {
            if ($(this).data('id') == closeTabId) {
                $(this).remove();
                return false;
            }
        });
        scrollToTab($('.J_menuTab.active'));
    }
    return false;
}

// 关闭其他选项卡
function closeOtherTabs() {
    $('.page-tabs-content').children("[data-id]").not(":first").not(".active").each(function () {
        $('.J_iframe[data-id="' + $(this).data('id') + '"]').remove();
        $(this).remove();
    });
    $('.page-tabs-content').css("margin-left", "0");
}

// 滚动到已激活的选项卡
function showActiveTab() {
    scrollToTab($('.J_menuTab.active'));
}

// 点击选项卡菜单
function activeTab() {
    if (!$(this).hasClass('active')) {
        var currentId = $(this).data('id');
        // 显示tab对应的内容区
        $('.J_mainContent .J_iframe').each(function () {
            if ($(this).data('id') == currentId) {
                $(this).show().siblings('.J_iframe').hide();
                return false;
            }
        });
        $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
        scrollToTab(this);
    }
}

// 刷新iframe
function refreshTab() {
    var dataId = $(this).data('id');
    var activeTab = null;
    if (dataId) {   //适应时双击时操作还是点击按钮时操作。
        activeTab = $('.J_mainContent').find('.J_iframe[data-id="' + dataId + '"]');
    } else {
        activeTab = $('.J_mainContent').find('iframe.J_iframe:visible');
    }
    if (activeTab.length > 0) {
        var url = activeTab.attr('src');
        activeTab.first().attr('src', url);
    }
}

// 关闭所有标签页
function closeAll() {
    $('.page-tabs-content').children("[data-id]").not(":first").each(function () {
        $('.J_iframe[data-id="' + $(this).data('id') + '"]').remove();
        $(this).remove();
    });
    $('.page-tabs-content').children("[data-id]:first").each(function () {
        $('.J_iframe[data-id="' + $(this).data('id') + '"]').show();
        $(this).addClass("active");
    });
    $('.page-tabs-content').css("margin-left", "0");
}

// 点击菜单打开新的tab页的操作，当前标签必须要有： href、data-index、data-id文本等熟悉。
function menuItem() {
    $('.dropdown').removeClass('open');

    // 获取标识数据
    var dataUrl = $(this).attr('href'),
        dataNeedToken = $(this).attr('needToken'),  // true，false
        dataIndex = $(this).data('index'),
        menuName = $.trim($(this).text()),
        flag = true;

    if (dataUrl == undefined || $.trim(dataUrl).length == 0)
        return false;

    // 选项卡菜单已存在
    $('.J_menuTab').each(function () {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                scrollToTab(this);
                // 显示tab对应的内容区
                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.J_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });

    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.J_menuTab').removeClass('active');


        // 添加选项卡对应的iframe
        var frameUrl = dataUrl;
        if (dataNeedToken) {
            frameUrl += "?token=" + lt.cache.token;
        }
        var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + frameUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
        $('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);

        // 显示loading提示
        // var loading = layer.load();
        //
        // $('.J_mainContent iframe:visible').load(function () {
        // //iframe加载完成后隐藏loading提示
        // layer.close(loading);
        // });
        // 添加选项卡
        $('.J_menuTabs .page-tabs-content').append(str);
        scrollToTab($('.J_menuTab.active'));
    }
    return false;
}



