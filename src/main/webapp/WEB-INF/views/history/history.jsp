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
    				<h2>History</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
    			</div>
    			
    			<div class="x_content">
    				<p>Need to find track your history?</p>
    				
    				<div class="container">
    					<form:form modelAttribute="searchForm" servletRelativeAction="/history/list/1" method="POST">
    						
    						<div class="row">
    						
    							<div class="col-xs-12 col-sm-6  col-md-6">
	    							<label for="${product.category.name }">Category</label>
	    							<div class="form-group">
	    								<form:input path="product.category.name" cssClass="form-control" />
	    							</div>
    							</div>
    							
    							<div class="col-xs-12 col-sm-6 col-md-6">
	    							<label for="${product.code }">Product Code</label>
	    							<div class="form-group">
	    								<form:input path="product.code" cssClass="form-control" />
	    							</div>
    							</div>
    							
    						</div>
    						
    						<div class="row">
    							<div class="col-xs-12 col-sm-6 col-md-6">
	    							<label for="action">Action</label>
	    							<div class="form-group">
	    								<form:input path="action" cssClass="form-control" />
	    							</div>
    							</div>
    							
    							<div class="col-xs-12 col-sm-6  col-md-6">
	    							<label for="type">Type</label>
	    							<div class="form-group">
	    								<form:select path="type" cssClass="form-control">
	    									<form:options items="${mapType }" />
	    								</form:select>
	    							</div>
    							</div>
    						</div>
    						
    						<div class="row mt-3">
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
				<div></div>
				<div>
					<jsp:include page="../layout/paging.jsp"></jsp:include>
				</div>
			</div>
			
			<div class="table-responsive">
				<table class="table table-striped jambo_table bulk_action">
					<thead>
						<tr class="headings">
							<th class="column-title">#</th>
							<th class="column-title">Category</th>
							<th class="column-title">Product Code</th>
							<th class="column-title">Product Name</th>
							<th class="column-title text-center">Quantity</th>
							<th class="column-title text-center">Price</th>
							<th class="column-title text-center">Type</th>
							<th class="column-title text-center">Activity</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${histories}" var="h" varStatus="loop">
							<c:choose>
								<c:when test="${loop.index % 2 == 0 }">
									<tr class="even pointer">
								</c:when>
								<c:otherwise>
									<tr class="odd pointer">
								</c:otherwise>
							</c:choose>
							<td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
							<td class=" ">${h.product.category.name }</td>
							<td class=" ">${h.product.code }</td>
							<td class=" ">${h.product.name }</td>
							<td class=" text-center">${h.quantity }</td>
							<td class=" text-center">${h.price }</td>
							<c:choose>
								<c:when test="${h.type == 1}">
									<td class=" text-center">Import</td>
								</c:when>
								<c:otherwise>
									<td class=" text-center">Export</td>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${h.action eq 'Add' }">
									<td class=" font-weight-bold text-center">
										<span style="color: #28a745;">Add</span>
									</td>
								</c:when>
								<c:when test="${h.action eq 'Edit' }">
									<td class="font-weight-bold  text-center">
										<span style="color: #17a2b8;">Edit</span>
									</td>
								</c:when>
								<c:when test="${h.action eq 'Delete' }">
									<td class="font-weight-bold  text-center">
										<span style="color: #dc3545;">Delete</span>
									</td>
								</c:when>
							</c:choose>
						</c:forEach>
					</tbody>
				</table>
				
			</div>
    	</div>
    </div>
  </div>
</div>

<script type="text/javascript">

	function gotoPage(page) {
		$('#searchForm').attr('action', '<c:url value="/history/list/"/>' + page);
		$('#searchForm').submit();
	};

</script>