<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader title="用户信息" type="form" ></@mc.ltheader>
</head>
<body class="bg-gray">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="fId" id="fId" value="${data.fId!''}" type="hidden"/>
            <input id="fEnabledelete" name="fEnabledelete" value="${data.fEnabledelete!1}" type="hidden"/>
            <input id="fSortnum" name="fSortnum" value="${data.fSortnum}" type="hidden"/>
            <input id="fEnabledflag" name="fEnabledflag" value="${data.fEnabledflag}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">帐号</label>
                <div class="col-sm-4">
                    <input id="fAccount" name="fAccount" value="${data.fAccount}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-4">
                    <input id="fRealname" name="fRealname" value="${data.fRealname}" type="text" class="form-control required"/>
                </div>
                <label class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-4">
                    <input id="fNickname" name="fNickname" value="${data.fNickname}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">性别</label>
                <div class="col-sm-4">
                    <input id="fSex" name="fSex" value="${data.fSex}" type="text" class="form-control"/>
                </div>
                <label class="col-sm-2 control-label">生日</label>
                <div class='date col-sm-4'>
                    <input id='fBirthday' name="fBirthday" value="${(data.fBirthday?string('yyyy-MM-dd'))!''}" type='text' class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">联系方式</label>
                <div class="col-sm-4">
                    <input id="fPhone" name="fPhone" value="${data.fPhone}" type="text" class="form-control"/>
                </div>
                <label class="col-sm-2 control-label">电子邮件</label>
                <div class="col-sm-4">
                    <input id="fEmail" name="fEmail" value="${data.fEmail}" type="email" class="form-control email"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">微信</label>
                <div class="col-sm-4">
                    <input id="fWechat" name="fWechat" value="${data.fWechat}" type="text" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">部门</label>
                <div class="col-sm-4">
                    <input id="fDepartmentid" name="fDepartmentid" value="${departmentId}" type="hidden"/>
                    <input id="departmentName" value="${departmentName}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">岗位</label>
                <div class="col-sm-4">
                    <input id="fRoleid" name="fRoleid" value="${data.fRoleid}" type="hidden"/>
                    <input id="roleName" value="${roleName!''}" type="text" class="form-control" readonly/>
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
            $('#fBirthday').datepicker({format: 'yyyy-mm-dd', startView: 2});
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

