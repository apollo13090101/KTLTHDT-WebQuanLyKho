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
    				<h2>Update Permission</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
    			</div>
    			
    			<div class="x_content">
    				<br>
    				<form:form modelAttribute="modelForm" method="POST" servletRelativeAction="/menu/update-permission"  cssClass="form-horizontal form-label-left">
    					
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="roleId">Role <span class="required">*</span>
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12">
								<form:select path="roleId" cssClass="form-control">
									<form:options items="${mapRole }" />
								</form:select> 
							</div>
						</div>
    					
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="menuId">Menu <span class="required">*</span>
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:select path="menuId" cssClass="form-control">
									<form:options items="${mapMenu }" />
								</form:select> 
							</div>
						</div>
    					
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="code">Permission
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12 mt-2"> 
								<div class="form-check form-check-inline">
									<form:radiobutton path="permission" value="1" cssClass="form-check-input"/>
									<label class="form-check-label" for="permission1">Yes</label>
								</div>
								<div class="form-check form-check-inline">
									<form:radiobutton path="permission" value="0" cssClass="form-check-input"/>
									<label class="form-check-label" for="permission2">No</label>
								</div>
							</div>
						</div>
						
						<div class="ln_solid"></div>
										
						<div class="item form-group">
							<div class="col-md-6 col-sm-6 offset-md-3">
								<button class="btn btn-primary" type="button" onclick="cancel();">Cancel</button>
								<button class="btn btn-primary" type="reset">Reset</button>
								<button type="submit" class="btn btn-success">Submit</button>
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
			$('#menulistId').addClass('cuurent-page').siblings().removeClass('current-page');
			var parent = $('#menulistId').parents('li');
			parent.addClass('active').siblings().removeClass('active');
			$('#menulistId').parents().show();
		}		
	);
	
	function cancel() {
		window.location.href = '<c:url value="/menu/list"/>'
	}
</script>