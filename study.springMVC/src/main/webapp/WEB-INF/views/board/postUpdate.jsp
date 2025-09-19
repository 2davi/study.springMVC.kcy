<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>postUpdate.jsp</h3>
<div class="wrapper">
	<div>
		<h2>자유게시글 작성 </h2>
	</div>
	<div><!-- 검색박스 공간만큼 비워둘 2행 --></div>
	<div><!-- 게시글 작성 폼이 위치할 3행 -->
		<div><!-- 글 등록버튼(상단) 만큼 비워둘 공간 --></div>
		<div><!-- 게시글 목록 -->
			<form method="POST" action="/board/post/insert" enctype="multipart/form-data">
				<div class="form-attr">
					<div class="form-label">
						<label for="postTitle">제목: </label>
					</div>
					<input type="text" id="postTitle" name="postTitle" value="${ }" />
				</div>
				<div class="form-attr">
					<div class="form-label">
						<label for="postContent">내용: </label>
					</div>
					<textarea id="postContent" name="postContent">${ }</textarea>
				</div>
				<div class="form-attr">
					<div class="form-label">
						<label for="attachFiles">첨부파일: </label>
					</div>
					<div>
						<input type="file" id="attachFiles" name="attachFiles[]" multiple />
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

</body>
</html>