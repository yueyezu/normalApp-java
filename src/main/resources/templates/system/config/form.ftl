<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="编辑配置信息" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="fId" id="fId" value="${data.fId!''}" type="hidden"/>
            <input name="fName" id="fName" value="${data.fName!''}" type="hidden"/>
            <input name="fGroup" id="fGroup" value="${data.fGroup!''}" type="hidden"/>
            <input name="fValuetype" id="fValuetype" value="${data.fValuetype!''}" type="hidden"/>
            <input name="fKey" id="fKey" value="${data.fKey!''}" type="hidden"/>
            <input name="fSortnum" id="fSortnum" value="${data.fSortnum!''}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-3 control-label required">属性值</label>
                <div class="col-sm-8">
                    <input id="fValue" name="fValue" value="${data.fValue}" type="text" class="form-control required"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">备注</label>
                <div class="col-sm-8">
                    <textarea id="fRemark" name="fRemark" class="form-control">${data.fRemark}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">描述</label>
                <div class="col-sm-8">
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
            var url = "/config/update";
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
