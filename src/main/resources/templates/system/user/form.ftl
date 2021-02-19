<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="编辑用户信息" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <input id="enableDelete" name="enableDelete" value="${data.enableDelete!1}" type="hidden"/>
            <div class="form-group">
                <div class="validationArea">
                    <label class="col-sm-2 control-label required">帐号</label>
                    <div class="col-sm-4">
                        <input id="account" name="account" value="${data.account}" type="text" class="form-control required"/>
                    </div>
                </div>
                <div class="validationArea">
                    <label class="col-sm-2 control-label required">姓名</label>
                    <div class="col-sm-4">
                        <input id="realName" name="realName" value="${data.realName}" type="text" class="form-control required" onkeyup="addNickname()"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="validationArea">
                    <label class="col-sm-2 control-label required">昵称</label>
                    <div class="col-sm-4">
                        <input id="nickName" name="nickName" value="${data.nickName}" type="text" class="form-control required"/>
                    </div>
                </div>
                <label class="col-sm-2 control-label">生日</label>
                <div class='date col-sm-4'>
                    <input id='birthday' name="birthday" value="${(data.birthday?string('yyyy-MM-dd'))!''}" type='text' class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <div class="validationArea">
                    <label class="col-sm-2 control-label">联系方式</label>
                    <div class="col-sm-4">
                        <input id="phone" name="phone" value="${data.phone}" type="text" class="form-control phone"/>
                    </div>
                </div>
                <div class="validationArea">
                    <label class="col-sm-2 control-label">电子邮件</label>
                    <div class="col-sm-4">
                        <input id="email" name="email" value="${data.email}" type="email" class="form-control email"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">微信</label>
                <div class="col-sm-4">
                    <input id="wechat" name="wechat" value="${data.wechat}" type="text" class="form-control"/>
                </div>
                <label class="col-sm-2 control-label">性别</label>
                <div class="col-sm-4">
                    <label for='sex_False'>
                        <input id="sex_False" name="sex" value="1" type="radio"/>男
                    </label>
                    <label for='sex_True'>
                        <input id="sex_True" name="sex" value="2" type="radio"/>女
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">部门</label>
                <div class="col-sm-4">
                    <input id="deptId" name="deptId" value="${deptId}" type="hidden"/>
                    <input id="deptName" value="${deptName}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">岗位</label>
                <div class="col-sm-4">
                    <select id="roleId" name="roleId" defValue="${data.roleId!''}" class="form-control">
                    </select>
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
            $("input[name=sex][value='" + ${data.sex} +"']").prop("checked", true);
            $('#birthday').datepicker({format: 'yyyy-mm-dd', startView: 2});

            // 部门选择的界面
            $('#deptName').click(function () {
                lt.openSelect("选择部门", "${rc.contextPath}/organize/selectView", function (win) {
                    var selDept = win.getSelected();
                    if (selDept) {
                        $('#deptId').val(selDept.id);
                        $('#deptName').val(selDept.text);
                    }
                });
            });

            var defVal = $('#roleId').attr("defValue");
            $("#roleId").initSelect("${rc.contextPath}/role/select?type=2", {
                disable_search: true
            }, defVal);
        }

        function addNickname() {
            var data = $('#dataForm').serializeJson();
            setTimeout(function () {
                if (data["nickName"] == "") {
                    var nickName = document.getElementById("realName").value;
                    $("#nickName").val(nickName);
                }
            }, 3000);
        }

        // 提交添加和修改的信息
        function submit() {
            var data = $('#dataForm').serializeJson();

            var url = "";
            if (data["id"] == "") {
                url = "/user/save";
            } else {
                url = "/user/update";
            }

            $.post(url, data, function (res) {
                if (res.code == 200) {
                    lt.alertSuccess("操作成功！", function () {
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