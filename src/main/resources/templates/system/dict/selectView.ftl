<!doctype html>
<html lang="en">
<head>
    <@mc.ltheader type="list" title="字典信息选择" import="tree" />
</head>
<body>
<div class="ibox float-e-margins">
    <div class="ibox-content">
        <div id="dictTree"></div>
    </div>
</div>
<@mc.ltfooter type="list" import="tree">
    <script type="text/javascript">
        $(function () {
            $.get("${rc.contextPath}/dict/tree", function (data) {
                lt.tree.init("#dictTree", {data: data});
            });
        });

        //关闭时，供外部调用的方法
        function getSelected() {
            return lt.tree.getSelectOne('#dictTree');
        }
    </script>
</@mc.ltfooter>
</body>
</html>



