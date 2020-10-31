<!DOCTYPE html>
<html lang="en">
<head>
	<@mc.ltheader title="修改头像" type="form" ></@mc.ltheader>
    <title>修改头像</title>
    <style>
        .kv-avatar .file-preview-frame, .kv-avatar .file-preview-frame:hover {
            margin: 0;
            padding: 0;
            border: none;
            box-shadow: none;
            text-align: center;
        }
    </style>
    <link href="${rc.contextPath}/static/js/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css">
	<link href="${rc.contextPath}/static/js/bootstrap-fileinput/themes/explorer/theme.css" media="all" rel="stylesheet" type="text/css">
</head>

<body class="bg-gray">
    <div class="ibox float-e-margins">
        <div class="ibox-content">
            <form id="avatarForm" class="form-horizontal">
                <input id="avatarUrl" name="avatarUrl" type="hidden" value="${admin.fPhoto!""}">
                <div class="kv-avatar center-block">
                    <input id="portraitFile" name="file" type="file" class="">
                </div>
                <div class="form-group">
                    <div class="col-sm-4 col-sm-offset-1">
                        <button class="btn btn-primary" type="button" id="submit">提交修改</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
<@mc.ltfooter type="form" import="">
    <script>
    	var originUrl = "${admin.fPhoto!""}";
    	var uploaded = []; //上传过的图片
	    $(function () {
	        $("#portraitFile").fileinput({
	            language: 'zh', //设置语言
	            overwriteInitial: true,
	            maxFileSize: 50000,   //单位为kb，如果为0表示不限制文件大小
	            showClose: false,
	            showCaption: false,
	            dropZoneTitle: '可以将图片拖放到这里',
	            uploadUrl: "${rc.contextPath}/sysFiles/upload", //上传的地址
	            allowedFileExtensions: ['jpg', 'png', 'gif'],//接收的文件后缀
	            uploadAsync: true, //默认异步上传
	            showUpload: false, //是否显示上传按钮
	            showRemove: false, //显示移除按钮
	            showPreview: true, //是否显示预览
	            browseLabel: '',
	            removeLabel: '',
	            browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
	            removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
	            removeTitle: 'Cancel or reset changes',
	            elErrorContainer: '#kv-avatar-errors-1',
	            msgErrorClass: 'alert alert-block alert-danger',
	            preferIconicPreview: true,
	            defaultPreviewContent: '${admin.fPhoto}' == ''?'':'<img style="widht:100px;height:100px;" src="${rc.contextPath}/sysFiles/loadFile?file=${admin.fPhoto!""}" />',
	            uploadExtraData: function () {		//额外上传参数
	                return {"uploadType": "1"};
	            }
	        }).on("filebatchselected", function (event, files) {
        		$(this).fileinput("upload");
	        }).on("fileuploaded", function (event, data, previewId, index) {  //异步上传返回结果处理
	            var response = data.response;
	            if (response.code === '200') {
	                $('#avatarUrl').val(response.data.fLocation);
	                uploaded.push(response.data.fLocation);
	            } else {
	                layer.alert(response.msg, {icon: 2});
	            }
	        }).on('filesuccessremove', function(event, id) {
	        	var avatarUrl = $('#avatarUrl').val();
	        	$.post('/sysFiles/delFile', {'fileName':avatarUrl}, function (res) {
	        		$('#fileInput').show();
	            });
	         });
	    });

	    //提交修改
	    $("#submit").click(function () {
	        var avatarUrl = $('#avatarUrl').val();
	        if (originUrl === avatarUrl) {
	            layer.alert('请先上传头像', {icon: 5});
	            return false;
	        }
	        //首先将上传过的但是不是最终头像的图片删掉
	        var pictureDel = [];
	        uploaded.map(function(e){
	        	if(e != avatarUrl){
	        		pictureDel.push(e);
	        	}
	        });
	        if(pictureDel.length > 0){
	        	var pictureDelStr = '';
	        	for(var i = 0;i < pictureDel.length;i++){
	        		if(i == pictureDel.length-1){
	        			pictureDelStr += pictureDel[i];
	        		}else{
	        			pictureDelStr += (pictureDel[i]+',');
	        		}
	        	}
	        	$.ajax({
		            url: '${rc.contextPath}/sysFiles/delFile',
		            data: {
		            	fileName: pictureDelStr
		            },
		            type:'post',
		            success: function (result) {
		            	$.ajax({
		    	            url: '${rc.contextPath}/user/updateAvatar',
		    	            data: {
		    	                avatarUrl: avatarUrl
		    	            },
		    	            success: function (result) {
		    	                if (result.code === '200') {
		    	                    layer.alert("头像修改成功!", {title: '提示框', icon: 1,closeBtn: 0}, function () {                   	
		    	                        location.reload();
		    	                        parent.document.getElementById("userHeadPortrait").src = '/sysFiles/loadFile?file=' + avatarUrl; 
		    	                    });
		    	                } else {
		    	                    layer.alert(result.msg, {title: '提示框', icon: 0});
		    	                }
		    	            }
		    	        });
		            }
		        });
	        }else{
	        	$.ajax({
    	            url: '${rc.contextPath}/user/updateAvatar',
    	            data: {
    	                avatarUrl: avatarUrl
    	            },
    	            success: function (result) {
    	                if (result.code === '200') {    	                	
    	                    layer.alert("头像修改成功!", {title: '提示框', icon: 1,closeBtn: 0}, function () {                   	
    	                        location.reload();    
    	                        parent.document.getElementById("userHeadPortrait").src = '/sysFiles/loadFile?file=' + avatarUrl; 
    	                    });
    	                } else {
    	                    layer.alert(result.msg, {title: '提示框', icon: 0});
    	                }
    	            }
    	        });
	        }
	        
	    });
	</script>
	<script src="${rc.contextPath}/static/js/bootstrap-fileinput/js/plugins/sortable.js"></script>
	<script src="${rc.contextPath}/static/js/bootstrap-fileinput/js/fileinput.js"></script>
	<script src="${rc.contextPath}/static/js/bootstrap-fileinput/themes/explorer/theme.js"></script>
	<script src="${rc.contextPath}/static/js/bootstrap-fileinput/js/locales/zh.js"></script>
 </@mc.ltfooter>
</body>
</html>