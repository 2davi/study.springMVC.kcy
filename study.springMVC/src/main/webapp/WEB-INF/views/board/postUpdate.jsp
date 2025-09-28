<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="mb-3">자유게시글 수정</h3>

<div class="post-writer card shadow-sm">
	<div class="card-body">
		<div><!-- 검색박스 공간만큼 비워둘 2행 --></div>
		<div><!-- 게시글 작성 폼이 위치할 3행 -->
			<div><!-- 글 등록버튼(상단) 만큼 비워둘 공간 --></div>
			<div><!-- 게시글 목록 -->
				<form id="postForm" method="POST" action="/board/post/update" enctype="multipart/form-data">
					<input type="hidden" id="postId" name="postId" value="${post.postId }" />
					<sec:csrfInput/>
					<div class="mb-3 d-flex flex-row gap-3 align-items-center">
						<div class="form-attr gap-4" style="padding-left:45px">
							<div class="d-flex flex-column gap-1">
								<label for="boardCd" class="form-label text-subtitle">게시판 선택: 
									<select id="boardCd" name="boardCd" class="form-select form-select-sm" required>
										<option value="AB010" selected>자유</option>
										<option value="AB020">유머</option>
									</select>
								</label>
							</div>
							<div class="d-flex flex-column gap-1">
								<label class="form-label" style="padding-left: 1.8em;margin-bottom:0;">글 설정:</label>
									<div class="d-flex flex-row">
										<div class="form-check d-flex flex-column ">
											<label>
												<input type="checkbox" name="hiddenYn" value="Y" style="width=200;" />
												비공개
											</label>
										</div>
										<div class="form-check d-flex flex-column ">
											<label>
												<input type="checkbox" name="cmtYn" value="Y" style="width=200;" checked/>
												댓글허용
											</label>
										</div>
									</div>
							</div>
						</div>
					</div>
					<div class="form-attr mb-3 gap-4">
						<div class="form-label">
							<label for="postTitle" class="form-label text-end" style="width:80px;">제목: </label>
						</div>
						<input type="text" id="postTitle" name="postTitle" class="form-control" value="${post.postTitle }" />
					</div>
					<div class="form-attr mb-3 gap-4">
						<div class="form-label">
							<label for="postContent" class="form-label text-end" style="width:80px;">내용: </label>
						</div>
						<textarea id="postContent" name="postContent" class="form-control" rows="10">${post.postContent }</textarea>
					</div>
					<div class="form-attr mb-3 gap-4">
						<div class="form-label">
							<label for="attachFiles" class="form-label text-end" style="width:80px;">첨부파일: </label>
						</div>
						<div class="d-flex flex-column gap-1 w-100">
							<div>
								<input type="hidden" id="postAttachGrpId" name="attachGrpId" value="${(post.attachGrpId != null) ?  post.attachGrpId : null}" />
								<input type="button" id="fakeAttachFiles" value="파일추가" class="btn btn-outline-warning btn-sm"  onclick="fn_activateAttachFiles()" />
								<input type="file" id="tempAttachFiles" onchange="fn_addAttachFiles(this)" multiple hidden /> 
								
								<div class="div-attach-file-list border rounded p-2 w-100" id="divAttachFilesList" style="min-height:40px;">
									<ul class="ul-attach-file-list mb-0" id="ulAttachFilesList">
										<c:forEach var="file" items="${attachFileList}">
											<c:if test="${file != null }">
											<li data-type="old" data-seq="${file.fileSeq}">
												${file.fileOrgNm}
												<input type="checkbox" class="deleteOldFile" value="${file.fileSeq}"/> 삭제
											</li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>
				
			</div>
			<div class="text-right"><!-- 글 등록버튼 -->
				<input type="button" value="등록" class="btn btn-sm btn-outline-secondary"  onclick="fn_submitPost()" />
				<input type="button" value="초기화" class="btn btn-sm btn-outline-primary" onclick="fn_resetPost()"/>
			</div>
				</form>
			<div><!-- 페이지네이션 만큼 비워둘 공간 --></div>
		</div>
	</div>
</div>
<script defer>
const postForm = document.getElementById('postForm');
const postId = document.getElementById("postId");
const boardCd = document.getElementById('boardCd');
const postTitle = document.getElementById('postTitle');
const postContent = document.getElementById('postContent');
const postAttachGrpId = document.getElementById('postAttachGrpId');
//const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
//const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
	
const attachInput = document.getElementById('attachFiles');
const tempAttachInput = document.getElementById('tempAttachFiles');
	
const attachList = document.getElementById('ulAttachFilesList');
const jsonAttachList = ${jsonAttachList};
let attachFiles = jsonAttachList.map(vo => ({
	  name: vo.fileOrgNm
	  , size: vo.fileSize
	  , lastModified: vo.lastModified
	  , seq: vo.fileSeq
	}));
