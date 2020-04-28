<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page import="jp.ne.anvil.master.dormitory.*" %>
<jsp:useBean id="login_bean" class="jp.ne.anvil.login.LoginBean" scope="session"/>
<jsp:useBean id="dormitory_search_bean" class="jp.ne.anvil.master.dormitory.DormitorySearchBean" scope="request"/>
<jsp:useBean id="dormitory_list" class="java.util.ArrayList" type="java.util.ArrayList<jp.ne.anvil.master.dormitory.DormitoryBean>" scope="request"/>
<jsp:useBean id="dormitory_add_bean" class="jp.ne.anvil.master.dormitory.DormitoryAddBean" scope="request"/>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員寮マスタ編集</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/styles/dormitory.css">
<script src="scripts/dormitory.js"></script>
</head>
<body>
<header>
    <h1>社員寮マスタ編集</h1>
	<div class="topicpath">
	    <ol>
	        <li><a href="./menu">メニュー</a></li>
	        <li><a href="./master">マスタ編集メニュー</a></li>
	        <li><p>社員寮マスタ編集</p></li>
	    </ol>
	</div>
    <hr>
</header>
<div class="input-area">
	<div class="select">
		<input id="radio1" type="radio" class="radio" name="tab" onclick="tabChange();" checked="checked" />
		<label class="search-label" for="radio1">検索</label>
		<input id="radio2" type="radio" class="radio" name="tab" onclick="tabChange();" />
		<label class="add-label" for="radio2">新規登録</label>
	</div>
    <div class="form-tab">
<!-- 検索 -->
	    <form action="MasterDormitory" method="post"  id ="tab1" name="searchForm">
	        <h2>検索</h2>
	        <table class="input-table">
		        <tr>
		            <td class="label">物件ID</td>
		            <td><input type="text" name="dId" value="<jsp:getProperty name="dormitory_search_bean" property="dormitoryId" />" ></td>
		        </tr>
		        <tr>
		            <td class="label">物件名</td>
		            <td><input type="text" name="dName" value="<jsp:getProperty name="dormitory_search_bean" property="dormitoryName" />" ></td>
		        </tr>
		        <tr>
		            <td class="label">更新日時</td>
		            <td><input type="date" name="cDtFrom" value="<jsp:getProperty name="dormitory_search_bean" property="createDtFrom" />" >から&nbsp;&nbsp;
		            <input type="date" name="cDtTo" value="<jsp:getProperty name="dormitory_search_bean" property="createDtTo" />" >まで&nbsp;</td>
		        </tr>
	        </table>
	        <button class="serch" type="submit" name="action" value="dormitory_search">検索</button>
	        <button class="reset" type="button" onclick="clr()">リセット</button>
	        <button class="download" type="submit" name="action" value="dormitory_download"
	        onclick="return confirm('検索結果をダウンロードしますか？');">ダウンロード</button>

	        <p></p>
	    </form>
    </div>
    <div class="form-tab">
    <!-- 登録 -->

	    <form action="MasterDormitory" method="post" id="tab2">
	        <h2>新規登録</h2>
	        <table >
	            <tr>
	               <td class="label">ID</td>
	               <td><input type="text" name="aId" maxlength="4" required value="<jsp:getProperty name="dormitory_add_bean" property="addId" />" ></td>

	               <td class="label">物件名</td>
	               <td><input type="text" name="aName" required value="<jsp:getProperty name="dormitory_add_bean" property="addName" />"  ></td>
	            </tr>
	        </table>
	        <button class="add" type="submit" name="action" value="dormitory_add" onclick="return confirm('登録しますか？');">新規登録</button>
	        <button class="reset" type="reset">リセット</button>
	        <p></p>
	    </form>
    </div>
</div>


<div class="list-area">
	<h3><%= request.getAttribute("msg") %>
	    <c:if test="${!empty dormitory_list }">
           （表示：<%=dormitory_list.size()%>件）
        </c:if>
	</h3>


<!-- 一覧 -->
    <div class="delCheckBtn">
        <c:if test="${!empty dormitory_list }">
		    <form action="MasterDormitory" method="post"  class="delCheckForm" id="selectDelete">
		        <button class="delCheck" id="selectDeleteBtn" type="submit" name="action" value="dormitory_select_delete" onclick="return confirm('選択された社員寮を削除しますか？');">一括削除</button>
		    </form>
		    <label for="delCheckHide"><input type="checkbox" id="delCheckHide" onchange="hide();">一括削除</label>
	    </c:if>
	</div>



    <table class="list-table">
	    <c:if test="${!empty dormitory_list }">
	        <tr>
	           <td  class="delCheck">
	           </td>
	           <td class="label">ID</td>
	           <td class="label">物件名</td>
	           <td class="label">更新日時</td>
	           <td class="label">編集</td>
	           <td class="label">削除</td>
	        </tr>
	    </c:if>

	    <c:forEach var="data" items="${dormitory_list}">
	      <tr>
	          <td class="delCheck" ><label for="delId${index }"><input type="checkbox" id="delId${index }" name="delId" value="${data.dormitory_id}" form="selectDelete"></label></td>
	          <td><c:out value="${data.dormitory_id}" /></td>
	          <td><c:out value="${data.dormitory_name}" /></td>
	          <td><c:out value="${data.create_dt}"/></td>
	          <td>
	              <form action="MasterDormitory" method="post">
	                  <input type="hidden" name="eId" value="${data.dormitory_id}">
	                  <input type="hidden" name="eName" value="${data.dormitory_name}">
	                  <button class="update" type="submit" name="action" value="dormitory_updateform">
	                  <img src="images/edit.png" alt="編集" width="20" height="20">
	                  </button>
	              </form>
	          </td>
	          <td>
	              <form action="MasterDormitory" method="post" >
	                  <input type="hidden" name="eId" value="${data.dormitory_id}">
	                  <input type="hidden" name="eName" value="${data.dormitory_name}">
	                  <button class="delete" type="submit" name="action" value="dormitory_delete" onclick="return confirm('社員寮「${data.dormitory_name}」を削除しますか？');">
	                  <img src="images/gomi.jpg" alt="削除" width="20" height="20">
	                  </button>
	              </form>
	          </td>
	       </tr>
	     </c:forEach>
     </table>
</div>
</body>
</html>