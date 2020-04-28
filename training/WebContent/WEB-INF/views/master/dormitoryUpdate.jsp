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
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dormitory.css">

</head>
<body>
<h1>物件名変更</h1>
<div class="topicpath">
        <ol>
            <li><a href="./menu">メニュー</a></li>
            <li><a href="./master">マスタ編集メニュー</a></li>
            <li><a href="./MasterDormitory">社員寮マスタ編集</a></li>
            <li><p>物件名変更</p></li>
        </ol>
    </div>
<hr>
<% String eId =(String)request.getAttribute("eId"); %>
<% String eName =(String)request.getAttribute("eName"); %>
<!-- 更新 -->
<div class="input-area">

<h3>物件ID：<%= eId %>　　物件名：<%= eName %></h3>
<form class="update-form" action="MasterDormitory" method="post" id="input-form">
    <input type="hidden" name="eId" value="${eId}">
    <input type="hidden" name="beforeName" value="${eName}">
    <label>変更後の物件名:</label>
    <input type="text" name="eName" value="" required><p></p>
    <button type="submit" name="action" value="dormitory_update">変更</button>
</form>
<p></p>
<form action="MasterDormitory" method="post" class="up-cancel">
    <button class="back" type="submit" name="action" value="dormitory_update_cancel">キャンセル</button>
</form>

</div>
</body>
</html>