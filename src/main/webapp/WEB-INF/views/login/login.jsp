<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Java Web App">
<meta name="author" content="An Vo">
<title>Inventory Management</title>
	<!-- Favicon -->
	<link rel="shortcut icon" href="<c:url value="/static/images/favicon.ico" />" type="image/x-icon">
	<!-- Bootstrap -->
    <link href="<c:url value="/static/vendors/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="<c:url value="/static/vendors/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- NProgress -->
    <link href="<c:url value="/static/vendors/nprogress/nprogress.css"/>" rel="stylesheet">
    <!-- jQuery custom content scroller -->
    <link href="<c:url value="/static/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet"/>

    <!-- Custom Theme Style -->
    <link href="<c:url value="/static/build/css/custom.min.css"/>" rel="stylesheet">
    <!-- PNotify -->
	<link href="<c:url value="/static/vendors/pnotify/dist/pnotify.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/vendors/pnotify/dist/pnotify.buttons.css"/>" rel="stylesheet">
	<link href="<c:url value="/static/vendors/pnotify/dist/pnotify.nonblock.css"/>" rel="stylesheet">
	<!-- jQuery -->
	<script src="<c:url value="/static/vendors/jquery/dist/jquery.min.js"/>"></script>
</head>
<body class="login">
	<div>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form:form modelAttribute="loginForm" servletRelativeAction="/processLogin" method="POST">
              <h1>Login Here</h1>
              <div>
              	<form:errors path="username" cssClass="help-block" cssStyle="color: #dc3545;" />
                <form:input path="username" cssClass="form-control" placeholder="Username" />
              </div>
              <div>
                <form:errors path="password" cssClass="help-block" cssStyle="color: #dc3545;" />
                <form:password path="password" cssClass="form-control" placeholder="Password" />
              </div>
              <div>
                <button class="btn btn-default submit" type="submit">Log in</button>
                <a class="reset_pass" href="/INT14103/resetpassword">Lost your password?</a>
              </div>

              <div class="clearfix"></div>

              <div class="separator">

                <div class="clearfix"></div>
                <br />

                <div>
                  <h1><i class="fa fa-paw"></i> Apollo IMS!</h1>
                  <p>©2021 All Rights Reserved. Inventory Management System. Privacy and Terms</p>
                </div>
              </div>
            </form:form>
          </section>
        </div>

        
      </div>
    </div>
    
    <!-- Bootstrap -->
   <script src="<c:url value="/static/vendors/bootstrap/dist/js/bootstrap.bundle.min.js"/>"></script>
    <!-- FastClick -->
    <script src="<c:url value="/static/vendors/fastclick/lib/fastclick.js"/>"></script>
    <!-- NProgress -->
    <script src="<c:url value="/static/vendors/nprogress/nprogress.js"/>"></script>
    <!-- jQuery custom content scroller -->
    <script src="<c:url value="/static/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"/>"></script>

	<!-- PNotify -->
	<script src="<c:url value="/static/vendors/pnotify/dist/pnotify.js"/>"></script>
	<script src="<c:url value="/static/vendors/pnotify/dist/pnotify.buttons.js"/>"></script>
	<script src="<c:url value="/static/vendors/pnotify/dist/pnotify.nonblock.js"/>"></script>
	
    <!-- Custom Theme Scripts -->
    <script src="<c:url value="/static/build/js/custom.js"/>"></script>
    
	<script type="text/javascript">
	
		function processMessage() {
			var msgSuccess = '${msgSuccess}';
			var msgError = '${msgError}';
			if (msgSuccess) {
				new PNotify({
					title : 'Success',
					text : msgSuccess,
					type : 'success',
					styling : 'bootstrap3'
				});
			}
			if (msgError) {
				new PNotify({
					title : 'Error',
					text : msgError,
					type : 'error',
					styling : 'bootstrap3'
				});
			}
		};
	
		$(document).ready(function(){
				processMessage();	
		});
	
	</script>
</body>
</html>