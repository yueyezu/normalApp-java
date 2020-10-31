<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="文件管理">
        <style type="text/css">
            .file .file-name span {
                width: 100%;
                display: inline-block;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
            }
        </style>
    </@mc.ltheader>
    <link href="${rc.contextPath}/static/css/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="row file-manager">
                        <div class="col-sm-12 input-group">
                            <@shiro.hasPermission name="funFile-btnQuery">
                            	<input id="keyword" class="form-control" type="text" placeholder="文件名"/>
                            </@shiro.hasPermission>
                            <span class="input-group-btn">
                                <a onclick="initTable(null)" class="btn btn-default"><i class="fa fa-search"></i></a>
                            </span>
                        </div>
                        <@shiro.hasPermission name="funFile-btnAdd">
                        	<button id="uploadFile" class="btn btn-primary btn-block"><i class="fa fa-plus"></i>上传文件</button>
                    	</@shiro.hasPermission>
                        <input id='upfile' type="file" style="display: none"/>
                        <div class="hr-line-dashed"></div>
                        <h5>文件类型</h5>
                        <ul class="folder-list" style="padding: 0">
                            <li><a onclick="initTable('all',1,this)" name="all" class="file-control active"><i class="fa fa-globe"></i> 全部</a></li>
                            <li><a onclick="initTable('picture',1,this)" name="picture" class="file-control"><i class="fa fa-picture-o"></i> 图片</a></li>
                            <li><a onclick="initTable('video',1,this)" name="video" class="file-control"><i class="fa fa-video-camera"></i> 视频</a></li>
                            <li><a onclick="initTable('audio',1,this)" name="audio" class="file-control"><i class="fa fa-volume-up"></i> 音频</a></li>
                            <li><a onclick="initTable('document',1,this)" name="document" class="file-control"><i class="fa fa-file"></i> 文档</a></li>
                        </ul>
                        <div class="hr-line-dashed"></div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-9 animated fadeInRight">
            <div class="row">
                <div class="col-sm-12" id="fileTable"></div>
            </div>
            <div class="m-l-lg">
                <ul id="paging"></ul>
            </div>
        </div>
    </div>
</div>

