<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="编辑用户信息" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="fId" id="fId" value="${data.fId!''}" type="hidden"/>
            <input id="fEnabledelete" name="fEnabledelete" value="${data.fEnabledelete!1}" type="hidden"/>
            <div class="form-group">
            	<div class="validationArea">
            		<label class="col-sm-2 control-label required">帐号</label>
	                <div class="col-sm-4">
	                    <input id="fAccount" name="fAccount" value="${data.fAccount}" type="text" class="form-control required"/>
	                </div>
            	</div>
                <div class="validationArea">
                	<label class="col-sm-2 control-label required">姓名</label>
	                <div class="col-sm-4">
	                    <input id="fRealname" name="fRealname" value="${data.fRealname}" type="text" class="form-control required" onkeyup="addNickname()"/>
	                </div>
                </div>
            </div>
            <div class="form-group">
            	<div class="validationArea">
            		<label class="col-sm-2 control-label required">昵称</label>
	                <div class="col-sm-4">
	                    <input id="fNickname" name="fNickname" value="${data.fNickname}" type="text" class="form-control required"/>
	                </div>
            	</div>
               	<label class="col-sm-2 control-label">生日</label>
                <div class='date col-sm-4'>
                    <input id='fBirthday' name="fBirthday" value="${(data.fBirthday?string('yyyy-MM-dd'))!''}" type='text' class="form-control"/>
                </div>         
            </div>
            <div class="form-group">
            	<div class="validationArea">
	                <label class="col-sm-2 control-label">联系方式</label>
	                <div class="col-sm-4">
	                    <input id="fPhone" name="fPhone" value="${data.fPhone}" type="text" class="form-control phone"/>
	                </div>
                </div>
                <div class="validationArea">
	                <label class="col-sm-2 control-label">电子邮件</label>
	                <div class="col-sm-4">
	                    <input id="fEmail" name="fEmail" value="${data.fEmail}" type="email" class="form-control email"/>
	                </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">微信</label>
                <div class="col-sm-4">
                    <input id="fWechat" name="fWechat" value="${data.fWechat}" type="text" class="form-control"/>
                </div>
                <label class="col-sm-2 control-label">性别</label>
                <div class="col-sm-4">
                    <label for='fSex_False'>
                        <input id="fSex_False" name="fSex" value="1" type="radio"/>男
                    </label>
                    <label for='fSex_True'>
                        <input id="fSex_True" name="fSex" value="2" type="radio"/>女
                    </label>
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
                    <select id="fRoleid" name="fRoleid" defValue="${data.fRoleid!''}" class="form-control">
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="fSortnum" name="fSortnum" value="${data.fSortnum}" type="number" class="form-control"/>
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
        	$("input[name=fSex][value='" + ${data.fSex} + "']").prop("checked",true);
            $('#fBirthday').datepicker({format: 'yyyy-mm-dd', startView: 2});

            // 部门选择的界面
            $('#departmentName').click(function () {
                lt.openSelect("选择部门", "${rc.contextPath}/organize/selectView", function (win) {
                    var selDept = win.getSelected();
                    if (selDept) {
                        $('#fDepartmentid').val(selDept.id);
                        $('#departmentName').val(selDept.text);
                    }
                });
            });

            var defVal = $('#fRoleid').attr("defValue");
            $("#fRoleid").initSelect("${rc.contextPath}/role/select?fType=2", {
                disable_search: true
            }, defVal);
        }
        
        function  addNickname() {

            var data = $('#dataForm').serializeJson();
            setTimeout(function(){
                if (data["fNickname"] == "") {
                    var Nickname = document.getElementById("fRealname").value;
                    $("#fNickname").val(Nickname);
                }
                 }, 3000);


        }

        // 提交添加和修改的信息
        function submit() {

            var data = $('#dataForm').serializeJson();
            var newurl ="/user/getByAccount";
            var url = "";
	        if (data["fId"] == "") {
	            url = "/user/save";
	        } else {
	            url = "/user/update";
	        }
            $.post(newurl,data, function (res) {
                if(res.msg == null) {
                    
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
                else {
                    lt.alertWarning("当前账号已被注册，请换一个账号")
                }
            });
        }
    </script>
</@mc.ltfooter>
</html>