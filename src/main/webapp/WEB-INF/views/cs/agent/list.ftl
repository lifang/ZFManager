<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb"> 
	<ul> 
    	<li>售后</li> 
    	<li><a href="<@spring.url "/cs/agent/list"/>">代理商售后</a></li> 
	</ul> 
</div> 

<div class="content clear"> 
	<div class="user_title">
    	<h1>代理商售后申请列表</h1> 
		<div class="userTopBtnBox">
	<#if Roles.hasRole("CS_AGENT_ASSIGN")>
			<a id="btn_dispatch" class="ghostBtn assign_a">分派</a>
	</#if>
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
						<option value="3">处理完成</option> 
						<option value="4">已取消</option> 
					</select> 
				</div>
			</li> 
		</ul> 
	</div> 
	<div id="page_fresh">
		<#include "table.ftl" />
	</div>
</div>

<div class="tab exchangeGoods_tab">
	<a class="close">关闭</a>
	<div class="tabHead">添加换货出库记录</div>
	<div class="tabBody">
		<div style="margin:-10px 0px 5px 0px;"><font id="errMsg" color="red"></font></div>
		<textarea id="output_content" name="" cols="40" rows="2" class="textarea_pe" style="padding:5px;font-size:13px;" placeholder="请输入终端号，以逗号(,)分隔"></textarea>
	</div>
	<div class="tabFoot">
		<button class="blueBtn" onClick="onOutput();">确定</button>
	</div>
</div>

<script type="text/javascript">
	var keyword;
	var status;
	
	$(function() {
		$('#select_status').change(function() {
			status = $(this).children('option:selected').val();
			pageChange(1);
		});

		$("#btn_search").bind("click", function() {
			keyword = $("#search_keyword").val();
			pageChange(1);
		});

		$('#search_keyword').keydown(function(e) {
			if (e.keyCode == 13) {
				keyword = $("#search_keyword").val();
				pageChange(1);
			}
		});
	});

	function pageChange(page) {
		$.get('<@spring.url "/cs/agent/page" />', {
			"page" : page,
			"keyword" : keyword,
			"status" : status
		}, function(data) {
			$('#page_fresh').html(data);
		});
	}

	function onCancel(csAgentId) {
		$.post('<@spring.url "" />' + '/cs/agent/' + csAgentId + '/cancel',
				{}, function(data) {
					$("#operation_" + csAgentId).html(
						'<a href="<@spring.url "" />' + '/cs/agent/' + csAgentId + '/info" class="a_btn">查看详情</a>'
					);
					$("#status_" + csAgentId).text("已取消");
				});
	}

	function onFinish(csAgentId) {
		$.post('<@spring.url "" />' + '/cs/agent/' + csAgentId + '/finish',
				{}, function(data) {
					$("#operation_" + csAgentId).html(
						'<a href="<@spring.url "" />' + '/cs/agent/'+ csAgentId+ '/info" class="a_btn">查看详情</a>'
					);
					$("#status_" + csAgentId).text("处理完成");
				});
	}
	
	function onHandle(csAgentId) {
		$.post('<@spring.url "" />' + '/cs/agent/' + csAgentId + '/handle',
				{}, function(data) {
					$("#operation_" + csAgentId).html(
						'<a href="<@spring.url "" />' + '/cs/agent/'+ csAgentId+ '/info" class="a_btn">查看详情</a>'
						+'<a class="a_btn" onClick="onCancel('+csAgentId+');">取消</a>'
						+'<a class="a_btn exchangeGoods_a" onClick="onPreOutput('+csAgentId+');">添加换货出库记录</a>'
						+'<a class="a_btn" onClick="onFinish('+csAgentId+');">标记为处理完成</a>'
					);
					$("#status_" + csAgentId).text("处理中");
					popup(".exchangeGoods_tab",".exchangeGoods_a");//添加换货出库记录
				});
	}
	
	var outputId;
	
	function onPreOutput(csAgentId) {
		outputId = csAgentId;
		$("#errMsg").html("");
		$("#output_content").val("");
	}
	
	function onOutput() {
		var terminalList = $("#output_content").val();
		var reg = /^[0-9a-zA-Z\,]+$/;
		if(!reg.test(terminalList)){
			$("#errMsg").html("终端号填写有误，请重新填写");
		}else{
			$.post('<@spring.url "" />'+'/cs/agent/'+outputId+'/output',
	            {"terminalList": terminalList}, 
	            function (data) {
	            	if(data.code==-1){
						$("#errMsg").html(data.message);
						return;
	            	}
	            	$(".exchangeGoods_tab").hide();
	    			$(".mask").hide();
	    			showErrorTip("添加换货出库记录成功");
	            });
		}
		
	}
	
</script>	
</@c.html>