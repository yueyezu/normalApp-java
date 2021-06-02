<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="form" title="菜单表单" />
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
                    <input id="parentName" value="${parentName}" type="text" class="form-control" readonly/>
                </div>
                <div class="validationArea" id="menuTypeDiv">
                    <label class="col-sm-2 control-label required">菜单类型</label>
                    <div class="col-sm-4">
                        <select id="typeList" class="form-control required">
                        </select>
                        <input id="type" name="type" value="${data.type}" type="hidden" class="form-control"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="validationArea">
                    <label class="col-sm-2 control-label required">菜单名称</label>
                    <div class="col-sm-4">
                        <input id="name" name="name" value="${data.name}" type="text" class="form-control required"/>
                    </div>
                </div>
                <div class="validationArea">
                    <label class="col-sm-2 control-label required">权限标识</label>
                    <div class="col-sm-4">
                        <input id="code" name="code" value="${data.code}" type="text" class="form-control required"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">菜单连接</label>
                <div class="col-sm-10">
                    <input id="url" name="url" value="${data.url}" type="text" class="form-control"/>
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
                    <input id="sortNum" name="sortNum" value="${data.sortNum!'1'}" type="number" class="form-control"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">备注</label>
                <div class="col-sm-10">
                    <textarea class="form-control" id="remark" name="remark" class="form-control">${data.remark}</textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-4 col-sm-offset-3">
                    <button type="submit" class="btn btn-success"><span class="fa fa-check"></span>提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>

<@mc.ltfooter  type="form">
    <script type="text/javascript">
        $(function () {
            // 初始化表单信息
            initForm();

            // 表单提交方法确定
            $('#dataForm').validate({
                submitHandler: function () {
                    submit();
                }
            });

            //如果不是模块或视图，隐藏菜单类型
            if ('${type}' != '1' && '${type}' != '4') {
                $('#menuTypeDiv').hide();
            }
        });

        // 初始化数据表单
        function initForm() {
            var data = $('#dataForm').serializeJson();
            $('#icon').click(function () {
                lt.openSelect("图标选择", '${rc.contextPath}/public/iconSelect', function (win) {
                    var selIcon = win.getSelected();
                    $('#icon').val(selIcon);
                    $('#iconShow').removeClass().addClass(selIcon);
                }, "800px");
            });
            //菜单类型
            var menuTypes = [{'value': 1, 'label': '模块'}, {'value': 2, 'label': '功能'}, {'value': 3, 'label': '按钮'}, {'value': 4, 'label': '视图'}];
            $("#typeList").initSelect(menuTypes, {
                disable_search: true
            }, data.type[0]);
        }

        // 提交添加和修改的信息
        function submit() {
            var data = $('#dataForm').serializeJson();
            data.type = $('#typeList').val();

            var url = data["id"] == "" ? "/menu/save" : "/menu/update";
            $.post(url, data, function (res) {
                if (res.code == 200) {
                    lt.msgSuccess(res.msg, function () {
                        lt.closeThis();
                    });
                } else {
                    lt.alertWarning(res.msg);
                }
            })
        }
    </script>
</@mc.ltfooter>
</html>
