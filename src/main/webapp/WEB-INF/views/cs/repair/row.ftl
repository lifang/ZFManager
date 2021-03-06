<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${csRepair.id!}" cs_status="${csRepair.status!}" cs_num="${csRepair.applyNum!}"/></td>
	<td>${csRepair.applyNum!}</td>
	<td>${csRepair.createdAt?datetime}</td>
	<td>${csRepair.processUserName!}</td>
	<td><#if csRepair.terminal??>${csRepair.terminal.serialNum!}</#if><br />
	<#if csRepair.good??>${csRepair.good.title!}</#if> 
	<#if csRepair.payChannel??>${csRepair.payChannel.name!}</#if> 
	</td>
	<td><#if csRepair.merchant??>${csRepair.merchant.title!}</#if><br />
	<#if csRepair.merchant??>${csRepair.merchant.phone!}</#if>
	</td>
	<td>
		<strong id="status_${csRepair.id}" class="strong_status">
		<#if csRepair.status=1>未付款
       	<#elseif csRepair.status=2>待发回
       	<#elseif csRepair.status=3>维修中
		<#elseif csRepair.status=4>处理完成
		<#elseif csRepair.status=5>已取消
		</#if>
		</strong>
	</td>
	<td id="operation_${csRepair.id}">
		<#if csRepair.status=1>
			<a href="<@spring.url "/cs/repair/${csRepair.id}/info" />" class="a_btn" target="_blank">查看详情</a>
			<#if Roles.hasRole("CS_REPAIR_CANCEL")><a class="a_btn" onClick="onCancel(${csRepair.id});">取消</a></#if>
			<#if Roles.hasRole("CS_REPAIR_ADD_PAY_RECORD")><a class="a_btn paymentRecord_a" onClick="onPreAddPay(${csRepair.id},${csRepair.repairPrice!0},${csRepair.payTypes!1});">添加付款记录</a></#if>
			<#if Roles.hasRole("CS_REPAIR_MODIFY_PRICE")><a class="a_btn priceOrder_a" onClick="onPreUpdatePay(${csRepair.id},${csRepair.repairPrice!0});">修改维修价格</a></#if>
		<#elseif csRepair.status=2>
			<a href="<@spring.url "/cs/repair/${csRepair.id}/info" />" class="a_btn" target="_blank">查看详情</a>
			<#if Roles.hasRole("CS_REPAIR_CANCEL")><a class="a_btn" onClick="onCancel(${csRepair.id});">取消</a></#if>
			<#if Roles.hasRole("CS_REPAIR_MARK_REPAIRING")><a class="a_btn" onClick="onHandle(${csRepair.id});">标记为维修中</a></#if>
       	<#elseif csRepair.status=3>
			<a href="<@spring.url "/cs/repair/${csRepair.id}/info" />" class="a_btn" target="_blank">查看详情</a>
			<#if Roles.hasRole("CS_REPAIR_MARK_FINISH")><a class="a_btn" onClick="onFinish(${csRepair.id});">标记为维修完成</a></#if>
		<#elseif csRepair.status=4>
			<a href="<@spring.url "/cs/repair/${csRepair.id}/info" />" class="a_btn" target="_blank">查看详情</a>
		<#elseif csRepair.status=5>
			<a href="<@spring.url "/cs/repair/${csRepair.id}/info" />" class="a_btn" target="_blank">查看详情</a>
       	</#if>
	</td>
</tr>