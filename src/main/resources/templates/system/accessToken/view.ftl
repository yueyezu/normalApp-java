<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="授权详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input name="fId" id="fId" value="${data.fId!''}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-3 control-label">账号</label>
                <div class="col-sm-8">
                    <input id="fUserid" name="fUserid" value="${data.fUserid}" type="text" class="form-control" readonly>
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
                    <input id="fClientmcode" name="fClientmcode" value="${data.fClientmcode}" type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">客户端IP</label>
                <div class="col-sm-8">
                    <input id="fClientip" name="fClientip" value="${data.fClientip}" type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">客户端MAC</label>
                <div class="col-sm-8">
                    <input id="fClientmac" name="fClientmac" value="${data.fClientmac}" type="text" class="form-control" readonly>
                </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
                <label class="col-sm-3 control-label">创建时间</label>
                <div class="col-sm-8">
                    <input id="fCreatetime" name="fCreatetime" value="${(data.fCreatetime?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">刷新时间</label>
                <div class="col-sm-8">
                    <input id="fLastmodifytime" name="fLastmodifytime" value="${(data.fLastmodifytime?string('yyyy-MM-dd HH:mm:ss'))!''}" type="text" class="form-control" readonly/>
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
                    <textarea id="fRemark" name="fRemark" text="${data.fRemark}" class="form-control" readonly></textarea>
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

