<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="view" title="字典值详情" />
</head>
<body class="bg-white">
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <form id="dataForm" role="view" class="form-horizontal m-t">
            <input name="id" id="id" value="${data.id!''}" type="hidden"/>
            <input name="dictId" id="dictId" value="${data.dictId!''}" type="hidden"/>
            <input id="enableDelete" name="enableDelete" value="${data.enableDelete!'1'}" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">编号</label>
                <div class="col-sm-4">
                    <input id="code" name="code" value="${data.code}" type="text" class="form-control" readonly/>
                </div>
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-4">
                    <input id="name" name="name" value="${data.name}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">排序码</label>
                <div class="col-sm-4">
                    <input id="sortNum" name="sortNum" value="${data.sortNum}" type="text" class="form-control" readonly/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">描述</label>
                <div class="col-sm-10">
                    <textarea id="description" name="description" class="form-control" readonly>${data.description}</textarea>
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
        });
    </script>
</@mc.ltfooter>
</html>