// layer组件版本老了些，换成新的，加上开始最大化的选项
var lt = window.lt || {};
lt = {
    // layer提醒的封装
    alertSuccess: function (msg, fn) {
        if (typeof msg == 'function') {
            fn = msg;
            msg = "";
        }
        layer.alert(msg || "执行成功！", {icon: 1, shadeClose: true, title: "成功"}, function (index) {
            fn && fn();
            layer.close(index);
        });
    },
    msgSuccess: function (msg, fn) {
        if (typeof msg == 'function') {
            fn = msg;
            msg = "";
        }
        layer.msg(msg || "执行成功！", {icon: 1, time: 1000}, function (index) {
            fn && fn();
        });
    },
    alertWarning: function (msg) {
        layer.alert(msg || "警告！", {icon: 0, shadeClose: true, title: "警告"});
    },
    msgWarning: function (msg) {
        layer.msg(msg || "警告！", {icon: 0, time: 1000});
    },
    alertError: function (msg, fn) {
        if (typeof msg == 'function') {
            fn = msg;
            msg = "";
        }
        layer.alert(msg || "操作失败！", {icon: 2, shadeClose: true, title: "失败"}, function (index) {
            fn && fn();
            layer.close(index);
        });
    },
    msgError: function (msg) {
        layer.msg(msg || "执行失败！", {icon: 2, time: 1000});
    },
    confirm: function (msg, fn) {
        layer.confirm(msg || "确认？", {
            btn: ['确定', '取消'], //按钮
            skin: 'layer-ext-moon'
        }, function (index) {
            fn && fn();
            layer.close(index);
        });
    },
    // 加载过程的封装
    loadShow: function (type) {
        layer.load(type || 1);
    },
    loadClose: function () {
        layer.closeAll('loading');
    },
    // 打开窗口的方法封装--主要用于总删改查等操作界面
    open: function (title, content, option) {
        var defaults = {
            width: "800px",
            height: "90%", //($(window).height() - 50)
            maxmin: true, // 开启最大化最小化按钮
            anim: 1,    //动画方式
            btn: null, //['确定', '取消'],
            yes: null, // 点击确定按钮的操作
            end: null  // 关闭或者结束时的操作
        };
        option = $.extend({}, defaults, option);
        option.type = (typeof content == "string") ? 2 : 1;

        layer.open({
            type: option.type,
            title: title || "操作窗口",
            area: [option.width, option.height],
            shadeClose: true,
            shade: 0.2,
            btn: option.btn,
            maxmin: option.maxmin,
            anim: option.anim, // 0-6的动画形式，-1不开启
            fix: false, // 不固定
            scrollbar: false, // 屏蔽游览器滚动条
            content: content,
            yes: option.yes,
            end: option.end
        });
    },
    // 打开一个窗口，用于选择界面的处理
    // fn 为点击确定后执行的方法
    openSelect: function (title, url, fn, width, height) {
        var option = {
            type: 2,
            title: title || "选择窗口",
            area: [width || "500px", height || "80%"],
            anim: 1,    //动画方式
            shadeClose: true,
            shade: 0.2,
            btn: ['确定', '取消'],
            content: url,
            yes: function (index, layero) { // 点击确定按钮的操作
                var selWindow = window[layero.find('iframe')[0]['name']];
                fn && fn(selWindow);

                layer.close(index);
            }
        }
        layer.open(option);
    },
    // 打开大图预览界面
    // data有两种形式：字符串 或 多图片数组,例：[ "src": "","alt": "图片名"]
    openPhoto: function (title, data) {
        if (!data) {
            data = title;
            title = "图片查看";
        }

        var photoJson = {
            "title": title,
            "start": 0, //初始显示的图片序号，默认0
            "data": []   //相册包含的图片，数组格式
        };

        if (typeof data == "string") {
            photoJson.data.push({"src": data});
        } else {
            data.forEach(function (cv, index, arr) {
                cv.pid = index;
            });
            photoJson.data = data;
        }

        //调用示例
        layer.photos({
            photos: photoJson,
            anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
        });
    },
    // 根据弹出框的id进行关闭
    close: function (id) {
        layer.close(id);
    },
    // 在iframe窗口内部，关闭当前弹出框
    closeThis: function () {
        if (parent.layer) {
            var index = parent.layer.getFrameIndex(window.name);
            if (index)
                parent.layer.close(index)
        }
    }
};

// 判断浏览器是否支持html5本地存储
lt.localStorageSupport = (('localStorage' in window) && window['localStorage'] !== null);

