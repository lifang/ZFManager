<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">订单管理</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title">
        <h1>订单列表</h1>
        <div class="userTopBtnBox">
          
        </div>
    </div>
    <div class="seenBox clear">
        <ul>
            <li><div class="user_search">
		            <input id="search_keys" name="" type="text" />
		            <input id="hidden_keys" type="hidden" name="keys" value="" />
		            <input id="hidden_status" type="hidden" name="status" value="" />
		            <input id="hidden_factory_id" type="hidden" name="factoryId" value="" />
		            <button id="btn_search"></button>
	            </div>
            </li>
            <li><div class="user_select">
                <label>状态筛选</label>
                     <select id="select_status"> 
                     	  <option value="0">全部</option> 
				          <option value="1">未付款</option> 
				          <option value="2">已付款</option> 
				          <option value="3">已发货</option> 
				          <option value="4">已评价</option> 	         
				          <option value="5">已取消</option> 
				          <option value="6">交易关闭</option> 
			          </select> 
	            
            </div></li>
        </ul>
    </div>
    <div id="page_fresh">
      	<#include "pageOrder.ftl" />
      </div>
</div>
<script type="text/javascript">

	$(function(){
			$('#select_status').change(function(){
				var status = $(this).children('option:selected').val();
				$("#hidden_status").val(status);
				orderPageChange(1);
			});
			
			$("#btn_search").bind("click",
		        function() {
				var keys = $("#search_keys").val();
				$("#hidden_keys").val(keys);
				orderPageChange(1);
		    });
		    
		    $('#select_factory').change(function(){
				var factoryId = $(this).children('option:selected').val();
				$("#hidden_factory_id").val(factoryId);
				orderPageChange(1);
			});
	       
	});

	function orderPageChange(page) {
		var keys = $("#hidden_keys").val();
		var status = $("#hidden_status").val();
		var factoryId=$("#hidden_factory_id").val();
	    $.get('<@spring.url "/factory/order/page" />',
	            {"page": page,
	             "keys": keys,
	             "status": status,
	             "factoryId":factoryId
	            },
	            function (data) {
	                $('#page_fresh').html(data);
	                popupPage();
	            });
	}
	
</script>
</@c.html>