<@mc.ltfooter  type="list" import="paginator">
	<script src="${rc.contextPath}/static/js/plugins/fancybox/jquery.fancybox.js"></script>
    <script type="text/javascript">
        var prefix = '${rc.contextPath}/sysFiles';// 本功能所有请求的根路径
        var queryType = "all"; // 查询类型,默认查询全部
        var pageSize = 9;   // 每页显示的数量
        var lastActiveType = 'all'; //上个选择的文件类型

        $(function () {
            initTable("all", 1);//初始化列表

            //上传文件按钮
            $('#uploadFile').click(function (event) {
                $('#upfile').trigger("click");
            });
            $('#upfile').change(function () {
                var fileVal = $(this).val();
                if (!fileVal) {
                    lt.alertWarning("请选择要进行上传的文件！")
                    return;
                }
                var formData = new FormData();
                formData.append("file", $(this)[0].files[0]);
                formData.append("uploadType", 0);
                $.ajax({
                    url: prefix + '/upload',
                    type: 'post',
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (res) {
                        if (res.code == 200) {
                            lt.alertSuccess('上传成功！', function () {
                                initTable(null);
                            });
                        } else {
                            lt.alertWarning("上传失败！");
                        }
                    }
                });
            });
            
            $('.fancybox').fancybox({
                openEffect: 'none',
                closeEffect: 'none'
            });
        });

        // 查询列表
        function initTable(type, page,obj) {
        	$('.file-control').each(function(){
        		$(this).removeClass('active');
        	});
        	if(null != type){
        		lastActiveType = type;
        		$(obj).addClass('active');
        	}else{
        		$('a[name="' + lastActiveType + '"]').addClass('active');
        	}      	
        	
            var param = {pageNumber: page || 1, pageSize: pageSize};
            if (type != null && type != queryType) {    // 当类型为null时，认为是类型不变
                queryType = type;
            }
            param.fFiletype = queryType == "all" ? '' : queryType;
            param.keyword = $('#keyword').val();

            $.get(prefix + '/page', param, function (result) {
                renderTable(result, page);
            });
        }

        //渲染数据
        function renderTable(result, page) {
            var data = result.rows;
            if (data.length == 0) { // 当未获取到数据时
                $('#fileTable').html('<div style="text-align:center;line-height:120px;">暂无文件</div>');
                $('#paging').hide();
                return;
            }

            var fileHtml = '';
            $.each(data, function (i, file) {
                var fileIcon = "<div class='icon'><i class='fa fa-file'></i></div>";
                if (isImage(file.fFiletype)) {
                    var url = prefix + '/loadFile?file=' + file.fLocation;
                    fileIcon = '<div style="cursor:pointer;" title="' + this.fOriginname + '" class="image fancybox" href="' + url + '"><img alt="image" style="width:100%;height:100%;" class="img-responsive" src="' + url + '"></div>';
                }
                fileHtml += '<div class="file-box">' +
                    '    <div class="file" title="' + this.fOriginname + '">' +
                    '        <span class="corner"></span>' + fileIcon +
                    '        <div class="file-name">' +
                    '            <span>' + this.fOriginname + '</span><br/>' +
                    '            <small>添加时间：' + file.fCreatetime + '</small><br/>' +
                    '<@shiro.hasPermission name="funFile-btnDelete"><a class="m-l-lg" onclick="fileDelete(\'' + file.fId + '\')"><i class="fa fa-remove"></i>删除</a></@shiro.hasPermission>' +
                    '<@shiro.hasPermission name="funFile-btnDownload"><a class="m-l-lg" onclick="fileDownload(\'' + file.fId + '\')"><i class="fa fa-cloud-download"></i>下载</a></@shiro.hasPermission>' +
                    '        </div>' +
                    '    </div>' +
                    '</div>';
            });
            $('#fileTable').html(fileHtml);
            $('#paging').show();

            // 添加动画
            $('.file-box').each(function () {
                animationHover(this, 'pulse');
            });

            var totalPages = pageCount(result.total); //总页数
            var options = {
                bootstrapMajorVersion: 3, //版本
                currentPage: page || 1, //当前页数
                totalPages: totalPages, //总页数
                shouldShowPage: true,//是否显示该按钮
                itemTexts: function (type, page, current) {
                    switch (type) {
                        case "first":
                            return "首页";
                        case "prev":
                            return "上一页";
                        case "next":
                            return "下一页";
                        case "last":
                            return "末页";
                        case "page":
                            return page;
                    }
                },//点击事件，用于通过Ajax来刷新整个list列表
                onPageClicked: function (event, originalEvent, type, page) {
                    initTable(null, page);
                }
            };
            $('#paging').bootstrapPaginator(options);
        }

        //文件下载
        function fileDownload(fId) {
            lt.confirm("确定要下载选中文件？", function () {
                var url = prefix + "/downloadById?fileId=" + fId;
                window.open(url);
            });
        }

        //文件删除
        function fileDelete(fId) {
        	//在文件管理之外上传的文件不允许删除
        	$.post(prefix + '/beforeDelete', {id: fId}, function (result) {
                 if (result.code == 200) {
                     if(result.data == true){
                    	 lt.confirm("确定要删除选中文件？", function () {
                             $.post(prefix + '/delete', {id: fId}, function (result) {
                                 if (result.code == 200) {
                                     lt.alertSuccess(result.msg, function () {
                                         initTable();
                                     });
                                 } else {
                                     lt.alertWarning(result.msg);
                                 }
                             });
                         });
                     }else{
                    	 lt.alertWarning('此文件正在使用中，不能删除！');
                     }
                 } else {
                     lt.alertWarning(result.msg);
                 }
             });
            
        }

        //判断文件是否为图片
        function isImage(ext) {
            return ["jpg", "png", "jpeg", "gif", "tif", "bmp", "dwg", "psd", "vsd"].indexOf(ext.toLowerCase()) !== -1;
        }

        //根据数据总条数和每页显示条数获取总页数
        function pageCount(totalnum) {
            return totalnum > 0 ? ((totalnum < pageSize) ? 1 : ((totalnum % pageSize) ? (parseInt(totalnum / pageSize) + 1) : (totalnum / pageSize))) : 0;
        }
    </script>
</@mc.ltfooter>
</body>
</html>