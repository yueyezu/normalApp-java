<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="系统日志详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">系统名称</label>
                <div class="col-sm-10">
                    <input id="fSystem" name="fSystem" value="${data.fSystem}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">模块</label>
                <div class="col-sm-4">
                    <input id="module" name="module" value="${data.module}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">操作类型</label>
                <div class="col-sm-4">
                    <input id="optType" name="optType" value="${data.optType}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">操作内容</label>
                <div class="col-sm-10">
                    <textarea id="optContent" name="optContent" class="form-control" style="height: 200px;" readonly>${data.optContent}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">IP城市</label>
                <div class="col-sm-4">
                    <input id="ipCity" name="ipCity" value="${data.ipCity}" type="text" class="form-control" readonly/>
                </div>
            </div>
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
        </form>
    </div>
</div>
</body>

<@mc.ltfooter  type="view">
    <script type="text/javascript">
    </script>
</@mc.ltfooter>
</html>