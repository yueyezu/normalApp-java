<#macro ltheader title="无标题" import="" >
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
    <!-- Bootstrap table -->
    <link type="text/css" href="${rc.contextPath}/static/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet"/>
    <!--fancybox-->
    <link type="text/css" href="${rc.contextPath}/static/css/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">

<#-- 框架额外引入插件 -->
    <#if import??>
        <#list import?split(",") as lib>
            <#if lib?trim != "">
                <#switch lib>
                    <#case "treegrid">
                        <link type="text/css" href="${rc.contextPath}/static/css/plugins/treegrid/jquery.treegrid.css" rel="stylesheet">
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
    <link type="text/css" href="${rc.contextPath}/static/css/lt-view-style.css" rel="stylesheet"/>

<#-- 预留添加代码的位置 -->
    <#nested />
</#macro>

<#macro ltfooter import="">
<#--公共引入内容-->
    <script type="text/javascript" src="${rc.contextPath}/static/js/jquery/jquery.min.js?v=2.1.4"></script>
    <script type="text/javascript" src="${rc.contextPath}/static/appcore/bootstrap/bootstrap.min.js?v=3.3.7"></script>
    <!--进度条-->
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <!--layer-->
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/layer/layer.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/layer/extend/layer.ext.js"></script>
    <!--Chosen-->
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/chosen/chosen.jquery.js"></script>
    <!--日期控件-->
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/datapicker/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/datapicker/locale/bootstrap-datepicker.zh-CN.min.js"></script>
    <!--fancybox-->
    <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/fancybox/jquery.fancybox.js"></script>

<#-- 框架额外引入插件 -->
    <#if import??>
        <#list import?split(",") as lib>
            <#if lib?trim != "">
                <#switch lib>
                    <#case "table">
                        <!-- Bootstrap table -->
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/bootstrap-table/bootstrap-table-export.min.js"></script>
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/bootstrap-table/export/tableExport.min.js"></script>
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
                        <!-- 加载进度条 -->
                        <script type="text/javascript" src="${rc.contextPath}/static/js/plugins/pace/pace.min.js"></script>
                        <#break />
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
    <script type="text/javascript" src="${rc.contextPath}/static/appcore/hplus/hplus.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/static/js/lt.content.web.js"></script>

<#-- 预留添加代码的位置 -->
    <#nested />
</#macro>

<#macro ltmaster>
    <div id="wrapper">
        <div id="page-wrapper" class="gray-bg" style="padding: 0;">
            <#-- 导航栏部分 -->
            <div class="menuShowDiv">
                <div class="systemLogo">
                    <span class="logo">Haier</span>
                </div>
                <div class="menuShow">
                    <ul style="width: 100%;height: 100%;">
                        <li class="firstLevel">
                            <a class="menuText" aria-expanded="false" role="button" href="#" data-toggle="dropdown">首页</a>
                        </li>
                        <li class="firstLevel">
                            <a class="menuText" aria-expanded="false" role="button" href="#" data-toggle="dropdown">行业情报</a>
                        </li>
                        <li class="firstLevel">
                            <a class="menuText" aria-expanded="false" role="button" href="#" data-toggle="dropdown">用户需求</a>
                        </li>
                        <li class="firstLevel">
                            <a class="menuText" aria-expanded="false" role="button" href="#" data-toggle="dropdown"> 技术引领 </a>
                        </li>
                        <li class="firstLevel dropdown">
                            <a class="menuText" aria-expanded="false" role="button" href="#" data-toggle="dropdown"> 方案选型 </a>
                            <ul class="dropdown-menu subitemMenu">
                                <li id="partShow"><a href="${rc.contextPath}/webView/scheme/partShowIndex">部件展示</a></li>
                                <li id="acShow"><a href="${rc.contextPath}/webView/scheme/acShowIndex">空调展示</a></li>
                            </ul>
                        </li>
                        <li class="firstLevel">
                            <a class="menuText" aria-expanded="false" role="button" href="#" data-toggle="dropdown"> 失败案例 </a>
                        </li>
                        <li class="firstLevel">
                            <a class="menuText" aria-expanded="false" role="button" href="#" data-toggle="dropdown"> 模块选型 </a>
                        </li>
                        <li class="firstLevel">
                            <a class="menuText" aria-expanded="false" role="button" href="#" data-toggle="dropdown"> 对标中心 </a>
                        </li>
                        <li class="firstLevel dropdown">
                            <a class="menuText" aria-expanded="false" role="button" href="#" data-toggle="dropdown"> 报价管理 </a>
                            <ul class="dropdown-menu subitemMenu">
                                <li id="offerShow"><a href="${rc.contextPath}/webView/offer/offerShowIndex">报价展示</a></li>
                                <li id="offerModelShow"><a href="${rc.contextPath}/webView/offer/offerModelShowIndex">型号展示</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
                <div class="userOperate">
                    <ul>
                        <li>
                            <img src="/static/img/userPhoto.png" width="30px" height="30px">
                        </li>
                        <li>
                            <div class="pageSwitch">
                                <a href="/index"> 管理后台 </a>
                            </div>
                        </li>
                        <li class="logout">
                            <a id="logout"> <i class="fa fa-sign-out"></i> 退出 </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div>
                <#-- 内容部分 -->
                <div class="wrapper wrapper-content web-content">
                    <#-- 预留添加代码的位置 -->
                    <#nested />
                </div>
                <#-- 页脚部分 -->
                <div class="footer web-footer">鲁ICP备09096283 | Copyright © 2020 海尔集团版权所有</div>
            </div>
        </div>
    </div>
</#macro>
