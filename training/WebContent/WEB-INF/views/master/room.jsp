<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="jp.ne.anvil.master.room.*" %>
<jsp:useBean id="login_bean" class="jp.ne.anvil.login.LoginBean" scope="session"/>
<jsp:useBean id="room_list" class="java.util.ArrayList" type="java.util.ArrayList<jp.ne.anvil.master.room.RoomMBean>" scope="request"/>
<jsp:useBean id="room_search_bean" class="jp.ne.anvil.master.room.RoomMListSearchBean" scope="request"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- スタイルシートの読込 -->
<title>部屋マスタ編集</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/room.css">

</head>
<body>
    <h1>部屋マスタ編集</h1>
    <div class="topicpath">
        <ol>
            <li><a href="./menu">メニュー</a></li>
            <li><a href="./master">マスタ編集メニュー</a></li>
            <li><p>部屋マスタ編集</p></li>
            <li><form action="MasterRoom" method="post" class="Btn">
                <button type="submit" name="action" value="room_add" class="addFormBtn">部屋の追加へ</button>
                </form>
            </li>
        </ol>
    </div>

    <div class="input-form">
	    <hr>

        <div class="select">
            <input id="radio1" type="radio" class="radio" name="tab" onclick="tabChange();"/>
            <label class="search-label" for="radio1">非表示</label>
            <input id="radio2" type="radio" class="radio" name="tab" onclick="tabChange();"  checked="checked" />
            <label class="add-label" for="radio2">検索</label>
        </div>

		<form action="MasterRoom" method="post" id="tab1" name="searchForm">
		<!-- 検索条件エリア -->
		<table>
		    <tr>
		        <th>　　物件ID</th>
		        <td>
		            <input type="text"  name="dormitory_id" value="<jsp:getProperty name="room_search_bean" property="dormitoryId" />" size="8"  >&nbsp;&nbsp;&nbsp;
		        </td>
		        <th>物件名</th>
		        <td>
		            <input type="text" name="dormitory_name" value="<jsp:getProperty name="room_search_bean" property="dormitoryName" />" size="24"  >&nbsp;&nbsp;&nbsp;
		        </td>
		        <th>部屋ID</th>
		        <td>
		            <input type="text" id="idText" name="room_id" value="<jsp:getProperty name="room_search_bean" property="roomId" />" size="8"  >&nbsp;
		        </td>
		        <td>
		            <label for="out">
		                <input type="checkbox" id="out" name="out" <% if("on".equals(room_search_bean.getOut())){ out.print("checked");} %> >入居済みを除外
		            </label>
		        </td>
		    </tr>
		    <tr>
		        <th>　　広さ</th>
		        <td>
		            <select name="meter">
		                <option value="">指定なし</option>
		                <option value="20" <% if("20".equals(room_search_bean.getSquareMeter())){ out.print("selected");} %>>20㎡以上</option>
		                <option value="25" <% if("25".equals(room_search_bean.getSquareMeter())){ out.print("selected");} %>>25㎡以上</option>
		                <option value="30" <% if("30".equals(room_search_bean.getSquareMeter())){ out.print("selected");} %>>30㎡以上</option>
		                <option value="35" <% if("35".equals(room_search_bean.getSquareMeter())){ out.print("selected");} %>>35㎡以上</option>
		            </select>
		        </td>
		        <th>更新日</th>
		        <td>
		            <input type="date" name="create_dt_from" value="<jsp:getProperty name="room_search_bean" property="createDtFrom" />" >から&nbsp;&nbsp;
		            <br><input type="date" name="create_dt_to" value="<jsp:getProperty name="room_search_bean" property="createDtTo" />" >まで&nbsp;
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
		    </tr>
	    </table>
	    <div class="center">
		    <button type="submit" name="action" value="room_search" class="searchBtn">検索する</button>
		    <button class="reset" type="button" onclick="clr()">リセット</button>
		    <button type="submit" name="action" value="room_download" class="downloadBtn"
		      onclick="return confirm('検索結果をダウンロードしますか？');">
		    <img src="images/dl.png" alt="ダウンロード" width="25" height="25"></button>
	    </div>

		<hr>
		</form>
    </div>
    <p></p>
