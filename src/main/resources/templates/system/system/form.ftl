<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="编辑系统信息" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="fId" id="fId" value="${data.fId!''}" type="hidden"/>
            <input name="fEnabledflag" id="fEnabledflag" value="${data.fEnabledflag!'1'}" type="hidden"/>
            <div class="form-group">
            	<div class="validationArea">
            		<label class="col-sm-2 control-label required">系统编号</label>
	                <div class="col-sm-4">
	                    <input id="fCode" name="fCode" value="${data.fCode}" type="text" class="form-control required"/>
	                </div>
            	</div>
                <div class="validationArea">
                	<label class="col-sm-2 control-label required">系统名称</label>
	                <div class="col-sm-4">
	                    <input id="fName" name="fName" value="${data.fName}" type="text" class="form-control required"/>
	                </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">系统类型</label>
                <div class="col-sm-4">
                    <select id="fType" name="fType" defValue="${data.fType!1}" class="form-control"></select>
                </div>
                <label class="col-sm-2 control-label">当前版本</label>
                <div class="col-sm-4">
                    <input id="fVersion" name="fVersion" value="${data.fVersion}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">访问密码</label>
                <div class="col-sm-4">
                    <input id="fSecret" name="fSecret" value="${data.fSecret}" type="text" class="form-control"/>
                </div>
                <label class="col-sm-2 control-label">建设时间</label>
                <div class="col-sm-4">
                    <input id="fDevtime" name="fDevtime" value="${(data.fDevtime?string('yyyy-MM-dd'))!''}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">建设单位</label>
                <div class="col-sm-10">
                    <input id="fDevorg" name="fDevorg" value="${data.fDevorg}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注</label>
                <div class="col-sm-10">
                    <textarea id="fRemark" name="fRemark" class="form-control">${data.fRemark}</textarea>
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
            var defVal = $('#fType').attr("defValue");
            $("#fType").initSelect(top.lt.cache.dictItemArr.systemType, {
                disable_search: true
            }, defVal);

            $('#fDevtime').datepicker({format: 'yyyy-mm-dd'});
        }

        // 提交添加和修改的信息
        function submit() {
            var data = $('#dataForm').serializeJson();

            var url = data["fId"] == "" ? "/system/save" : "/system/update";
            $.post(url, data, function (res) {
                if (res.code == 200) {
                    lt.alertSuccess(res.msg, function () {
                        lt.closeThis();
                    });
                } else {
                    lt.alertWarning(res.msg);
                }
            })
        }

    </script>
</@mc.ltfooter>
</html>
