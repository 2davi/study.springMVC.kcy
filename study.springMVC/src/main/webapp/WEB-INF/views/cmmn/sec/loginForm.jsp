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

<h2>loginForm.jsp</h2>

로그인페이지로 이동할 링크<br/>
걔를 해결할 컨트롤러<br/>
로그인페이지<br/>

Security Tag 사용<br/>

<c:if test="${loginFailMsg != null}">
	<div class="error">${loginFailMsg }</div>
	<%
        request.removeAttribute("loginFailMsg");
    %>
</c:if>


<form method="POST" action="/cmmn/sec/loginProc">
	<label for="userId">아이디</label>
	<input type="text" name="userId" />
	<label for="userPw">비밀번호</label>
	<input type="password" name="userPw"/>
    <%-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
	<input type="submit" value="로그인" />
	<sec:csrfInput />
</form>

</body>
</html>