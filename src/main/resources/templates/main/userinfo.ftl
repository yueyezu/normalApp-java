<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader title="用户信息" type="form" ></@mc.ltheader>
</head>
<body class="bg-gray">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <input id="enableDelete" name="enableDelete" value="${data.enableDelete!1}" type="hidden"/>
            <input id="sortNum" name="sortNum" value="${data.sortNum}" type="hidden"/>
            <input id="enableFlag" name="enableFlag" value="${data.enableFlag}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">帐号</label>
                <div class="col-sm-4">
                    <input id="account" name="account" value="${data.account}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-4">
                    <input id="realName" name="realName" value="${data.realName}" type="text" class="form-control required"/>
                </div>
                <label class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-4">
                    <input id="nickName" name="nickName" value="${data.nickName}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">性别</label>
                <div class="col-sm-4">
                    <input id="sex" name="sex" value="${data.sex}" type="text" class="form-control"/>
                </div>
                <label class="col-sm-2 control-label">生日</label>
                <div class='date col-sm-4'>
                    <input id='birthday' name="birthday" value="${(data.birthday?string('yyyy-MM-dd'))!''}" type='text' class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">联系方式</label>
                <div class="col-sm-4">
                    <input id="phone" name="phone" value="${data.phone}" type="text" class="form-control"/>
                </div>
                <label class="col-sm-2 control-label">电子邮件</label>
                <div class="col-sm-4">
                    <input id="email" name="email" value="${data.email}" type="email" class="form-control email"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">微信</label>
                <div class="col-sm-4">
                    <input id="wechat" name="wechat" value="${data.wechat}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">部门</label>
                <div class="col-sm-4">
                    <input id="deptId" name="deptId" value="${departmentId}" type="hidden"/>
                    <input id="departmentName" value="${departmentName}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">岗位</label>
                <div class="col-sm-4">
                    <input id="roleId" name="roleId" value="${data.roleId}" type="hidden"/>
                    <input id="roleName" value="${roleName!''}" type="text" class="form-control" readonly/>
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

<@mc.ltfooter type="form">

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
            $('#birthday').datepicker({format: 'yyyy-mm-dd', startView: 2});
        }

        // 提交添加和修改的信息
        function submit() {
            var data = $('#dataForm').serializeJson();

            $.post("/user/update", data, function (res) {
                if (res.code == 200) {
                    lt.alertSuccess(res.msg);
                } else {
                    lt.alertWarning(res.msg);
                }
            })
        }

    </script>
</@mc.ltfooter>
</body>
</html>

