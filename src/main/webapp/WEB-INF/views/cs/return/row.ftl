<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${csReturn.id!}" cs_status="${csReturn.status!}" cs_num="${csReturn.applyNum!}"/></td>
	<td>${csReturn.applyNum!}</td>
	<td>${csReturn.createdAt?datetime}</td>
	<td>${csReturn.processUserName!}</td>
	<td><#if csReturn.terminal??>${csReturn.terminal.serialNum!}</#if><br />
	<#if csReturn.good??>${csReturn.good.title!}</#if> 
	<#if csReturn.payChannel??>${csReturn.payChannel.name!}</#if> 
	</td>
	<td><#if csReturn.merchant??>${csReturn.merchant.title!}</#if><br />
	<#if csReturn.merchant??>${csReturn.merchant.phone!}</#if>
	</td>
	<td>
		<strong id="status_${csReturn.id}" class="strong_status">
		<#if csReturn.status=1>待处理
       	<#elseif csReturn.status=2>退货中
       	<#elseif csReturn.status=4>处理完成
		<#elseif csReturn.status=5>已取消
		</#if>
		</strong>
	</td>
	<td id="operation_${csReturn.id}">
		<#if csReturn.status=1>
			<a href="<@spring.url "/cs/return/${csReturn.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn">确认退货</a>
			<a class="a_btn" onClick="onCancel(${csReturn.id});">取消</a>
			<a class="a_btn" onClick="onHandle(${csReturn.id});">标记为退货中</a>
		<#elseif csReturn.status=2>
			<a href="<@spring.url "/cs/return/${csReturn.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onCancel(${csReturn.id});">取消</a>
			<a class="a_btn" onClick="onFinish(${csReturn.id});">标记为退货完成</a>
       	<#elseif csReturn.status=4>
			<a href="<@spring.url "/cs/return/${csReturn.id}/info" />" class="a_btn">查看详情</a>
		<#elseif csReturn.status=5>
			<a href="<@spring.url "/cs/return/${csReturn.id}/info" />" class="a_btn">查看详情</a>
       	</#if>
	</td>
</tr>