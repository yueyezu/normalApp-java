<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="系统版本详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="fId" id="fId" value="${data.fId!''}" type="hidden"/>
            <input name="fEnabledflag" id="fEnabledflag" value="${data.fEnabledflag!'1'}" type="hidden"/>
            <input name="fSystemcode" id="fSystemcode" value="${sysCode!''}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">版本号</label>
                <div class="col-sm-4">
                    <input id="fVersion" name="fVersion" value="${data.fVersion}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">更新时间</label>
                <div class="col-sm-4">
                    <input id="fUpdatedate" name="fUpdatedate" value="${(data.fUpdatedate?string('yyyy-MM-dd'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">下载地址</label>
                <div class="col-sm-10">
                    <input id="fDownloadpath" name="fDownloadpath" value="${data.fDownloadpath}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">版本更新说明</label>
                <div class="col-sm-10">
                    <textarea id="fUpdatenote" name="fUpdatenote" class="form-control" readonly>${data.fUpdatenote}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注</label>
                <div class="col-sm-10">
                    <textarea id="fRemark" name="fRemark" class="form-control" readonly>${data.fRemark}</textarea>
                </div>
            </div>
            <!-- <div class="hr-line-dashed"></div> -->
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

<@mc.ltfooter  type="view" />
</html>