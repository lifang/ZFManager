<#import "../../common.ftl" as c /> <@c.html>
<div class="breadcrumb">
	<ul>
		<li><a>任务</a></li>
		<li><a>认证开通</a></li>
	</ul>
</div>



<div class="content clear">
	<div class="user_title">
		<h1>开通申请列表</h1>
		<div class="userTopBtnBox">
			<a id="btn_dispatch" class="ghostBtn assign_a">分派</a>
		</div>
	</div>
	<div class="seenBox clear">
		<ul>
			<li><div class="user_search">
					<input id="hidden_keys"  type="text" />
					<button id="btn_search"></button>
				</div></li>
			<li>
			<input id="hidden_status" type="hidden" />
			<div class="user_select">
					<label>状态筛选</label> <select id="select_status">
						  <option value="0">全部</option> 
				          <option value="1">待初次预审</option> 
				          <option value="2">初次预审不通过</option> 
				          <option value="3">二次预审中</option> 
				          <option value="4">二次预审不通过</option> 	         
				          <option value="5">待审核</option> 
				          <option value="6">审核中</option> 
				          <option value="7">审核失败</option> 	         
				          <option value="8">审核成功</option> 
				          <option value="9">已取消</option> 
					</select>
				</div>
			</li>
		</ul>
	</div>
	<div id="page_fresh"><#include "page.ftl" /></div>
</div>



<script type="text/javascript">
	$(function() {
		$("#search_keys").val("");
		$('#select_status').change(function(){
			var status = $(this).children('option:selected').val();
			$("#hidden_status").val(status);
			certifiedOpenPageChange(1);
		});
		
		$("#btn_search").bind("click",
	        function() {
			var keys = $("#search_keys").val();
			certifiedOpenPageChange(1);
	    });

	});
	
	function certifiedOpenPageChange(page) {
		var keys = $("#hidden_keys").val();
		var status = $("#hidden_status").val();
	    $.get('<@spring.url "/task/certifiedopen/page" />',
	            {"page": page,
	             "keys": keys,
	             "status": status
	            },
	            function (data) {
	                $('#page_fresh').html(data);
	            });
	}
	
	var ups=function(id,status,page){
		//var page=${apply.currentPage};
		$.post('<@spring.url "/task/certifiedopen/upstatus" />',
				{"id": id,"status":status},
		        function (data) {
					if(1==data){
						certifiedOpenPageChange(page);
					}else{
						alert("操作失败!");
					}
		        });
	}
	
	var upvs=function(id,status,page){
		//var page=${apply.currentPage};
		$.post('<@spring.url "/task/certifiedopen/upvstatus" />',
				{"id": id,"status":status},
		        function (data) {
					if(data>0){
						certifiedOpenPageChange(page);
					}else{
						alert("操作失败!");
					}
		        });
	}
	
</script>
</@c.html>
