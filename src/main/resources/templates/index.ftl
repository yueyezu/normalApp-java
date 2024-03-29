<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/>
    <![endif]-->

    <title>励图开发框架</title>

    <link rel="shortcut icon" href="${rc.contextPath}/static/img/favicon.ico">

    <link href="${rc.contextPath}/static/appcore/bootstrap/bootstrap.min.css?v=3.3.7" rel="stylesheet">
    <link href="${rc.contextPath}/static/css/font-awesome.css?v=4.7.0" rel="stylesheet">
    <link href="${rc.contextPath}/static/css/animate.css" rel="stylesheet">
    <link href="${rc.contextPath}/static/appcore/hplus/style.css" rel="stylesheet">

    <style type="text/css">
        .sysName {
            margin-top: 19px;
            margin-left: 5px;
        }

        .labelCount {
            top: 15px !important;
        }

        .unReadOuter {
            width: 150px !important;
        }

        .link-block {
            padding: 0;
        }
    </style>

</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close">
            <i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <#--用户信息部分-->
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" width="64" height="64" class="img-circle" id="userHeadPortrait" src="${userPhoto}"/></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear">
                                <span class="block m-t-xs"><strong class="font-bold">${userMsg.nickName}</strong></span>
                                <span class="text-muted text-xs block">修改用户<b class="caret"></b></span>
                            </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a class="J_menuItem" href="${rc.contextPath}/main/changepassword">修改密码</a></li>
                            <li><a class="J_menuItem" href="${rc.contextPath}/user/changeHeader" needToken="true">修改头像</a></li>
                            <li><a class="J_menuItem" href="${rc.contextPath}/main/userinfo">个人资料</a></li>
                            <li class="divider"></li>
                            <li id="liLogout"><a>安全退出</a>
                            </li>
                        </ul>
                    </div>
                    <div class="logo-element">
                        <span><img alt="image" width="58" height="58" class="img-circle" src="${userPhoto}"/></span>
                    </div>
                </li>
                <#--主页-->
                <li>
                    <a href="#">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">主页</span>
                    </a>
                </li>
                <#list menuList as module>
                <#--三级菜单-->
                    <li>
                        <#if module.children?? && (module.children?size > 0)>
                            <a href="#"><i class="${module.icon}"></i><span class="nav-label">${module.name}</span><span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <#list module.children as menu>
                                    <li>
                                        <#if menu.children?? && (menu.children?size > 0)>
                                            <a href="#"><i class="${menu.icon}"></i>${menu.name}<span class="fa arrow"></span> </a>
                                            <ul class="nav nav-third-level">
                                                <#list menu.children as submenu>
                                                    <li>
                                                        <a class="J_menuItem" href="${submenu.url}"><i class="${submenu.icon}"></i>${submenu.name}</a>
                                                    </li>
                                                </#list>
                                            </ul>
                                        <#else>
                                            <a class="J_menuItem" href="${menu.url}"><i class="${menu.icon}"></i>${menu.name}</a>
                                        </#if>
                                    </li>
                                </#list>
                            </ul>
                        <#else>
                            <a class="J_menuItem" href="${module.url}"><i class="fa fa-columns"></i> <span class="nav-label">${module.name}</span></a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#">
                        <i class="fa fa-bars"></i>
                    </a>
                    <label class="sysName">
                        励图开发框架
                    </label>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-envelope"></i> <span class="label label-warning labelCount" id="unreadCount"></span>
                        </a>
                        <ul class="dropdown-menu dropdown-messages unReadOuter" id="unReadOuter">
                            <li class="m-t-xs">
                                <div class="dropdown-messages-box">
                                    <div class="media-body">
                                        <strong id="unreadName"></strong>
                                    </div>
                                </div>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a class="J_menuItem" href="rsumResume/index"><i class="fa fa-envelope"></i><strong>查看所有消息</strong></a>
                                </div>
                            </li>
                        </ul>
                    </li>

                    <li class="hidden-xs">
                        <a href="index_v1.html" class="J_menuItem" data-index="0">
                            快捷功能
                        </a>
                    </li>
                    <li class="dropdown hidden-xs">
                        <a class="right-sidebar-toggle" aria-expanded="false">
                            <i class="fa fa-tasks"></i> 主题
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft">
                <i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:void(0);" class="active J_menuTab" data-id="index_v1.html">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight">
                <i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">标签操作<span class="caret"></span>
                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabRefresh"><a>刷新当前选项卡</a></li>
                    <!--<li class="J_tabShowActive"><a>定位当前选项卡</a></li>
                    <li class="divider"></li>-->
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
                </ul>
            </div>
            <a id="btnLogout" href="javascript:void(0);" class="roll-nav roll-right J_tabExit">
                <i class="fa fa fa-sign-out"></i> 退出
            </a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${rc.contextPath}/home" frameborder="0" data-id="index_v1.html" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">
                &copy; 2020-2025 <a href="https://xk.qust.edu.cn/" target="_blank">科技大学</a>
            </div>
        </div>
    </div>
    <!--右侧部分结束-->
    <!--右侧边栏开始-->
    <div id="right-sidebar">
        <div class="sidebar-container">
            <ul class="nav nav-tabs navs-2">
                <li class="active">
                    <a data-toggle="tab" href="#tab-1"> <i class="fa fa-gear"></i> 主题 </a>
                </li>
                <li class="">
                    <a data-toggle="tab" href="#tab-2"> 通知 </a>
                </li>
            </ul>
            <div class="tab-content">
                <div id="tab-1" class="tab-pane active">
                    <div class="sidebar-title">
                        <h3><i class="fa fa-comments-o"></i> 主题设置</h3>
                        <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                    </div>
                    <div class="skin-setttings">
                        <div class="title">主题设置</div>
                        <div class="setings-item">
                            <span>收起左侧菜单</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                                    <label class="onoffswitch-label" for="collapsemenu">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                            <span>固定顶部</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                                    <label class="onoffswitch-label" for="fixednavbar">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                            <span>固定宽度</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                                    <label class="onoffswitch-label" for="boxedlayout">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="title">皮肤选择</div>
                        <div class="setings-item default-skin nb">
                            <span class="skin-name"><a href="#" class="s-skin-0">默认皮肤</a></span>
                        </div>
                        <div class="setings-item blue-skin nb">
                            <span class="skin-name "><a href="#" class="s-skin-1">蓝色主题</a></span>
                        </div>
                        <div class="setings-item yellow-skin nb">
                            <span class="skin-name "><a href="#" class="s-skin-3">黄色/紫色主题</a></span>
                        </div>
                    </div>
                </div>
                <div id="tab-2" class="tab-pane">
                    <div class="sidebar-title">
                        <h3><i class="fa fa-comments-o"></i> 最新通知</h3>
                        <small><i class="fa fa-tim"></i> 您当前有10条未读信息</small>
                    </div>
                    <div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="${rc.contextPath}/static/img/p1.jpg">
                                    <div class="m-t-xs">
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                    </div>
                                </div>
                                <div class="media-body">
                                    据天津日报报道：瑞海公司董事长于学伟，副董事长董社轩等10人在13日上午已被控制。
                                    <br>
                                    <small class="text-muted">今天 4:21</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="${rc.contextPath}/static/img/p2.jpg">
                                </div>
                                <div class="media-body">
                                    HCY48之音乐大魔王会员专属皮肤已上线，快来一键换装拥有他，宣告你对华晨宇的爱吧！
                                    <br>
                                    <small class="text-muted">昨天 2:45</small>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--右侧边栏结束-->
</div>

<!-- 全局js -->
<script type="text/javascript" src="${rc.contextPath}/static/js/jquery/jquery.min.js?v=2.1.4"></script>
<script type="text/javascript" src="${rc.contextPath}/static/appcore/bootstrap/bootstrap.min.js?v=3.3.7"></script>
<script type="text/javascript" src="${rc.contextPath}/static/js/jquery/jquery.metisMenu.js"></script>
<script type="text/javascript" src="${rc.contextPath}/static/js/layer/layer.min.js"></script>
<#--进度条-->
<script type="text/javascript" src="${rc.contextPath}/static/js/jquery/jquery.slimscroll.min.js"></script>
<!-- 加载进度条 -->
<script type="text/javascript" src="${rc.contextPath}/static/js/plugins/pace/pace.min.js"></script>

<!-- 自定义js -->
<script type="text/javascript" src="${rc.contextPath}/static/appcore/hplus/contabs.js"></script>
<script type="text/javascript" src="${rc.contextPath}/static/js/lt.util.js"></script>
<script type="text/javascript" src="${rc.contextPath}/static/appcore/hplus/hplus.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
