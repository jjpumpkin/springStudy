<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--  브라우저가 캐시를 전혀 사용하지 않도록 -->
<meta http-equiv="Cache-Control" content="no-store, no-cache , must-revalidate">
<!-- 과거 http/1.0 호환을 위해 -->
<meta http-equiv="Pragma" content="no-cache">
<!-- 페이지가 만료되었음을 다시 요청하도록 -->
<meta http-equiv="Expires" content="0">
<style>
    .error{
      color:red;, font-size:0.9em;
    }
</style>
<title>회원가입</title>
</head>
<body>
	<jsp:include page="/WEB-INF/inc/top.jsp"></jsp:include>
	<!--회원가입 -->
	<!-- Contact Section-->
    <section class="page-section" style="margin-top:100px" id="contact">
        <div class="container">
            <!-- Contact Section Heading-->
            <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">회원가입</h2>
            <!-- Icon Divider-->
            <div class="divider-custom">
                <div class="divider-custom-line"></div>
                <div class="divider-custom-icon"><i class="fas fa-star"></i></div>
                <div class="divider-custom-line"></div>
            </div>
            <!-- Contact Section Form-->
            <div class="row justify-content-center">
                <div class="col-lg-8 col-xl-7">
                    <form:form modelAttribute="member" action="/registDo" method="post">
                       
                        <div class="form-floating mb-3">
                            <form:input path="memId" name="memId" class="form-control" id="id" type="text" placeholder="아이디를 입력하세요..."/>
                            <form:errors path="memId" cssClass="error"/>
                            <label for="id">ID</label>
                        </div>
                       
                        <div class="form-floating mb-3">
                            <form:input path="memPw" name="memPw" class="form-control" id="password"  type="password" placeholder="비밀번호를 입력하세요..."  />
                            <form:errors path="memPw" cssClass="error"/>
                            <label for="password">Password</label>
                        </div>
           
                     
                        <div class="form-floating mb-3">
                            <form:input path="memNm" name="memNm" class="form-control" id="name"  type="text" placeholder="이름을 입력하세요..."  />
                            <form:errors path="memNm" cssClass="error"/>
                            <label for="id">Name</label>
                        </div>
                        <button class="btn btn-primary btn-xl" id="submitButton" type="submit">가입하기</button>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
	
	<!-- Modal -->
		<div class="modal fade" id="messageModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h1 class="modal-title fs-5" id="exampleModalLabel">${messageVO.title} </h1>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
				      <div class="modal-body">
		                     <!-- title input-->
		                     <div class="mb-3">
		                         <label for="title">${messageVO.message}</label>
		                     </div> 
		                     <a href="<c:url value='${messageVO.url}'/>">
		                      ${messageVO.urlTitle}
		                     </a>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				    </div>
		    </div>
		  </div>
		</div>
		<!--회원가입 -->
	<jsp:include page="/WEB-INF/inc/footer.jsp"></jsp:include>
	<script>
	  $(document).ready(function(){
		 var message ="${messageVO.message}";
		 if(message !=''){
			 $("#messageModal").modal('show');
		 }  
	  });
	
	</script>
	
</body>
</html>