// 表格处理的封装方法，在bootstrapTable基础上进行的封装
lt.dataTable = {
    init: function (selector, option) {
        var defaults = {
            url: "",
            toolbar: '#tableToolbar',                //工具按钮用哪个容器
            uniqueId: "fId",                     //每一行的唯一标识，一般为主键列
            //searchAlign: 'left',
            search: false,                      //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            showFullscreen: false,
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            showColumns: true,                  //是否显示所有的列
            showExport: true,
            singleSelect: false,
            exportDataType: 'all',               //导出checkbox选中的行数: all\selected\
            //classes: 'table-no-bordered',
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pagination: true,                   //是否显示分页（*）
            sortOrder: "asc",                   //排序方式
            //height: 680,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            detailView: false,                   //是否显示父子表
            queryParams: null,
            columns: [],
            // 树结构使用的参数
            idField: null,                     //行主键的字段信息，当进行treeGird格式展示时，该字段为必须
            treeShowField: null,
            parentIdField: null
        };
        // 本系统固定设置的参数
        var staticOption = {
            method: 'get',         //请求方式（*）
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            showHeader: true,     //是否显示列头
            showLoading: true,
            showRefresh: false,                  //是否显示刷新按钮
            undefinedText: '',
            toolbarAlign: 'left',
            silent: true,
            striped: true,                      //是否显示行间隔色
            cardView: false,                    //是否显示详细视图
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            sortable: false                     //是否启用排序
        };

        option = $.extend({}, defaults, option, staticOption);

        // 如果现实搜索框时，固定搜索框的相关配置，只有在非远程请求时才使用搜索框
        if (option.search) {
            option.sidePagination = "client";
            option.showRefresh = true;
            option.strictSearch = true;
        }

        // 如果分页，默认分页的条件
        if (option.pagination) {
            option.queryParamsType = '';                // 默认是limit
            option.pageNumber = 1;                      //初始化加载第一页，默认第一页
            option.pageSize = 10;                      //每页的记录行数（*）
            option.pageList = [10, 20, 50, 100];       //可供选择的每页的行数（*）
            option.paginationLoop = false;            //是否无限循环
        }

        // 树结构的treeGird
        if (option.treeShowField && option.parentIdField) {
            option.rootParentId = "0";
            option.onPostBody = function () {
                var columns = $(selector).bootstrapTable('getOptions').columns
                if (columns && columns[0][1].visible) {
                    $(selector).treegrid({
                        treeColumn: 1,
                        expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                        expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                        onChange: function () {
                            $(selector).bootstrapTable('resetWidth')
                        }
                    })
                }
            }
        }

        // 初始化数据表格
        $(selector).bootstrapTable(option);
    }
};

lt.tree = {
    init: function (selector, option) {
        var defaults = {
            checkbox: false,
            multiple: true,
            data: [],
            openAll: true, // 默认展开全部
            ready: null,
            click: null
        };
        option = $.extend({}, defaults, option);

        // 加载完成的事件
        $(selector).on('ready.jstree', function (e, data) {
            if (option.openAll) {
                // 加载完成后，将所有的树展开
                $(selector).jstree(true).open_all();
            }
            option.ready && option.ready(e, data);
        });

        // 添加单击事件
        if (option.click) {
            $(selector).on('select_node.jstree', function (e, data) {    // 点击切换事件
                option.click && option.click(e, data);
            });
        }

        var treeParam = {
            'plugins': ["search"],
            'core': {	//core主要功能是控制树的形状，单选多选等等
                multiple: option.multiple,
                data: option.data
            }
        };

        // 渲染树
        if (option.checkbox) {
            treeParam.plugins.push("checkbox")
            treeParam.checkbox = {
                keep_selected_style: false,//是否默认选中
                three_state: true //父子级别级联选择
            };
        }
        $(selector).jstree(treeParam);
    },
    destroy: function () {
        $.jstree.destroy(); // 销毁树
    },
    initSelect: function (selector, data) {
        if (data.length > 0) {
            $(selector).jstree('select_node', data);
        }
    },
    getSelects: function (selector) {
        var sels = $(selector).jstree('get_selected')
        return sels;
    },
    getSelectOne: function (selector) {
        var ref = $(selector).jstree(true); // 获得整个树
        var sels = ref.get_selected(true);
        if (sels != null && sels.length > 0) {
            return sels[0];
        } else {
            return null;
        }
    }
};

// jquery方法扩展
$.fn.extend({
    // 表单序列化，将表单内容序列化为json字符串。
    serializeJson: function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(
            function () {
                if (serializeObj[this.name]) {
                    if ($.isArray(serializeObj[this.name])) {
                        serializeObj[this.name].push(this.value);
                    } else {
                        serializeObj[this.name] = [serializeObj[this.name], this.value];
                    }
                } else {
                    serializeObj[this.name] = this.value;
                }
            });
        return serializeObj;
    },
    // 选择框初始化
    initSelect: function (resource, opt, defValue, fn) {
        if (!resource) return;

        if (typeof defValue == "function") {
            fn = defValue;
            defValue = null;
        }
        var $this = $(this);
        var selHtml = "";
        if (typeof resource == "string") {   // 如果是字符串，则认为是url，通过远程获取
            $.get(resource, function (data) {
                if (data && data.length > 0) {
                    $.each(data, function (i, one) {
                        if (!!defValue && one.id == defValue) {
                            selHtml += '<option value="' + one.id + '" selected="selected">' + one.text + '</option>'
                        } else {
                            selHtml += '<option value="' + one.id + '">' + one.text + '</option>'
                        }
                    });
                    $this.append(selHtml).chosen(opt);
                } else {
                    $this.chosen(opt);
                }
                fn && fn();
            });
        } else {
            $.each(resource, function (i, data) {
                if (!!defValue && data.id == defValue) {
                    selHtml += '<option value="' + data.id + '" selected="selected">' + data.text + '</option>'
                } else {
                    selHtml += '<option value="' + data.id + '">' + data.text + '</option>'
                }
            });
            $this.append(selHtml).chosen(opt);
        }
    },
});

// jquery静态方法扩展
$.extend({
    // 获取url参数 返回：请求字符串中的参数组成的对象
    requestParams: function () {
        var url = location.search; // 获取url中"?"符后的字串
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            var strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                theRequest[strs[i].split("=")[0]] = strs[i].split("=")[1];
            }
        }
        return theRequest;
    },
    // 可点击放大的图片初始化
    initCanBig: function (selector) {
        if (!selector) selector = "img.can-big";
        $(selector).off("click", "**"); // 移除所有的点击事件
        $(selector).on("click", function (e) {   // 加入新的点击事件
            var title = e.target.alt || "图片";
            lt.openPhoto(title, e.target.src);
        });
    }
});
