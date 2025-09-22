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
</script>
</head>
<body>
<h2>userDetail.jsp</h2>
<div>
	<table border="1">
		<tr><th colspan="2">회원 정보</th></tr>
		<tr>
			<th>프로필 사진 <br/>
				<input type="button" value="이미지 저장" onclick="fn_downloadProfileImg()"
					<c:if test="${empty user.profileGrpId }">disabled</c:if>
				/>
			</th>
			<td><img class="image-preview" src="${userProfileImgSrc }" alt="프로필_이미지" /></td>
		</tr>
		<tr><th>사용자 ID</th><td>${user.userId }</td></tr>
		<%-- <tr><th>사용자 PW</th><td>${user.userPw }</td></tr> --%>
		<tr><th>사용자 이름</th><td>${user.userNm }</td></tr>
   		<tr><th>사용자 주민번호</th>
			<td>
				<c:if test="${not empty user.regno1 and not empty user.regno2 }">
					${user.regno1 }-${user.regno2 }
				</c:if>
			</td>
		</tr>
		<tr><th>사용자 권한</th>
			<td>
				<c:set var="first" value="true" />
				<c:forEach items="${cmmnCodeList }" var="cmmnCode">
					<c:forEach items="${userRoles }" var="userRole">
						<c:if test="${cmmnCode.cmmnCd eq userRole }">
							<c:if test="${not first }">/&nbsp;</c:if>
							${cmmnCode.cmmnNm }
							<c:set var="first" value="false" />
						</c:if>
					</c:forEach>
				</c:forEach>
			</td>
		</tr>
 		<%-- <tr><th colspan="2">관리 정보</th></tr>
		<tr><th>등록자 ID</th>
			<td>
			<c:if test="${not empty user.rgstId }">
				${user.rgstId }
			</c:if>
			</td>
		</tr>
		<tr><th>등록일시</th>
			<td>
			<c:if test="${not empty user.rgstDt}">
				${user.rgstDt }
			</c:if>
			</td>
		</tr>
		<tr><th>수정자 ID</th>
		<td>
			<c:if test="${not empty user.updtId}">
				${user.updtId }
			</c:if>
			</td>
		</tr>
		<tr><th>수정일시</th>
			<td>
			<c:if test="${not empty user.updtDt}">
				${user.updtDt }
			</c:if>
			</td>
		</tr>
		<tr><th>삭제여부</th>
				<td>${user.delYn }</td>
		</tr> --%>
	</table>
</div>
<div>
	<input type="button" value="수정" onclick="updatePage()"/>
	<input type="button" value="사용자삭제" onclick="deleteUser()"/>
	<input type="button" value="목록" onclick="listPage()" />
</div>
<script>
function fn_downloadProfileImg() {
	
	const profileGrpId = "${user.profileGrpId}";
	if(profileGrpId !== null) {
		location.href = `/cmmn/user/profile-img/select?profileGrpId=${user.profileGrpId}`;
	} else {
		return;
	}
}

</script>

</body>
</html>
<!-- 
	/* 
	if( confirm("사용자 ${user.userNm}(${user.userId})를 삭제하시겠습니까?") ) {
		const resp = await fetch("/cmmn/user/delete?userId=${user.userId}", {
			method: "POST"
			, headers: {
				"accept": "application/json"
				,"content-type": "application/json"
			}
			, body: JSON.stringify({"userId": "${user.userId}"})
		});
		
		if (!resp.ok) {
		  const text = await resp.text();
		  console.error("서버 오류:", resp.status, text);
		  return;
		}
		const jsObj = await resp.json();
		console.debug("jsObj = ", jsObj);
		 
		if(jsObj.success === true) {
			alert("성공적으로 삭제 완료.");
			location.href = "${pageContext.request.contextPath}/cmmn/user/list";
		} else {
			alert("삭제 도중 오류 발생.");
			location.href = "${pageContext.request.contextPath}/cmmn/user/detail?userId=${user.userId}";
		} 
	} else {
		location.href = "${pageContext.request.contextPath}/cmmn/user/list";
	} */

 -->