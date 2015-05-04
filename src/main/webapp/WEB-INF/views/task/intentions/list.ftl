<#import "../../common.ftl" as c /> <@c.html>
<div class="breadcrumb">
    <ul>
        <li><a>购买意向</a></li>
    </ul>
</div>

<div class="content clear">
	<div class="user_title">
		<h1>购买意向列表</h1>
		<div class="userTopBtnBox">
	<#if Roles.hasRole("INTENTION_ASSIGN")>
			<a id="btn_dispatch" class="ghostBtn assign_a">分派</a>
	</#if>
		</div>
	</div>
	<div class="seenBox clear">
		<ul>
			<li><div class="user_search">
					<input id="hidden_keys"  type="text" />
					<button id="btn_search"></button>
				</div></li>
				<li>
				<div class="user_select">
					<input id="hidden_type" type="hidden" />
					<label>查看类型</label> <select id="select_type"> 
			          	<option value="1">购买意向</option> 
			          	<option value="2">申请代理商</option> 
					</select>
				</div>
			</li>	
			<li><div class="user_select">
					<input id="hidden_status" type="hidden" />
					<label>状态筛选</label> <select id="select_status">
						<option value="0">全部</option> 
			          	<option value="1">待处理</option> 
				        <option value="2">处理中</option> 
				        <option value="3">已处理</option> 
					</select>
				</div></li> 
		</ul>
	</div>
	<div id="page_fresh"><#include "page.ftl"/></div>
	
</div> 

<script type="text/javascript">
	$(function() { 
		var type = 0;
		$("#search_keys").val("");
		$('#select_status').change(function(){
			var status = $(this).children('option:selected').val(); 
			type = $('#select_type').children('option:selected').val();
			$("#hidden_status").val(status);
			if(type == 1){
				intentionPageChange(1);
			}
			if(type == 2){
				intentionPageChangeByType(1);
			} 
		});
		
		
		$('#select_type').change(function(){
			var status = $("#select_status").children('option:selected').val();
			$("#hidden_status").val(status);
			type = $(this).children('option:selected').val();
			if(type == 1){
				intentionPageChange(1);
			}
			if(type == 2){
				intentionPageChangeByType(1);
			}
			
		});
		
		
		$("#btn_search").bind("click",
	        function() {
			var keys = $("#search_keys").val();
			type = $('#select_type').children('option:selected').val();
			if(type == 1){
				intentionPageChange(1);
			}
			if(type == 2){
				intentionPageChangeByType(1);
			} 
	    });
	});
	
	function intentionPageChangeByType(page) {
		var keys = $("#hidden_keys").val().trim();
		var status = $("#hidden_status").val();
	    $.post('<@spring.url "/task/agentjoin/findAngetJoin" />',
	            {"page": page,
	             "keys": keys,
	             "statu": status
	            },
	            function (data) {
	                $('#page_fresh').html(data);
	            });
	}  
	  
	function intentionPageChange(page) {
		var keys = $("#hidden_keys").val().trim();
		var status = $("#hidden_status").val();
		type = $('#select_type').children('option:selected').val();
		if(type == 1){
			$.post('<@spring.url "/task/intention/page" />',
	            {"page": page,
	             "keys": keys,
	             "status": status
				},
			function (data) {
				$('#page_fresh').html(data);
			});
		}
		if(type == 2){
			intentionPageChangeByType(page);
		}  
	} 
	
	
	var ups=function(id,status,page){
		type = $('#select_type').children('option:selected').val();
		if(type == 1){
			$.post('<@spring.url "/task/intention/upstatus" />',
				{"id": id,"status":status},
		        function (data) {
					if(1==data){
						intentionPageChange(page);
					}else{
						alert("操作失败!");
					}
			});
		}
		
		if(type == 2){
			$.post('<@spring.url "/task/agentjoin/upstatus" />',
				{"id": id,"status":status},
		        function (data) {
					if(1==data){
						//intentionPageChange(page);
						intentionPageChangeByType(1)
					}else{
						alert("操作失败!");
					}
			});
		}     
	}
	
</script>
</@c.html>
