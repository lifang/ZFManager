<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${csAgent.id!}" cs_status="${csAgent.status!}" cs_num="${csAgent.applyNum!}"/></td>
	<td>${csAgent.applyNum!}</td>
	<td>${csAgent.createdAt?datetime}</td>
	<td>${csAgent.processUserName!}
	<td>${csAgent.terminalsQuantity!}</td>
	<td>
		<strong id="status_${csAgent.id}" class="strong_status">
		<#if csAgent.status=0>待处理
       	<#elseif csAgent.status=1>处理中
       	<#elseif csAgent.status=2>已取消
		<#elseif csAgent.status=3>已完成
		</#if>
		</strong>
	</td>
	<td id="operation_${csAgent.id}">
		<#if csAgent.status=0>
			<a href="<@spring.url "/cs/agent/${csAgent.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onCancel(${csAgent.id});">取消</a>
			<a class="a_btn" onClick="onHandle(${csAgent.id});">标记为处理中</a>
		<#elseif csAgent.status=1>
			<a href="<@spring.url "/cs/agent/${csAgent.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onCancel(${csAgent.id});">取消</a>
			<a href="#" class="a_btn">同步</a>
			<a class="a_btn exchangeGoods_a" onClick="onPreOutput(${csAgent.id});">添加换货出库记录</a>
			<a class="a_btn" onClick="onFinish(${csAgent.id});">标记为处理完成</a>
       	<#elseif csAgent.status=2>
			<a href="<@spring.url "/cs/agent/${csAgent.id}/info" />" class="a_btn">查看详情</a>
		<#elseif csAgent.status=3>
			<a href="<@spring.url "/cs/agent/${csAgent.id}/info" />" class="a_btn">查看详情</a>
       	</#if>
	</td>
</tr>

<div class="tab exchangeGoods_tab">
	<a href="#" class="close">关闭</a>
	<div class="tabHead">添加换货出库记录</div>
	<div class="tabBody">
		<textarea name="" cols="" rows="" class="textarea_pe"></textarea>
	</div>
	<div class="tabFoot">
		<button class="blueBtn" onClick="onOutput();">确定</button>
	</div>
</div>