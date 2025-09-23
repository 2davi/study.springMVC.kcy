<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.comment-form {
	display: none;
}
</style>
</head>
<body>
<h3>postDetail.jsp</h3>
<p>${post.postId }</p>
<div class="wrapper">
	<div class="post-title" id="postTitle" data-post-id="${post.postId }">
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
	<div class="comment-insert-btn">
		<input type="button" value="댓글등록_" data-post-id="${post.postId }" onclick="toggle_insertCmt(this)" />
	</div>
	<div class="comment-tree" id="cmtTree">
		<!-- cloneNode로 댓글트리 출력 -->
	</div>
</div>
<!-- 댓글용 템플릿 -->
<div class="comment-section" id="cmtSection" style="display:none;" data-cmt-id="" data-cmt-parent-id="">
	<div class="comment-meta" style="display:flex;flex-direction:row;">
		<div><span class="cmt-user"></span></div>
		<div><span class="cmt-time"></span></div>
	</div>
	<div class="comment-content">
		<span class="cmt-content"></span>
	</div>
	<div class="comment-btn">
		<input type="button" value="삭제" class="btn-comment-delete" />
		<input type="button" value="수정" class="btn-comment-update" />
		<input type="button" value="답글달기_"  class="btn-comment-reply" />
	</div>
</div>
<div class="comment-form" id="cmtForm">
	<form>
		<input type="hidden"  name="postId" value="${post.postId }" /> <!-- id="cmtPostId" -->
		<input type="hidden"  name="cmtParentId" /> <!-- id="cmtParentId" -->
		<input type="hidden"  name="cmtDepth" />
		<textarea id="cmtContent" name="cmtContent"></textarea>
		<input type="button" class="btn-save" value="등록" /> <!-- id="btnSaveCmt" --> 
		<input type="button" class="btn-cancel" value="취소" /> <!-- id="btnCancleCmt" --> 
	</form>
</div>
<script defer>
//Form 요소를 복사하려면 id가 중복될 수 있음
//querySelector를 쓰는 방향으로 깔쌈하게 가는 게 나음.

function fn_deletePost() {
	
	location.href = `/board/post/delete?postId=${post.postId}`;
}
function fn_updatePost() {
	
	location.href = `/board/forum/post/update?postId=${post.postId}`;
}
function toggle_updateCmt(btn) {
	const cmtId = btn.dataset.cmtId;
	const originalCmtDiv = btn.closest(".comment-section");
	const originalCmtContent = originalCmtDiv.querySelector(".comment-content span").innerText;
	const cmtForm = document.querySelector("#cmtForm").cloneNode(true);
	cmtForm.id = "";
	cmtForm.style.display = "flex";
	cmtForm.querySelector("textarea[name=cmtContent]").value = originalCmtContent;
	cmtForm.querySelector(".btn-save").value = "수정";
	cmtForm.querySelector(".btn-save").onclick = async () => {
		const cmtContent = cmtForm.querySelector("textarea[name=cmtContent]").value;
		
		const resp = await fetch("/board/comment/update", {
			method: "POST"
			, body: JSON.stringify({
				"cmtId": cmtId
				, "cmtContent": cmtContent
			})
			, headers: {
				"Content-Type": "application/json"
				, "Accept": "application/json"
			}
		});
		
		if(resp.ok) {
			alert("댓글이 성공적으로 수정되었습니다.");
			//location.reload();
			loadComments();
		}
	}
	
	originalCmtDiv.appendChild(cmtForm);
}
function toggle_insertCmt(btn) {
	const cmtParentId = btn.dataset.cmtId || null;
	const cmtDepth = btn.dataset.cmtDepth || 0;
	//const cmtForm = document.getElementById("cmtForm").cloneNode(true);
	const cmtForm = document.querySelector("#cmtForm").cloneNode(true);
	const postId = btn.dataset.postId;
	console.debug("postId:", postId);
	cmtForm.id = "";
	cmtForm.style.display = "flex";
	
	// 댓글/답글 여부는 동적으로 처리
	cmtForm.querySelector("input[name=cmtParentId]").value = cmtParentId;
	cmtForm.querySelector("input[name=cmtDepth]").value = cmtDepth;

	cmtForm.querySelector(".btn-save").value = "등록";
	cmtForm.querySelector(".btn-save").onclick = async () => {
		const postId = cmtForm.querySelector("input[name=postId]").value;
		const cmtDepth = cmtForm.querySelector("input[name=cmtDepth]").value;
		const cmtContent = cmtForm.querySelector("textarea[name=cmtContent]").value;
		
		const resp = await fetch("/board/comment/insert", {
			method: "POST"
			, body: JSON.stringify({
				"postId": postId
				, "cmtParentId": cmtParentId
				, "cmtContent": cmtContent 
				, "cmtDepth": cmtDepth
			})
			, headers: {
				"Content-Type": "application/json"
				, "Accept": "application/json"
			}
		});
		
		if(resp.ok) {
			alert("댓글이 성공적으로 등록되었습니다.");
			//location.reload();
			loadComments();
		}
	}
	
	//btn.closest(".comment-section").appendChild(cmtForm);
	  const parentSection = btn.closest(".comment-section");
  if (parentSection) {
    // 답글인 경우: 부모 댓글 밑에 붙임
    parentSection.appendChild(cmtForm);
  } else {
    // 최상위 댓글인 경우: 댓글 트리에 붙임
    document.getElementById("cmtTree").appendChild(cmtForm);
  }
}


