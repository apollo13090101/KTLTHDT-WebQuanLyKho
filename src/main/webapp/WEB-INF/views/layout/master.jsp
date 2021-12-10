<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<body class="nav-md footer_fixed">
    <div class="container body">
      <div class="main_container">
        <div class="col-md-3 left_col">
          <div class="left_col scroll-view">
            <div class="navbar nav_title" style="border: 0;">
              <a href="/INT14103/index" class="site_title"><i class="fa fa-paw"></i> <span>APOLLO IMS!</span></a>
            </div>

            <div class="clearfix"></div>

            <!-- menu profile quick info starts -->
            <div class="profile clearfix">
              <div class="profile_pic">
              	<img src="<c:url value="/static/images/img.jpg"/>" alt="..." class="img-circle profile_img">
               <%--  <c:choose>
					<c:when test="${userInfo.imageUrl eq 'Unavailable' }">
						<img src="<c:url value="/static/images/img.jpg"/>" alt="..." class="img-circle profile_img">
					</c:when>
					<c:otherwise>
						<img src="<c:url value="${userInfo.imageUrl }"/>" alt="..." class="img-circle profile_img" width="50" height="50">
					</c:otherwise>
				</c:choose> --%>
              </div>
              <div class="profile_info">
                <span>Welcome,</span>
                <h2>${userInfo.lastName } ${userInfo.firstName }</h2>
              </div>
            </div>
            <!-- menu profile quick info ends -->

            <br />

            <!-- sidebar menu starts -->
            <tiles:insertAttribute name="sidebar"></tiles:insertAttribute>
            <!-- sidebar menu ends -->

            <!-- menu footer buttons starts -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout" href="<c:url value="/logout"/>">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
            <!-- menu footer buttons ends -->
          </div>
        </div>

        <!-- top navigation starts -->
        <tiles:insertAttribute name="topnav"></tiles:insertAttribute>
        <!-- top navigation ends -->

        <!-- page content starts -->
        <tiles:insertAttribute name="body"></tiles:insertAttribute>
        <!-- page content ends -->

        <!-- footer content starts -->
        <tiles:insertAttribute name="footer"></tiles:insertAttribute>
        <!-- footer content ends -->
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
</body>
</html>