<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="编辑配置信息" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <input name="name" id="name" value="${data.name!''}" type="hidden"/>
            <input name="groups" id="groups" value="${data.groups!''}" type="hidden"/>
            <input name="valueType" id="valueType" value="${data.valueType!''}" type="hidden"/>
            <#--            <input name="code" id="code" value="${data.code!''}" type="hidden"/>-->
            <input name="sortNum" id="sortNum" value="${data.sortNum!''}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-3 control-label required">属性值</label>
                <div class="col-sm-8">
                    <input id="value" name="value" value="${data.value}" type="text" class="form-control required"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">描述</label>
                <div class="col-sm-8">
                    <textarea id="description" name="description" class="form-control">${data.description}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">备注</label>
                <div class="col-sm-8">
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
            var defVal = $('#type').attr("defValue");
            $("#type").initSelect(top.lt.cache.dictItemArr.systemType, {
                disable_search: true
            }, defVal);

            $('#devTime').datepicker({format: 'yyyy-mm-dd'});
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
