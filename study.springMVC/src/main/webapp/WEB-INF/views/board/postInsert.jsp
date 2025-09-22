<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<sec:csrfMetaTags/>
</head>
<body>
<h3>postInsert.jsp</h3>
<div class="wrapper">
	<div>
		<h2>자유게시글 작성 </h2>
	</div>
	<div><!-- 검색박스 공간만큼 비워둘 2행 --></div>
	<div><!-- 게시글 작성 폼이 위치할 3행 -->
		<div><!-- 글 등록버튼(상단) 만큼 비워둘 공간 --></div>
		<div><!-- 게시글 목록 -->
			<form id="postForm" method="POST" action="/board/post/insert" enctype="multipart/form-data">
				<sec:csrfInput/>
				<div class="form-attr">
					<label for="boardCd">게시판 선택: 
						<select id="boardCd" name="boardCd" required>
							<option value="AB010" selected>자유</option>
							<option value="AB020">유머</option>
						</select>
					</label>
					<label>
						<input type="checkbox" name="hiddenYn" value="Y" style="width=200;" />
						비공개
					</label>
					<label>
						<input type="checkbox" name="cmtYn" value="Y" style="width=200;" checked/>
						댓글허용
					</label>
				</div>
				<div class="form-attr">
					<div class="form-label">
						<label for="postTitle">제목: </label>
					</div>
					<input type="text" id="postTitle" name="postTitle" value="" />
				</div>
				<div class="form-attr">
					<div class="form-label">
						<label for="postContent">내용: </label>
					</div>
					<textarea id="postContent" name="postContent"></textarea>
				</div>
				<div class="form-attr">
					<div class="form-label">
						<label for="attachFiles">첨부파일: </label>
					</div>
					<div>
						<input type="button" id="fakeAttachFiles" value="파일추가_" onclick="fn_activateAttachFiles()" />
						<input type="file" id="tempAttachFiles" onchange="fn_addAttachFiles(this)" multiple hidden /> 
						<!-- <input type="file" id="attachFiles" name="attachFiles" multiple /> -->
						<div class="div-attach-file-list" id="divAttachFilesList">
							<ul class="ul-attach-file-list" id="ulAttachFilesList">
							</ul>
						</div>
					</div>
				</div>
			
		</div>
		<div><!-- 글 등록버튼 -->
			<input type="button" value="등록" onclick="fn_submitPost()" />
			<input type="button" value="초기화" onclick="fn_resetPost()"/>
		</div>
			</form>
		<div><!-- 페이지네이션 만큼 비워둘 공간 --></div>
	</div>
</div>

<script >
const postForm = document.getElementById('postForm');
const boardCd = document.getElementById('boardCd');
const postTitle = document.getElementById('postTitle');
const postContent = document.getElementById('postContent');
//const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
//const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
	
const attachInput = document.getElementById('attachFiles');
const tempAttachInput = document.getElementById('tempAttachFiles');
	
const attachList = document.getElementById('ulAttachFilesList');
let attachFiles = [];
	
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
		const delBtn = document.createElement('input');
		li.textContent = f.name + ' ';
		delBtn.type = "button";
		delBtn.value= "삭제";
		delBtn.onclick = () => {
			attachFiles.splice(idx, 1);
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
	attachFiles.forEach(f => {
		formData.append('attachFiles', f);
		// 0922_파일 메타정보 컬럼 추가
		formData.append('lastModified', f.lastModified);
	});
	
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
			let postId = data.postId;
			location.href = `/board/\${boardCate}/post?postId=\${postId}`;
			console.debug("최종 주소:: ",`/board/\${boardCate}/post?postId=\${postId}` );
		}
	} catch (error) {
		console.error('업로드 실패:', error);
	}
}

function fn_resetPost() {
	postForm.reset();
	attachFiles = [];
	console.debug("attachFiles:: ", attachFiles);
	renderList();
}
	
	
//브라우저 로딩 후 최초 렌더링
renderList();

	// 초기 렌더
renderList();
</script>

</body>
</html>