let deleteFileSeq = new Set();
console.debug("가져온 첨부파일 리스트: jsonAttachList {{},{},...}", jsonAttachList);

	
/** 커스텀 함수 */
//-- dudupe(arr[]) : 동일 파일의 중복 첨부를 제거.
function dedupe(arr) {
	console.debug("dedupe() 실행. 전달된 개수:", arr.length);
	const m = new Map();
	arr.forEach(file => {
		  console.log("file:", file);
		  console.log("name:", file.name, "size:", file.size, "lastModified:", file.lastModified);
		const key = `\${file.name}_\${file.size}_\${file.lastModified}`;
		console.debug("처리 중:", key);
		m.set(key, file);
	});
	console.debug("중복 제거 후 개수:", m.size);
	return Array.from(m.values());
};
	
//-- renderList() : 전역변수를 참조해서 첨부파일의 리스트를 새로 렌더링
function renderList() {
	console.debug("attachFiles:: ", attachFiles);
	attachList.innerHTML = ''; //초기화.
	if(attachFiles.length === 0) {
		attachList.innerHTML = "<li>선택된 파일이 없습니다.</li>";
		return;
	}
	attachFiles.forEach((f, idx) => {
		const li = document.createElement('li');
		// 0925_BootStrap
	    li.classList.add("d-flex","justify-content-between","align-items-center");
		const delBtn = document.createElement('input');
		li.innerHTML = '<span class="card-subtitle"><i class="ti ti-menu-2 fs-5"></i>&nbsp;' + f.name + ' (' + f.size + 'Bytes) </span>';
		delBtn.type = "button";
		// 0925_BootStrap
	    delBtn.className = "btn btn-sm btn-outline-danger";
		delBtn.value= "삭제";
		delBtn.onclick = () => {
			attachFiles.splice(idx, 1);
			console.debug("삭제버튼 클릭: f객체의 seq : ", f.seq);
			deleteFileSeq.add(f.seq);
			console.debug("deleteFileSeq: ", deleteFileSeq);
			deleteFileSeq.delete(undefined);
			console.debug("deleteFileSeq: ", deleteFileSeq);
			renderList();
		};
		
		li.appendChild(delBtn);
		attachList.appendChild(li);
	});
}

//-- fn_activateAttachFiles() : 가짜 버튼 사용
function fn_activateAttachFiles() {
	tempAttachInput.click();
};
	
//-- fn_addAttachFiles(<input type='file'>) : 첨부파일 추가(누적)
function fn_addAttachFiles(input) {
	console.debug("fn_addAttachFiles() 실행.");
	//const selected = Array.from(e.target.files);
	const selected = Array.from(input.files);
	console.debug("selected:: ", selected);
	attachFiles = dedupe([...attachFiles, ...selected]);
	console.debug("dedupe한 뒤의 attachFiles:: ", attachFiles);
	renderList(); //파일이 업로드될 때마다 목록 갱신
	tempAttachInput.value = ''; 
}

//-- fn_submitPost() : 폼 submit 이벤트를 대신 호출 
async function fn_submitPost() {
	console.debug("fn_submitPost() 실행.");
	
	const formData = new FormData();
	formData.append('boardCd', boardCd.value);
	formData.append('postTitle', postTitle.value);
	formData.append('postContent', postContent.value);
	formData.append('attachGrpId', postAttachGrpId.value);
	attachFiles.forEach(f => {
		formData.append('attachFiles', f);
		// 0922_파일 메타정보 컬럼 추가
		formData.append('lastModified', f.lastModified);

	});
	deleteFileSeq.forEach(seq => formData.append('deleteFileSeq', seq))
	console.debug("deleteFileSeq: ", deleteFileSeq);
	
	try {
		const resp = await fetch(postForm.action, {
			method: "POST"
			, body: formData
			/* , headers: {
				"Accept": "application/json"
			} */
		});
		console.debug("서버 응답 상태: ", resp.status);
		
		if(resp.ok) {
			console.debug("resp OK인가?");
			const data = await resp.json();
			console.debug("Response 객체 DATA:: ", data);
			
			let boardCate = data.boardCate;
			
			console.debug("최종 주소:: ",`/board/\${boardCate}/post?postId=\${postId.value}` );
			location.href = `/board/\${boardCate}/post?postId=\${postId.value}`;
		}
	} catch (error) {
		console.error('업로드 실패:', error);
	}
}

function fn_resetPost() {
	postForm.reset();
	attachFiles = jsonAttachList.map(vo => ({
		  name: vo.fileOrgNm
		  , size: vo.fileSize
		  , lastModified: vo.lastModified
		  , seq: vo.fileSeq
		}));
	deleteFileSeq = new Set();
	console.debug("attachFiles:: ", attachFiles);
	renderList();
}
	
//브라우저 로딩 후 최초 렌더링
document.addEventListener("DOMContentLoaded", () => renderList());
</script>
</body>
</html>