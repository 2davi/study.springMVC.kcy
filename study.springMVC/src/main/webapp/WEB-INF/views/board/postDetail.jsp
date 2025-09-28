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
	<div><!-- 게시판 유형 위치할 1행 -->
		<h5>자유게시판</h5>
	</div>
<div class="post-details card">
	<div class="card-header d-flex flex-row gap-4">
		<div class="mt-2">
			<h4>${post.postTitle }</h4>
		</div>
		<div class="mt-3">
			<small>작성자: ${post.userId}</small> | <small class="text-muted">작성일: <fmt:formatDate value="${post.rgstDt}" pattern="yyyy-MM-dd HH:mm" /></small>
			<c:if test="${post.updtDt != post.rgstDt}">
	           | <small class="text-muted">수정일: <fmt:formatDate value="${post.updtDt}" pattern="yyyy-MM-dd HH:mm" /></small>
			</c:if>
		</div>
	</div>

	<div class="card-body">
		<p class="card-text">${post.postContent}</p>
	</div>
	<div class="card-body py-1 text-end">
		<c:if test="${username eq post.userId }">
			<input type="button" value="수정" class="btn btn-outline-primary btn-sm" onclick="fn_updatePost()" />
			<input type="button" value="삭제" class="btn btn-outline-danger btn-sm" onclick="fn_deletePost()" />
			<input type="button" value="덧글 등록" class="btn btn-sm btn-outline-success" data-post-id="${post.postId }" onclick="toggle_insertCmt(this)" />
		</c:if>
	</div>
	<c:choose>
	<c:when test="${not empty attachFileList }">
	<div class="post-attach card m-4">
		<div class="card-header d-flex flex-col gap-2">
			<div class="mt-2">
				<c:forEach var="file" items="${attachFileList}">
		            <div classs="attach-file d-flex flex-row gap-3">
		                <a href="/board/attach/select?fileGrpId=${file.fileGrpId}&fileSeq=${file.fileSeq}">
		            	<i class="ti ti-chevron-down"></i>
						<abbr>${file.fileOrgNm}</abbr>
		                </a>
		                <span class="text-muted">(${file.fileSize} byte)</span>
		            </div>
		        </c:forEach>
			</div>
		</div>
	</div>
	</c:when>
	<c:otherwise>
		<small class="m-4 text-muted"><em>첨부된 파일이 없습니다.</em></small>
	</c:otherwise>
	</c:choose>
</div>
<div class="card">
	<div class="card-body">
		<div class="comment-tree" id="cmtTree">
			<!-- cloneNode로 댓글트리 출력 -->
		</div>
	</div>
	<div class="card-body py-1 text-end">
		<c:if test="${username eq post.userId }">
			<input type="button" value="게시글 수정" class="btn btn-outline-primary btn-sm" onclick="fn_updatePost()" />
			<input type="button" value="게시글 삭제" class="btn btn-outline-danger btn-sm" onclick="fn_deletePost()" />
			<input type="button" value="덧글 등록" class="btn btn-sm btn-outline-success" data-post-id="${post.postId }" onclick="toggle_insertCmt(this)" />
		</c:if>
	</div>
</div>
<div class="cmt-template" style="display:none;">
	<!-- 댓글용 템플릿 -->
	<div class="comment-section d-flex gap-3 py-3 border-bottom align-items-start" id="cmtSection" style="display:none;" data-cmt-id="" data-cmt-parent-id="">

	<!-- 프로필 이미지 -->
	<img src="#" alt="user-avatar" class="rounded-circle flex-shrink-0" width="40" height="40" />

	<!-- 본문 -->
	<div class="flex-grow-1">
		<div class="d-flex justify-content-between align-items-center mb-1">
			<div class="d-flex align-items-center gap-2">
				<span class="cmt-user fw-bold"></span>
				<small class="cmt-time text-muted"></small>
			</div>
			<div class="btn-group btn-group-sm">
				<button type="button" class="btn btn-outline-danger btn-comment-delete" style="display:none;">삭제</button>
				<button type="button" class="btn btn-outline-primary btn-comment-update" style="display:none;">수정</button>
				<button type="button" class="btn btn-outline-secondary btn-comment-reply">답글</button>
			</div>
		</div>
		<div class="comment-content">
			<span class="cmt-content mb-0 fs-6"></span>
		</div>
	</div>
</div>
<div class="comment-form card border-0 mb-2" id="cmtForm" style="display:none;">
<div class="card-body py-2">
<form class="d-flex gap-2 align-items-start">
<!-- hidden field -->
<input type="hidden" name="postId" value="${post.postId}" />
<input type="hidden" name="cmtParentId" />
<input type="hidden" name="cmtDepth" />

<!-- 프로필 -->
<img src="#" alt="me-avatar" class="rounded-circle flex-shrink-0" width="36" height="36" />

<!-- 입력창 -->
<div class="flex-grow-1">
<textarea class="form-control" name="cmtContent" rows="2" placeholder="댓글을 입력하세요..."></textarea>
</div>

<!-- 버튼 -->
<div class="d-flex flex-column gap-1">
<button type="button" class="btn-save btn btn-sm btn-primary">등록</button>
<button type="button" class="btn-cancel btn btn-sm btn-outline-secondary">취소</button>
</div>
</form>
</div>
</div>
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
	//const originalCmtDiv.querySelector(".cmt-content")?.innerText || "";
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
		console.debug("덧글수정버튼:: postId",cmtForm.querySelector("input[name=postId]").value);
		console.debug("덧글수정버튼:: cmtContent",cmtForm.querySelector("textarea[name=cmtContent]").value );
		console.debug("덧글수정버튼:: cmtDepth", cmtForm.querySelector("input[name=cmtDepth]").value);
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
		//sectionClone.classList.add("ms-" + Math.min(cmt.cmtDepth * 20, 5));
		console.debug("---sectionClone::: ", sectionClone.style);
		
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














