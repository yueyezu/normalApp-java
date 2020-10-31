<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="字典值表单" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="fId" id="fId" value="${data.fId!''}" type="hidden"/>
            <input name="fDictid" id="fDictid" value="${data.fDictid!''}" type="hidden"/>
            <input id="fEnabledelete" name="fEnabledelete" value="${data.fEnabledelete!'1'}" type="hidden"/>
            <div class="form-group">
            	<div class="validationArea">
            		<label class="col-sm-2 control-label required">编号</label>
	                <div class="col-sm-4">
	                    <input id="fCode" name="fCode" value="${data.fCode}" type="text" class="form-control required"/>
	                </div>
            	</div>
                <div class="validationArea">
                	<label class="col-sm-2 control-label required">名称</label>
	                <div class="col-sm-4">
	                    <input id="fName" name="fName" value="${data.fName}" type="text" class="form-control required"/>
	                </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="fSortnum" name="fSortnum" value="${data.fSortnum}" type="number" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">描述</label>
                <div class="col-sm-10">
                    <textarea id="fDescription" name="fDescription" class="form-control">${data.fDescription}</textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-4 col-sm-offset-3">
                    <button type="submit" class="btn btn-success"><span class="fa fa-check"></span>提交</button>
                </div>
            </div>
        </form>
    </div>
</body>
<@mc.ltfooter  type="form">
    <script type="text/javascript">
        $(function () {
            if ($.requestParams().fDictid) {
                $('#fDictid').val($.requestParams().fDictid);
            }

            // 表单提交方法确定
            $('#dataForm').validate({
                submitHandler: function () {
                    submit();
                }
            });
        });

        // 提交添加和修改的信息
        function submit() {
            var data = $('#dataForm').serializeJson();

            var url = data["fId"] == "" ? "/dictItem/save" : "/dictItem/update";
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