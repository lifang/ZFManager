<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${csUpdate.id!}" cs_status="${csUpdate.status!}" cs_num="${csUpdate.applyNum!}"/></td>
	<td>${csUpdate.applyNum!}</td>
	<td>${csUpdate.createdAt?datetime}</td>
	<td>${csUpdate.processUserName!}</td>
	<td><#if csUpdate.terminal??>${csUpdate.terminal.serialNum!}</#if><br />
	<#if csUpdate.good??>${csUpdate.good.title!}</#if> 
	<#if csUpdate.payChannel??>${csUpdate.payChannel.name!}</#if> 
	</td>
	<td><#if csUpdate.merchant??>${csUpdate.merchant.title!}</#if><br />
	<#if csUpdate.merchant??>${csUpdate.merchant.phone!}</#if>
	</td>
	<td>
		<strong id="status_${csUpdate.id}" class="strong_status">
		<#if csUpdate.status=0>待处理
       	<#elseif csUpdate.status=1>处理中
       	<#elseif csUpdate.status=2>处理完成
		<#elseif csUpdate.status=3>已取消
		</#if>
		</strong>
	</td>
	<td id="operation_${csUpdate.id}">
		<#if csUpdate.status=0>
			<a href="<@spring.url "/cs/update/${csUpdate.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onCancel(${csUpdate.id});">取消</a>
			<a class="a_btn" onClick="onHandle(${csUpdate.id});">标记为处理中</a>
		<#elseif csUpdate.status=1>
			<a href="<@spring.url "/cs/update/${csUpdate.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onFinish(${csUpdate.id});">标记为处理完成</a>
       	<#elseif csUpdate.status=2>
			<a href="<@spring.url "/cs/update/${csUpdate.id}/info" />" class="a_btn">查看详情</a>
		<#elseif csUpdate.status=3>
			<a href="<@spring.url "/cs/update/${csUpdate.id}/info" />" class="a_btn">查看详情</a>
       	</#if>
	</td>
</tr>