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
	<h5>사용자 상세 조회</h5>
</div>
<div>
	<table class="table">
		<tr><th colspan="2" class="text-center card-title">회원 정보</th></tr>
		<tr>
			<th class="text-end">프로필 사진 <br/>
				<input type="button" value="이미지 저장" class="btn btn-outline-warning btn-sm" onclick="fn_downloadProfileImg()"
					<c:if test="${empty user.profileGrpId }">disabled</c:if>
				/>
			</th>
			<td><img class="image-preview" src="${userProfileImgSrc }" alt="프로필_이미지" /></td>
		</tr>
		<tr><th class="text-end">사용자 ID</th><td><strong>${user.userId }</strong></td></tr>
		<%-- <tr><th>사용자 PW</th><td>${user.userPw }</td></tr> --%>
		<tr><th class="text-end">사용자 이름</th><td><strong>${user.userNm }</strong></td></tr>
   		<tr><th class="text-end">사용자 주민번호</th>
			<td>
				<c:if test="${not empty user.regno1 and not empty user.regno2 }">
					<strong>${user.regno1 }-${user.regno2 }</strong>
				</c:if>
			</td>
		</tr>
		<tr><th class="text-end">사용자 권한</th>
			<td>
				<c:set var="first" value="true" />
				<c:forEach items="${cmmnCodeList }" var="cmmnCode">
					<c:forEach items="${userRoles }" var="userRole">
						<c:if test="${cmmnCode.cmmnCd eq userRole }">
							<c:if test="${not first }">/&nbsp;</c:if>
							<strong>${cmmnCode.cmmnNm }</strong>
							<c:set var="first" value="false" />
						</c:if>
					</c:forEach>
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td></td>
			<td class="d-flex flex-row bd-highlight">
				<div class="p-2 bd-highlight row-hover"><span style="cursor:pointer;" onclick="updatePage()">수정</span></div>
				<div class="p-2 bd-highlight row-hover"><span style="cursor:pointer;" onclick="deleteUser()">삭제</span></div>
				<div class="p-2 bd-highlight row-hover"><span style="cursor:pointer;" onclick="listPage()">목록</span></div>
<!-- 				<input type="button" value="수정" class="btn btn-sm btn-outline-primary" onclick="updatePage()"/>
				<input type="button" value="사용자삭제" class="btn btn-sm btn-outline-primary" onclick="deleteUser()"/>
				<input type="button" value="목록" class="btn btn-sm btn-outline-primary" onclick="listPage()" /> -->
			</td>
		</tr>
	</table>
</div>
<script>
function updatePage() {
	location.href="${pageContext.request.contextPath}/cmmn/user/update?userId=${user.userId}";
};

function deleteUser() {
	location.href="/cmmn/user/delete?userId=${user.userId}";
}

function listPage() {
	location.href = "/cmmn/user/list";
}
function fn_downloadProfileImg() {
	
	const profileGrpId = "${user.profileGrpId}";
	if(profileGrpId !== null) {
		location.href = `/cmmn/user/profile-img/select?profileGrpId=${user.profileGrpId}`;
	} else {
		return;
	}
}

$(document).ready(function() {
	const $slotList = $("row-hover");
	$slotList.each(function(idx, slot) {
		const $slot = $(slot);
		if(idx % 2 == 0) { $slot.addClass("row-odd"); }
		else { $slot.addClass("row-even"); }
		console.debug($slot);
	});
	
});
</script>

</body>
</html>