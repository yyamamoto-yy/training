<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="login_bean" class="jp.ne.anvil.login.LoginBean" scope="session"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
<body>
<h1>メニュー</h1>
<h3><jsp:getProperty name="login_bean" property="id" />さん</h3>
<hr>
<div align="center">
    <table>
        <tr>
            <td>
                <button type="submit" name="action" onclick="location.href='./room'">部屋を検索する</button>
            </td>
        </tr>
        <tr>
            <td>
                <button type="submit" name="action" onclick="location.href='./master'">マスタを編集する</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>