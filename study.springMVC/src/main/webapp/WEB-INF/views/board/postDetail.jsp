<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>postDetail.jsp</h3>
<p>${isOk }</p>
<div class="wrapper">
	<div class="post-title">
		<h2>${post.postTitle}</h2>
	</div>
	<div class="post-details">
		<span>작성자: ${post.userId}</span> | <span>작성일: <fmt:formatDate value="${post.rgstDt}" pattern="yyyy-MM-dd HH:mm" /></span>
		<c:if test="${post.updtDt != post.rgstDt}">
           | <span>수정일: <fmt:formatDate value="${post.updtDt}" pattern="yyyy-MM-dd HH:mm" /></span>
		</c:if>
	</div>
	<div class="post-content">
		<pre style="white-space: pre-wrap;">${post.postContent}</pre>
	</div>
	<div class="post-attach">
	<c:if test="${not empty attachFileList}">
	    <h4>첨부파일</h4>
	    <ul>
	        <c:forEach var="file" items="${attachFileList}">
	            <li>
	                <a href="/board/attach/select?fileGrpId=${file.fileGrpId}&fileSeq=${file.fileSeq}">
	                    ${file.fileOrgNm}
	                </a>
	                <span>(${file.fileSize} byte)</span>
	            </li>
	        </c:forEach>
	    </ul>
	</c:if>
	<c:if test="${empty attachFileList}">
	    <p>첨부파일 없음</p>
	</c:if>
	</div>
	<div class="comment-section">
	</div>
</div>

</body>
</html>