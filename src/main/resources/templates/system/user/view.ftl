<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="系统信息详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="view" class="form-horizontal m-t">
            <input id="enableDelete" name="enableDelete" value="${data.enableDelete!'1'}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">帐号</label>
                <div class="col-sm-4">
                    <input id="account" name="account" value="${data.account}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-4">
                    <input id="realName" name="realName" value="${data.realName}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-4">
                    <input id="nickName" name="nickName" value="${data.nickName}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">性别</label>
                <div class="col-sm-4">
                    <label for='sex_Man'>
                        <input id="sex_Man" name="sex" value="1" type="radio" readonly/>男
                    </label>
                    <label for='sex_WoMan'>
                        <input id="sex_WoMan" name="sex" value="2" type="radio" readonly/>女
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">微信</label>
                <div class="col-sm-4">
                    <input id="wechat" name="wechat" value="${data.wechat}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">电话</label>
                <div class="col-sm-4">
                    <input id="phone" name="phone" value="${data.phone}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">电子邮件</label>
                <div class="col-sm-4">
                    <input id="email" name="email" value="${data.email}" type="email" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">生日</label>
                <div class="col-sm-4">
                    <input name="birthday" value="${(data.birthday?string('yyyy-MM-dd'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">部门</label>
                <div class="col-sm-4">
                    <input id="deptId" name="deptId" value="${deptId}" type="hidden"/>
                    <input value="${deptName}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">岗位</label>
                <div class="col-sm-4">
                    <input id="roleId" name="roleId" value="${data.roleId}" type="hidden"/>
                    <input value="${roleName!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="sortNum" name="sortNum" value="${data.sortNum}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注</label>
                <div class="col-sm-10">
                    <textarea id="remark" name="remark" class="form-control" readonly>${data.remark}</textarea>
                </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <label class="col-sm-2 control-label">创建人</label>
                <div class="col-sm-4">
                    <input id="createBy" name="createBy" value="${data.createBy}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">创建时间</label>
                <div class="col-sm-4">
                    <input id="createTime" name="createTime" value="${(data.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">修改人</label>
                <div class="col-sm-4">
                    <input id="modifyBy" name="modifyBy" value="${data.modifyBy}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">修改时间</label>
                <div class="col-sm-4">
                    <input id="modifyTime" name="modifyTime" value="${(data.modifyTime?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
        </form>
    </div>
</div>
<@mc.ltfooter  type="view">
	<script type="text/javascript">
        $(function () {
        	$("input[name=sex][value='" + ${data.sex} + "']").prop("checked",true);
        });
	</script>
</@mc.ltfooter>
</body>
</html>
