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
						<input type="file" id="attachFiles" name="attachFiles" multiple />
						<div id="divAttachFilesList">
							<ul id="ulAttachFilesList">
							</ul>
						</div>
					</div>
				</div>
			
		</div>
		<div><!-- 글 등록버튼 -->
			<input type="submit" value="등록" />
			<input type="reset" value="초기화" />
		</div>
			</form>
		<div><!-- 페이지네이션 만큼 비워둘 공간 --></div>
	</div>
</div>

<script>
  const form = document.getElementById('postForm');
  const attachInput = document.getElementById('attachFiles');
  const listEl = document.getElementById('ulAttachFilesList');


  let files = [];

  // 파일 추가
  attachInput.addEventListener('change', (e) => {
    const selected = Array.from(e.target.files);
    files = dedupe([...files, ...selected]);  // 누적 + 중복 제거
    renderList();
    // 같은 파일을 또 선택해도 change가 다시 뜨도록 값 초기화
    attachInput.value = '';
  });

  // 목록 렌더링
  function renderList() {
    listEl.innerHTML = '';
    if (files.length === 0) {
      const li = document.createElement('li');
      li.textContent = '선택된 파일이 없습니다.';
      listEl.appendChild(li);
      return;
    }
    files.forEach((file, idx) => {
      const li = document.createElement('li');
      li.textContent = file.name + ' ';

      const delBtn = document.createElement('button');
      delBtn.type = 'button';
      delBtn.textContent = '삭제';
      delBtn.addEventListener('click', () => {
        files.splice(idx, 1);
        renderList();
      });

      li.appendChild(delBtn);
      listEl.appendChild(li);
    });
  }
/* 
  // 파일 중복 제거: 이름+사이즈+수정시각 조합으로 Key 생성
  function dedupe(arr) {
    const m = new Map();
    arr.forEach(f => m.set(`${f.name}_${f.size}_${f.lastModified}`, f));
    return Array.from(m.values());
  }

  // 폼 submit 가로채서 FormData로 우리가 보관한 files만 전송
  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const fd = new FormData();
    fd.append('postTitle', document.getElementById('postTitle').value);
    fd.append('postContent', document.getElementById('postContent').value);
    files.forEach(f => fd.append('attachFiles[]', f)); // 같은 이름으로 여러 파일 전송 가능

    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    
    const res = await fetch(form.action, {
    	method: 'POST'
    	, body: fd 
    	, headers: { [header]: token } 
    });
    // 필요하면 여기서 location.href = '/board/post/list' 같은 후처리
    console.log('서버 응답 상태:', res.status);
  });

  // reset 누르면 내부 상태도 초기화
  form.addEventListener('reset', () => {
    files = [];
    renderList();
    attachInput.value = '';
  }); */

  // 초기 렌더
  renderList();
</script>

</body>
</html>