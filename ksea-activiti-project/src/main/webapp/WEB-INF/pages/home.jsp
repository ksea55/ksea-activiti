<%--
  Created by IntelliJ IDEA.
  User: mexican
  Date: 2018/1/2
  Time: 22:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>home</title>
</head>
<body>

没有事务
<form action="${pageContext.request.contextPath}/student/save" method="post">

    <input type="text" name="sid" value="12">
    <input type="text" name="name" value="jacky">
    <input type="submit"/>

</form>

有事务
<form action="${pageContext.request.contextPath}/student/add" method="post">

    <input type="text" name="sid" value="12">
    <input type="text" name="name" value="jacky">
    <input type="submit"/>

</form>

</body>
</html>
