<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="right_col" role="main">
  <div class="">
    <div class="clearfix"></div>
    
    <div class="row">
    	<div class="col-md-12 col-sm-12">
    		<div class="x_panel">
    			<div class="x_title">
    				<h2>Menus</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						<li>
							<a href="<c:url value="/menu/permission"/>" class="font-weight-bold" style="color: #2A3F54;">
							<i class="fa fa-refresh"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
    			</div>
    			
    			<div class="x_content">
    				<p>Need to find a specific menu url?</p>
    				
    				<div class="container">
    					<form:form modelAttribute="searchForm" servletRelativeAction="/menu/list/1" method="POST">
    						
    						<div class="row">
    							<div class="col-xs-12 col-sm-6 col-md-6 offset-md-3 ">
	    							<label for="url">Search for Url</label>
	    							<div class="form-group">
	    								<form:input path="url" cssClass="form-control" />
	    							</div>
    							</div>
    							
    						</div>
    						
    						<div class="row">
								<div class="col-xs-12 col-sm-6 col-md-6 offset-md-3 text-center">
									<button type="submit" class="btn btn-success">Search</button>
									<button class="btn btn-primary" type="reset">Reset</button>
								</div>
    						</div>
    					</form:form>
    				</div>
    			</div>
    		</div>
    	
    	</div>
    </div>
    
    <div class="row">
    	<div class="col-md-12 col-sm-12">
    		<div class="d-flex justify-content-between align-items-center">
				<div>
					<a href="<c:url value="/menu/permission"/>" class="font-weight-bold btn btn-dark mb-2">
						<i class="fa fa-refresh"></i> Change permission
					</a>
				</div>
				<div>
					<jsp:include page="../layout/paging.jsp"></jsp:include>
				</div>
			</div>
			
			<div class="table-responsive">
				<table class="table table-striped jambo_table bulk_action mb-5">
					<thead>
						<tr class="headings">
							<th rowspan="2" class="column-title text-center" style="border-left: 2px solid">#</th>
							<th rowspan="2" class="column-title " style="border-left: 2px solid">Url</th>
							<th rowspan="2" class="column-title text-center" style="border-left: 2px solid">Status</th>
							<th colspan="${roles.size() }" class="column-title text-center" style="border-left: 2px solid">Role</th>
						</tr>
						<tr>
							<c:forEach items="${roles }" var="role">
								<th class="column-title text-center" style="border-left: 2px solid">${role.roleName }</th>
							</c:forEach>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${menuList}" var="menu" varStatus="loop">
							<c:choose>
								<c:when test="${loop.index % 2 == 0 }">
									<tr class="even pointer">
								</c:when>
								<c:otherwise>
									<tr class="odd pointer">
								</c:otherwise>
							</c:choose>
							<td class="text-center ">${pageInfo.getOffset()+loop.index+1}</td>
							<td class=" ">${menu.url }</td>
							
							<c:choose>
								<c:when test="${menu.active == 1 }">
									<td class="text-center ">
										<a href="javascript:void(0);" onclick="confirmChange(${menu.id}, ${menu.active});" class="btn btn-round btn-danger">
											Disable
										</a>
									</td>
								</c:when>
								<c:otherwise>
									<td class="text-center ">
										<a href="javascript:void(0);" onclick="confirmChange(${menu.id}, ${menu.active});" class="btn btn-round btn-primary">
											Enable
										</a>
									</td>
								</c:otherwise>
							</c:choose>
							
							<c:forEach items="${menu.mapAuth }" var="auth">
								<c:choose>
									<c:when test="${auth.value == 1}">
										<td class="text-center">
											<i class="fa fa-check" style="color: green"></i>
										</td>
									</c:when>
									<c:otherwise>
										<td class="text-center">
											<i class="fa fa-times" style="color:red;"></i>
										</td>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							
						</c:forEach>
					</tbody>
				</table>
				
			</div>
    	</div>
    </div>
  </div>
</div>

<script type="text/javascript">

	function confirmChange(id, flag) {
		var msg = flag == 1 ? 'Disable this menu?' : 'Enable this menu?';
		if (confirm(msg)) {
			window.location.href = '<c:url value="/menu/change-status/"/>' + id;
		}
	};
	
	function gotoPage(page) {
		$('#searchForm').attr('action', '<c:url value="/menu/list/"/>' + page);
		$('#searchForm').submit();
	};

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