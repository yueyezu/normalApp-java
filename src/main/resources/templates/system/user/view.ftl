<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="系统信息详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="view" class="form-horizontal m-t">
            <input id="fEnabledelete" name="fEnabledelete" value="${data.fEnabledelete!'1'}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">帐号</label>
                <div class="col-sm-4">
                    <input id="fAccount" name="fAccount" value="${data.fAccount}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-4">
                    <input id="fRealname" name="fRealname" value="${data.fRealname}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-4">
                    <input id="fNickname" name="fNickname" value="${data.fNickname}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">性别</label>
                <div class="col-sm-4">
                    <label for='fSex_Man'>
                        <input id="fSex_Man" name="fSex" value="1" type="radio" readonly/>男
                    </label>
                    <label for='fSex_WoMan'>
                        <input id="fSex_WoMan" name="fSex" value="2" type="radio" readonly/>女
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">微信</label>
                <div class="col-sm-4">
                    <input id="fWechat" name="fWechat" value="${data.fWechat}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">电话</label>
                <div class="col-sm-4">
                    <input id="fPhone" name="fPhone" value="${data.fPhone}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">电子邮件</label>
                <div class="col-sm-4">
                    <input id="fEmail" name="fEmail" value="${data.fEmail}" type="email" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">生日</label>
                <div class="col-sm-4">
                    <input name="fBirthday" value="${(data.fBirthday?string('yyyy-MM-dd'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">部门</label>
                <div class="col-sm-4">
                    <input id="fDepartmentid" name="fDepartmentid" value="${departmentId}" type="hidden"/>
                    <input value="${departmentName}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">岗位</label>
                <div class="col-sm-4">
                    <input id="fRoleid" name="fRoleid" value="${data.fRoleid}" type="hidden"/>
                    <input value="${roleName!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="fSortnum" name="fSortnum" value="${data.fSortnum}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注</label>
                <div class="col-sm-10">
                    <textarea id="fRemark" name="fRemark" class="form-control" readonly>${data.fRemark}</textarea>
                </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <label class="col-sm-2 control-label">创建人</label>
                <div class="col-sm-4">
                    <input id="fCreateUserid" name="fCreateUserid" value="${data.fCreateuserid}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">创建时间</label>
                <div class="col-sm-4">
                    <input id="fCreatetime" name="fCreatetime" value="${(data.fCreatetime?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">修改人</label>
                <div class="col-sm-4">
                    <input id="fLastModifyUserid" name="fLastModifyUserid" value="${data.fLastmodifyuserid}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">修改时间</label>
                <div class="col-sm-4">
                    <input id="fLastmodifytime" name="fLastModifytime" value="${(data.fLastmodifytime?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
        </form>
    </div>
</div>
<@mc.ltfooter  type="view">
	<script type="text/javascript">
        $(function () {
        	$("input[name=fSex][value='" + ${data.fSex} + "']").prop("checked",true);
        });
	</script>
</@mc.ltfooter>
</body>
</html>
