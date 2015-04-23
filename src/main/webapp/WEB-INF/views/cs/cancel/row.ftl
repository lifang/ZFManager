<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${csCancel.id!}" cs_status="${csCancel.status!}" cs_num="${csCancel.applyNum!}"/></td>
	<td>${csCancel.applyNum!}</td>
	<td>${csCancel.createdAt?datetime}</td>
	<td>${csCancel.processUserName!}</td>
	<td><#if csCancel.terminal??>${csCancel.terminal.serialNum!}</#if><br />
	<#if csCancel.good??>${csCancel.good.title!}</#if> 
	<#if csCancel.payChannel??>${csCancel.payChannel.name!}</#if> 
	</td>
	<td><#if csCancel.merchant??>${csCancel.merchant.title!}</#if><br />
	<#if csCancel.merchant??>${csCancel.merchant.phone!}</#if>
	</td>
	<td>
		<strong id="status_${csCancel.id}" class="strong_status">
		<#if csCancel.status=1>待处理
       	<#elseif csCancel.status=2>处理中
       	<#elseif csCancel.status=4>处理完成
		<#elseif csCancel.status=5>已取消
		</#if>
		</strong>
	</td>
	<td id="operation_${csCancel.id}">
		<#if csCancel.status=1>
			<a href="<@spring.url "/cs/cancel/${csCancel.id}/info" />" class="a_btn">查看详情</a>
			<#if Roles.hasRole("CS_CANCEL_CANCEL")><a class="a_btn" onClick="onCancel(${csCancel.id});">取消</a></#if>
			<#if Roles.hasRole("CS_CANCEL_CANCELING")><a class="a_btn" onClick="onHandle(${csCancel.id});">标记为处理中</a></#if>
		<#elseif csCancel.status=2>
			<a href="<@spring.url "/cs/cancel/${csCancel.id}/info" />" class="a_btn">查看详情</a>
			<#if Roles.hasRole("CS_CANCEL_MARK_FINISH")><a class="a_btn" onClick="onFinish(${csCancel.id});">标记为处理完成</a></#if>
       	<#elseif csCancel.status=4>
			<a href="<@spring.url "/cs/cancel/${csCancel.id}/info" />" class="a_btn">查看详情</a>
		<#elseif csCancel.status=5>
			<a href="<@spring.url "/cs/cancel/${csCancel.id}/info" />" class="a_btn">查看详情</a>
			<#if Roles.hasRole("CS_CANCEL_RE_SUBMIT")><a class="a_btn" onClick="onResubmit(${csCancel.id});">重新提交</a></#if>
       	</#if>
	</td>
</tr>