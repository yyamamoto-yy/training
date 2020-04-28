<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="jp.ne.anvil.master.room.*" %>
<jsp:useBean id="login_bean" class="jp.ne.anvil.login.LoginBean" scope="session"/>
<jsp:useBean id="dorm_list" class="java.util.ArrayList" type="java.util.ArrayList<jp.ne.anvil.master.room.DormitoryListBean>" scope="request"/>
<jsp:useBean id="room_add_bean" class="jp.ne.anvil.master.room.RoomAddBean" scope="request"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- スタイルシートの読込 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/room.css">
<title>部屋追加</title>
</head>
<body>
<h1>部屋の追加</h1>
    <div class="topicpath">
        <ol>
            <li><a href="./menu">メニュー</a></li>
            <li><a href="./master">マスタ編集メニュー</a></li>
            <li><a href="./MasterRoom">部屋マスタ編集</a></li>
            <li><p>部屋の追加</p></li>
        </ol>
    </div>
<div class="input-form">
	<hr>
	<form action="MasterRoom" method="post" >
	<!-- 追加項目 -->
	<table class ="add-table">
	    <tr>
	        <th>物件名</th>
	        <td>
	            <select name="dormitoryName">
			    <c:forEach var="data" items="${dorm_list}">
			    <option><c:out value="${data.dormitory_name}"/></option>
			    </c:forEach>
			    </select>
			    &nbsp;&nbsp;
	        </td>
	        <th>部屋番号</th>
	        <td>
	        <input type="text" name="roomId" maxlength="4" required value="<jsp:getProperty name="room_add_bean" property="roomId" />"  >&nbsp;&nbsp;</td>

	        </td>

	        <th>間取り</th>
	        <td>
	            <label for="plan0"><input type="radio" id="plan0" name="plan" value="1R" checked>1R</label>
	            <label for="plan1"><input type="radio" id="plan1" name="plan" value="1K">1K</label>
	            <label for="plan2"><input type="radio" id="plan2" name="plan" value="1DK">1DK</label>
	            <label for="plan3"><input type="radio" id="plan3" name="plan" value="1LDK">1LDK</label>
	            <br>
	            <label for="plan4"><input type="radio" id="plan4" name="plan" value="2DK">2DK</label>
	            <label for="plan5"><input type="radio" id="plan5" name="plan" value="2LDK">2LDK</label>
	        </td>
	        <th>平米</th>
	        <td>
	            <input type="text"  maxlength="3"  name="squareMeter" required value="<jsp:getProperty name="room_add_bean" property="squareMeter" />">
	        </td>

	    </tr>
	</table>
	<div class="center">
	   <button type="submit" name="action" value="room_addition" class="addBtn" onclick="return confirm('追加しますか？');">追加する</button>
	</div>
	</form>
	<div class="center">
	<form action="MasterRoom" method="post" class="Btn">
        <button  class="back" type="submit" >戻る</button>
    </form>
    </div>
	<hr>
</div>
<h3 align="center"><font color="red"><%= request.getAttribute("msg") %></font></h3>


</body>
</html>