<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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

#formSecondCheck, /* #formChangePassword */ {
	display: none;
}
</style>
</head>
<body>
<div>
	<h5>사용자 정보 수정</h5>
</div>
<div class="user-writer card shadow-sm">
	<div class="card-body container w-75">
	<form method="POST" action="/cmmn/user/update" enctype="multipart/form-data">
	<sec:csrfInput/>
		<div class="form-attr gap-4">
			<div class="form-label text-end">
				<label class="form-label" for="userId">*사용자 ID: </label>
				<input id="inputUserId" type="hidden" name="userId" value="${user.userId }" />
			</div>
			<div>
				<input type="text" value="${user.userId }" class="form-control" readonly disabled />
			</div>
		</div>
		<div class="form-attr gap-4">
			<div class="form-label text-end ">
				<label class="form-label" for="userPw">*사용자 PW: </label>
			</div>
			<div id="formChangePassword">
				<div class="form-attr">
					<div class="form-label">
						<label for="userPw">*신규 비밀번호: </label>
					</div>
					<div>
						<input type="password" id="userPw" name="userPw" class="form-control" />
					</div>
				</div>
 				<div class="form-attr">
					<div class="form-label">
						<label for="userPw_re">*비밀번호 확인: </label>
					</div>
					<div>
						<input type="password" id="userPw_re" name="userPw_re" class="form-control" />
					</div>
				</div>
			</div>
		</div>
		<div class="form-attr gap-4">
			<div class="form-label text-end ">
				<label class="form-label" for="userNm">*사용자 이름: </label>
			</div>
			<div>
				<input type="text" id="userNm" name="userNm" class="form-control" value="${user.userNm }" />
			</div>
		</div>
		<div class="form-attr gap-4">
			<div class="form-label text-end ">
				<label class="form-label" for="regno1">사용자 주민번호: </label>
			</div>
			<div>
				<input type="number" id="regno1" name="regno1" class="form-control" value="${user.regno1 }" />
				&nbsp;-&nbsp;
				<input type="number" id="regno2" name="regno2" class="form-control" value="${user.regno2 }" />
			</div>
		</div>
		
		<div class="form-attr gap-4">
			<div class="form-label text-end ">
				<label class="form-label" for="userProfileImg">프로필 사진: </label>
				<input type="button" id="deleteProfileImg" class="btn btn-sm btn-outline-dark" value="이미지 삭제" onclick="fn_deleteProfileImg()" />
			</div>
			<div>
				<div>
					<img class="image-preview" id="previewImg" src="${userProfileImgSrc }" onclick="fn_readImage()" accept=".jpg,.png,.webp" />
				</div>
				<div>
					<input type="file" id="userProfileImg" name="userProfileImg" class="form-control" />
				</div>
			</div>
		</div>
		<div class="form-attr gap-4">
		    <div class="form-label text-end">
		    	<label class="form-label">사용자 권한: </label>
		    </div>
			<c:forEach var="commonCode" items="${cmmnCodeList}">
				<c:set var="isChecked" value="false" />
				<c:forEach var="userRole" items="${userRoles }">
					<c:if test="${userRole eq commonCode.cmmnCd }">
						<c:set var="isChecked" value="true" />
					</c:if>
				</c:forEach>
				<label> <input type="checkbox" name="userRoles"
					value="${commonCode.cmmnCd}"
					<c:if test="${isChecked}">checked</c:if> />
					${commonCode.cmmnNm }
				</label>
			</c:forEach>
		</div>
		<div class="form-attr gap-4">
			<div class="form-label text-end ">
				<label class="form-label" for="userNm"></label>
			</div>
			<div class="btn-group" role="user-insert-group" aria-label="Basic example">
				<input type="submit" class="btn btn-sm btn-outline-primary" value="수정" />
				<input type="reset"  class="btn btn-sm btn-outline-secondary" value="초기화" />
				<input type="button" class="btn btn-sm btn-outline-danger" value="취소" onclick="fn_cancle()"/>
			</div>
		</div>
	</form>
	</div>
</div>

<script>

document.addEventListener("DOMContentLoaded", () => {
  const divChangePassword = document.getElementById("divChangePassword");
  const formSecondCheck = document.getElementById("formSecondCheck");
  const formChangePassword = document.getElementById("formChangePassword");

  const btnChangePassword = document.getElementById("changePassword");
  const btnCancelChangePassword = document.getElementById("cancelChangePassword");
  const btnConfirm = document.getElementById("confirmChangePassword");

  // step1
  btnChangePassword.addEventListener("click", () => {
    divChangePassword.style.display = "none";
    formSecondCheck.style.display = "block";
  });

  // step2
  btnCancelChangePassword.addEventListener("click", () => {
    formSecondCheck.style.display = "none";
    divChangePassword.style.display = "block";
  });

  // step3
  btnConfirm.addEventListener("click", () => {
    const secondCheck = document.getElementById("secondCheck").value;
    if (!secondCheck) {
      alert("현재 비밀번호를 입력하세요.");
      return;
    }
    // Ajax 성공 가정
    formSecondCheck.style.display = "none";
    formChangePassword.style.display = "block";
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


// 0918_이미지 삭제
function fn_deleteProfileImg() {
	if(confirm("프로필 이미지를 삭제하시겠습니까?\n작성중이었던 변경 사항을 잃게 됩니다...")) {
		if("${userProfileImgSrc}" == "/user-profile/user-aae74513-54fe-4dda-8060-9bcc85ad5ba8-blank-image-placeholder-profile-picture-260nw-1923893873.webp") {
			alert("등록된 프로필 이미지가 없습니다.");
			 return;
		} else {
			const userId = document.getElementById("inputUserId").value;
			location.href = "/cmmn/user/delete/img?userId=" + userId;			
		}
	}	
}

function fn_cancle() {
	if(document.referrer) {
		location.href = document.referrer;
	} else {
		location.href = "/cmmn/user/list";
	}
}

function fn_toggleChangePassword() {
    divChangePassword.style.display = "none";
    formSecondCheck.style.display = "block";
}
</script>
</body>
</html>