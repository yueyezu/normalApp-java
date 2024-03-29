<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="组织表单" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input id="id" name="id" value="${data.id!''}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label required">父级</label>
                <div class="col-sm-4">
                    <div class="validationArea">
                        <input id="parentId" name="parentId" value="${parentId!'0'}" type="hidden"/>
                        <input id="parentName" value="${parentName}" type="text" class="form-control required" readonly/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="validationArea">
                    <label class="col-sm-2 control-label required ">名称</label>
                    <div class="col-sm-4">
                        <input id="name" name="name" value="${data.name}" type="text" class="form-control required"/>
                    </div>
                </div>
                <div class="validationArea">
                    <label class="col-sm-2 control-label required">编号</label>
                    <div class="col-sm-4">
                        <input id="code" name="code" value="${data.code}" type="text" class="form-control required"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">部门说明</label>
                <div class="col-sm-10">
                    <textarea id="description" name="description" class="form-control">${data.description}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">负责人</label>
                <div class="col-sm-4">
                    <input id="manager" name="manager" value="${data.manager}" type="text" class="form-control"/>
                </div>
                <label class="col-sm-2 control-label">电话</label>
                <div class="col-sm-4">
                    <input id="phone" name="phone" value="${data.phone}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="sortNum" name="sortNum" value="${data.sortNum!'1'}" type="number" class="form-control"/>
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
            $('#parentName').click(function () {
                lt.openSelect("选择父级", "${rc.contextPath}/organize/selectView", function (win) {
                    var selDept = win.getSelected();
                    if (selDept) {
                        $('#parentId').val(selDept.id);
                        $('#parentName').val(selDept.text);
                    }
                });
            });
        }

        // 提交添加和修改的信息
        function submit() {
            var data = $('#dataForm').serializeJson();

            var url = data["id"] == "" ? "/organize/save" : "/organize/update";
            $.post(url, data, function (res) {
                if (res.code == 200) {
                    lt.msgSuccess(res.msg, function () {
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