<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="//cdnjs.cloudflare.com/ajax/libs/numeral.js/2.0.6/numeral.min.js"></script>

<!-- bootstrap-datetimepicker -->
<link href="<c:url value="/static/vendors/bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css"/>" rel="stylesheet">

<div class="right_col" role="main">
  <div class="">
    <div class="clearfix"></div>
    
    <div class="row">
    	<div class="col-md-12 col-sm-12">
    		<div class="x_panel">
    			<div class="x_title">
    				<h2>Summary of Import/Export</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
						</li>
					</ul>
					<div class="clearfix"></div>
    			</div>
    			
    			<div class="x_content">
    				<p>Enter the required parameters!</p>
    				
    				<div class="container">
    					<form action="/INT14103/thnx/export" target="_blank" method="POST" > 
    						
    						<div class="row">
    							<div class="col-xs-12 col-sm-6 col-md-6">
	    							<label for="ngaybd">Date From</label>
	    							<div class="form-group">
	    								<input type="date" name="NGAYBD" id="ngaybd" class="form-control" required />
	    							</div>
    							</div>
    							
    							<div class="col-xs-12 col-sm-6 col-md-6">
	    							<label for="ngaykt">Date To</label>
	    							<div class="form-group">
	    								<input type="date" name="NGAYKT" id="ngaykt" class="form-control" required />
	    							</div>
    							</div>
    							
    						</div>
    						
    						<div class="row">
								<div class="col-xs-12 col-sm-6 col-md-6 offset-md-3 text-center">
									<button type="submit" class="btn btn-success">Generate</button>
									<button class="btn btn-primary" type="reset">Reset</button>
								</div>
    						</div>
    					</form>
    				</div>
    			</div>
    		</div>
    	
    	</div>
    </div>
    
  </div>
</div>

<!-- bootstrap-daterangepicker -->
<script src="<c:url value="/static/vendors/moment/min/moment.min.js"/>"></script>
<script src='<c:url value="/static/vendors/bootstrap-daterangepicker/daterangepicker.js"/>'></script>
<!-- bootstrap-datetimepicker -->
<script src="<c:url value="/static/vendors/bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"/>"></script>

<script type="text/javascript">

	function confirmDelete(code) {
		if (confirm('Are you sure you want delete this thnx?')) {
			window.location.href = '<c:url value="/thnx/delete/"/>' + code;
		}
	};
	
	function gotoPage(page) {
		$('#searchForm').attr('action', '<c:url value="/thnx/list/"/>' + page);
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
			
			$('#ngaybd').datetimepicker({
				"allowInputToggle": true,
	            "showClose": true,
	            "showClear": true,
	            "showTodayButton": true,
	            "format": "YYYY-MM-DD",
			});
			
			$('#ngaykt').datetimepicker({
				"allowInputToggle": true,
	            "showClose": true,
	            "showClear": true,
	            "showTodayButton": true,
	            "format": "YYYY-MM-DD",
			});
	});

</script>