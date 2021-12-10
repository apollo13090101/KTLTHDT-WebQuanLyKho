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
    				<h2>Products in Stock</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
    			</div>
    			
    			<div class="x_content">
    				<p>Need to find products in stock?</p>
    				
    				<div class="container">
    					<form:form modelAttribute="searchForm" servletRelativeAction="/product-in-stock/list/1" method="POST">
    						
    						<div class="row">
    						
    							<div class="col-xs-12 col-sm-4 col-md-4">
	    							<label for="${product.category.code }">Category Code</label>
	    							<div class="form-group">
	    								<form:input path="product.category.code" cssClass="form-control" />
	    							</div>
    							</div>
    							
    							<div class="col-xs-12 col-sm-4 col-md-4">
	    							<label for="${product.code }">Product Code</label>
	    							<div class="form-group">
	    								<form:input path="product.code" cssClass="form-control" />
	    							</div>
    							</div>
    							
    							
    							<div class="col-xs-12 col-sm-4 col-md-4">
	    							<label for="${product.name }">Product Name</label>
	    							<div class="form-group">
	    								<form:input path="product.name" cssClass="form-control" />
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
							<th class="column-title">Prod Code</th>
							<th class="column-title">Prod Name</th>
							<th class="column-title text-center">Image</th>
							<th class="column-title text-center">Quantity</th>
							<th class="column-title">Category</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${stocks}" var="s" varStatus="loop">
							<c:choose>
								<c:when test="${loop.index % 2 == 0 }">
									<tr class="even pointer">
								</c:when>
								<c:otherwise>
									<tr class="odd pointer">
								</c:otherwise>
							</c:choose>
							<td class=" ">${pageInfo.getOffset()+loop.index+1}</td>
							<td class=" ">${s.product.code }</td>
							<td class=" ">${s.product.name }</td>
							<td class=" text-center">
								<img class="figure-img img-fluid rounded" src="<c:url value="${s.product.imageUrl}"/>" width="100px" height="100px"/>
							</td>
							<td class=" text-center">${s.quantity }</td>
							<td class=" ">${s.product.category.code }</td>
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
		$('#searchForm').attr('action', '<c:url value="/product-in-stock/list/"/>' + page);
		$('#searchForm').submit();
	};

</script>