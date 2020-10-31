<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="系统信息详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="fId" id="fId" value="${data.fId}" type="hidden"/>
            <input name="fEnabledflag" id="fEnabledflag" type="hidden" value="${data.fEnabledflag}"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">系统编号</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="fCode" name="fCode" value="${data.fCode}" required="required" readonly/>
                </div>
                <label class="col-sm-2 control-label">系统名称</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="fName" name="fName" value="${data.fName}" required="required" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">系统类型</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="fType" name="fType" defValue="${data.fType!''}" readonly/>
                </div>
                <label class="col-sm-2 control-label">当前版本</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="fVersion" name="fVersion" value="${data.fVersion}" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">访问密码</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="fSecret" name="fSecret" value="${data.fSecret}" readonly/>
                </div>
                <label class="col-sm-2 control-label">建设时间</label>
                <div class="col-sm-4">
                    <input id="fDevtime" name="fDevtime" value="${(data.fDevtime?string('yyyy-MM-dd'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">建设单位</label>
                <div class="col-sm-10">
                    <input id="fDevorg" name="fDevorg" value="${data.fDevorg}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注</label>
                <div class="col-sm-10">
                    <textarea id="fRemark" name="fRemark" class="form-control" readonly>${data.fRemark}</textarea>
                </div>
            </div>
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
</body>

<@mc.ltfooter  type="view">
    <script type="text/javascript">
        $(function () {
            var defVal = $('#fType').attr("defValue");
            $('#fType').val(top.lt.cache.dictItems["systemType"][defVal]);
        });
    </script>
</@mc.ltfooter>
</html>
