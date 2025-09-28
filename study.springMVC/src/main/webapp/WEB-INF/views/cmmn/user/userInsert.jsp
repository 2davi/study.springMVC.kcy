<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.image-preview {
	width: 300px;
	height: 210px;
	display: flex;
	justify-content: center;
}
</style>
</head>
<body>
<div>
	<h5>신규 사용자 등록</h5>
</div>
<div class="user-writer card shadow-sm">
	<div class="card-body container w-75">
	<form id="userInfo" method="POST" enctype="multipart/form-data">
	<sec:csrfInput/>
		
		<div class="form-attr gap-4">
			<div class="form-label text-end">
				<label class="form-label" for="userId">*사용자 ID: </label>
			</div>
			<div class="">
				<input type="text" id="userId" name="userId" class="form-control" />
			</div>
		</div>
		<div class="form-attr gap-4">
			<div class="form-label text-end ">
				<label class="form-label" for="userPw">*사용자 PW: </label>
			</div>
			<div class="">
				<input type="password" id="userPw" name="userPw" class="form-control" />
			</div>
		</div>
		<div class="form-attr gap-4">
			<div class="form-label text-end ">
				<label class="form-label" for="userNm">*사용자 이름: </label>
			</div>
			<div class="">
				<input type="text" id="userNm" name="userNm" class="form-control" />
			</div>
		</div>
		<div class="form-attr gap-4">
			<div class="form-label text-end ">
				<label class="form-label" for="regno1">사용자 주민번호: </label>
			</div>
			<div class="">
				<input type="number" id="regno1" name="regno1" class="form-control" />
				&nbsp;-&nbsp;
				<input type="number" id="regno2" name="regno2" class="form-control" />
			</div>
		</div>
		<div class="form-attr gap-4">
			<div class="form-label text-end ">
				<label class="form-label" for="userProfileImg">프로필 사진: </label>
			</div>
			
			<div class="">
				<div>
				<div>
					<img class="image-preview" id="previewImg" src="${userProfileImgSrc }" onclick="fn_readImage()" />
				</div>
				<input type="file" id="userProfileImg" name="userProfileImg" class="form-control" accept=".jpg,.png,.webp" />
			</div>
			</div>
		</div>
		

		<div class="form-attr gap-4">
		    <div class="form-label text-end">
		    	<label class="form-label">사용자 권한: </label>
		    </div>
			<c:forEach var="commonCode" items="${cmmnCodeList}">
		        <label>
		        <input type="checkbox" name="userRoles" value="${commonCode.cmmnCd}" 
		         style="width=200;"/>
		        ${commonCode.cmmnNm }
		        </label>
			</c:forEach>
		</div>
		<div class="form-attr gap-4">
			<div class="form-label text-end ">
				<label class="form-label" for="userNm"></label>
			</div>
			<div class="btn-group" role="user-insert-group" aria-label="Basic example">
				<input type="submit" class="btn btn-sm btn-outline-primary" value="등록" />
				<input type="reset"  class="btn btn-sm btn-outline-secondary" value="초기화" />
				<input type="button" class="btn btn-sm btn-outline-danger" value="취소" onclick="fn_cancle()"/>
			</div>
		</div>
	</form>
	</div>
</div>
<script>
function fn_cancle() {
	document.getElementById("userInfo").reset();
    window.location.href = "/cmmn/user/list";
}

document.addEventListener("DOMContentLoaded", () => {
	const input = document.getElementById("userProfileImg");
	input.addEventListener("change", () => {
		fn_readImage(input);
		
	});
	
});

// 0917_이미지 미리보기
function fn_readImage(input) {
    if(input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e){
            const previewImage = document.getElementById("previewImg");
            previewImage.src = e.target.result;
        }
        // reader가 이미지 읽도록 하기
        reader.readAsDataURL(input.files[0]);
    }
}
</script>
</body>
</html>