async function fn_deleteCmt(btn) {
	const cmtId = btn.dataset.cmtId;
	console.debug("삭제할 CMT_ID :", cmtId);
	
	if(confirm("댓글을 삭제하시겠습니까?")){
		const resp = await fetch("/board/comment/delete", {
			method: "POST"
			, body: JSON.stringify({"cmtId": cmtId})
			, headers: {
				"Content-Type": "application/json"
				, "Accept": "application/json"
			}
		});
		
		if(resp.ok) {
			alert("댓글이 성공적으로 삭제되었습니다.");
			//location.reload();
			loadComments();
		}
		
	} else {
		return;
	}
}

async function loadComments() {
	const postId = "${post.postId}";
	console.debug("여기 안 찍히면 데타셋으로 가져온다", postId);
	const resp = await fetch("/board/comment/select?postId=" + postId, {
		method: "GET"
		, headers: {"Accept": "application/json"}
	});
	if(!resp.ok) { console.error("댓글트리 조회 실패."); return; }
	
	const data = await resp.json();
	const cmtList = data.cmtList || [];
	renderComments(cmtList);
}
function renderComments(cmtList) {
	const commentTree = document.getElementById("cmtTree");
	commentTree.innerHTML = "";
	const commentSection = document.getElementById("cmtSection");
	
	if(cmtList.length === 0) {
		commentTree.innerHTML = "<span>아직 등록된 댓글이 없습니다</span>";
		return;
	}
	
	cmtList.forEach(cmt => {
		const sectionClone = commentSection.cloneNode(true);
		sectionClone.removeAttribute("id");
		sectionClone.removeAttribute("style");
		sectionClone.dataset.cmtId = cmt.cmtId;
		sectionClone.dataset.cmtParentId = cmt.cmtParentId || "";
		sectionClone.dataset.cmtDepth = cmt.cmtDepth;
		sectionClone.style.display = "flex";
		sectionClone.style.marginLeft = (cmt.cmtDepth * 20) + "px";
		
		sectionClone.querySelector(".cmt-user").innerText = cmt.userId;
		sectionClone.querySelector(".cmt-time").innerText = formatDate(cmt.rgstDt);
		sectionClone.querySelector(".cmt-content").innerText = cmt.cmtContent;
		
		
		const btnDelete = sectionClone.querySelector(".btn-comment-delete");
		const btnUpdate = sectionClone.querySelector(".btn-comment-update");
		const btnReply = sectionClone.querySelector(".btn-comment-reply");
		
		if(cmt.userId === "${username}") {
			btnDelete.style.display = "inline-block";
			btnDelete.dataset.cmtId = cmt.cmtId;
			//btnDelete.dataset.cmtDepth = cmt.cmtDepth;
			btnDelete.onclick = () => fn_deleteCmt(btnDelete);
			btnUpdate.style.display = "inline-block";
			btnUpdate.dataset.cmtId = cmt.cmtId;
			btnUpdate.dataset.cmtDepth = cmt.cmtDepth;
			btnUpdate.onclick = () => toggle_updateCmt(btnUpdate);
		}
		btnReply.dataset.cmtId = cmt.cmtId;
		btnReply.dataset.cmtDepth = cmt.cmtDepth + 1;
		btnReply.onclick = () => toggle_insertCmt(btnReply);
		
		commentTree.appendChild(sectionClone);
	});
}

document.addEventListener("DOMContentLoaded", () => {
	loadComments();
});

<!-- 0922_ISO시간 변환하는 함수 -->
function formatDate(ts) {
	  if (!ts) return "";
	  const d = (typeof ts === "number") 
	      ? new Date(ts) 
	      : new Date(ts.replace(" ", "T"));
	  if (isNaN(d)) return "";
	  return `\${d.getFullYear()}-\${(d.getMonth()+1).toString().padStart(2,"0")}-\${d.getDate().toString().padStart(2,"0")} `
	       + `\${d.getHours().toString().padStart(2,"0")}:\${d.getMinutes().toString().padStart(2,"0")}`;
	}
</script>
</body>
</html>














