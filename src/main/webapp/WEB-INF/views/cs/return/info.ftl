<#import "../../common.ftl" as c />
<#import "../material.ftl" as material />
<@c.html>
<div class="breadcrumb">
	<ul>
		<li>售后</li>
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
			<a class="ghostBtn replace_a">确认退货</a>
			<a class="ghostBtn" onClick="onHandle();">标记为退货中</a>
		</div>
		<#elseif csReturn.status=2>
		<div class="userTopBtnBox">
			<a class="ghostBtn" onClick="onCancel();">取消申请</a> 
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
		<h2>备注</h2>
		<div id="mark_container">
		<#list csReturnMarks as mark>
			<#include "../mark.ftl" />
		</#list>
		<div>
	</div>
</div>

<div class="tab replace_tab">
	<a href="" class="close">关闭</a>
	<div class="tabHead">退换地址电话</div>
	<div class="tabBody">
		<div class="item_list">
			<ul>
				<li>
					<span class="labelSpan">收件人：</span>
					<div class="text"><input name="receiver" type="text" /></div>
				</li>
				<li>
					<span class="labelSpan">电话：</span>
					<div class="text"><input name="phone" type="text" /></div>
				</li>
				<li>
					<span class="labelSpan">邮编：</span>
					<div class="text"><input name="zipCode" type="text" /></div>
				</li>
				<li>
					<span class="labelSpan">地址：</span>
					<div class="text"><input name="address" type="text" /></div>
				</li>
			</ul>
		</div>
	</div>
	<div class="tabFoot">
		<button class="blueBtn" onClick="onConfirm();">确定</button>
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
		$.post('<@spring.url "/cs/return/${csReturn.id}/mark/create" />',
	    	{"content": content},
	    	 function (data) {
	    	 	$('#mark_container').prepend(data);
	            $("#textarea_mark").val("");
	         });
	}
	
	function onCancel() {
		$.post('<@spring.url "/cs/return/${csReturn.id}/cancel" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
	function onFinish() {
		$.post('<@spring.url "/cs/return/${csReturn.id}/finish" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
	function onHandle() {
		$.post('<@spring.url "/cs/return/${csReturn.id}/handle" />',
	            {}, function (data) {
	            	location.reload();
	            });
	}
	
	function onConfirm() {
		var receiver = $("input[name='receiver']").val();
		var phone = $("input[name='phone']").val();
		var zipCode = $("input[name='zipCode']").val();
		var address = $("input[name='address']").val();
		
		$.post('<@spring.url "/cs/return/${csReturn.id}/confirm" />',
			{'receiver':receiver, 
			 'phone':phone,
			 'zipCode':zipCode,
			 'address':address
			 }, function(data) {
			 	location.reload();
			 });
	}
	
</script>
</@c.html>