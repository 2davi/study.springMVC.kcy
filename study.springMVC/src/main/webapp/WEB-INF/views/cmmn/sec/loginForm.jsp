<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<div class="d-flex flex-column align-items-stretch">
	<div class="d-flex card w-100 h-100"  >
		<div class="card-body">
			<h5 class="card-title">로그인 페이지</h5>
			<p class="card-text text-danger h-3">
				<c:if test="${not empty loginFailMsg }">${loginFailMsg }</c:if>
			</p>
			<form method="POST" action="/cmmn/sec/loginProc" class="card-text">
				<div class="form-attr d-flex flex-row">
					<label class="form-label text-end" for="userId" style="padding:8px 20px 0 0;">아이디</label>
					<input class="form-control w-75" type="text" name="userId" />
				</div>
				<div class="form-attr d-flex flex-row">
					<label class="form-label text-end" for="userPw" style="padding:8px 20px 0 0;">비밀번호</label>
					<input class="form-control w-75" type="password" name="userPw"/>
				</div>
				<div class="form-attr px-0 justify-content-center">
				    <input class="form-control" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="submit" class="btn btn-outline-primary w-50 mx-5" value="로그인" />
				</div>
				<sec:csrfInput />
			</form>
		</div>
		<ul class="list-group list-group-flush">
			<li class="list-group-item text-center">아이디 찾기 / 비밀번호 찾기</li>
			<li class="list-group-item text-center">소셜 로그인</li>
			<li class="list-group-item text-center">이용약관</li>
		</ul>
		<div class="card-body">
			<a href="#" class="card-link">Card link</a>
			<a href="#" class="card-link">Another link</a>
		</div>
	</div>
</div>
</body>
</html>