<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录后的首页</title>
</head>
<body>
	<!-- 通过session来控制，只有登录了才能查看页面内容 -->
	<c:if test="${sessionScope.name == null}">
		<c:redirect url="/login" />
	</c:if>

	<p>
		欢迎您: <b style="color: red">${sessionScope.name}</b>, 用户身份是: <b
			style="color: red">${sessionScope.typeName}</b>
	</p>
	<p>功能列表：</p>
	<c:if test="${sessionScope.name != null && sessionScope.type == 'A'}">
		<a href="/toView">查看用户列表</a>
		<a href="/add">添加用户</a>
	</c:if>
	<a href="/modify">修改密码</a>
	<a href="/loginOut">注销登录</a>
</body>
</html>