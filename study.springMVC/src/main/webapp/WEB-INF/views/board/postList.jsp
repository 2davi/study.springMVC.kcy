<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<div><!-- 게시판 유형 위치할 1행 -->
	<h5>자유게시판</h5>
</div>
<div class="wrapper card-body">

	<div class="container"><!-- 검색박스 위치할 2행 -->
	<div class="row">
		<div class="searchBox col-sm-6">
		<div class="row w-100">
			<div class="col-sm-4">
				<div><label for="searchkeyword"></label></div>
				<div>
					<select id="searchKeyword" class="form-select form-select-sm mb-3" size="1" aria-label="size 3 select example" required>
						<!-- <option value="0">--전체--</option> -->
						<option value="1">작성자(ID)</option>
						<option value="2">작성자(NM)</option>
						<option value="3">글+내용</option>
						<option value="4" selected>글 제목</option>
						<option value="5">내용</option>
					</select>
				</div>
			</div>
			<div class="col-sm-8">
				<div><label for="searchTerm"></label></div>
				<div class="input-group input-group-sm mb-3">
					<input type="text" class="form-control" placeholder="검색어 입력" name="searchTerm" id="searchTerm"/>
					<input type="button" id="searchBtn" class="btn btn-outline-secondary" value="검색" onclick="jq_searchId()"/>
					<input type="button" id="searchReset" class="btn btn-outline-secondary" value="검색초기화" onclick="searchReset()" />
					<div>
					</div>
				</div>
			</div>
		</div>
		</div>
		<script>
			window.addEventListener("DOMContentLoaded", () => {
				
				const params = new URLSearchParams(window.location.search);
				const key = params.get("key");
				const term= params.get("term");
				
				if(key != null) {
					document.getElementById("searchKeyword").value = key;
				}
				if(term) {
					document.getElementById("searchTerm").value = decodeURIComponent(term);
				}
			});
		</script>
		<div class="col d-flex flex-row-reverse">
			<!-- 글 등록버튼(상단) -->
			<div class="mb-3">
				<div><label></label></div>
				<input type="button" class="id_insertPage btn btn-secondary btn-sm" value="등록" onclick="fn_post_insertPage()" />
			</div>
		</div>
	</div>
	</div>
	<div>
		<div class="container">
			<div id="absoluteHeader" class="row">
				<div class="col-sm-1 text-center">번호</div>
				<div class="col-sm-5 text-left">제목</div>
				<div class="col-sm-2 text-left">작성자</div>
				<div class="col-sm-3 text-center">등록일자</div>
				<div class="col-sm-1 text-center">첨부</div>
			</div>
			<c:set scope="page" value="1" var="cnt" />
			<c:forEach items="${postList }" var="post">
				<div class="row row-hover py-2" onclick='location.href="/board/forum/post?postId=${post.postId}"' style="cursor:pointer;" >
					<div class="col-sm-1 text-center">${cnt }</div>
					<div class="col-sm-5 text-left">${post.postTitle }</div>
					<div class="col-sm-2 text-left">${post.userId }</div>
					<div class="col-sm-3 text-center kcy_rgst_datetime">${post.rgstDt }</div>
					<div class="col-sm-1 text-center"></div>
				</div>
				<c:set var="cnt" value="${cnt +1 }" />
			</c:forEach>
		</div>
		<div class="container"><!-- 글 등록버튼(하단) -->
			<div class="row">
				<div class="col"></div>
				<div class="col d-flex flex-row-reverse">
					<div>
						<div><label></label></div>
						<input type="button" class="id_insertPage btn btn-secondary btn-sm" value="등록" onclick="fn_post_insertPage()" />
					</div>
				</div>
			</div>
		</div>
		<div><!-- 페이지네이션 -->
		
		</div>
	</div>
</div>
<script defer>
function fn_post_insertPage() {
	let boardCate = "forum";
	
	location.href = "/board/" +boardCate+ "/post/insert";
}

function jq_searchId() {
	let searchKeyword = $("#searchKeyword").val();
	console.debug("--------00: ", searchKeyword);
	let searchTerm = $("#searchTerm").val();
	console.debug("--------00: ", searchTerm);
	const urlSafeEncoded = encodeURI(`/board/forum/list?key=\${searchKeyword}&term=\${searchTerm}`);
	console.debug("--------00: ", urlSafeEncoded);
	location.href = urlSafeEncoded;
}

function toMonthDayTime(str) {
	  if (typeof str !== "string") return "";

	  // "Tue Sep 23 13:02:25 KST 2025"
	  const parts = str.trim().split(/\s+/);
	  if (parts.length < 6) return "";

	  const monthMap = {
	    Jan:"01", Feb:"02", Mar:"03", Apr:"04", May:"05", Jun:"06",
	    Jul:"07", Aug:"08", Sep:"09", Oct:"10", Nov:"11", Dec:"12"
	  };

	  const month = monthMap[parts[1]];
	  const day   = parts[2].padStart(2, "0");
	  const [hh, mm] = parts[3].split(":"); // 초는 버림

	  if (!month || !hh || !mm) return "";
	  return `\${month}-\${day} \${hh.padStart(2,"0")}:\${mm.padStart(2,"0")}`;
	}

searchTerm.addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        document.getElementById('searchBtn').click();
    }
});

$(document).ready(function() {
 	const $cols_rgstDt = $(".kcy_rgst_datetime");
	console.debug("cols_rgstDt: ", $cols_rgstDt);
	
	$cols_rgstDt.each(function(idx, col) {
		const $col_rgstDt = $(col);
		console.debug("col_rgstDt", $col_rgstDt);
		
		let textlol = $col_rgstDt.html();
		console.debug("col_rgstDt.text()", textlol);
		
		let parsedStr = toMonthDayTime(textlol);
		console.debug("parsedStr", parsedStr);
		$col_rgstDt.text(parsedStr);
	});

	//목록 짝홀수 배경바꾸기
	$(".row-hover:even").addClass("row-even");
	$(".row-hover:odd").addClass("row-odd");
	
});
</script>
</body>
</html>