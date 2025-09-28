<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!-- Tiles 설정 0202 start -->
<!-- 공통변수 처리 -->
<c:set var="CONTEXT_PATH" value="${pageContext.request.contextPath}"
	scope="application" />
<c:set var="RESOURCES_PATH" value="${CONTEXT_PATH}/resources"
	scope="application" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="description" content="">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<sec:csrfMetaTags />
<script type="text/javascript">
	var CONTEXT_PATH = "${CONTEXT_PATH}";
	var RESOURCES_PATH = "${RESOURCES_PATH}";
</script>
<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script src="${RESOURCES_PATH}/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script src="${RESOURCES_PATH}/js/sidebarmenu.js"></script>
<script src="${RESOURCES_PATH}/js/app.min.js"></script>
<script src="${RESOURCES_PATH}/libs/apexcharts/dist/apexcharts.min.js"></script>
<script src="${RESOURCES_PATH}/libs/simplebar/dist/simplebar.js"></script>
<%-- <script src="${RESOURCES_PATH}/js/dashboard.js"></script> --%>
<!-- solar icons -->
<script src="https://cdn.jsdelivr.net/npm/iconify-icon@1.0.8/dist/iconify-icon.min.js"></script>
<link rel="stylesheet" href="${RESOURCES_PATH}/css/custom.css">
<link rel="shortcut icon" type="image/png" href="${RESOURCES_PATH}/images/logos/favicon.png" />
<link rel="stylesheet" href="${RESOURCES_PATH}/css/styles.min.css" />
<title>
	<tiles:insertAttribute name="title" />
</title>
</head>
<body>
<!-- <div class='wrap'> -->
<!--  Body Wrapper -->
	<div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed">


			<tiles:insertAttribute name="app-left" />
			
			<div class='content body-wrapper'>
				<tiles:insertAttribute name="app-header" />
					<div class="page_content body-wrapper-inner kcy_container">
						<tiles:insertAttribute name="app-body" />
					</div>
			</div>
			
			<tiles:insertAttribute name="app-foot" />

	</div>
<!-- </div> -->
<script>
<!-- 0923_JQuery 기반 스크롤바 설정 -->
$(document).ready(function() {
	$(".kcy_container").css({
		'height':'100%'
		, 'overflow-y':'scroll'
	});
	$(".page_content").css({
		'padding':'10px'
	});
	$(".content").css({
		"display": "flex"
	});
	$(".left-sidebar").css("position", "relative");
	$(".wrap").css("height", "100%");
/* 	$(".kcy_container").addClass(".kcy_container::-webkit-scrollbar");
	$(".kcy_container").addClass(".kcy_container::-webkit-scrollbar-track");
	$(".kcy_container").addClass(".kcy_container::-webkit-scrollbar-thumb");
	$(".kcy_container").addClass(".kcy_container::-webkit-scrollbar-button"); */
	

});

const url = "/";
if (window.opener) {
	console.debug("분기를 타고 있나? ");
	window.opener.location.href = url;
	window.close();
}
</script>
</body>
</html>