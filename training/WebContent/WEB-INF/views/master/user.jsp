<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="jp.ne.anvil.master.user.*" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.ArrayList" %>
<jsp:useBean id="login_bean" class="jp.ne.anvil.login.LoginBean" scope="session"/>
<jsp:useBean id="user_list" class="java.util.ArrayList" type="java.util.ArrayList<jp.ne.anvil.master.user.UserBean>" scope="request"/>
<jsp:useBean id="user_search_bean" class="jp.ne.anvil.master.user.UserSearchBean" scope="request"/>
<jsp:useBean id="user_add_bean" class="jp.ne.anvil.master.user.UserAddBean" scope="request"/>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>ログインユーザ編集</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/user.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="scripts/jquery-3.4.1.min.js"></script>

</head>
<body>
<header>
    <h1>ログインユーザ編集</h1>
	<div class="topicpath">
	    <ol>
	        <li><a href="./menu">メニュー</a></li>
	        <li><a href="./master">マスタ編集メニュー</a></li>
	        <li><p>ログインユーザ編集</p></li>
	    </ol>
	</div>
</header>
<hr>


    <!-- 検索 -->

	<form action="MasterUser" method="post">
		<table>
		<tr>
			<td class="label">ユーザ名</td>
			<td><input type="text" name="uId" value="<jsp:getProperty name="user_search_bean" property="userId" />" ></td>
		</tr>
		<tr>
			<td class="label">更新日時</td>
			<td><input type="date" name="cDtFrom" value="<jsp:getProperty name="user_search_bean" property="createDtFrom" />" >から&nbsp;&nbsp;
			<input type="date" name="cDtTo" value="<jsp:getProperty name="user_search_bean" property="createDtTo" />" >まで&nbsp;</td>
		</tr>
		</table>
		<button class="serch" type="submit" name="action" value="user_search">検索</button>
		<button class="serch" type="submit" name="action" value="user_download"
		      onclick="return confirm('検索結果をダウンロードしますか？');">ダウンロード</button>
		<p></p>
	</form>




	<!-- 登録 -->
	<form action="MasterUser" method="post">
	    <table>
	        <tr>
		       <td class="label">ユーザ名</td>
	           <td><input type="text" name="aId"  value="<jsp:getProperty name="user_add_bean" property="addId" />" ></td>
	        </tr>
	        <tr>
		       <td class="label">パスワード</td>
	           <td><input type="text" name="aPass" value="<jsp:getProperty name="user_add_bean" property="addPass" />"  ></td>
	        </tr>
	    </table>
	    <button class="add" type="submit" name="action" value="user_add">新規登録</button>
	    <button type="submit" name="action"  form="ajax_add" value="user_add_ajax" class="addFormBtn add">AJax 登録画面へ</button>

	    <p></p>
	</form>

<!-- AJaxによる追加 -->
    <form action="MasterUser" method="post" class="Btn" id="ajax_add">
        </form>
<!-- メッセージ　件数表示 -->
	<h3><%= request.getAttribute("msg") %></h3>

	<c:if test="${!empty user_list }">
	   <h4><%=user_list.size()%>件</h4>
	</c:if>
<!-- 一覧 -->

    <table>
    <c:if test="${!empty user_list }">
        <tr>
	       <td class="label">ユーザ名</td>
	       <td class="label">更新日時</td>
	       <td class="label">   </td>
	       <td class="label">   </td>
        </tr>
    </c:if>

      <c:forEach var="data" items="${user_list}">
        <tr>
            <td><c:out value="${data.user_id}" /></td>
            <td><c:out value="${data.create_dt}"/></td>

            <td>
                <form action="MasterUser" method="post">
                    <input type="hidden" name="eId" value="${data.user_id}">
                    <button class="update" type="submit" name="action" value="user_updateform">変更</button>
                </form>
            </td>
            <td>
                <form action="MasterUser" method="post" >
                    <input type="hidden" name="eId" value="${data.user_id}">
                    <button class="delete" type="submit" name="action" value="user_delete">削除</button>
                </form>
            </td>
        </tr>
       </c:forEach>
    </table>

</body>
</html>