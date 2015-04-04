<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
	<ul>
		<li><a href="#">售后</a></li>
		<li><a href="<@spring.url "/cs/repair/list"/>">维修</a></li>
		<li><a href="<@spring.url "/cs/repair/${csRepair.id}/info"/>">详情</a></li>
	</ul>
</div>
<div class="content clear">
	<div class="user_title">
		<h1>维修申请记录</h1>
		<#if (csRepair.status=1) || (csRepair.status=2)>
		<div class="userTopBtnBox">
			<a class="ghostBtn" onClick="onCancel();">取消申请</a> 
			<a href="#" class="ghostBtn">修改维修价格</a>
			<a href="#" class="ghostBtn">添加付款记录</a>
		</div>
		</#if>
	</div>
	<div class="attributes_box">
		<h2>记录信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>编号：${csRepair.applyNum!}</li>
				<li>处理人：${csRepair.processUserName!}</li>
				<li>状态：<span class="orangeText">
					<#if csRepair.status=1>未付款
					<#elseif csRepair.status=2>待发回
       				<#elseif csRepair.status=3>维修中
					<#elseif csRepair.status=4>处理完成
					<#elseif csRepair.status=5>已取消
					</#if>
				</span></li>
				<li>申请日期：${csRepair.createdAt?datetime}</li>
			</ul>
		</div>
	</div>
	<div class="attributes_box">
		<h2>终端信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>品牌型号：<#if csRepair.good??>${csRepair.good.title!}</#if></li>
				<li>支付通道：<#if csRepair.payChannel??>${csRepair.payChannel.name!}</#if></li>
				<li>终端号：<#if csRepair.terminal??>${csRepair.terminal.serialNum!}</#if></li>
				<li>商户名称：<#if csRepair.merchant??>${csRepair.merchant.title!}</#if></li>
				<li>商户电话：<#if csRepair.merchant??>${csRepair.merchant.phone!}</#if></li>
			</ul>
		</div>
	</div>

	<div class="attributes_box">
		<h2>维修信息</h2>
		<div class="attributes_list clear">
			<ul>
				<li><em>终端号：</em><span><#if csRepair.terminal??>${csRepair.terminal.serialNum!}</#if></span></li>
				<li><em>收货地址：</em><span><#if csRepair.csReceiverAddress??>${csRepair.csReceiverAddress.address!}</#if></span></li>
				<li><em>维修费用：</em><span><strong>￥${csRepair.repairPrice!}</strong></span></li>
				<li><em>故障描述：</em><span>${csRepair.description!}</span></li>
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
		<#list csRepairMarks as mark>
			<#include "../mark.ftl" />
		</#list>
		<div>
	</div>
</div>


<script type="text/javascript">

	function onMark() {
		var csRepairId = ${csRepair.id};
		var status = ${csRepair.status}
		var content = $("#textarea_mark").val();
		if (content.length==0) {
			alert("请输入备注内容");
			return;
		}
		$.post('<@spring.url "/cs/repair/mark/create" />',
	    	{"csRepairId": csRepairId,
	    	 "content": content},
	    	 function (data) {
	         	if (status==3) {
	    	 		$('#mark_container').prepend(data);
	            	$("#textarea_mark").val("");
	    	 	} else {
	    	 		location.href='<@spring.url "" />'+'/cs/repair/'+csRepairId+'/info';
	    	 	}
	         });
	}
	
	function onCancel() {
		$.post('<@spring.url "/cs/repair/${csRepair.id}/cancel" />',
	            {}, function (data) {
	            	location='<@spring.url "/cs/repair/list" />';
	            });
	}
</script>
</@c.html>