<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="组织详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input id="fId" name="fId" value="${data.fId!''}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">父级</label>
                <div class="col-sm-4">
                    <input id="fParentid" name="fParentid" value="${parentId!'0'}" type="hidden"/>
                    <input id="parentName " value="${parentName}" type="text" class="form-control" readonly/>
                </div>
            </div>
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
                <label class="col-sm-2 control-label">部门说明</label>
                <div class="col-sm-10">
                    <textarea id="fDescription" name="fDescription" class="form-control" readonly>${data.fDescription}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">负责人</label>
                <div class="col-sm-4">
                    <input id="fManager" value="${fManager}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">电话</label>
                <div class="col-sm-4">
                    <input id="fPhone" name="fPhone" value="${data.fPhone}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">联系地址</label>
                <div class="col-sm-10">
                    <input id="fAddress" name="fAddress" value="${data.fAddress}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="fSortnum" name="fSortnum" value="${data.fSortnum!'1'}" placeholder="排序码" type="number" class="form-control" readonly/>
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
</body>

<@mc.ltfooter  type="view"></@mc.ltfooter>
</html>

