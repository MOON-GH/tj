<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- tag library 선언 : c tag --%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title>로그인 & 회원가입</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/login.css" />" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var panelOne = $('.form-panel.two').height(), 
			panelTwo = $('.form-panel.two')[0].scrollHeight;
	
		$('.form-panel.two').not('.form-panel.two.active').on('click', function(e) {
			e.preventDefault();
	
		    $('.form-toggle').addClass('visible');
		    $('.form-panel.one').addClass('hidden');
		    $('.form-panel.two').addClass('active');
		    $('.form').animate({
		      'height': panelTwo
		    }, 200);
		});
	
		$('.form-toggle').on('click', function(e) {
		    e.preventDefault();
		    $(this).removeClass('visible');
		    $('.form-panel.one').removeClass('hidden');
		    $('.form-panel.two').removeClass('active');
		    $('.form').animate({
		      'height': panelOne
		    }, 200);
		});
		
		$('#btnRegister').on('click', function() {
			// 회원가입시 빈값 및 중복 아이디 검사
			var joinForm = $("#joinForm");
			var memberId = $("#JmemberId");
			var memberPw = $("#JmemberPw");
			var memberPw2 = $("#JmemberPw2");
			var memberName = $("#JmemberName");
			var memberNick = $("#JmemberNick");
			var email = $("#Jemail");
			var birthDate = $("#JbirthDate");
			
			var regExp = /^([_0-9a-zA-Z]){6,12}$/i;
			if(memberId.val() == null || memberId.val() == ""){
				alert("아이디를 입력하세요.");
				return memberId.focus();
			}
			if(memberId.match(regExp) == null){
				alert("아이디는 6~12글자로 입력해주세요. \n(특수문자는  _ 가능) ");
				return memberId.focus();
			}
			if(memberPw.val() == null || memberPw.val() == ""){
				alert("비밀번호를 입력하세요.");
				return memberPw.focus();
			}
			if(memberPw.val() != memberPw2.val()){
				alert("비밀번호가 일치하지 않습니다.");
				return memberPw2.focus();
			}
			if(memberName.val() == null || memberName.val() == ""){
				alert("이름을 입력하세요.");
				return memberName.focus();
			}
			if(memberNick.val() == null || memberNick.val() == ""){
				alert("닉네임을 입력하세요.");
				return memberNick.focus();
			}
			if(birthDate.val() == null || birthDate.val() == ""){
				alert("생일을 입력하세요.");
				return birthDate.focus();
			}
			if(email.val() == null || email.val() == "") {
				alert("이메일을 입력하세요.");
				return email.focus();
			}
			
			regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-z-A-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{3,4}$/i;
			
			if(email.val().match(regExp) == null) {
				alert("Invaild Fromat. You Should follow this format. \nex: sample ");
				$('#email').focus();
				return;
			}
			
			$.ajax({
				// 회원가입 요청 전 비동기 통신으로 ID 중복 검사
				url : '<c:url value="/member/checkId.do"/>',
				// url에 보낼 데이터(파라미터)
				data : {memberId: memberId.val()},
				// '통신'이 성공하면 success
				success : function(result, textStatus, XMLHttpRequest){
					if(result == 0){
						// 회원가입 요청
						joinForm.attr("action", "<c:url value='/member/join.do'/>");
						joinForm.attr("method", "post");
						joinForm.submit();
					} else {
						alert("아이디가 중복됩니다.");
						memberId.focus();
					}
				},
				error : function(e){
					alert(e);
				}
			});
		});
	});
	
</script>
</head>
<body>
	<!-- 디자인 출처 : http://www.blueb.co.kr/?c=2/34&where=subject%7Ctag&keyword=%EB%A1%9C%EA%B7%B8%EC%9D%B8&uid=4050 -->
	<!-- Form-->
	<div class="form">
		<div class="form-toggle"></div>
		<div class="form-panel one">
			<div class="form-header">
				<h1>Account Login</h1>
				<c:if test="${msg != null || msg != ''}">
					<b><font color="red">${msg}</font></b>
				</c:if>
			</div>
			<!-- 로그인 -->
			<div class="form-content">
				<form action="<c:url value='/member/login.do'/>" method="post">
					<div class="form-group">
						<label for="username">User Id</label> 
						<input type="text" id="LuserId" name="memberId" required="required" />
					</div>
					<div class="form-group">
						<label for="password">Password</label> 
						<input type="password" id="LuserPw" name="memberPw" required="required" />
					</div>
					<div class="form-group">
						<label class="form-remember"> 
							<input type="checkbox" />Remember Me
						</label>
						<a href="#" class="form-recovery">Forgot Password?</a>
					</div>
					<div class="form-group">
						<button type="submit">Log In</button>
					</div>
				</form>
			</div>
		</div>
		
		<!-- 회원가입 -->
		<div class="form-panel two">
			<div class="form-header">
				<h1>Register Account</h1>
			</div>
			<div class="form-content">
				<form id="joinForm" name="joinForm">
					<input type="hidden" name="cmd" value="join" />
					<div class="form-group">
						<label for="username">User Id</label> 
						<input type="text" id="JmemberId" name="memberId" required="required" />
					</div>
					<div class="form-group">
						<label for="password">Password</label> 
						<input type="password" id="JmemberPw" name="memberPw" required="required" />
					</div>
					<div class="form-group">
						<label for="cpassword">Confirm Password</label> 
						<input type="password" id="JmemberPw2" required="required" />
					</div>
					<div class="form-group">
						<label for="name">Name</label> 
						<input type="text" id="JmemberName" name="memberName" required="required" />
					</div>
					<div class="form-group">
						<label for="nickname">Nickname</label> 
						<input type="nick" id="JmemberNick" name="memberNick" required="required" />
					</div>
					<div class="form-group">
						<label for="email">e-mail</label> 
						<input type="email" id="Jemail" name="email" required="required" />
					</div>
					<div class="form-group">
						<label for="birthDate">Birth Date(yyyy-mm-dd)</label> 
						<input type="birthDate" id="JbirthDate" name="birthDate" required="required" />
					</div>
					<div class="form-group">
						<button type="button" id="btnRegister">Register</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>