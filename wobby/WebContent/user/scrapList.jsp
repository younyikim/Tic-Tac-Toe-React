<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
   src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
   src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<!-- for Icon -->
<script src="https://kit.fontawesome.com/cfb4151af4.js"></script>
<title>스크랩 목록</title>
</head>
<body class="pt-5">
   <!-- Navigation -->
     <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
       <div class="container">
         <a class="navbar-brand" href="#">Wobby</a>
         <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
           <span class="navbar-toggler-icon"></span>
         </button>
         <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
          <li class="nav-item active">
            <a class="nav-link" href="<c:url value='/user/myPage'/>">My Page</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/user/clubHome'/>">Clubs</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/matching/matching.jsp'/>">Matching</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<c:url value='/search'/>">Search</a>
          </li>
        </ul>
         </div>
       </div>
     </nav>
    <!-- Main jumbotron for a primary marketing message or call to action -->
<div class="jumbotron">
      <div class="container">
        <i class="far fa-star"></i> <h2><b>${userId}</b>님, 
        <span class="text-primary"><b>${scrapNum}</b></span>개의 스크랩을 확인해보세요.<br>
         </h2></div>
    </div>

   
    <div class="container pb-2">
    <form action="<c:url value='/user/myPage/scrap/delete' />" >
      <table class="table table-hover">
              <thead>
                <tr>
                  <th>#</th>
                  <th></th>
                  <th></th>
                  <th></th>
                  <th></th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                 <c:set var="pageIdx" value="${(page - 1) * 15}" />
                 <c:forEach var="scrapPost" items="${scrapList}" varStatus="status">
                    <tr>
                     <td>${status.count + pageIdx}</td>
                     <td><a class="text-muted" href="<c:url value='/board/post'>
                              <c:param name='postId' value='${scrapPost.postId}'/>
                           </c:url>" class="btn btn-primary">${scrapPost.title}</a></td>
                     <td><a class="text-muted" href="<c:url value='/board'>
                              <c:param name='boardId' value='${scrapPost.boardId}'/>
                           </c:url>" class="btn btn-primary">${scrapPost.boardName}</a></td>
                     <td><a class="text-muted">${scrapPost.postDate}</a></td>
                     <td><a class="text-muted">${scrapPost.nickname}</a></td>
                     <td><input type="checkbox" name="delete" value="${scrapPost.scrapNo}"></td>
                   </tr>
                 </c:forEach>
                <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td><button class="btn btn-light" type="submit">삭제</button></td>
                </tr>
              </tbody>
            </table>
       </form>
    </div> <!-- /container -->
    
    
    <div class="container pb-5">
       <ul class="pagination justify-content-center">
              <c:forEach var="i" begin="1" end="${lastPage}">
            <li class="page-item">
               <c:if test="${i eq page}"><a class="page-link" href="#"><b>${i}</b></a></c:if>
               <c:if test="${i ne page}"><a class="page-link" 
                  href="<c:url value='/user/scrapList'>
                           <c:param name='page' value='${i}'/>
                        </c:url>">${i}</a></c:if>
            </li>
         </c:forEach>
      </ul>
   </div>
</body>
</html>