<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="系统信息详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input id="fId" name="fId" value="${data.fId!''}" type="hidden"/>
            <input id="fSystemcode" name="fSystemcode" value="${systemCode}" type="hidden"/>
            <input id="fType" name="fType" value="${type}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">上级菜单</label>
                <div class="col-sm-4">
                    <input id="fParentid" name="fParentid" value="${parentId!'0'}" type="hidden"/>
                    <input id="parentName" value="${parentName}" type="text" class="form-control required" readonly/>
                </div>
                <div class="validationArea" id="menuTypeDiv">
                    <label class="col-sm-2 control-label required">菜单类型</label>
                    <div class="col-sm-4">
                        <select id="fTypeList" class="form-control required" disabled>
                        </select>
                        <input id="fType" name="fType" value="${data.fType} == '1'?'模块':'视图'" type="hidden" class="form-control" readonly/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">菜单名称</label>
                <div class="col-sm-4">
                    <input id="fName" name="fName" value="${data.fName}" type="text" class="form-control" readonly />
                </div>
                <label class="col-sm-2 control-label">权限标识</label>
                <div class="col-sm-4">
                    <input id="fCode" name="fCode" value="${data.fCode}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">菜单连接</label>
                <div class="col-sm-10">
                    <input id="fUrl" name="fUrl" value="${data.fUrl}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">菜单图标</label>
                <div class="col-sm-3">
                    <input id="fIcon" name="fIcon" value="${data.fIcon}" type="text" class="form-control" readonly/>
                </div>
                <div class="col-sm-1">
                    <i id="fIconShow" style="font-size:20px;line-height: 30px;" class="${data.fIcon}"></i>
                </div>
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="fSortnum" name="fSortnum" value="${data.fSortnum!'1'}" type="number" class="form-control" readonly/>
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
</body>

<@mc.ltfooter  type="view">
    <script>
        $(function(){
            //如果不是模块或视图，隐藏菜单类型
            if('${data.fType}' != '1' && '${data.fType}' != '4'){
                $('#menuTypeDiv').hide();
            }
            //菜单类型
            var menuTypes = [{'id':1,'text':'模块'},{'id':4,'text':'视图'}];
            $("#fTypeList").initSelect(menuTypes, {
                disable_search: true
            }, '${data.fType}');
        });
    </script>
</@mc.ltfooter>
</html>
