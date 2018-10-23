<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/jquery-ui/css/jquery-ui.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/jqgrid/css/ui.jqgrid.css" />" />
<style type="text/css">
.board_search{
	margin-bottom: 5px;
	text-align: right;
	margin-right: -7px;
}
.board_search .btn_search, #btnWrite{
	height: 20px;
	line-height: 20px;
	padding: 0 10px;
	vertical-align: middle;
	border: 1px solid #e9e9e9;
	background-color: #f7f7f7;
	font-size: 12px;
	font-family: Dotum, "돋움";
	font-weight: bold;
	text-align: center;
	cursor: pointer;
}
select {
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	margin-left : 3px;
	padding: 0;
	font-size: 12px;
	height: 20px;
	line-height: 20px;
	border: 1px solid #d7d7d7;
	color: #7f7f7f;
	/* padding: 0 5px; */
	vertical-align: middle;
}
</style>
<script type="text/javascript" src="<c:url value="/resources/jquery/js/jquery-3.2.1.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jqgrid/js/jquery.jqGrid.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jqgrid/js/i18n/grid.locale-kr.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery-ui/js/jquery-ui.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery/js/jquery-migrate-1.4.1.js" />"></script>

<script type="text/javascript">

$(document).ready(function(){
	// Tab
	$( "#tabs" ).tabs();
	
	$("#jqGrid").jqGrid({
		// 비동기 통신은 크로스 도메인이 허용되지 않는다. (허용 되는 한가지 : ajax) 
		url: '<c:url value="/board/getGridList.do" />',
		datatype: 'json',
		jsonReader: {id: "boardSeq"},	// pk 컬럼/변수 값
		prmNames: {id: 'boardSeq'},		// pk 컬럼명
		colModel: [
			{ label: '번호', name: 'boardSeq', width: 40, align:'center' },
			{ label: '제목', name: 'title', width: 200 },
			{ label: '작성자', name: 'memberNick', width: 70 },
			{ label: '작성일', name: 'createDate', width: 60 },
			{ label: '조회', name: 'hits', width: 40, align:'center' }
		],
		viewrecords: true, // show the current page, data rang and total records on the toolbar
		width: 740,
		height: 200,
		rowNum: 5,							// 가져올 게시글 수 (기본)
		rowList: [5, 10, 15],		// 가져올 게시글 수 (선택)
		caption: '회원 목록',				// 그리드 제목
		rownumbers: true,				// 그리드 목록에 대한 순번
		sortname: "boardSeq",	// 기본 정렬 컬럼
		sortorder: "desc",				// 기본 정렬
		scollrows: true,				// 스크롤 유무
		viewrecords: true,			// row 보기
		pager: "#jqGridPager",
		onCellSelect: function(rowId, colIdx, content, event){
			/*
			특정 셀에대한 이벤트를 설정해줄때 이런식으로 사용한다.
			if(colIdx == 3){
				alert(content);
			} else {
				alert('읽기');
			} */
		},
		onSelectRow: function(rowId, tf, event){
			// rowId: key 값이다 -> row에 대한 데이터 전체를 가지고 올 수 있다.
			// tf or status: click 이 되었는지 안되었는지 따지는 것.
			// event: 여러가지 event
			console.log('onSelectRow rowId:' + rowId);
			console.log('onSelectRow tf: ' + tf);
			console.log('onSelectRow event: ' + event);
			
			var url = "<c:url value='/board/read.do?boardSeq="+rowId+"&useGrid=1'/>";
			
			window.location.href = url;
		}
	});
	
	// navButtons
	$("#jqGrid").jqGrid('navGrid', "#jqGridPager",
			{	// navbar options
				edit: false,
				add: false,
				del: false,
				search: false,
				refresh: true,
				view: true
			},
			{}, // edit
			{}, // add
			{} // del
	);


	$('#btnSearch').click(function(){
		// 아이디/이메일
		var searchType = $('#searchType option:selected').val();
		// 검색어
		var searchText = $('#searchText').val();
		$('#jqGrid').jqGrid('setGridParam', 
				{
					postData : {
						searchType : searchType,
						searchText : searchText
					},
					page : 1
				}
		).trigger('reloadGrid');
	});
	
	$('#btnWrite').click(function(){
		window.location.href = "<c:url value='/board/goWritePage.do' />";
	});
});
</script>
<title></title>
</head>
<body>

<div id="tabs">
	<ul>
		<li><a href="#tabs-1">자유게시판(그리드)</a></li>
	</ul>
	<div id="tabs-1">
		<!-- content -->
		<div id="content">

			<!-- board_search -->
			<div class="board_search">
				<form name="searchForm" method="get">
					<select id="searchType" name="searchType" title="선택메뉴">
						<option value="1" selected="selected">전체</option>
						<option value="2">제목</option>
						<option value="3">내용</option>
					</select> 
					<input type="text" id="searchText" name="searchText" title="검색어 입력박스" class="input_100" /> 
					<input type="button" id="btnSearch" value="검색" title="검색버튼" class="btn_search" />
				</form>
			</div>
			
			<!-- //board_search -->

			<!-- board_area -->
			<div class="board_area board_search">
				<table id="jqGrid"></table>
  			<div id="jqGridPager"></div>
  			<br/>
  			<c:if test="${sessionScope.memberId != null}">
					<button id="btnWrite" style="margin-bottom:3px;">작성</button>
				</c:if>
			</div>
			<!-- //board_area -->

		</div>
		<!-- //content -->
	</div>
</div>

</body>
</html>