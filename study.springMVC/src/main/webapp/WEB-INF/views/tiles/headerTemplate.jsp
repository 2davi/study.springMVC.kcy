<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="Header">
	<div class="nav-bar">
		<sec:authorize access="isAnonymous()">
			<a href="/cmmn/user/insert">회원가입</a>
			<a href="/cmmn/sec/loginForm">로그인</a>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<sec:authentication property="principal.userVO.userNm" />님 안녕하세요.&nbsp;&nbsp;
			<a href="/">&nbsp;홈&nbsp;&nbsp;</a>|
			<a href="/cmmn/user/list">&nbsp;사용자목록&nbsp;&nbsp;</a>|
			<a href="/board/forum/list">&nbsp;게시판&nbsp;&nbsp;</a>|
			<a href="/cmmn/sec/logout">&nbsp;로그아웃&nbsp;</a>
		</sec:authorize>
		
	
	<!-- 
		<span class="nav-attr" id="signin">회원가입</span>
		<span class="nav-dist" id="dist"> | </span>
		<span class="nav-attr" id="login"><a href="/cmmn/sec/login.do">로그인</a></span> -->
	</div>
</div>