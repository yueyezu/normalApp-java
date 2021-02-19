<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="系统信息详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id}" type="hidden"/>
            <input name="enableFlag" id="enableFlag" type="hidden" value="${data.enableFlag}"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">系统编号</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="code" name="code" value="${data.code}" required="required" readonly/>
                </div>
                <label class="col-sm-2 control-label">系统名称</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="name" name="name" value="${data.name}" required="required" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">系统类型</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="type" name="type" defValue="${data.type!''}" readonly/>
                </div>
                <label class="col-sm-2 control-label">当前版本</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="version" name="version" value="${data.version}" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">访问密码</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="secret" name="secret" value="${data.secret}" readonly/>
                </div>
                <label class="col-sm-2 control-label">建设时间</label>
                <div class="col-sm-4">
                    <input id="devTime" name="devTime" value="${(data.devTime?string('yyyy-MM-dd'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">建设单位</label>
                <div class="col-sm-10">
                    <input id="devOrg" name="devOrg" value="${data.devOrg}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注</label>
                <div class="col-sm-10">
                    <textarea id="remark" name="remark" class="form-control" readonly>${data.remark}</textarea>
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

<@mc.ltfooter  type="view">
    <script type="text/javascript">
        $(function () {
            var defVal = $('#type').attr("defValue");
            $('#type').val(top.lt.cache.dictItems["systemType"][defVal]);
        });
    </script>
</@mc.ltfooter>
</html>
