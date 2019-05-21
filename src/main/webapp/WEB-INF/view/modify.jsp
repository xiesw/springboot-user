<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户信息修改</title>
</head>
<body>
	<!-- 通过session来控制，登录了才能修改密码 -->
	<c:if test="${sessionScope.name == null}">
		<c:redirect url="/login" />
	</c:if>

	<form action="/toModify" method="post">
		<table>
			<tr>
				<td>原密码：</td>
				<td><input type="text" name="password" size=25></td>
			</tr>
			<tr>
				<td>新密码：</td>
				<td><input type="text" name="newPassword" size=25></td>
			</tr>
			<tr>
				<td>确认新密码：</td>
				<td><input type="text" name="confirmNewPassword" size=25></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="确认修改"></td>
			</tr>

		</table>
	</form>
</body>
</html>