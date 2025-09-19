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
<h2>InsertUser.jsp</h2>
<div>
	<div>
		<h3>신규 사용자 정보 등록</h3>
	</div>
	<div>
		<form id="userInfo" method="POST" enctype="multipart/form-data">
		
		<div class="form-attr">
			<div class="form-label"><label for="userId">*사용자 ID: </label></div>
				<input type="text" id="userId" name="userId" />
		</div>
		<div class="form-attr">
			<div class="form-label"><label for="userPw">*사용자 PW: </label></div>
				<input type="password" id="userPw" name="userPw" />
		</div>
		<div class="form-attr">
			<div class="form-label"><label for="userNm">*사용자 이름: </label></div>
				<input type="text" id="userNm" name="userNm" />
		</div>
		<div class="form-attr">
			<div class="form-label"><label for="regno1">사용자 주민번호: </label></div>
				<input type="number" id="regno1" name="regno1" />
				&nbsp;-&nbsp;
				<input type="number" id="regno2" name="regno2" />
		</div>
		<div class="form-attr">
			<div class="form-label"><label for="userProfileImg">프로필 사진: </label></div>
				<div>
				<div>
					<img class="image-preview" id="previewImg" src="${userProfileImgSrc }" onclick="fn_readImage()" />
				</div>
				<input type="file" id="userProfileImg" name="userProfileImg" class="form-control" accept=".jpg,.png,.webp" />
			</div>
		</div>
		

		<div class="form-attr">
		    <div class="form-label"><label>사용자 권한: </label></div>
			<c:forEach var="commonCode" items="${cmmnCodeList}">
		        <label>
		        <input type="checkbox" name="userRoles" value="${commonCode.cmmnCd}" 
		         style="width=200;"/>
		        ${commonCode.cmmnNm }
		        </label>
			</c:forEach>
		</div>
		<div>
			<input type="submit" value="등록" />
			<input type="reset" value="초기화" />
			<input type="button" value="취소" onclick="fn_cancle()"/>
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