<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>ztree 学习</title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/plugin/ztree_v3/css/zTreeStyle/zTreeStyle.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery.ztree.core.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery.ztree.exedit.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery.ztree.exhide.js"></script>

    <!--加载ztree模块学习的js方法 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/modules/ztree/ksea_ztree.js"></script>


</head>
<body>
<div>
    <ul id="treeDemo" class="ztree"></ul>
</div>
<script type="text/javascript">
    //加载ztree数据
    loadTree("${pageContext.request.contextPath}/ztree/json", "treeDemo");
</script>
</body>
</html>
