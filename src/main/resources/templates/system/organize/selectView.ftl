<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="组织架构选择" import="tree" />
</head>
<body>
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <div id="deptTree"></div>
    </div>
</div>
<@mc.ltfooter type="list" import="tree">
    <script type="text/javascript">
        $(function () {
            $.get("${rc.contextPath}/organize/tree", function (data) {
                lt.tree.init("#deptTree", {data: data});
            });
        });

        //关闭时，供外部调用的方法
        function getSelected() {
            return lt.tree.getSelectOne('#deptTree');
        }
    </script>
</@mc.ltfooter>
</body>
</html>



