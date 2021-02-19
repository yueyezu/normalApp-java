<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="系统版本详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <input name="enableFlag" id="enableFlag" value="${data.enableFlag!'1'}" type="hidden"/>
            <input name="systemCode" id="systemCode" value="${sysCode!''}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">版本号</label>
                <div class="col-sm-4">
                    <input id="version" name="version" value="${data.version}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">更新时间</label>
                <div class="col-sm-4">
                    <input id="updateDate" name="updateDate" value="${(data.updateDate?string('yyyy-MM-dd'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">下载地址</label>
                <div class="col-sm-10">
                    <input id="downloadPath" name="downloadPath" value="${data.downloadPath}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">版本更新说明</label>
                <div class="col-sm-10">
                    <textarea id="updateNote" name="updateNote" class="form-control" readonly>${data.updateNote}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注</label>
                <div class="col-sm-10">
                    <textarea id="remark" name="remark" class="form-control" readonly>${data.remark}</textarea>
                </div>
            </div>
            <!-- <div class="hr-line-dashed"></div> -->
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
</body>

<@mc.ltfooter  type="view" />
</html>