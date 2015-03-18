<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
	<ul>
		<li><a href="#">售后</a></li>
		<li><a href="#">代理商售后</a></li>
		<li><a href="#">详情</a></li>
	</ul>
</div>
<div class="content clear">
	<div class="user_title">
		<h1>代理商售后申请记录</h1>
		<div class="userTopBtnBox">
			<a href="#" class="ghostBtn">取消申请</a>
		</div>
	</div>
	<div class="attributes_box">
		<h2>记录信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>编号：${csAgent.applyNum}</li>
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
		<textarea name="" cols="" rows=""></textarea>
		<button class="whiteBtn">备注</button>
	</div>
	<div class="user_record">
		<h2>追踪记录</h2>
		<#list csAgentMarks as csAgentMark>
			<div class="ur_item">
				<div class="ur_item_text">${csAgentMark.content!}</div>
				<div class="ur_item_name">
					<#if csAgentMark.customer??>${csAgentMark.customer.name!}</#if>
					<em>${csAgentMark.createdAt?datetime}</em>
				</div>
			</div>
		</#list>
	</div>
</div>

<script type="text/javascript">

</script>
</@c.html>