<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>登录页面</title>
    <%@ include file="/js/commons.jspf" %>
    <link href="${pageContext.request.contextPath}/css/public.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        if (parent != window) {
            parent.location.href = window.location.href;
        }

        function func_login() {
            document.forms[0].submit();
        }
    </script>
</head>
<body style="text-align:center;">
<form action="${pageContext.request.contextPath}/login/doLogin" method="post">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td bgcolor="#1075b1">&nbsp;</td>
        </tr>
        <tr>
            <td height="608" background="${pageContext.request.contextPath}/images/login_03.gif">
                <table width="847" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td height="318" background="${pageContext.request.contextPath}/images/login_04.gif">&nbsp;</td>
                    </tr>
                    <tr>
                        <td height="84">
                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td width="381" height="84"
                                        background="${pageContext.request.contextPath}/images/login_06.gif">&nbsp;
                                    </td>
                                    <td width="200" valign="middle"
                                        background="${pageContext.request.contextPath}/images/login_07.gif">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td width="55" height="24" valign="bottom">
                                                    <div align="right"><span class="STYLE3">用户名</span></div>
                                                </td>
                                                <td width="10" valign="bottom">&nbsp;</td>
                                                <td height="24" colspan="2" valign="bottom">
                                                    <select name="userId">
                                                        <c:forEach items="${employees}" var="employee">
                                                            <option value="${employee.id}">${employee.name}</option>
                                                        </c:forEach>
                                                    </select><br/>
                                                </td>
                                            </tr>

                                            <tr><s:fielderror/></tr>
                                        </table>
                                    </td>
                                    <td width="26"><img src="${pageContext.request.contextPath}/images/login_08.gif"
                                                        width="26" height="84"></td>
                                    <td width="67" background="${pageContext.request.contextPath}/images/login_09.gif">
                                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr>
                                                <td height="25">
                                                    <div align="center" style="cursor:hand" onclick="func_login()"><img
                                                            src="${pageContext.request.contextPath}/images/dl.gif"
                                                            width="57" height="20"></div>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td width="211" background="${pageContext.request.contextPath}/images/login_10.gif">
                                        &nbsp;
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td height="206" background="${pageContext.request.contextPath}/images/login_11.gif">&nbsp;</td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td bgcolor="#152753">&nbsp;</td>
        </tr>
    </table>
</form>
<script type="text/javascript">
    document.getElementById("username").focus();
</script>
</body>
</html>