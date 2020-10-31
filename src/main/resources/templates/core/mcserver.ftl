<#macro ltheader title="无标题" type="view" import="" theme="default">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="renderer" content="webkit">

    <title>${title}</title>
    <link rel="shortcut icon" href="${rc.contextPath}/static/img/favicon.ico">

<#--公共引入内容-->
    <!--bootstrap-->
    <link type="text/css" href="${rc.contextPath}/static/appcore/bootstrap/bootstrap.min.css?v=3.3.7" rel="stylesheet"/>
    <!--字体文件-->
    <link type="text/css" href="${rc.contextPath}/static/css/font-awesome.min.css?v=4.7.0" rel="stylesheet"/>
    <!--动画插件-->
    <link type="text/css" href="${rc.contextPath}/static/css/animate.css" rel="stylesheet"/>
    <!--Chosen-->
    <link type="text/css" href="${rc.contextPath}/static/css/plugins/chosen/chosen.css" rel="stylesheet"/>
    <!--日期控件-->
    <link type="text/css" href="${rc.contextPath}/static/css/plugins/datepicker/bootstrap-datepicker.min.css" rel="stylesheet"/>

<#--固定类型引入部分-->
    <!--根据不同的引入类型，引入不同的js等文件-->
    <#switch type>
        <#case "list">
            <!-- Bootstrap table -->
            <link type="text/css" href="${rc.contextPath}/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
            <#break >
        <#case "form">
            <#break >
        <#case "view">
            <#break >
        <#default >
    </#switch>

<#-- 框架额外引入插件 -->
    <#if import??>
        <#list import?split(",") as lib>
            <#if lib?trim != "">
                <#switch lib>
                    <#case "treegrid">
                        <link type="text/css" href="${rc.contextPath}/static/js/jquery/treegrid/jquery.treegrid.css" rel="stylesheet">
                        <#break />
                    <#case "tree">
                        <link type="text/css" href="${rc.contextPath}/static/css/plugins/jsTree/style.min.css" rel="stylesheet">
                        <#break />
                    <#case "file">
                        <#break>
                </#switch>
            </#if>
        </#list>
    </#if>

<#--自定义样式部分-->
    <!--hplus样式-->
    <link type="text/css" href="${rc.contextPath}/static/appcore/hplus/style.css?v=4.1.0" rel="stylesheet"/>
    <!--自定义的全局样式-->
    <link type="text/css" href="${rc.contextPath}/static/css/lt-style.css" rel="stylesheet"/>

<#-- 预留添加代码的位置 -->
    <#nested />
</#macro>

<#macro ltfooter type="view" import="">
<#--公共引入内容-->
    <script type="text/javascript" src="${rc.contextPath}/static/js/jquery/jquery.min.js?v=2.1.4"></script>
    <script type="text/javascript" src="${rc.contextPath}/static/appcore/bootstrap/bootstrap.min.js?v=3.3.7"></script>
    <!--layer-->
    <script type="text/javascript" src="${rc.contextPath}/static/js/layer/layer.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/static/js/layer/extend/layer.ext.js"></script>
    <!--Chosen-->
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/chosen/chosen.jquery.js"></script>
    <!--日期控件-->
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/datapicker/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/datapicker/locale/bootstrap-datepicker.zh-CN.min.js"></script>

<#--固定类型引入部分-->
    <!--根据不同的引入类型，引入不同的js等文件-->
    <#switch type>
        <#case "list">
            <!-- Bootstrap table -->
            <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
            <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/bootstrap-table/bootstrap-table-export.min.js"></script>
            <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/bootstrap-table/export/tableExport.min.js"></script>
            <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
            <!-- 加载进度条 -->
            <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/pace/pace.min.js"></script>
            <#break >
        <#case "form">
            <!--jquery校验-->
            <script type="text/javascript" src="${rc.contextPath}/static/js/jquery/validate/jquery.validate.min.js"></script>
            <script type="text/javascript" src="${rc.contextPath}/static/js/jquery/validate/messages_zh.min.js"></script>
            <#break >
        <#case "view">
            <#break >
        <#default >
    </#switch>

<#-- 框架额外引入插件 -->
    <#if import??>
        <#list import?split(",") as lib>
            <#if lib?trim != "">
                <#switch lib>
                    <#case "md5">
                        <script type="text/javascript" src="${rc.contextPath}/static/js/jquery/jquery.md5.js"></script>
                        <#break />
                    <#case "highcharts">
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/highcharts/highstock.js"></script>
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/highcharts/exporting.js"></script>
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/highcharts/highcharts-zh_CN.js"></script>
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/highcharts/no-data-to-display.js"></script>
                        <#break />
                    <#case "echarts">
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/echarts/echarts.min.js"></script>
                        <#break />
                    <#case "treegrid">
                        <script type="text/javascript" src="${rc.contextPath}/static/js/jquery/treegrid/jquery.treegrid.min.js"></script>
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/bootstrap-table/bootstrap-table-treegrid.min.js"></script>
                        <#break />
                    <#case "tree">
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/jsTree/jstree.min.js"></script>
                        <#break />
                    <#case "paginator">
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/paginator/bootstrap-paginator.min.js"></script>
                        <#break />
                    <#case "hoverdropdown">
                        <script type="text/javascript" src="${rc.contextPath}/static/appcore/bootstrap/bootstrap-hover-dropdown.js"></script>
                        <#break />
                    <#case "file">
                        <#break>
                </#switch>
            </#if>
        </#list>
    </#if>

<#--自定义样式部分-->
    <!-- 自定义js -->
    <script type="text/javascript" src="${rc.contextPath}/static/js/lt.util.js?v=1.0.0"></script>
    <script type="text/javascript" src="${rc.contextPath}/static/js/lt.content.js?v=1.0.0"></script>

<#-- 预留添加代码的位置 -->
    <#nested />
</#macro>
