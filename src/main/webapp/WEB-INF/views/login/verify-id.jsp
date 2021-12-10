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
<title>Inventory Management System - Verification code</title>
<style type="text/css">
	label {
	color: #ff1a1a
}
</style>
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
            <form action="verifyid" method="post">
            	<h1>Verification Code</h1>
            	<div><label for="id">${message}</label></div> 
            	<div align="center">
            		
            		<input name="code" placeholder="Your Code" id="id">
            		<input type="submit" value="Verify" >
            	</div>
            	<p>
            	<div>Please check your email for the verification code!</div>
            	
            	<div class="clearfix"></div>

              	<div class="separator">

                <div class="clearfix"></div>
                
                <br />

                <div>
                  <h1><i class="fa fa-paw"></i> Apollo IMS!</h1>
                  <p>©2021 All Rights Reserved. Inventory Management System. Privacy and Terms</p>
                </div>
              </div>
            </form>
          </section>
        </div>

        
      </div>
    </div>
</body>
</html>