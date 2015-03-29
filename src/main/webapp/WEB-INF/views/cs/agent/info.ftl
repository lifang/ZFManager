<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
	<ul>
		<li><a href="#">售后</a></li>
		<li><a href="<@spring.url "/cs/agent/list"/>">代理商售后</a></li>
		<li><a href="<@spring.url "/cs/agent/${csAgent.id}/info"/>">详情</a></li>
	</ul>
</div>
<div class="content clear">
	<div class="user_title">
		<h1>代理商售后申请记录</h1>
		<#if csAgent.status=0>
		<div class="userTopBtnBox">
			<a class="ghostBtn" onClick="onCancel();">取消申请</a>
			<a class="ghostBtn" onClick="onHandle();">标记为处理中</a>
		</div>
		<#elseif csAgent.status=1>
		<div class="userTopBtnBox">
			<a class="ghostBtn" onClick="onCancel();">取消申请</a>
			<a class="ghostBtn" onClick="onFinish();">标记为处理完成</a>
		</div>
		</#if>
	</div>
	<div class="attributes_box">
		<h2>记录信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>编号：${csAgent.applyNum!}</li>
				<li>处理人：${csAgent.processUserName!}</li>
				<li>状态：<span class="orangeText">
					<#if csAgent.status=0>待处理
       				<#elseif csAgent.status=1>处理中
       				<#elseif csAgent.status=2>已取消
					<#elseif csAgent.status=3>已完成
					</#if>
				</span></li>
				<li>申请日期：${csAgent.createdAt?datetime}</li>
			</ul>
		</div>
	</div>
	<div class="attributes_box">
		<h2>售后单信息</h2>
		<div class="attributes_list clear">
			<ul>
				<li><em>终端号：</em><span>${csAgent.terminalsList!}</span></li>
				<li><em>地址：</em><span>${csAgent.address!}</span></li>
				<li><em>原因：</em><span>${csAgent.reason!}</span></li>
			</ul>
		</div>
	</div>
	<div class="user_remark">
		<textarea id="textarea_mark" name="" cols="" rows=""></textarea>
		<button class="whiteBtn" onClick="onMark();">备注</button>
	</div>
	<div class="user_record">
		<h2>追踪记录</h2>
		<div id="mark_container">
		<#list csAgentMarks as mark>
			<#include "../mark.ftl" />
		</#list>
		</div>
	</div>
</div>

<script type="text/javascript">
	
	function onMark() {
		var csAgentId = ${csAgent.id};
		var status = ${csAgent.status};
		var content = $("#textarea_mark").val();
		if (content.length==0) {
			alert("请输入备注内容");
			return;
		}
		$.post('<@spring.url "/cs/agent/mark/create" />',
	    	{"csAgentId": csAgentId,
	    	 "content": content},
	    	 function (data) {
	    	 	if (status==1) {
	    	 		$('#mark_container').prepend(data);
	            	$("#textarea_mark").val("");
	    	 	} else {
	    	 		location.href='<@spring.url "" />'+'/cs/agent/'+csAgentId+'/info';
	    	 	}
	         });
	}
	
	function onCancel() {
		$.post('<@spring.url "/cs/agent/${csAgent.id}/cancel" />',
	            {}, function (data) {
	            	location.href='<@spring.url "/cs/agent/list" />';
	            });
	}
	
	function onFinish() {
		$.post('<@spring.url "" />' + '/cs/agent/${csAgent.id}/finish',
				{}, function(data) {
					location.href='<@spring.url "/cs/agent/list" />';
				});
	}
	
	function onHandle() {
		$.post('<@spring.url "" />' + '/cs/agent/${csAgent.id}/handle',
				{}, function(data) {
					location.href='<@spring.url "/cs/agent/list" />';
				});
	}
</script>
</@c.html>