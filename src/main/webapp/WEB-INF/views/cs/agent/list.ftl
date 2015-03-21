<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb"> 
	<ul> 
    	<li><a href="#">售后</a></li> 
    	<li><a href="<@spring.url "/cs/agent/list"/>">代理商售后</a></li> 
	</ul> 
</div> 

<div class="content clear"> 
	<div class="user_title">
    	<h1>代理商售后申请列表</h1> 
		<div class="userTopBtnBox"> 
			<a id="btn_dispatch" class="ghostBtn assign_a">分派</a>
		</div> 
	</div>
	<div class="seenBox clear"> 
		<ul> 
			<li>
				<div class="user_search">
					<input id="search_keyword" name="" type="text" class="" />
					<button id="btn_search"></button>
				</div>
			</li> 
        	<li>
				<div class="user_select"> 
					<label>状态筛选</label> 
					<select id="select_status"> 
						<option value="-1">全部</option> 
						<option value="0">待处理</option> 
						<option value="1">处理中</option> 
						<option value="2">已取消</option> 
						<option value="3">已完成</option> 
					</select> 
				</div>
			</li> 
		</ul> 
	</div> 
	<div id="page_fresh">
		<#include "table.ftl" />
	</div>
</div>

<div class="tab assign_tab">
	<a href="#" class="close">关闭</a>
	<div class="tabHead">任务分派</div>
	<div id="dispatch_select" class="tabBody">
	</div>
	<div id="dispatch_submit" class="tabFoot">
		<button class="blueBtn" onClick="onDispatch();">确定</button>
	</div>
</div>

<script type="text/javascript">
	var keyword;
	var status;
	
	$(function(){
	
		$("#btn_dispatch").bind("click", function() {
			var dispatchIds = [];
			var dispatchedNums = [];
			$("input[name='cb_row']").each(function () {
				var id = $(this).attr("cs_agent_id");
				var status = $(this).attr("cs_agent_status");
				var num = $(this).attr("cs_agent_num");
           		if($(this).prop("checked") ) {
           			if (status==0) {
           				dispatchIds.push(id);
           			} else {
           				dispatchedNums.push(num);
           			}
           		}
            });
            $('#dispatch_select').empty();
            $.get('<@spring.url "/cs/dispatch" />',
	            {}, function (data) {
	            	$('#dispatch_select').append('<p class="assign_tab_p">将选中的 <span class="orangeText">'+dispatchIds.length+'</span> 条<span class="orangeText">待处理</span>任务分派给</p>')
	                $('#dispatch_select').append(data);
	                if (dispatchedNums.length>0) {
            			$('#dispatch_select').append('<br/><br/><em>编号<span class="orangeText">'+dispatchedNums+'</span>的申请已经在分派，不能再分派<em>')
            		}
	            });
	    });
	
		$("input[name='cb_row']").bind("click", function () {
			var total=0;
			var checked=0;
			$("input[name='cb_row']").each(function () {
           		total++;
           		if ($(this).prop("checked")) {
           			checked++;
           		}
            });
            if(checked<total) {
            	$("input[name='cb_all']").prop("checked",false);
            } else if(total==checked) {
            	$("input[name='cb_all']").prop("checked",true);
            }
        });
        
        $("input[name='cb_all']").bind("click", function () {
			var checked = this.checked;
			$("input[name='cb_row']").each(function () {
           		$(this).prop("checked", checked);
            });
        });
	
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
	    $.get('<@spring.url "/cs/agent/page" />',
	            {"page": page,
	            "keyword": keyword,
	            "status": status},
	            function (data) {
	                $('#page_fresh').html(data);
	            });
	}
	
	function onCancel(csAgentId) {
		$.post('<@spring.url "" />'+'/cs/agent/'+csAgentId+'/cancel',
	            {}, function (data) {
	            	$("#operation_"+csAgentId).html('<a href="<@spring.url "" />"+"/cs/agent/"+csAgentId+"/info" class="a_btn">查看详情</a>');
	            	$("#status_"+csAgentId).text("已取消");
	            });
	}
	
	function onFinish(csAgentId) {
		$.post('<@spring.url "" />'+'/cs/agent/'+csAgentId+'/finish',
	            {}, function (data) {
	            	$("#operation_"+csAgentId).html('<a href="<@spring.url "" />"+"/cs/agent/"+csAgentId+"/info" class="a_btn">查看详情</a>');
	            	$("#status_"+csAgentId).text("已完成");
	            });
	}
	
	function onDispatch() {
		var csAgentIds = [];
		$("input[name='cb_row']").each(function () {
				var id = $(this).attr("cs_agent_id");
				var status = $(this).attr("cs_agent_status");
           		if($(this).prop("checked") && status==0) {
           			csAgentIds.push(id);
           		}
            });
        var ids = csAgentIds.join(',');
		var customerId = $("#customer_select  option:selected").attr("customer_id");
		var customerName = $("#customer_select  option:selected").text();
		$.post('<@spring.url "" />'+'/cs/agent/dispatch',
	            {"ids": ids,
	            "customerId":customerId,
	            "customerName":customerName}, function (data) {
	            	location.href = '<@spring.url "" />'+'/cs/agent/list';
	            });
	}
	
</script>	
</@c.html>