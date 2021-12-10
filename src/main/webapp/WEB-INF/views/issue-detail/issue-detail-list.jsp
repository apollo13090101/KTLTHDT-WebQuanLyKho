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
    				<h2>Issues' Details</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
						<li>
							<a href="<c:url value="/issue-detail/add"/>" class="font-weight-bold" style="color: #2A3F54;">
							<i class="fa fa-plus"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
    			</div>
    			
    			<div class="x_content">
    				<p>Need to find an issue detail?</p>
    				
    				<div class="container">
    					<form:form modelAttribute="searchForm" servletRelativeAction="/issue-detail/list/1" method="POST">
    						
    						<div class="row">
    							<div class="col-xs-12 col-sm-4 col-md-4">
	    							<label for="id">ID</label>
	    							<div class="form-group">
	    								<form:input path="id" cssClass="form-control" />
	    							</div>
    							</div>
    							<div class="col-xs-12 col-sm-4 col-md-4">
	    							<label for="issueCode">Issue Code</label>
	    							<div class="form-group">
	    								<form:input path="issue.code" cssClass="form-control" />
	    							</div>
    							</div>
    							<div class="col-xs-12 col-sm-4 col-md-4">
	    							<label for="productCode">Product Code</label>
	    							<div class="form-group">
	    								<form:input path="product.code" cssClass="form-control" />
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
					<a href="<c:url value="/issue-detail/add"/>" class="font-weight-bold btn btn-dark mb-2">
						<i class="fa fa-plus"></i> Add New Issue Detail
					</a>
				</div>
				<div>
					<jsp:include page="../layout/paging.jsp"></jsp:include>
				</div>
			</div>
			
			<div class="table-responsive">
				<table class="table table-striped jambo_table bulk_action">
					<thead>
						<tr class="headings">
							<th class="column-title">#</th>
							<th class="column-title">ID</th>
							<th class="column-title">Issue Code</th>
							<th class="column-title">Product</th>
							<th class="column-title">Quantity</th>
							<th class="column-title">Price</th>
							<th class="column-title no-link last text-center">
								<span class="nobr">Action</span>
							</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${issueDetails}" var="i" varStatus="loop">
							<c:choose>
								<c:when test="${loop.index % 2 == 0 }">
									<tr class="even pointer">
								</c:when>
								<c:otherwise>
									<tr class="odd pointer">
								</c:otherwise>
							</c:choose>
							<td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
							<td class=" ">${i.id }</td>
							<td class=" ">${i.issue.code }</td>
							<td class=" ">${i.product.name }</td>
							<td class=" ">${i.quantity }</td>
							<td class="font-weight-bold " style="color: #26B99A;">${i.price }</td>
							<td class="text-center">
								<a href="<c:url value="/issue-detail/view/${i.id }"/>"
									class="btn btn-round btn-secondary mr-2"> 
									<i class="fa fa-eye"></i>
								</a> 
								
								<a href="<c:url value="/issue-detail/edit/${i.id }"/>"
									class="btn btn-round btn-primary mr-2"> 
									<i class="fa fa-edit"></i>
								</a> 
								
								<a href="javascript:void(0);" onclick="confirmDelete(${i.id});"
									class="btn btn-round btn-danger"> 
									<i class="fa fa-trash"></i>
								</a>
							</td>
						</c:forEach>
					</tbody>
				</table>
			</div>
    	</div>
    </div>
  </div>
</div>

<script type="text/javascript">

	function confirmDelete(id) {
		if (confirm('Are you sure you want delete this issue detail?')) {
			window.location.href = '<c:url value="/issue-detail/delete/"/>' + id;
		}
	};
	
	function gotoPage(page) {
		$('#searchForm').attr('action', '<c:url value="/issue-detail/list/"/>' + page);
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