<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>
<%@ page import="jp.ne.anvil.master.user.*" %>
<jsp:useBean id="login_bean" class="jp.ne.anvil.login.LoginBean" scope="session"/>
<jsp:useBean id="user_add_bean" class="jp.ne.anvil.master.user.UserAddBean" scope="request"/>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>ログインユーザ編集</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/user.css">

</head>
<body>
<h1>パスワード変更</h1>
<div class="topicpath">
        <ol>
            <li><a href="./menu">メニュー</a></li>
            <li><a href="./master">マスタ編集メニュー</a></li>
            <li><a href="./MasterUser">ログインユーザ編集</a></li>
            <li><p>パスワード変更</p></li>
        </ol>
    </div>
<hr>
<% String eId =(String)request.getAttribute("eId"); %>
<!-- 更新 -->
<h2>ユーザ名：<%= eId %></h2>
<form action="MasterUser" method="post">
    <input type="text" name="ePass" value="" required>
    <input type="hidden" name="eId" value="${eId}">
    <button type="submit" name="action" value="user_update">変更</button>
</form>

<p></p>

<form action="MasterUser" method="post">
    <button class="back" type="submit" name="action" value="user_update_cancel">キャンセル</button>
</form>

</body>
</html>