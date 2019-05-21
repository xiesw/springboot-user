<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看用户列表</title>
</head>
<body>
	<!-- 通过session来控制，只有管理员角色才能访问当前页面，非管理员登录直接跳登录页 -->
	<c:if test="${sessionScope.name == null || sessionScope.type != 'A'}">
		<c:redirect url="/login" />
	</c:if>

	<table border="1" cellspacing="0">
		<tr>
			<td>ID</td>
			<td>名字</td>
			<td>用户类型</td>
			<td>注册时间</td>
			<td>操作类型</td>
		</tr>
		<!-- 循环展示普通用户列表数据 -->
		<c:forEach items="${lstViewVOs}" var="viewVo" varStatus="id">
			<tr>
				<td>${viewVo.id}</td>
				<td>${viewVo.name}</td>
				<td>${viewVo.type}</td>
				<td>${viewVo.gmtCreate}</td>
				<td><a href="/delete?id=${viewVo.id}">删除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>