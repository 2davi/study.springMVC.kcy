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
		<div class="post-btn">
			<c:if test="${username eq post.userId }">
				<input type="button" value="삭제" onclick="fn_deletePost()" />
				<input type="button" value="수정" onclick="fn_updatePost()" />
			</c:if>
		</div>
	</div>
	<div class="comment-tree-section">
		<c:choose>
		<c:when test="${not empty cmtList }">
			<div class="comment-insert-btn">
				<input type="button" value="댓글등록_" onclick="toggle_insertCmt(this)" />
			</div>
			<div class="comment-depth-0">
				<c:forEach items="${cmtList }" var="cmt">
					<div class="comment-section">
						<div class="comment-meta" style="display:flex;flex-direction:row;">
							<div><label>${cmt.userId }</label></div>
							<div><span><fmt:formatDate value="${cmt.rgstDt}" pattern="yyyy-MM-dd HH:mm" /></span></div>
						</div>
						<div class="comment-content">
							<span>${cmt.content }</span>
						</div>
						<div class="comment-btn">
							<c:if test="${username eq post.userId }">
								<input type="button" value="삭제" data-cmt-id="${cmt.cmtId }" onclick="fn_deleteCmt(this)" />
								<input type="button" value="수정" data-cmt-id="${cmt.cmtId }" onclick="toggle_updateCmt(this)" />
							</c:if>
							<div class="comment-insert-btn">
								<input type="button" value="답글달기_" data-cmt-id="${cmt.cmtId }" onclick="toggle_insertCmt(this)" />
							</div>
							<div class="when-toggled-to-insert-comment">
							<!-- 
								toggle_insertCmt([input]) 함수가 실행됐을 시 해당 위치에 보여질 댓글 등록 폼.
								(1) 댓글의 depth가 원댓글인지 자식답글인지에 따라 정해져야 하고,
								(2) 자식답글이라면 부모댓글의 cmtId도 가져와야 하고,
								(3) 수정 폼의 경우에는 원래 댓글의 내용도 가지고 와야 함.
							 -->
								<form id="cmtForm" method="POST" action="/board/comment/insert">
									<input type="text" hidden name="postId" value="${post.postId }" />
									<input type="text" hidden name="userId" value="${username }" />
									<input type="text" hidden name="cmtParentId" 
										<c:if test="${not empty cmt.cmtParentId }"> value="${cmt.cmtParentId }"</c:if>
									/>
									<input type="text" name="cmtContent" />
									<input type="button" id="submitCmt" onclick="fn_insertCmt()" />
								</form>
							</div>
						</div>
					</div>
					<div class="child-comment-tree">
						
					</div>
				</c:forEach>
			</div>
		</c:when>
		<c:otherwise>
			<span>아직 등록된 댓글이 없습니다.</span>
		</c:otherwise>
		</c:choose>
	</div>
</div>
<script>
function fn_deletePost() {
	
	location.href = `/board/post/delete?postId=${post.postId}`;
}
function fn_updatePost() {
	
	location.href = `/board/forum/post/update?postId=${post.postId}`;
}
function fn_deleteCmt() {
	
	if(confirm("댓글을 삭제하시겠습니까?")){
		location.href = `/board/comment/delete?postId=${post.postId}`;
	} else {
		return;
	}
}
function fn_updateCmt() {
	
	location.href = `/board/comment/update?postId=${post.postId}`;
}
function toggle_updateCmt() {
	
	//---
}
function toggle_insertChildCmt() {
	
	//---
}

</script>
</body>
</html>