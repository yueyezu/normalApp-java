<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="字典值详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="view" class="form-horizontal m-t">
            <input name="fId" id="fId" value="${data.fId!''}" type="hidden"/>
            <input name="fDictid" id="fDictid" value="${data.fDictid!''}" type="hidden"/>
            <input id="fEnabledelete" name="fEnabledelete" value="${data.fEnabledelete!'1'}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">编号</label>
                <div class="col-sm-4">
                    <input id="fCode" name="fCode" value="${data.fCode}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-4">
                    <input id="fName" name="fName" value="${data.fName}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="fSortnum" name="fSortnum" value="${data.fSortnum}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">描述</label>
                <div class="col-sm-10">
                    <textarea id="fDescription" name="fDescription" class="form-control" readonly>${data.fDescription}</textarea>
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
        });
    </script>
</@mc.ltfooter>
</html>