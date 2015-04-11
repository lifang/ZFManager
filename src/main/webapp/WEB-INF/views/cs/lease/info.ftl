<#import "../../common.ftl" as c />
<#import "../material.ftl" as material />
<@c.html>
<div class="breadcrumb">
	<ul>
		<li><a href="#">售后</a></li>
		<li><a href="<@spring.url "/cs/lease/list"/>">租赁退还</a></li>
		<li><a href="<@spring.url "/cs/update/${csLease.id}/info"/>">详情</a></li>
	</ul>
</div>
<div class="content clear">
	<div class="user_title">
		<h1>租赁退还申请记录</h1>
		<div class="userTopBtnBox">
			<#if csLease.status=1>
			<div class="userTopBtnBox">
				<a class="ghostBtn" onClick="onCancel();">取消申请</a> 
				<a class="ghostBtn" onClick="onHandle();">标记为退还中</a>
			</div>
			<#elseif csLease.status=2>
			<div class="userTopBtnBox">
				<a class="ghostBtn" onClick="onCancel();">取消申请</a> 
				<a class="ghostBtn" onClick="onFinish();">标记为处理完成</a>
			</div>
			</#if>
		</div>
	</div>
	<div class="attributes_box">
		<h2>记录信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>编号：${csLease.applyNum!}</li>
				<li>处理人：${csLease.processUserName!}</li>
				<li>状态：<span class="orangeText">
					<#if csLease.status=1>待处理
       				<#elseif csLease.status=2>退还中
       				<#elseif csLease.status=4>处理完成
					<#elseif csLease.status=5>已取消
					</#if>
				</span></li>
				<li>申请日期：${csLease.createdAt?datetime}</li>
			</ul>
		</div>
	</div>
	<div class="attributes_box">
		<h2>终端信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>品牌型号：<#if csLease.good??>${csLease.good.title!}</#if></li>
				<li>支付通道：<#if csLease.payChannel??>${csLease.payChannel.name!}</#if></li>
				<li>终端号：<#if csLease.terminal??>${csLease.terminal.serialNum!}</#if></li>
				<li>商户名称：<#if csLease.merchant??>${csLease.merchant.title!}</#if></li>
				<li>商户电话：<#if csLease.merchant??>${csLease.merchant.phone!}</#if></li>
				<li>租赁日期：<#if leaseStart??>${leaseStart?datetime}</#if></li>
				<li>最短租赁时间：${minLeaseMonth!}个月</li>
				<li>最长租赁时间：${maxLeaseMonth!}个月</li>
				<li>租赁押金：<strong>￥${deposit!}</strong></li>
				<li>租赁时长：${daysApart!}天</li>
				<li>租金：<strong>￥${rentTotal!}</strong></li>
			</ul>
		</div>
	</div>

	<div class="attributes_box">
		<h2>退还信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>退款金额：${refundAmount!}</li>
				<li>联系人：<#if csLease.csReceiverAddress??>${csLease.csReceiverAddress.receiver!}</#if></li>
				<li>联系电话：<#if csLease.csReceiverAddress??>${csLease.csReceiverAddress.phone!}</#if></li>
			</ul>
		</div>
	</div>

	<div class="attributes_box">
		<h2>交易流水统计</h2>
		<div class="attributes_table">
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<colgroup>
					<col width="80" />
					<col />
					<col />
				</colgroup>
				<thead>
					<tr>
						<th>序号</th>
						<th>起止日期</th>
						<th>月交易汇总</th>
					</tr>
				</thead>
				<#if statistics??>
				<#list statistics as statistic>
					<tr>
						<td>${statistic_index + 1}</td>
						<td>${statistic.start?string("yyyy/MM/dd")} - ${statistic.end?string("yyyy/MM/dd")}</td>
						<td><strong>￥${statistic.amount!}</strong></td>
					</tr>
				</#list>
				</#if>
			</table>
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
		<#list csLeaseMarks as mark>
			<#include "../mark.ftl" />
		</#list>
		<div>
	</div>
</div>


<script type="text/javascript">

	function onMark() {
		var csLeaseId = ${csLease.id};
		var status = ${csLease.status}
		var content = $("#textarea_mark").val();
		if (content.length==0) {
			alert("请输入备注内容");
			return;
		}
		$.post('<@spring.url "/cs/lease/${csLease.id}/mark/create" />',
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
		$.post('<@spring.url "/cs/lease/${csLease.id}/cancel" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
	function onFinish() {
		$.post('<@spring.url "/cs/lease/${csLease.id}/finish" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
	function onHandle() {
		$.post('<@spring.url "/cs/lease/${csLease.id}/handle" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
</script>
</@c.html>