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
    				<form:form modelAttribute="modelForm" method="POST" servletRelativeAction="/user/edit"  cssClass="form-horizontal form-label-left">
    					<form:hidden path="id"/>    					
    					<form:hidden path="password"/>    					
    					<form:hidden path="active"/>    					
    					<form:hidden path="created"/>
    					
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="roleId">Role
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:errors path="roleId" cssClass="help-block" cssStyle="color: #dc3545;"></form:errors>
								<form:select path="roleId" cssClass="form-control " disabled="${viewOnly }">
									<form:options items="${mapRole }" />
								</form:select>
							</div>
						</div>
    					
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="lastName">Last Name
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:errors path="lastName" cssClass="help-block" cssStyle="color: #dc3545;" />
								<form:input path="lastName" disabled="${viewOnly }" cssClass="form-control" />
							</div>
						</div>
    					
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="firstName">First Name
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:errors path="firstName" cssClass="help-block" cssStyle="color: #dc3545;" />
								<form:input path="firstName" disabled="${viewOnly }" cssClass="form-control" />
							</div>
						</div>
    					
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="username">Username 
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:errors path="username" cssClass="help-block" cssStyle="color: #dc3545;" />
								<form:input path="username" disabled="${viewOnly }" cssClass="form-control" />
							</div>
						</div>
						
    					<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="password">Password 
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:errors path="password" cssClass="help-block" cssStyle="color: #dc3545;" />
								<form:input path="password" cssClass="form-control" disabled="true" />
							</div>
						</div>
						
						<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="email">Email
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:errors path="email" cssClass="help-block" cssStyle="color: #dc3545;" />
								<form:input path="email" disabled="${viewOnly }" cssClass="form-control" />
							</div>
						</div>
						
						<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="address">Address
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:errors path="address" cssClass="help-block" cssStyle="color: #dc3545;" />
								<form:input path="address" disabled="${viewOnly }" cssClass="form-control" />
							</div>
						</div>
						
						<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="birthday">Birthday
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:errors path="birthday" cssClass="help-block" cssStyle="color: #dc3545;" />
								<form:input path="birthday" disabled="${viewOnly }" type="date" pattern="(?:19|20)(?:(?:[13579][26]|[02468][048])-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))|(?:[0-9]{2}-(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:29|30))|(?:(?:0[13578]|1[02])-31)))" cssClass="form-control" />
							</div>
						</div>
						
						<div class="item form-group">
							<label class="col-form-label col-md-3 col-sm-3 label-align" for="salary">Salary
							</label>
							<div class="col-md-6 col-sm-6 col-xs-12"> 
								<form:errors path="salary" cssClass="help-block" cssStyle="color: #dc3545;" />
								<form:input path="salary" disabled="${viewOnly }" cssClass="form-control" />
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
			$('#userlistId').addClass('cuurent-page').siblings().removeClass('current-page');
			var parent = $('#userlistId').parents('li');
			parent.addClass('active').siblings().removeClass('active');
			$('#userlistId').parents().show();
		}		
	);
	
	function cancel() {
		window.location.href = '<c:url value="/user/list"/>'
	}
</script>