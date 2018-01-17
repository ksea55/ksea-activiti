<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>ztree用于一次性加载所有节点，适用于案例，大纲条目的加载案例</title>

    <script type="text/javascript">
        //声明全局变量必须先加载
        var ctx = "${pageContext.request.contextPath}";
    </script>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/plugin/ztree_v3/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/modules/layer/theme/default/layer.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery-3.2.1.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery.ztree.core.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery.ztree.exedit.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery.ztree.exhide.js"></script>


    <script type="text/javascript" src="${pageContext.request.contextPath}/modules/layer/layer.js"></script>


</head>
<body>
<div>
    <ul style="list-style: none;display:inline-block">
        <li>描述:ztree用于一次性加载所有节点，适用于案例，大纲条目的加载案例</li>
        <li><a href="javascript:void(0)" onclick="addNode()">添加</a></li>
        <li><a href="javascript:void(0)" onclick="renameNode()">重命名</a></li>
        <li><a href="javascript:void(0)" onclick="removeNode()">删除</a></li>
    </ul>
    <ul id="treeDemo" class="ztree"></ul>
    <div>
        单击节点查看节点信息:<br/>
        <div id="node"></div>
    </div>


</div>


<!--加载ztree模块学习的js方法 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/modules/ztree/load_all_tree.js"></script>

<script type="text/javascript">
    //加载ztree数据
    loadTree("${pageContext.request.contextPath}/ztree/json", "treeDemo");
</script>


</body>
</html>
