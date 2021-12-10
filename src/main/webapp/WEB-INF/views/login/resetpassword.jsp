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
<title>Inventory Management System - Reset Password</title>
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
</head>
<body class="login">
	<div>

      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form:form modelAttribute="resetpasswordForm" servletRelativeAction="/resetpassword" method="POST">
              <h1>Reset Password</h1>
              <div>
              	<form:errors path="username" cssClass="help-block" cssStyle="color: #dc3545;" />
                <form:input path="username" cssClass="form-control" placeholder="Username" />
              </div>
              <div>
                <form:errors path="email" cssClass="help-block" cssStyle="color: #dc3545;" />
                <form:input path="email" cssClass="form-control" placeholder="email" />
              </div>
              <div>
                <button class="btn btn-round btn-primary" type="submit">Take Verify Code</button>
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
</body>
</html>