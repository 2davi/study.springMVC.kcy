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
<h3>postList.jsp</h3>

<div class="wrapper">
	<div><!-- 게시판 유형 위치할 1행 -->
		<h2>자유게시판</h2>
	</div>
	<div><!-- 검색박스 위치할 2행 -->
		<div class="searchBox">
			<div><label for="searchTerm">검색</label></div>
			<div>
				<select id="searchKeyword" required>
					<option value="0">--전체--</option>
					<option value="1">작성자(ID)</option>
					<option value="2">작성자(NM)</option>
					<option value="3">글+내용</option>
					<option value="4">글 제목</option>
					<option value="5">내용</option>
				</select>
			</div>
			<input type="text" placeholder="검색어 입력" name="searchTerm" id="searchTerm"/>
			<div><input type="button" id="searchBtn" value="검색" onclick="searchId()"/>
				 <input type="button" id="searchReset" value="검색초기화" onclick="searchReset()" /></div>
		</div>
	</div>
	<div><!-- 게시글 리스트가 위치할 3행 -->
		<div><!-- 글 등록버튼(상단) -->
			<input type="button" class="id_insertPage" value="등록" onclick="fn_post_insertPage()" />
		</div>
		<div><!-- 게시글 목록 -->
			<table border="1">
				<tr>
					<th class="tb-post-cols-no">번호</th>
					<th class="tb-post-cols-title">제목</th>
					<th class="tb-post-cols-author">작성자</th>
					<th class="tb-post-cols-rgst-date">등록일자</th>
					<th class="tb-post-cols-attach-cnt">첨부</th>
				</tr>
				<!-- core:forEach 위치할 자리. -->
			<c:forEach items="${postList }" var="post">
				<tr onclick='location.href="/board/forum/post?postId=${post.postId}"' style="cursor: pointer;">
					<td></td>
					<td>${post.postTitle }</td>
					<td>${post.userId }</td>
					<td>${post.rgstDt }</td>
					<td></td>
				</tr>
			</c:forEach>
			</table>
		</div>
		<div><!-- 글 등록버튼(하단) -->
			<input type="button" class="id_insertPage" value="등록" onclick="fn_post_insertPage()" />
		</div>
		<div><!-- 페이지네이션 -->
		
		</div>
	</div>
</div>
<script>
function fn_post_insertPage() {
	let boardCate = "forum";
	
	location.href = "/board/" +boardCate+ "/post/insert";
}
</script>

</body>
</html>