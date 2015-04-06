<#import "../../common.ftl" as c />
<#import "../material.ftl" as material />
<@c.html>
<div class="breadcrumb">
	<ul>
		<li><a href="#">售后</a></li>
		<li><a href="<@spring.url "/cs/update/list"/>">资料更新</a></li>
		<li><a href="<@spring.url "/cs/update/${csUpdate.id}/info"/>">详情</a></li>
	</ul>
</div>
<div class="content clear">
	<div class="user_title">
		<h1>资料更新申请记录</h1>
		<#if csUpdate.status=1>
		<div class="userTopBtnBox">
			<a class="ghostBtn" onClick="onCancel();">取消申请</a> 
			<a class="ghostBtn" onClick="onHandle();">标记为处理中</a>
		</div>
		<#elseif csUpdate.status=2>
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
				<li>编号：${csUpdate.applyNum!}</li>
				<li>处理人：${csUpdate.processUserName!}</li>
				<li>状态：<span class="orangeText">
					<#if csUpdate.status=1>待处理
       				<#elseif csUpdate.status=2>处理中
       				<#elseif csUpdate.status=4>处理完成
					<#elseif csUpdate.status=5>已取消
					</#if>
				</span></li>
				<li>申请日期：${csUpdate.createdAt?datetime}</li>
			</ul>
		</div>
	</div>
	<div class="attributes_box">
		<h2>终端信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>品牌型号：<#if csUpdate.good??>${csUpdate.good.title!}</#if></li>
				<li>支付通道：<#if csUpdate.payChannel??>${csUpdate.payChannel.name!}</#if></li>
				<li>终端号：<#if csUpdate.terminal??>${csUpdate.terminal.serialNum!}</#if></li>
				<li>商户名称：<#if csUpdate.merchant??>${csUpdate.merchant.title!}</#if></li>
				<li>商户电话：<#if csUpdate.merchant??>${csUpdate.merchant.phone!}</#if></li>
			</ul>
		</div>
	</div>

	<#if materials??>
		<@material.material title="更新申请资料" materials=materials/>
	</#if>

	<div class="user_remark">
		<textarea id="textarea_mark" name="" cols="" rows=""></textarea>
		<button class="whiteBtn" onClick="onMark();">备注</button>
	</div>
	<div class="user_record">
		<h2>追踪记录</h2>
		<div id="mark_container">
		<#list csUpdateMarks as mark>
			<#include "../mark.ftl" />
		</#list>
		<div>
	</div>
</div>


<script type="text/javascript">

	function onMark() {
		var csUpdateId = ${csUpdate.id};
		var status = ${csUpdate.status}
		var content = $("#textarea_mark").val();
		if (content.length==0) {
			alert("请输入备注内容");
			return;
		}
		$.post('<@spring.url "/cs/update/mark/create" />',
	    	{"csUpdateId": csUpdateId,
	    	 "content": content},
	    	 function (data) {
	         	if (status==2) {
	    	 		$('#mark_container').prepend(data);
	            	$("#textarea_mark").val("");
	    	 	} else {
	    	 		location.reload();
	    	 	}
	         });
	}
	
	function onCancel() {
		$.post('<@spring.url "/cs/update/${csUpdate.id}/cancel" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
	function onFinish() {
		$.post('<@spring.url "/cs/update/${csUpdate.id}/finish" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
	function onHandle() {
		$.post('<@spring.url "/cs/update/${csUpdate.id}/handle" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
</script>
</@c.html>