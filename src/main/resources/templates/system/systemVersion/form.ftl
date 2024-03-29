<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="系统版本表单" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <input name="systemCode" id="systemCode" value="${sysCode!''}" type="hidden"/>
            <div class="form-group">
            	<div class="validationArea">
	                <label class="col-sm-2 control-label required">版本号</label>
	                <div class="col-sm-4">
	                    <input id="version" name="version" value="${data.version}" type="text" class="form-control required"/>
	                </div>
                </div>
                <div class="validationArea">
	                <label class="col-sm-2 control-label required">更新时间</label>
	                <div class="col-sm-4">
	                    <input id="updateDate" name="updateDate" value="${(data.updateDate?string('yyyy-MM-dd'))!''}" type="text" class="form-control required"/>
	                </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">下载地址</label>
                <div class="col-sm-10">
                    <input id="downloadPath" name="downloadPath" value="${data.downloadPath}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">版本更新说明</label>
                <div class="col-sm-10">
                    <textarea id="updateNote" name="updateNote" class="form-control">${data.updateNote}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注</label>
                <div class="col-sm-10">
                    <textarea id="remark" name="remark" class="form-control">${data.remark}</textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-4 col-sm-offset-3">
                    <button type="submit" class="btn btn-success"><span class="fa fa-check"></span>提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
<@mc.ltfooter  type="form">
    <script type="text/javascript">
        $(function () {
            // 初始化表单信息
            initForm();

            // 表单提交方法确定
            $('#dataForm').validate({
                submitHandler: function () {
                    submit();
                }
            });
        });

        // 初始化数据表单
        function initForm() {
            $('#updateDate').datepicker({format: 'yyyy-mm-dd'});
        }

        // 提交添加和修改的信息
        function submit() {
            var data = $('#dataForm').serializeJson();

            var url = data["id"] == "" ? "/systemVersion/save" : "/systemVersion/update";
            $.post(url, data, function (res) {
                if (res.code == 200) {
                    lt.alertSuccess(res.msg, function () {
                        lt.closeThis();
                    });
                } else {
                    lt.alertWarning(res.msg);
                }
            });
        }

    </script>
</@mc.ltfooter>
</html>