<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add node</title>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/modules/layer/theme/default/layer.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/plugin/ztree_v3/js/jquery-3.2.1.js"></script>
    <!--加载ztree模块学习的js方法 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/modules/ztree/ksea_ztree.js"></script>


</head>
<body>
<form id="nodeForm" action="${pageContext.request.contextPath}/ztree/save" method="post">
    sid: <input type="text" name="sid"/><br/>
    name: <input type="text" name="name"/><br/>
    parent:<input type="text" name="parent" value="${parent}"/><br/>

</form>


<script type="text/javascript">
    function submitForm(){
        $("#nodeForm").submit();
    }
</script>
</body>
</html>
