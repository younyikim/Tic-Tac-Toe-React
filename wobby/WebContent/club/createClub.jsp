<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- Optional JavaScript -->
<!-- JS, Popper.js, and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<!-- for Icon -->
<script src="https://kit.fontawesome.com/cfb4151af4.js"></script>
<script>
	function clubCreate() {
		if (form.clubId.value == "") {
			alert("클럽 ID를 입력하십시오.");
			form.userId.focus();
			return false;
		}
		if (form.clubName.value == "") {
			alert("클럽 이름을 입력하십시오.");
			form.password.focus();
			return false;
		}
		if (form.btn.value == "") {
			alert("클럽 카테고리를 고르시오.");
			form.nickname.focus();
			return false;
		}
		form.submit();
	}
</script>
<title>Create Club</title>
</head>
<body class="pt-5">
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="#">Wobby</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
		</div>
	</nav>
	<div class="col-xs-6 col-sm-4 py-5 container">
		<form class="form-signin" method="POST"
			action="<c:url value='/club/createClub' />" id="form">
			<h2 class="pb-2 text-center form-signin-heading">클럽 만들기</h2>

			<div class="form-group">
				<label for="inputName">클럽 아이디</label> <input type="text"
					class="form-control" name="clubId" required autofocus
					placeholder="">
			</div>

			<div class="form-group">
				<label for="inputName">클럽 이름</label> <input type="text"
					class="form-control" name="clubName" required autofocus
					placeholder="">
			</div>
			<div class="form-group">
				<label for="inputName">클럽 최대 멤버 수</label>
				<div class="row">
					<select name="maxOfmembers">
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="30" selected="selected">30</option>
						<option value="40">40</option>
						<option value="50">50</option>
					</select>
				</div>
			</div>

			<div class="pt-2 form-group">
				<label for="selectHobby"><i class="fab fa-diaspora"></i>
					카테고리 선택 <small> <br>클럽 카테고리 2가지를 선택하세요.<br></small></label>
				<div class="btn-group btn-group-toggle pt-1 btn-block"
					data-toggle="buttons">
					<label class="btn btn-light"> <input type="checkbox"
						name="btn" value="1"><i class="fas fa-palette"></i><br>
						<small>미술</small>
					</label> <label class="btn btn-light"> <input type="checkbox"
						name="btn" value="2"><i class="fas fa-book-reader"></i><br>
						<small>독서</small>
					</label> <label class="btn btn-light"> <input type="checkbox"
						name="btn" value="3"><i class="fas fa-gamepad"></i><br>
						<small>게임</small>
					</label> <label class="btn btn-light"> <input type="checkbox"
						name="btn" value="4"><i class="fas fa-pizza-slice"></i><br>
						<small>요리</small>
					</label> <label class="btn btn-light"> <input type="checkbox"
						name="btn" value="5"><i class="fas fa-language"></i><br>
						<small>어학</small>
					</label> <label class="btn btn-light"> <input type="checkbox"
						name="btn" value="6"><i class="fas fa-chart-line"></i><br>
						<small>재테크</small>
					</label> <label class="btn btn-light"> <input type="checkbox"
						name="btn" value="7"><i class="fas fa-music"></i><br>
						<small>음악</small>
					</label> <label class="btn btn-light"><input type="checkbox"
						name="btn" value="8"><i class="fas fa-icons"></i><br>
						<small>IT</small> </label>
				</div>
			</div>

			<div class="pt-2 form-group">
				<label for="selectHobby"><i class="fab fa-diaspora"></i> 지역
					선택<small> <br>클럽이 주로 활동할 지역을 선택하세요. <br></small></label>
				<div class="row">
					<div class="col-md-4">
						<input type="text" class="form-control" name="region1"
							placeholder="시도">
					</div>
					<div class="col-md-4">
						<input type="text" class="form-control" name="region2"
							placeholder="시군구">
					</div>
					<div class="col-md-4">
						<input type="text" class="form-control" name="region3"
							placeholder="읍면동">
					</div>
				</div>
			</div>

			<div class="pt-2 pb-5">
				<button class="btn btn-lg btn-primary btn-block"
					onClick="createClub()">클럽 생성</button>
			</div>
		</form>
	</div>
</body>
</html>
