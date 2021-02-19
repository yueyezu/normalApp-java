<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" import="tree" title="角色表单" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <input name="type" id="type" type="hidden" value="1"/>
            <div class="form-group">
            	<div class="validationArea">
            		<label class="col-sm-2 control-label required">角色编号</label>
	                <div class="col-sm-4">
	                    <input id="code" name="code" value="${data.code}" type="text" class="form-control required"/>
	                </div>
            	</div>
                <div class="validationArea">
                	<label class="col-sm-2 control-label required">角色名称</label>
	                <div class="col-sm-4">
	                    <input id="name" name="name" value="${data.name}" type="text" class="form-control required"/>
	                </div>
                </div>                
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">角色说明</label>
                <div class="col-sm-10">
                    <input id="description" name="description" value="${data.description}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="sortNum" name="sortNum" value="${data.sortNum}" type="number" class="form-control"/>
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
<@mc.ltfooter  type="form"  import="tree">
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

            var url = data["id"] == "" ? "/role/save" : "/role/update";
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

