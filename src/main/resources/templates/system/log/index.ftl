<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="日志列表"/>
    <style>
        #dataTable {
            text-align: center;
            table-layout: fixed;
            word-break: break-all;
        }

        #dataTable td {
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

        #dataTable td:hover {
            overflow: auto;
            white-space: pre-wrap;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <!--系统列表显示位置-->
                <ul id="systemPnl" class="nav nav-tabs m-b-sm">
                    <#list systemList as system>
                        <li <#if system_index ==0>class="active"</#if>><a code="${system.code}" href="javascript:void(0)">${system.name}</a></li>
                    </#list>
                </ul>
                <!--菜单列表信息-->
                <form role="form" class="form-inline query-form">
                    <div class="form-group">
                        <span>操作时间：</span>
                        <input type="text" class="form-control" id="startTime" name="startTime" placeholder="起始时间"/>
                        <span>-</span>
                        <input type="text" class="form-control" id="endTime" name="endTime" placeholder="结束时间"/>
                    </div>
                    <div class="input-group">
                        <input type="text" class="form-control" id="queryKeyword" placeholder="功能模块、操作内容"/>
                        <span class="input-group-btn">
                            <button type="button" class="btn btn-primary" style="margin-bottom:0;" onclick="refreshTable()"><i class="fa fa-search"></i>查询</button>
                        </span>
                    </div>
                </form>
                <div id="tableToolbar" role="group" class="t-bar">
                    <@shiro.hasPermission name="funcLog-btnLogsClear">
                        <a class="btn btn-danger" title="清空日志" onclick="logsClear()">
                            <i class="fa fa-trash-o" aria-hidden="true"></i>清空日志
                        </a>
                    </@shiro.hasPermission>
                    <@shiro.hasPermission name="funcLog-btnDelete">
                        <a class="btn btn-warning" title="批量删除" onclick="batchDelete()">
                            <i class="fa fa-times" aria-hidden="true"></i>批量删除
                        </a>
                    </@shiro.hasPermission>
                </div>
                <table id="dataTable" data-mobile-responsive="true"></table>
            </div>
        </div>
    </div>
</div>
<@mc.ltfooter  type="list">
    <script type="text/javascript">
        // 本功能所有请求的根路径
        var prefix = '${rc.contextPath}/log';
        var nowSystem = '';
        $(function () {
            nowSystem = $('#systemPnl li.active a').attr('code');   // 初始化当前系统的值
            initQuery();
            initTable();
        });

        // 初始化查询列表
        function initTable() {
            lt.dataTable.init("#dataTable", {
                url: prefix + "/page",
                showExport: true,
                height: 490,
                exportDataType: 'all',               //导出checkbox选中的行数: all\selected\
                toolbar: '#tableToolbar',            //工具按钮用哪个容器
                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                pagination: true,                    //是否显示分页（*）
                queryParams: function (params) {
                    params.keyword = $('#queryKeyword').val();
                    params.startTime = $('#startTime').val();
                    params.endTime = $('#endTime').val();
                    params.systemCode = nowSystem;
                    return params;
                },
                columns: [{field: 'ck', checkbox: true},
                    {
                        title: '系统', field: 'systemCode',
                        formatter: function (value, row, index) {
                            return '<a onclick="view(\'' + row.id + '\')">' + value + '</a>';
                        }
                    },
                    {title: '功能模块', field: 'module'},
                    {title: '操作类型', field: 'optType'},
                    {title: '操作内容', field: 'optContent'},
                    {title: 'IP地址', field: 'ipAddr', visible: false},
                    {title: 'IP城市', field: 'ipCity', visible: false},
                    {title: '操作人', field: 'createBy'},
                    {title: '操作时间', field: 'createTime', width: 150},
                    {
                        title: "操作", field: "id",
                        formatter: function (value, row) {
                            var d = '<a class="btn btn-danger btn-xs m-r-xs" onclick="del(\'' + value + '\');return false;"><i class="fa fa-remove"></i>删除</a>';
                            return d;
                        }
                    }
                ]
            });
        }

        // 初始化查询条件
        function initQuery() {
            // 系统选择的事件
            $('#systemPnl a').click(function (e) {
                e.preventDefault()
                var $this = $(this);
                var clickSystem = $this.attr('code');
                if (clickSystem != nowSystem) {
                    $(this).tab('show');
                    nowSystem = clickSystem;
                    refreshTable();
                }
            });
            $("#queryType").initSelect(top.lt.cache.dictItemArr.systemType, {
                disable_search: true
            });
            $("#startTime").datepicker({format: 'yyyy-mm-dd'});
            $("#endTime").datepicker({format: 'yyyy-mm-dd'});
        }

        // 刷新查询列表
        function refreshTable() {
            $('#dataTable').bootstrapTable("refresh");
        }

        // 详情查看的操作方法
        function view(id) {
            lt.open("查看日志信息", prefix + "/view/" + id);
        }

        // 删除的代码
        function del(id) {
            lt.confirm("确定要删除选中数据？", function () {
                $.post(prefix + '/delete', {id: id}, function (res) {
                    if (res.code == 200) {
                        lt.alertSuccess(res.msg, function () {
                            refreshTable();
                        });
                    } else {
                        lt.alertError(res.msg);
                    }
                });
            });
        }

        //清空按钮操作
        function logsClear() {
            lt.confirm("确定要清空所有日志？", function () {
                $.post(prefix + '/clear', function (res) {
                    if (res.code == 200) {
                        lt.alertSuccess(res.msg, function () {
                            refreshTable();
                        });
                    } else {
                        lt.alertError(res.msg);
                    }
                });
            });
        }

        //批量删除
        function batchDelete() {
            lt.confirm("确定要批量删除日志？", function () {
                var allSel = $('#dataTable').bootstrapTable('getAllSelections');
                var ids = "";
                for (var i = 0; i < allSel.length; i++) {
                    ids = ids + allSel[i].id + ',';
                }
                ids = ids.substring(0, ids.length - 1);
                $.post(prefix + '/batchDelete', {ids: ids}, function (res) {
                    if (res.code == 200) {
                        lt.alertSuccess(res.msg, function () {
                            refreshTable();
                        });
                    } else {
                        lt.alertError(res.msg);
                    }
                });
            });
        }
    </script>
</@mc.ltfooter>
</body>
</html>
