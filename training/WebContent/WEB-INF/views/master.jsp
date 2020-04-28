<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="login_bean" class="jp.ne.anvil.login.LoginBean" scope="session"/>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
<h1>マスタ編集メニュー</h1>
<h3><jsp:getProperty name="login_bean" property="id" />さん</h3>
<!-- <button class="back" type="submit" name="action" onclick="location.href='./menu'">メニュー</button> -->

<div class="topicpath">
    <ol>
        <li><a href="./menu">メニュー</a></li>
        <li><p>マスタ編集メニュー</p></li>
    </ol>
</div>

<hr>
<body>
<form action="master" method="post">
<div align="center">
    <table>
        <tr>
            <td align="left">
                <button name="action" id="user" value="user">ログインユーザを編集</button>
            </td>
        </tr>
        <tr>
            <td align="left">
                <button name="action" id="dormitory" value="dormitory">社員寮の物件を編集</button>
            </td>
        <tr>
            <td align="left">
                <button name="action" id="room" value="room">部屋を編集</button>
            </td>
        </tr>
        <tr>
            <td align="left">
                <button name="action" value="toApi">API</button>
            </td>
        </tr>
    </table>
</div>
</form>
</body>
</html>