<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="编辑系统信息" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <input name="enableFlag" id="enableFlag" value="${data.enableFlag!'1'}" type="hidden"/>
            <div class="form-group">
                <div class="validationArea">
                    <label class="col-sm-2 control-label required">系统编号</label>
                    <div class="col-sm-4">
                        <input id="code" name="code" value="${data.code}" type="text" class="form-control required"/>
                    </div>
                </div>
                <div class="validationArea">
                    <label class="col-sm-2 control-label required">系统名称</label>
                    <div class="col-sm-4">
                        <input id="name" name="name" value="${data.name}" type="text" class="form-control required"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">系统类型</label>
                <div class="col-sm-4">
                    <select id="type" name="type" defValue="${data.type!1}" class="form-control"></select>
                </div>
                <label class="col-sm-2 control-label">当前版本</label>
                <div class="col-sm-4">
                    <input id="version" name="version" value="${data.version}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">访问密码</label>
                <div class="col-sm-4">
                    <input id="secret" name="secret" value="${data.secret}" type="text" class="form-control"/>
                </div>
                <label class="col-sm-2 control-label">建设时间</label>
                <div class="col-sm-4">
                    <input id="devTime" name="devTime" value="${(data.devTime?string('yyyy-MM-dd'))!''}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">建设单位</label>
                <div class="col-sm-10">
                    <input id="devOrg" name="devOrg" value="${data.devOrg}" type="text" class="form-control"/>
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
            var defVal = $('#type').attr("defValue");
            $("#type").initSelect(top.lt.cache.dictItemArr.systemType, {
                disable_search: true
            }, defVal);

            $('#devTime').datepicker({format: 'yyyy-mm-dd'});
        }

        // 提交添加和修改的信息
        function submit() {
            var data = $('#dataForm').serializeJson();

            var url = data["id"] == "" ? "/system/save" : "/system/update";
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
