<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>userList.jsp</h2>
<div>
	<div class="searchBox">
		<div><label for="searchTerm">검색</label></div>
		<div>
			<select id="searchKeyword" required>
				<option value="0" selected>--전체--</option>
				<option value="1">아이디</option>
				<option value="2">이름</option>
			</select>
		</div>
		<input type="text" placeholder="검색어 입력" name="searchTerm" id="searchTerm"/>
		<div><input type="button" id="searchBtn" value="검색" onclick="searchId()"/>
			 <input type="button" id="searchReset" value="검색초기화" onclick="searchReset()" /></div>
	</div>
	<script>
		window.addEventListener("DOMContentLoaded", () => {
			
			const params = new URLSearchParams(window.location.search);
			const key = params.get("key");
			const term= params.get("term");
			
			if(key != null) {
				document.getElementById("searchKeyword").value = key;
			}
			if(term) {
				document.getElementById("searchTerm").value = decodeURIComponent(term);
			}
		});
	</script>
</div>
<div>
	<table border="1">
		<thead><tr>
			<!-- <th>no</th> -->
			<th>사용자 아이디</th>
			<th>사용자 이름</th>
		</tr></thead>
		<tbody>
			<c:choose>
			<c:when test="${not empty userList }">
				<c:forEach items="${userList}" var="list">
					<tr>
					
						<%-- <td>${ }</td> --%>
						<td>
						<a href="${pageContext.request.contextPath }/cmmn/user/detail?userId=${list.userId}">
							${list.userId }
						</a>
						</td>
						<td>${list.userNm }</td>
					</tr>
				</c:forEach>
			</c:when>
			<c:otherwise> <tr><td colspan="2">검색 결과가 없습니다.</td></tr> </c:otherwise>
			</c:choose>
		</tbody>
	</table>
</div>
<div>
	<input type="button" value="등록" onclick="insertPage()"/>
	<input type="button" value="일괄삭제" />
</div>
<script>
const searchTerm = document.getElementById('searchTerm');

function insertPage() {
	location.href = "/cmmn/user/insert";
}

function searchId() {
	console.debug(document.getElementById("searchBtn"), document.getElementById("searchTerm").value);
	let searchKeyword = document.getElementById("searchKeyword").value;
	let searchTerm = document.getElementById("searchTerm").value;
	const uri = "/cmmn/user/list?key=" +searchKeyword+ "&term=" +searchTerm;
	const urlSafeEncoded = encodeURI(uri);
	console.debug("URI::: ", uri);
	console.debug("urlSafeEncoded::: ", urlSafeEncoded);
	location.href = urlSafeEncoded;
}

function searchReset() {
	location.href = "/cmmn/user/list";
}

searchTerm.addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        document.getElementById('searchBtn').click();
    }
});
</script>
</body>
</html>