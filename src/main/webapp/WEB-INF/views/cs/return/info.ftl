<#import "../../common.ftl" as c />
<#import "../material.ftl" as material />
<@c.html>
<div class="breadcrumb">
	<ul>
		<li><a href="#">售后</a></li>
		<li><a href="<@spring.url "/cs/return/list"/>">退货</a></li>
		<li><a href="<@spring.url "/cs/return/${csReturn.id}/info"/>">详情</a></li>
	</ul>
</div>
<div class="content clear">
	<div class="user_title">
		<h1>退货申请记录</h1>
		<#if csReturn.status=1>
		<div class="userTopBtnBox">
			<a class="ghostBtn" onClick="onCancel();">取消申请</a> 
			<a class="ghostBtn">确认退货</a>
			<a class="ghostBtn" onClick="onHandle();">标记为退货中</a>
		</div>
		<#elseif csReturn.status=2>
		<div class="userTopBtnBox">
			<a class="ghostBtn" onClick="onCancel();">取消申请</a> 
			<a class="ghostBtn">确认退货</a>
			<a class="ghostBtn" onClick="onFinish();">标记为退货完成</a>
		</div>
		</#if>
	</div>
	<div class="attributes_box">
		<h2>记录信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>编号：${csReturn.applyNum!}</li>
				<li>处理人：${csReturn.processUserName!}</li>
				<li>状态：<span class="orangeText">
					<#if csReturn.status=1>待处理
       				<#elseif csReturn.status=2>退货中
       				<#elseif csReturn.status=4>处理完成
					<#elseif csReturn.status=5>已取消
					</#if>
				</span></li>
				<li>申请日期：${csReturn.createdAt?datetime}</li>
			</ul>
		</div>
	</div>
	<div class="attributes_box">
		<h2>终端信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>品牌型号：<#if csReturn.good??>${csReturn.good.title!}</#if></li>
				<li>支付通道：<#if csReturn.payChannel??>${csReturn.payChannel.name!}</#if></li>
				<li>终端号：<#if csReturn.terminal??>${csReturn.terminal.serialNum!}</#if></li>
				<li>商户名称：<#if csReturn.merchant??>${csReturn.merchant.title!}</#if></li>
				<li>商户电话：<#if csReturn.merchant??>${csReturn.merchant.phone!}</#if></li>
			</ul>
		</div>
	</div>

	<div class="attributes_box">
		<h2>退货信息</h2>
		<div class="attributes_list clear">
			<ul>
				<li><em>终端号：</em><span><#if csReturn.terminal??>${csReturn.terminal.serialNum!}</#if></span></li>
				<li><em>收货地址：</em><span><#if csReturn.csReceiverAddress??>${csReturn.csReceiverAddress.address!}</#if></span></li>
				<li><em>退货原因：</em><span>${csReturn.reason!}</span></li>
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
		<#list csReturnMarks as mark>
			<#include "../mark.ftl" />
		</#list>
		<div>
	</div>
</div>


<script type="text/javascript">

	function onMark() {
		var csReturnId = ${csReturn.id};
		var status = ${csReturn.status}
		var content = $("#textarea_mark").val();
		if (content.length==0) {
			alert("请输入备注内容");
			return;
		}
		$.post('<@spring.url "/cs/return/mark/create" />',
	    	{"csReturnId": csReturnId,
	    	 "content": content},
	    	 function (data) {
	         	if (status==2) {
	    	 		$('#mark_container').prepend(data);
	            	$("#textarea_mark").val("");
	    	 	} else {
	    	 		location.href='<@spring.url "" />'+'/cs/return/'+csReturnId+'/info';
	    	 	}
	         });
	}
	
	function onCancel() {
		$.post('<@spring.url "/cs/return/${csReturn.id}/cancel" />',
	            {}, function (data) {
	            	location='<@spring.url "/cs/return/list" />';
	            });
	}
	
	function onFinish() {
		$.post('<@spring.url "/cs/return/${csReturn.id}/finish" />',
	            {}, function (data) {
	            	location='<@spring.url "/cs/return/list" />';
	            });
	}
	
	function onHandle() {
		$.post('<@spring.url "/cs/return/${csReturn.id}/handle" />',
	            {}, function (data) {
	            	location='<@spring.url "/cs/return/list" />';
	            });
	}
</script>
</@c.html>