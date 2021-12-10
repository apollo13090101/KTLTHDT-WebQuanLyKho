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
    				<h2>${titlePage }</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
    			</div>
    			
    			<div class="x_content">
    				<br>
    				<form:form modelAttribute="modelForm" method="GET" cssClass="form-horizontal form-label-left">
    					
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="code">ID
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:input path="id" disabled="${viewOnly }" cssClass="form-control" />
							</div>
						</div>
    					
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Issue Code 
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:input path="issue.code" disabled="${viewOnly }" cssClass="form-control" />
							</div>
						</div>
						
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Product Code 
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:input path="product.code" disabled="${viewOnly }" cssClass="form-control" />
							</div>
						</div>
						
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Quantity
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:input path="quantity" disabled="${viewOnly }" cssClass="form-control" />
							</div>
						</div>
						
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Price
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:input path="price" disabled="${viewOnly }" cssClass="form-control" />
							</div>
						</div>
    					
						
						<div class="ln_solid"></div>
										
						<div class="item form-group">
							<div class="col-md-6 col-sm-6 col-xs-12 offset-md-3">
								<button class="btn btn-primary" type="button" onclick="cancel();">Cancel</button>
							</div>
						</div>
						    					
    				</form:form>
    			</div>
    		</div>
    	</div>
    </div>
  </div>
</div>

<script type="text/javascript">
	$(document).ready(
		function () {
			$('#issue-detaillistId').addClass('cuurent-page').siblings().removeClass('current-page');
			var parent = $('#issue-detaillistId').parents('li');
			parent.addClass('active').siblings().removeClass('active');
			$('#issue-detaillistId').parents().show();
		}		
	);
	
	function cancel() {
		window.location.href = '<c:url value="/issue-detail/list"/>'
	}
</script>