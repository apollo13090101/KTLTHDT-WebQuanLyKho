<style>
   .mydivstyle:hover {
       box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.3);
       transform: translateY(-3px);
   }

   .bg-red {
       background-color: #ec4646;
       color: whitesmoke;
   }

   .bg-blue {
       background-color: #1687a7;
       color: whitesmoke;
   }

   .bg-black {
       background-color: #393e46;
       color: whitesmoke;
   }

   .bg-green {
       background-color: #16c79a;
       color: whitesmoke;
   }

   h5.card-title {
       font-size: 3rem;
   }
</style>
<div class="right_col" role="main">
	<div class="">
		<div class="clearfix"></div>
    
	    <div class="row">
	    	<div class="col-md-12 col-sm-12">
	    		<div class="x_panel">
	    			<div class="x_title">
	    				<h2>Spring MVC Java Project</h2>
						<ul class="nav navbar-right panel_toolbox">
							<!-- <li>
								<a class="collapse-link"><i class="fa fa-chevron-up"></i></a>
							</li> -->
						</ul>
						<div class="clearfix"></div>
	    			</div>
	    			
	    			<div class="x_content">
	    				<div class="container">
	    					<div class="row row-cols-1 row-cols-md-4">
	    						<div class="col mb-4">
						            <div class="card border-danger h-100 text-center bg-red mydivstyle">
						                <div class="card-body">
						                    <h5 class="card-title">${numUsers }</h5>
						                </div>
						                <div class="card-footer bg-transparent border-danger">
						                    <a href="/INT14103/user/list/1" class="text-white" style="font-size: 1.5rem">Active Users</a>
						                </div>
						            </div>
						        </div>
						        <div class="col mb-4">
						            <div class="card border-info h-100 text-center bg-blue mydivstyle">
						                <div class="card-body">
						                    <h5 class="card-title">${numProducts }</h5>
						                </div>
						                <div class="card-footer bg-transparent border-info">
						                    <a href="/INT14103/product/list/1" class="text-white" style="font-size: 1.5rem">Products</a>
						                </div>
						            </div>
						        </div>
	    					</div>
	    				</div>
	    			</div>
	    		</div>
	    	</div>
	    </div>
	</div>
</div>