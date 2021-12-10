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
    				<h2>Users</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						<li>
							<a href="<c:url value="/user/add"/>" class="font-weight-bold" style="color: #2A3F54;">
							<i class="fa fa-plus"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
    			</div>
    			
    			<div class="x_content">
    				<p>Need to find a specific user?</p>
    				
    				<div class="container">
    					<form:form modelAttribute="searchForm" servletRelativeAction="/user/list/1" method="POST">
    						
    						<div class="row">
    							<div class="col-xs-12 col-sm-4 col-md-4">
	    							<label for="lastName">Last Name</label>
	    							<div class="form-group">
	    								<form:input path="lastName" cssClass="form-control" />
	    							</div>
    							</div>

    							<div class="col-xs-12 col-sm-4 col-md-4">
	    							<label for="firstName">First Name</label>
	    							<div class="form-group">
	    								<form:input path="firstName" cssClass="form-control" />
	    							</div>
    							</div>
    							
    							<div class="col-xs-12 col-sm-4 col-md-4">
	    							<label for="username">Username</label>
	    							<div class="form-group">
	    								<form:input path="username" cssClass="form-control" />
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
    
    <div class="row mb-4">
    	<div class="col-md-12 col-sm-12">
    		<div class="d-flex justify-content-between align-items-center">
				<div>
					<a href="<c:url value="/user/add"/>" class="font-weight-bold btn btn-dark mb-2">
						<i class="fa fa-plus"></i> Add New User
					</a>
				</div>
				<div></div>
			</div>
			
			<div class="table-responsive">
				<table class="table table-striped jambo_table bulk_action">
					<thead>
						<tr class="headings">
							<th class="column-title">#</th>
							<th class="column-title">ID</th>
							<th class="column-title">Last Name</th>
							<th class="column-title">First Name</th>
							<th class="column-title">Username</th>
							<th class="column-title">Email</th>
							<th class="column-title">Birthday</th>
							<th class="column-title">Salary</th>
							<th class="column-title no-link last text-center">
								<span class="nobr">Action</span>
							</th>
						</tr>
					</thead>
					
					<tbody>
					
						<c:forEach items="${users}" var="u" varStatus="loop">
							<c:choose>
								<c:when test="${loop.index % 2 == 0 }">
									<tr class="even pointer">
								</c:when>
								<c:otherwise>
									<tr class="odd pointer">
								</c:otherwise>
							</c:choose>
							<td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
							<td class=" ">${u.id }</td>
							<td class=" ">${u.lastName }</td>
							<td class=" ">${u.firstName }</td>
							<td class=" ">${u.username }</td>
							<td class=" ">${u.email }</td>
							<td class=" ">${u.birthday }</td>
							<td class="font-weight-bold " style="color: #26B99A;">${u.salary }</td>
							<td class="text-center">
								<a href="<c:url value="/user/view/${u.id }"/>"
									class="btn btn-round btn-secondary mr-2"> 
									<i class="fa fa-eye"></i>
								</a> 
								
								<%-- <a href="<c:url value="/user/edit/${u.id }"/>"
									class="btn btn-round btn-primary mr-2"> 
									<i class="fa fa-edit"></i>
								</a>  --%>
								
								
								<c:choose>
									<c:when test="${u.username == userInfo.username }">
										<a href="<c:url value="/user/edit/${u.id }"/>"
											class="btn btn-round btn-primary mr-2"> 
											<i class="fa fa-edit"></i>
										</a> 
									</c:when>
									<c:otherwise>
									
									</c:otherwise>
								</c:choose>
								
								<c:choose>
									<c:when test="${u.username == userInfo.username }">
									</c:when>
									<c:otherwise>
										<a href="<c:url value="/user/edit/${u.id }"/>"
											class="btn btn-round btn-primary mr-2"> 
											<i class="fa fa-edit"></i>
										</a> 
										
										<a href="javascript:void(0);" onclick="confirmDelete(${u.id});"
											class="btn btn-round btn-danger"> 
											<i class="fa fa-trash"></i>
										</a>
									</c:otherwise>
								</c:choose>
								
							</td>
						</c:forEach>
					</tbody>
				</table>
				<jsp:include page="../layout/paging.jsp"></jsp:include>
			</div>
    	</div>
    </div>
  </div>
</div>

<script type="text/javascript">

	function confirmDelete(id) {
		if (confirm('Are you sure you want delete this user?')) {
			window.location.href = '<c:url value="/user/delete/"/>' + id;
		}
	};
	
	function gotoPage(page) {
		$('#searchForm').attr('action', '<c:url value="/user/list/"/>' + page);
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