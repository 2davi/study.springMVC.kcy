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
<div>
	<h5>사용자 관리</h5>
</div>
<div class="wrapper card-body card-shadow-sm">
	<div class="container">
	<div class="row w-100">
		<div class="searchBox col-sm-6">
		<div class="row w-100">
			<div class="col-sm-4">
				<div><label for="searchkeyword"></label></div>
				<div>
					<select id="searchKeyword" class="form-select form-select-sm mb-3" size="1" aria-label="size 3 select example" required>
						<option value="0" selected>--전체--</option>
						<option value="1">아이디</option>
						<option value="2">이름</option>
					</select>
				</div>
			</div>
			<div class="col-sm-8">
				<div><label for="searchTerm"></label></div>
				<div class="input-group input-group-sm mb-3">
					<input type="text" class="form-control" placeholder="검색어 입력" name="searchTerm" id="searchTerm"/>
					<input type="button" id="searchBtn" class="btn btn-outline-secondary" value="검색" onclick="searchId()"/>
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
				<input type="button" class="btn btn-outline-danger btn-sm" value="일괄삭제" />
			</div>
		</div>
	</div>
	</div>
	<div class="card-body py-1 px-3">
		<table class="table table-striped table-hover">
			<thead>
				<tr>
					<th scope="col">no</th>
					<th scope="col">사용자 아이디</th>
					<th scope="col">사용자 이름</th>
					<th scope="col">등록일자</th>
					<th scope="col">비고</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
				<c:when test="${not empty userList }">
					<c:forEach items="${userList}" var="user">
						<tr class="row-hover" onclick="location.href='${pageContext.request.contextPath }/cmmn/user/detail?userId=${user.userId}'">
							<td scope="row">#</td>
							<td>
								<%-- <a href="${pageContext.request.contextPath }/cmmn/user/detail?userId=${user.userId}"> --%>
									${user.userId }
								<!-- </a> -->
							</td>
							<td>${user.userNm }</td>
							<td class="user-rgst-datetime">${user.rgstDt }</td>
							<td></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise> <tr><td colspan="5" class="text-center"><span class="text-muted">검색 결과가 없습니다.</span></td></tr> </c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div class="container"><!-- 글 등록버튼(하단) -->
		<div class="row">
			<div class="col"></div>
			<div class="col d-flex flex-row-reverse">
				<div>
					<div><label></label></div>
					<input type="button" class="btn btn-outline-danger btn-sm" value="일괄삭제" />
				</div>
			</div>
		</div>
	</div>
</div>

<script>
const searchTerm = document.getElementById('searchTerm');

function insertPage() {
	location.href = "/cmmn/user/insert";
}

function searchId() {
	console.debug(document.getElementById("searchBtn"), document.getElementById("searchTerm").value);
	let searchKeyword = document.getElementById("searchKeyword").value;
	let searchTerm = document.getElementById("searchTerm").value;
	const uri = "/cmmn/user/list?key=" +searchKeyword+ "&term=" +searchTerm;
	const urlSafeEncoded = encodeURI(uri);
	console.debug("URI::: ", uri);
	console.debug("urlSafeEncoded::: ", urlSafeEncoded);
	location.href = urlSafeEncoded;
}

function searchReset() {
	location.href = "/cmmn/user/list";
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
 	const $cols_rgstDt = $(".user-rgst-datetime");
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
	$(".row-hover:even").addClass("row-odd");
	$(".row-hover:odd").addClass("row-even");
	
});

</script>
</body>
</html>