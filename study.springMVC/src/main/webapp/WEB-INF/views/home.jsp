<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="rootPath" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Insert title here</title>
</head>
<body>
	<script defer>
		$(document).ready(function() {
			const reset = "${serverTime}";
			let currentTime = new Date(reset);

			//const display = document.getElementById("clock");
			const display = $("#clock");

			console.debug("display객체: ", display);
			function updateClock() {
				//display.textContent = currentTime.toLocaleString();
				//jQuery 객체는 jQuery 라이브러리 함수로 감싸줘야 한다.
				display.text(currentTime.toLocaleString());

				currentTime.setSeconds(currentTime.getSeconds() + 1);
			}

			// 최초 렌더링 시 실행
			updateClock();
			// 1초마다 카운트 업^0^
			setInterval(updateClock, 1000);
		});
	</script>
	<h1>Hello!! Korea</h1>
	<h2>
		The time on the Today is <span id="clock"></span>
	</h2>

</body>
</html>


