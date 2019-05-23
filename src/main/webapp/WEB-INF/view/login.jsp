<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户登录</title>
</head>
<body>
	<c:if test="${not empty responseVO.message}">${responseVO.message}</c:if>
	<form action="/toLogin" method="post">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" name="name" size=25
					value="<c:if test="${not empty registerDTO && not empty registerDTO.name}">${registerDTO.name}</c:if>"></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="text" name="password" size=25></td>
			</tr>
			<tr>
				<td>验证码</td>
				<td><input id="checkCode" name="checkCode" class="checkCode"
					type="text" /> <img
					style="cursor: pointer; float: right; margin-left: 3px;"
					src="/imageCode"
					onclick="this.src=this.src + '?' + new Date().valueOf();"
					id="validateImg" alt="验证码" class="codePic" title="验证码。点击此处更新验证码。" />
				</td>
			</tr>
			<tr>
				<td>用户类型：</td>
				<td><select name="type">
						<option value="O">普通用户</option>
						<option value="A">管理员用户</option>
				</select></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="登录"></td>
			</tr>

		</table>
	</form>
</body>
</html>