<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>
<%@ page import="jp.ne.anvil.master.room.*" %>
<jsp:useBean id="login_bean" class="jp.ne.anvil.login.LoginBean" scope="session"/>
<jsp:useBean id="room_update_bean" class="jp.ne.anvil.master.room.RoomUpdateBean" scope="request"/>


<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>部屋情報編集</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/room.css">
</head>
<body>

<h1>部屋情報変更</h1>
<div class="topicpath">
        <ol>
            <li><a href="./menu">メニュー</a></li>
            <li><a href="./master">マスタ編集メニュー</a></li>
            <li><a href="./MasterRoom">部屋マスタ編集</a></li>
            <li><p>部屋情報変更</p></li>
        </ol>
    </div>
<div class="up-area">
	<hr>
	<% String dormitoryId =(String)request.getAttribute("dormitoryId");
	   String dormitoryName =(String)request.getAttribute("dormitoryName");
	   String roomId =(String)request.getAttribute("roomId");
	   String floorPlan =(String)request.getAttribute("plan");
	   String squareMeter =(String)request.getAttribute("squareMeter");
	%>

	<!-- 更新 -->


	<h3>物件名：${dormitoryName }　　部屋番号：${roomId }</h3>

	<!-- メッセージ表示 -->
	<h3><font color="red"><%= request.getAttribute("msg") %></font></h3>

	<form class="update-form" action="MasterRoom" method="post" id="input-form">
	    <input type="hidden" name="dormitoryId" value="${dormitoryId}">
	    <input type="hidden" name="dormitoryName" value="${dormitoryName}">
	    <input type="hidden" name="roomId" value="${roomId}">
	    <input type="hidden" name="squareMeter" value="${squareMeter}">
	    <table class="up-table">
	    <tr>
	        <th></th>
	        <th>変更前</th>
	        <th>変更後</th>
			</tr>
			<tr>
			<th>間取り</th>
			<td>${plan }</td>
			<td>
			    <label for="plan0"><input type="radio" id="plan0" name="plan" value="1R" checked>1R</label>
				<label for="plan1"><input type="radio" id="plan1" name="plan" value="1K">1K</label>
				<br>
				<label for="plan2"><input type="radio" id="plan2" name="plan" value="1DK">1DK</label>
				<label for="plan3"><input type="radio" id="plan3" name="plan" value="1LDK">1LDK</label>
				<br>
				<label for="plan4"><input type="radio" id="plan4" name="plan" value="2DK">2DK</label>
				<label for="plan5"><input type="radio" id="plan5" name="plan" value="2LDK">2LDK</label></td>
			</tr>
			<tr>
			<th>平米</th>
			<td>${squareMeter }</td>
			<td><input type="text" name="updateMeter" value="" required maxlength="3" size="6"></td>
			</tr>

		</table>
		<div class="center">
		    <button class="upBtn" type="submit" name="action" value="room_update" onclick="return confirm('変更しますか？');">変更</button>
		</div>
	</form>

	<form action="MasterRoom" method="post" class="up-cancel">
	    <button class="back" type="submit" name="action" value="">キャンセル</button>
	</form>
	<hr>
</div>
</body>
</html>

