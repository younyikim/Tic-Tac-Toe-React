<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>WOBBY</title>
</head>
<body class="pt-5">
	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<c:url value='/home' />">WOBBY</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="nav-link"
						href="wobbyHome.jsp">Home<span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link"
						href="<c:url value='/user/login/form'/>">Login</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
      <div class="card-body">
         <div class="input-group">
         <form action="<c:url value='/search'/>">
            <input type="text" name="keyword" class="form-control" placeholder="검색어를 입력하세요.">
            <span class="input-group-append">
               <button class="btn btn-secondary">Search</button>
            </span>
         </form>
         </div>
      </div>
   </div>


	<!-- Page Content -->
	<div class="container">

		<!-- Jumbotron Header -->
		<header class="jumbotron my-4">
			<h6>여성들을 위한 취미 공유 플랫폼</h6>
			<h1 class="display-3">WOBBY</h1>
			<a href="<c:url value='/user/login/form'/>" class="btn btn-primary btn-lg">로그인</a> 	
			<a href="<c:url value='/user/signUp/form'/>" class="btn btn-primary btn-lg">가입하기</a>
		</header>
	</div>

		<div class="container">
		<h5 class="card-header">게시판</h5>
		<div class="card-body">
			<div class="input-group">
				<span class="input-group-append">
					<a class="btn btn-outline-dark pull-right"  href="<c:url value='/community/board'>
								<c:param name="boardId" value="1"/>
							 </c:url>">
					그림 </a> &nbsp;
					&nbsp;
				</span>
			    <span class="input-group-append">
					<a class="btn btn-outline-dark pull-right"  href="<c:url value='/community/board'>
								<c:param name="boardId" value="2"/>
							 </c:url>">
					독서 </a> &nbsp;
					&nbsp;
				</span> 
				<span class="input-group-append">
					<a class="btn btn-outline-dark pull-right"  href="<c:url value='/community/board'>
								<c:param name="boardId" value="3"/>
							 </c:url>">
					요리 </a> &nbsp;
					&nbsp;
				</span> 
				<span class="input-group-append">
					<a class="btn btn-outline-dark pull-right"  href="<c:url value='/community/board'>
								<c:param name="boardId" value="4"/>
							 </c:url>">
					언어 </a> &nbsp;
					&nbsp;
				</span> 
				 <span class="input-group-append">
					<a class="btn btn-outline-dark pull-right"  href="<c:url value='/community/board'>
								<c:param name="boardId" value="5"/>
							 </c:url>">
					경제 </a> &nbsp;
					&nbsp;
				</span> 
				 <span class="input-group-append">
					<a class="btn btn-outline-dark pull-right"  href="<c:url value='/community/board'>
								<c:param name="boardId" value="6"/>
							 </c:url>">
					음악 </a> &nbsp;
					&nbsp;
				</span> 
				<span class="input-group-append">
					<a class="btn btn-outline-dark pull-right"  href="<c:url value='/community/board'>
								<c:param name="boardId" value="0"/>
							 </c:url>">
					더보기 </a> &nbsp;
					&nbsp;
				</span>
			</div>
		</div>
	</div>


	<!-- Page Features -->
	<div class="container">
		<div class="row text-center">

			<div class="col-lg-3 col-md-6 mb-4">
				<div class="card h-100">
					<div class="card-body">
						<h4 class="card-title">
							<b>당신의 지역을 찾아보세요.</b>
						</h4>
						<!--  <img class="card-img-top" src="img/지도.JPG"
							alt="지도.JPG">-->
					</div>
					<div class="card-footer">
						<input type="text" class="form-control" placeholder="시/도">
						<input type="text" class="form-control" placeholder="시/군/구">
						<input type="text" class="form-control" placeholder="읍/면/동">
						<a href="#" class="btn btn-primary">검색</a>
					</div>
				</div>
			</div>

			<div class="col-lg-3 col-md-6 mb-4">
				<div class="card h-100">
					<div class="card-body">
						<h4 class="card-title">
							<b>실시간 인기클럽</b>
						</h4>
						<p class="card-text">실시간 인기 클럽을 알아보세요.</p>
						<!--  <img class="card-img-top" src="img/클럽.JPG"
							alt="챠�쨈챘�쩍.JPG"> -->
					</div>
					<div class="card-footer">
						<a href="club/ClubHome.jsp" class="btn btn-primary">클럽 더보기</a>
					</div>
				</div>
			</div>


		</div>
		<!-- /.row -->

	</div>
	<!-- /.container -->

	<!-- Footer -->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; WOBBY 2020</p>
		</div>
		<!-- /.container -->
	</footer>

	<!-- Bootstrap core JavaScript -->

</body>
</html>
