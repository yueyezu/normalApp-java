// 需要lt.util.js支持
// 需要jQuery.validator支持

var $parentNode = window.parent.document;
var $top = top.$;

function $childNode(name) {
    return window.frames[name]
}

// tooltips
$('.tooltip-demo').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
});

// 使用animation.css修改Bootstrap Modal
$('.modal').appendTo("body");

$("[data-toggle=popover]").popover();

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

//判断当前页面是否在iframe中
// if (top == this) {
//     var gohome = '<div class="gohome"><a class="animated bounceInUp" href="/index" title="返回首页"><i class="fa fa-home"></i></a></div>';
//     $('body').append(gohome);
// }

//animation.css
function animationHover(element, animation) {
    element = $(element);
    element.hover(function () {
        element.addClass('animated ' + animation);
    }, function () {
        //动画完成之前移除class
        window.setTimeout(function () {
            element.removeClass('animated ' + animation);
        }, 2000);
    });
}

//拖动面板
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
        handle: handle,
        connectWith: connect,
        tolerance: 'pointer',
        forcePlaceholderSize: true,
        opacity: 0.8,
    }).disableSelection();
}

// jquery ajax的默认处理方式
$.ajaxSetup({
    cache: false,
    dataType: 'json',
    error: function (jqXHR, textStatus, errorThrown) {
        var errorMsg = "未知错误,请联系管理员";
        var codeMsg = {
            500: "服务器系统内部错误",
            401: "未登录",
            403: "无权限执行此操作",
            408: "请求超时"
        };
        lt.alertError(codeMsg[jqXHR.status] || '未知错误,请联系管理员');
    }
});

//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
if (jQuery.validator) {
    jQuery.validator.setDefaults({
        highlight: function (element) {
            $(element).closest('.validationArea').removeClass('has-success').addClass('has-error');
        },
        success: function (element) {
            element.closest('.validationArea').removeClass('has-error').addClass('has-success');
        },
        errorElement: "span",
        errorPlacement: function (error, element) {
            if (element.is(":radio") || element.is(":checkbox")) {
                error.appendTo(element.parent().parent().parent());
            } else {
                error.appendTo(element.parent());
            }
        },
        errorClass: "help-block m-b-none",
        validClass: "help-block m-b-none"
    });

    // jquery Validation 验证方法的扩展。
    // 联系电话(手机/电话皆可)验证
    jQuery.validator.addMethod("phone", function (value, element) {
        var length = value.length;
        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
        return this.optional(element) || tel.test(value) || (length == 11 && mobile.test(value));
    }, "电话号码格式错误");
}
