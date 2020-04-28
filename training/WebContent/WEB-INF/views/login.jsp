<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
<body>
<h1>社員寮管理 ログイン</h1>
<hr>
<form action="todo" method="post">
<input type="hidden" name="action" value="login">
<div align="center">
    <table>
        <tr>
            <td>ID</td>
            <td>
                <input type="text" name="id" value="${id}" id="id">
            </td>
        </tr>
        <tr>
            <td>パスワード</td>
            <td>
                <input type="password" name="pass" value="" id="pass" >
             </td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input type="submit" value="ログインする" id="login">
            </td>
       </tr>
    </table>
    <!-- メッセージ表示 -->
    <span class="message">${message}</span>
</div>
</form>

</body>
</html>