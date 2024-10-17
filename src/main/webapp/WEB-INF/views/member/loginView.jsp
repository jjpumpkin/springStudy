<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
   .error{
   color:red; font-size:0.9em;
   }

</style>
<title>로그인</title>
</head>
<body>
   <jsp:include page="/WEB-INF/inc/top.jsp"></jsp:include>
   <script>

      $(document).ready(function(){
      });
   
   </script>
    <section class="page-section" style="margin-top:100px" id="contact">
        <div class="container">
            <!-- Contact Section Heading-->
            <h2 class="page-section-heading text-center text-uppercase text-secondary mb-0">로그인</h2>
            <!-- Icon Divider-->
            <div class="divider-custom">
                <div class="divider-custom-line"></div>
                <div class="divider-custom-icon"><i class="fas fa-star"></i></div>
                <div class="divider-custom-line"></div>
            </div>
            <!-- Contact Section Form-->
            <div class="row justify-content-center">
                <div class="col-lg-8 col-xl-7">
                    <form:form modelAttribute="member" action="/loginDo" method="post">
                        <!-- Id input-->
                        <div class="form-floating mb-3">
                            <form:input class="form-control" path="memId" id="id" name="memId" value="${cookie.rememberId.value}" type="text" placeholder="아이디를 입력하세요..."/>
                            <form:errors path="memId" cssClass="error"/>
                            <label for="id">ID</label>
                        </div>
                        <!-- Pw  input-->
                        <div class="form-floating mb-3">
                            <form:input class="form-control" path="memPw" id="password" name="memPw" type="password" placeholder="비밀번호를 입력하세요..."  />
                            <form:errors path="memPw" cssClass="error"/>
                            <label for="password">Password</label>
                        </div>
                       <div class="form-floating mb-3">
                          <input ${ cookie.rememberId.value ==null ? "" : "checked" } type="checkbox" class="form-check-input" name="remember"> 아이디 기억하기
                       </div>
                        <button class="btn btn-primary btn-xl" id="submitButton" type="submit">로그인</button>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
   <jsp:include page="/WEB-INF/inc/footer.jsp"></jsp:include>
</body>
</html>