<script src="scripts/room.js"></script>
    <h3 class="msg"><%= request.getAttribute("msg") %></h3>
    <c:if test="${!empty room_list }">
       <h4 class="count"><%=room_list.size()%>件</h4>
    </c:if>

<!-- 一括削除ボタン制御 -->
    <div class="delCheckBtn">
        <c:if test="${!empty room_list }">
            <form action="MasterRoom" method="post"  class="delCheckForm" id="selectDelete">
                <button class="delCheck" id="selectDeleteBtn" type="submit" name="action" value="room_select_delete"
                        onclick="return confirm('選択された部屋を削除しますか？');">一括削除</button>
            </form>
            <label for="delCheckHide"><input type="checkbox" id="delCheckHide" onchange="hide();">一括削除</label>
        </c:if>
    </div>
<!-- 一覧表示エリア -->
	<table class="list-table">
	<c:if test="${!empty room_list }">
	    <tr class ="center">
	        <td  class="delCheck">
            </td>
	        <th>物件ID</th>
	        <th>物件名</th>
	        <th>部屋番号</th>
	        <th>間取り</th>
	        <th>平米</th>
	        <th>更新日時</th>
	        <th>入居者</th>
	        <th>編集</th>
	        <th>削除</th>
	    </tr>
	</c:if>
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
	            <!-- 一括削除用チェックボックス -->
	            <td class="delCheck" ><label for="delId${index }"><input type="checkbox" id="delId${index }"
	               name="del_id" value="${data.dormitory_id},${data.room_id}" form="selectDelete"></label></td>

	            <td class ="center"><c:out value="${data.dormitory_id}"/></td>
	            <c:choose>
	            <c:when test = "${not empty data.dormitory_name}">
	                <td><c:out value="${data.dormitory_name}"/></td>
	            </c:when>
	            <c:otherwise>
	                <td><font color="red">不明</font></td>
		        </c:otherwise>
		        </c:choose>
	            <td class ="center"><c:out value="${data.room_id}"/></td>
	            <td class ="center"><c:out value="${data.floor_plan}"/></td>
	            <td class ="center"><c:out value="${data.square_meter}"/></td>
	            <td class ="center"><c:out value="${data.create_dt}"/></td>
	            <td class ="center"><c:out value="${data.user_name}"/></td>
	            <td class ="center">
	                <form action="MasterRoom" method="post">
	                    <input type="hidden" name="dormitoryId" value="${data.dormitory_id}">
	                    <input type="hidden" name="dormitoryName" value="${data.dormitory_name}">
	                    <input type="hidden" name="roomId" value="${data.room_id}">
	                    <input type="hidden" name="plan" value="${data.floor_plan}">
	                    <input type="hidden" name="squareMeter" value="${data.square_meter}">
	                    <button class="update" type="submit" name="action" value="room_updateform">
	                    <img src="images/edit.png" alt="編集" width="20" height="20">
	                    </button>
	                </form>
	            </td>
	            <td class ="center">
	                <form action="MasterRoom" method="post" >
	                    <input type="hidden" name="dormitoryId" value="${data.dormitory_id}">
	                    <input type="hidden" name="dormitoryName" value="${data.dormitory_name}">
	                    <input type="hidden" name="roomId" value="${data.room_id}">
	                    <button class="delete" type="submit" name="action" value="room_delete" onclick="return confirm('「${data.dormitory_name}」:「${data.room_id }」を削除しますか？');">
	                    <img src="images/gomi.jpg" alt="削除" width="20" height="20">
	                    </button>
	                </form>
	            </td>
	        </tr>

	    </c:forEach>
	</table>
</body>
</html>