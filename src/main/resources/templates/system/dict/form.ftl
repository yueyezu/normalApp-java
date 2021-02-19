<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="字典信息表单" import="tree"/>
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <input id="enableDelete" name="enableDelete" value="${data.enableDelete!'1'}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label required">父级</label>
                <div class="col-sm-4">
                    <input id="parentId" name="parentId" value="${parentId!'0'}" type="hidden"/>
                    <input id="parentName" value="${parentName}" type="text" class="form-control required" readonly/>
                </div>
            </div>
            <div class="form-group">
            	<div class="validationArea">
            		<label class="col-sm-2 control-label required">编号</label>
	                <div class="col-sm-4">
	                    <input id="code" name="code" value="${data.code}" type="text" class="form-control required"/>
	                </div>
            	</div>
                <div class="validationArea">
                	<label class="col-sm-2 control-label required">名称</label>
	                <div class="col-sm-4">
	                    <input id="name" name="name" value="${data.name}" type="text" class="form-control required"/>
	                </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="sortNum" name="sortNum" value="${data.sortNum}" type="number" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">字典描述</label>
                <div class="col-sm-10">
                    <textarea id="description" name="description" class="form-control">${data.description}</textarea>
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
<@mc.ltfooter  type="form" import="tree">
    <script type="text/javascript">
        var prefix = '${rc.contextPath}/dict';
        var nowDict = null;
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
                lt.openSelect("选择父级", prefix + "/selectView", function (win) {
                    var selDict = win.getSelected();
                    if (selDict) {
                        $('#parentId').val(selDict.id);
                        $('#parentName').val(selDict.text);
                    }
                });
            });
        }

        // 提交添加和修改的信息
        function submit() {
            var data = $('#dataForm').serializeJson();

            var url = data["id"] == "" ? prefix + "/save" : prefix + "/update";
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