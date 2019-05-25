<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户注册</title>
</head>
<body>
    <c:if test="${not empty responseVO.message}">${responseVO.message}</c:if>
    <form action="/toRegister" method="post">
        <table>
            <tr>
                <td>用户名：</td>
                <td><input type="text" id="name" name="name" size=25></td>
            </tr>
            <tr>
                <td>密码：</td>
                <td><input type="text" id="password" name="password" size=25></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="注册"></td>
            </tr>
        </table>
    </form>
</body>
</html>