<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="系统信息详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="form" class="form-horizontal m-t">
            <input id="id" name="id" value="${data.id!''}" type="hidden"/>
            <input id="systemCode" name="systemCode" value="${systemCode}" type="hidden"/>
            <input id="type" name="type" value="${type}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">上级菜单</label>
                <div class="col-sm-4">
                    <input id="parentId" name="parentId" value="${parentId!'0'}" type="hidden"/>
                    <input id="parentName" value="${parentName}" type="text" class="form-control required" readonly/>
                </div>
                <div class="validationArea" id="menuTypeDiv">
                    <label class="col-sm-2 control-label required">菜单类型</label>
                    <div class="col-sm-4">
                        <select id="typeList" class="form-control required" disabled>
                        </select>
                        <input id="type" name="type" value="${data.type} == '1'?'模块':'视图'" type="hidden" class="form-control" readonly/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">菜单名称</label>
                <div class="col-sm-4">
                    <input id="name" name="name" value="${data.name}" type="text" class="form-control" readonly />
                </div>
                <label class="col-sm-2 control-label">权限标识</label>
                <div class="col-sm-4">
                    <input id="code" name="code" value="${data.code}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">菜单连接</label>
                <div class="col-sm-10">
                    <input id="url" name="url" value="${data.url}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">菜单图标</label>
                <div class="col-sm-3">
                    <input id="icon" name="icon" value="${data.icon}" type="text" class="form-control" readonly/>
                </div>
                <div class="col-sm-1">
                    <i id="iconShow" style="font-size:20px;line-height: 30px;" class="${data.icon}"></i>
                </div>
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="sortNum" name="sortNum" value="${data.sortNum!'1'}" type="number" class="form-control" readonly/>
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
</body>

<@mc.ltfooter  type="view">
    <script>
        $(function(){
            //如果不是模块或视图，隐藏菜单类型
            if('${data.type}' != '1' && '${data.type}' != '4'){
                $('#menuTypeDiv').hide();
            }
            //菜单类型
            var menuTypes = [{'id':1,'text':'模块'},{'id':4,'text':'视图'}];
            $("#typeList").initSelect(menuTypes, {
                disable_search: true
            }, '${data.type}');
        });
    </script>
</@mc.ltfooter>
</html>
