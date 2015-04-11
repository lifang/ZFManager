<#import "../../common.ftl" as c />
<#import "../material.ftl" as material />
<@c.html>
<div class="breadcrumb">
	<ul>
		<li><a href="#">售后</a></li>
		<li><a href="<@spring.url "/cs/cancel/list"/>">注销</a></li>
		<li><a href="<@spring.url "/cs/cancel/${csCancel.id}/info"/>">详情</a></li>
	</ul>
</div>
<div class="content clear">
	<div class="user_title">
		<h1>注销申请记录</h1>
		<#if csCancel.status=1>
		<div class="userTopBtnBox">
			<a class="ghostBtn" onClick="onCancel();">取消申请</a> 
			<a class="ghostBtn" onClick="onHandle();">标记为处理中</a>
		</div>
		<#elseif csCancel.status=2>
		<div class="userTopBtnBox">
			<a class="ghostBtn" onClick="onCancel();">取消申请</a> 
			<a class="ghostBtn" onClick="onFinish();">标记为处理完成</a>
		</div>
		<#elseif csCancel.status=5>
		<div class="userTopBtnBox">
			<a class="ghostBtn" onClick="onResubmit();">重新提交</a> 
		</div>
		</#if>
	</div>
	<div class="attributes_box">
		<h2>记录信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>编号：${csCancel.applyNum!}</li>
				<li>处理人：${csCancel.processUserName!}</li>
				<li>状态：<span class="orangeText">
					<#if csCancel.status=1>待处理
       				<#elseif csCancel.status=2>处理中
       				<#elseif csCancel.status=4>处理完成
					<#elseif csCancel.status=5>已取消
					</#if>
				</span></li>
				<li>申请日期：${csCancel.createdAt?datetime}</li>
			</ul>
		</div>
	</div>
	<div class="attributes_box">
		<h2>终端信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>品牌型号：<#if csCancel.good??>${csCancel.good.title!}</#if></li>
				<li>支付通道：<#if csCancel.payChannel??>${csCancel.payChannel.name!}</#if></li>
				<li>终端号：<#if csCancel.terminal??>${csCancel.terminal.serialNum!}</#if></li>
				<li>商户名称：<#if csCancel.merchant??>${csCancel.merchant.title!}</#if></li>
				<li>商户电话：<#if csCancel.merchant??>${csCancel.merchant.phone!}</#if></li>
			</ul>
		</div>
	</div>

	<#if materials??>
		<@material.material title="申请资料" materials=materials/>
	</#if>

	<div class="user_remark">
		<textarea id="textarea_mark" name="" cols="" rows=""></textarea>
		<button class="whiteBtn" onClick="onMark();">备注</button>
	</div>
	<div class="user_record">
		<h2>追踪记录</h2>
		<div id="mark_container">
		<#list csCancelMarks as mark>
			<#include "../mark.ftl" />
		</#list>
		<div>
	</div>
</div>


<script type="text/javascript">

	function onMark() {
		var csCancelId = ${csCancel.id};
		var status = ${csCancel.status}
		var content = $("#textarea_mark").val();
		if (content.length==0) {
			alert("请输入备注内容");
			return;
		}
		$.post('<@spring.url "/cs/cancel/${csCancel.id}/mark/create" />',
	    	{"content": content},
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
		$.post('<@spring.url "/cs/cancel/${csCancel.id}/cancel" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
	function onFinish() {
		$.post('<@spring.url "/cs/cancel/${csCancel.id}/finish" />',
	            {}, function (data) {
	            	location='<@spring.url "/cs/cancel/list" />';
	            });
	}
	
	function onHandle() {
		$.post('<@spring.url "/cs/cancel/${csCancel.id}/handle" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
	function onResubmit() {
		$.post('<@spring.url "/cs/cancel/${csCancel.id}/resubmit" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
</script>
</@c.html>