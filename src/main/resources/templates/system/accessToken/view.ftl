<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="授权详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-3 control-label">账号</label>
                <div class="col-sm-8">
                    <input id="userId" name="userId" value="${data.userId}" type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">令牌</label>
                <div class="col-sm-8">
                    <input id="fToken" name="fToken" value="${data.fToken}" type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">刷新令牌</label>
                <div class="col-sm-8">
                    <input id="fRefreshtoken" name="fRefreshtoken" value="${data.fRefreshtoken}" type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">授权类型</label>
                <div class="col-sm-8">
                    <input id="fClienttype" name="fClienttype" value="${data.fClienttype}" type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">客户端机器码</label>
                <div class="col-sm-8">
                    <input id="clientMcode" name="clientMcode" value="${data.clientMcode}" type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">客户端IP</label>
                <div class="col-sm-8">
                    <input id="clientIp" name="clientIp" value="${data.clientIp}" type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">客户端MAC</label>
                <div class="col-sm-8">
                    <input id="clientMac" name="clientMac" value="${data.clientMac}" type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">创建时间</label>
                <div class="col-sm-8">
                    <input id="createTime" name="createTime" value="${(data.createTime?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">刷新时间</label>
                <div class="col-sm-8">
                    <input id="modifyTime" name="modifyTime" value="${(data.modifyTime?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">有效时间</label>
                <div class="col-sm-8">
                    <textarea id="fEnabletime" name="fEnabletime" text="${data.fEnabletime}" class="form-control" readonly></textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">刷新有效时间</label>
                <div class="col-sm-8">
                    <input id="fEnablerefreshtime" name="fEnablerefreshtime" value="${data.fEnablerefreshtime}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">备注</label>
                <div class="col-sm-8">
                    <textarea id="remark" name="remark" text="${data.remark}" class="form-control" readonly></textarea>
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

