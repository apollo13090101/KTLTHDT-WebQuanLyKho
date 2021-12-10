<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="sidebar-menu" class="main_menu_side hidden-print main_menu" style="z-index: 100001">
	<div class="menu_section">
		<h3>General</h3>
		<ul class="nav side-menu">
			<c:forEach items="${menuSession }" var="menu">
				<li id="${menu.menuId }">
					<a>
						<c:choose>
						    <c:when test="${menu.menuId eq 'productId'}">
						       <i class="fa fa-cubes"></i> 
						    </c:when>
						    <c:when test="${menu.menuId eq 'stockId'}">
						        <i class="fa fa-bank"></i> 
						    </c:when>
						    <c:when test="${menu.menuId eq 'reportId'}">
						        <i class="fa fa-bar-chart"></i> 
						    </c:when>
						    <c:otherwise>
						        <i class="fa fa-gear"></i> 
						    </c:otherwise>
						</c:choose>
						${menu.name } 
						<span class="fa fa-chevron-down"></span>
					</a>
					<ul class="nav child_menu">
						<c:forEach items="${menu.child }" var="child">
							<li id="${child.menuId }">
								<a href="<c:url value="${child.url}"/>">
									${child.name }
								</a>
							</li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>