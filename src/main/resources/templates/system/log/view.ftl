<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="系统日志详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="fId" id="fId" value="${data.fId!''}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">系统名称</label>
                <div class="col-sm-10">
                    <input id="fSystem" name="fSystem" value="${data.fSystem}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">模块</label>
                <div class="col-sm-4">
                    <input id="fModule" name="fModule" value="${data.fModule}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">操作类型</label>
                <div class="col-sm-4">
                    <input id="fOpttype" name="fOpttype" value="${data.fOpttype}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">操作内容</label>
                <div class="col-sm-10">
                    <textarea id="fOptcontent" name="fOptcontent" class="form-control" style="height: 200px;" readonly>${data.fOptcontent}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">IP城市</label>
                <div class="col-sm-4">
                    <input id="fIpcity" name="fIpcity" value="${data.fIpcity}" type="text" class="form-control" readonly/>
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
        </form>
    </div>
</div>
</body>

<@mc.ltfooter  type="view">
    <script type="text/javascript">
    </script>
</@mc.ltfooter>
</html>