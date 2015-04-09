<#import "../../common.ftl" as c />
<@c.html>
     <div class="breadcrumb">
                    <ul>
                        <li><a href="#">售后</a></li>
                        <li><a href="#">退款</a></li>
                    </ul>
                </div>
                <div class="content clear">
                    <div class="user_title">
                    	<h1>退款单列表</h1>
                        <div class="userTopBtnBox">
                        	<a id="btn_dispatch" class="ghostBtn assign_a">分派</a>
                     	</div>
                    </div>
                    <div class="seenBox clear">
                    	<ul>
                        	<li><div class="user_search">
                        	<input name="" type="text" id="search_keys"/>
                        	<input id="hidden_keys" type="hidden" name="keys" value="" />
          					<input id="hidden_status" type="hidden" name="status" value="" />
                        	<button id="btn_search"></button>
                        	</div></li>
                            <li><div class="user_select">
                            	<label>状态筛选</label>
                            	<select id="select_status"> 
	          						<option value="0">全部</option> 
	          						<option value="1">待处理</option> 
	          						<option value="2">处理完成</option> 
	          						<option value="3">已取消</option> 
          						</select> 
                            </div></li>
                        </ul>
                    </div>
                    <div id="page_fresh">
      					<#include "pageRefund.ftl" />
      				</div>
            	</div> 
            	
           <script type="text/javascript">
            	
            	function outRefundPageChange(page) {
					var keys = $("#hidden_keys").val();
					var status = $("#hidden_status").val();
	    		$.get('<@spring.url "/task/refund/page" />',
	            	{"page": page,
	             	"orderNumber": keys,
	             	"status": status
	            	},
	            	function (data) {
	               		$('#page_fresh').html(data);
	            	});
				}
				
				 $(function(){
        			$('#select_status').change(function(){
            		var status = $(this).children('option:selected').val();
            		$("#hidden_status").val(status);
            		refundPageChange(1);
        			});

       			 	$("#btn_search").bind("click",
               		function() {
                    var keys = $("#search_keys").val();
                    $("#hidden_keys").val(keys);
                    refundPageChange(1);
                	});
    			});
    			
    			function refundPageChange(page) {
        			var keys = $("#hidden_keys").val();
        			var status = $("#hidden_status").val();
        			$.get('<@spring.url "/task/refund/page" />',
                	{"page": page,
                    	"orderNumber": keys,
                    	"status": status
                	},
                	function (data) {
                    	$('#page_fresh').html(data);
                	});
    			}
           </script>
</@c.html>