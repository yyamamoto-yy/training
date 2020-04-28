<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="jp.ne.anvil.master.user.*" %>
<jsp:useBean id="login_bean" class="jp.ne.anvil.login.LoginBean" scope="session"/>
<jsp:useBean id="room_list" class="java.util.ArrayList" type="java.util.ArrayList<jp.ne.anvil.roomList.RoomBean>" scope="request"/>
<jsp:useBean id="room_search_bean" class="jp.ne.anvil.roomList.RoomListSearthBean" scope="request"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- スタイルシートの読込 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/roomlist.css">
<title>部屋一覧</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/common.css">
</head>
<body>
<h1>部屋一覧</h1>
<h3><jsp:getProperty name="login_bean" property="id" />さん</h3>
<hr>
<form action="room" method="post">
<!-- 検索条件エリア -->
<table>
    <tr>
        <th>物件名</th>
        <td>
            <input type="text" name="dormitory" value="<jsp:getProperty name="room_search_bean" property="dormitoryName" />" size="24" id="id" >
        </td>
        <th>間取り</th>
        <td>
            <label for="plan0"><input type="checkbox" id="plan0" name="plan" value="1R">1R</label>
            <label for="plan1"><input type="checkbox" id="plan1" name="plan" value="1K">1K</label>
            <label for="plan2"><input type="checkbox" id="plan2" name="plan" value="1DK">1DK</label>
            <label for="plan3"><input type="checkbox" id="plan3" name="plan" value="1LDK">1LDK</label>
            <br>
            <label for="plan4"><input type="checkbox" id="plan4" name="plan" value="2DK">2DK</label>
            <label for="plan5"><input type="checkbox" id="plan5" name="plan" value="2LDK">2LDK</label>
        </td>
        <th>広さ</th>
        <td>
            <select name="meter">
                <option value="">指定なし</option>
                <option value="20" <% if("20".equals(room_search_bean.getSquareMeter())){ out.print("selected");} %>>20㎡以上</option>
                <option value="25" <% if("25".equals(room_search_bean.getSquareMeter())){ out.print("selected");} %>>25㎡以上</option>
                <option value="30" <% if("30".equals(room_search_bean.getSquareMeter())){ out.print("selected");} %>>30㎡以上</option>
                <option value="35" <% if("35".equals(room_search_bean.getSquareMeter())){ out.print("selected");} %>>35㎡以上</option>
            </select>
        </td>
        <td>
            <label for="out">
                <input type="checkbox" id="out" name="out" <% if("on".equals(room_search_bean.getOut())){ out.print("checked");} %> >入居済みを除外
            </label>
        </td>
    </tr>
    <tr>
        <td colspan="7" style="text-align:right">
            <button type="submit" name="action" value="room_search">検索する</button>
        </td>
    </tr>
</table>
</form>
<hr>
<!-- 一覧表示エリア -->
<%=room_list.size()%>件
<table border="1">
    <tr>
        <th>物件名</th>
        <th>部屋番号</th>
        <th>間取り</th>
        <th>平米</th>
        <th>入居者</th>
    </tr>
    <!-- JSTLを利用してBeanの中身を表示 -->
    <c:forEach var="data" items="${room_list}">
        <c:choose>
        <c:when test = "${not empty data.user_name}">
            <tr class="color">
        </c:when>
        <c:otherwise>
            <tr>
        </c:otherwise>
        </c:choose>
            <td><c:out value="${data.dormitory_name}"/></td>
            <td><c:out value="${data.room_id}"/></td>
            <td><c:out value="${data.floor_plan}"/></td>
            <td><c:out value="${data.square_meter}"/></td>
            <td><c:out value="${data.user_name}"/></td>
        </tr>
        <%
        //RoomBean roomBean = (RoomBean)pageContext.getAttribute("data");
        //out.println(roomBean.getUser_name());
        %>
    </c:forEach>
</table>
</body>
</html>