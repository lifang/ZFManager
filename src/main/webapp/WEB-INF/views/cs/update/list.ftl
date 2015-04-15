<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb"> 
	<ul> 
    	<li><a href="#">售后</a></li> 
    	<li><a href="<@spring.url "/cs/update/list"/>">资料更新</a></li> 
	</ul> 
</div> 

<div class="content clear"> 
	<div class="user_title">
    	<h1>资料更新申请列表</h1> 
		<div class="userTopBtnBox"> 
			<a id="btn_dispatch" class="ghostBtn assign_a">分派</a>
		</div> 
	</div>
	<div class="seenBox clear"> 
		<ul> 
			<li>
				<div class="user_search">
					<input id="search_keyword" name="" type="text" class="" placeholder="请输入售后单号"/>
					<button id="btn_search"></button>
				</div>
			</li> 
        	<li>
				<div class="user_select"> 
					<label>状态筛选</label> 
					<select id="select_status"> 
						<option value="-1">全部</option> 
						<option value="1">待处理</option> 
						<option value="2">处理中</option> 
						<option value="4">处理完成</option>
						<option value="5">已取消</option> 
					</select> 
				</div>
			</li> 
		</ul> 
	</div> 
	<div id="page_fresh">
		<#include "table.ftl" />
	</div>
</div>

<script type="text/javascript">

	var keyword;
	var status;

	$(function(){
		$('#select_status').change(function(){
			status = $(this).children('option:selected').val();
			pageChange(1);
		});
		
		$("#btn_search").bind("click", function() {
			keyword = $("#search_keyword").val();
			pageChange(1);
	    });
	    
	    $('#search_keyword').keydown(function(e){
			if(e.keyCode==13){
   				keyword = $("#search_keyword").val();
				pageChange(1);
			}
		});
	});
	
	function pageChange(page) {
	    $.get('<@spring.url "/cs/update/page" />',
	            {"page": page,
	            "keyword": keyword,
	            "status": status},
	            function (data) {
	                $('#page_fresh').html(data);
	            });
	}
	
	function onCancel(csUpdateId) {
		$.post('<@spring.url "" />'+'/cs/update/'+csUpdateId+'/cancel',
	            {}, function (data) {
	            	$("#operation_"+csUpdateId).html(
	            		'<a href="<@spring.url "" />'+'/cs/update/'+csUpdateId+'/info" class="a_btn">查看详情</a>'
	            	);
	            	$("#status_"+csUpdateId).text("已取消");
	            });
	}
	
	function onFinish(csUpdateId) {
		$.post('<@spring.url "" />'+'/cs/update/'+csUpdateId+'/finish',
	            {}, function (data) {
	            	$("#operation_"+csUpdateId).html(
	            		'<a href="<@spring.url "" />'+'/cs/update/'+csUpdateId+'/info" class="a_btn">查看详情</a>'
	            	);
	            	$("#status_"+csUpdateId).text("处理完成");
	            });
	}
	
	function onHandle(csUpdateId) {
		$.post('<@spring.url "" />'+'/cs/update/'+csUpdateId+'/handle',
	            {}, function (data) {
	            	$("#operation_"+csUpdateId).html(
	            		'<a href="<@spring.url "" />'+'/cs/update/'+csUpdateId+'/info" class="a_btn">查看详情</a>'
						+'<a class="a_btn" onClick="onFinish('+csUpdateId+');">标记为处理完成</a>	'
	            	);
	            	$("#status_"+csUpdateId).text("处理中");
	            });
	}

</script>	
</@c.html>