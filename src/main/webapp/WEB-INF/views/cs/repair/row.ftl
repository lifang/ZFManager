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
			<a href="<@spring.url "/cs/repair/${csRepair.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onCancel(${csRepair.id});">取消</a>
			<a class="a_btn">添加付款记录</a>
			<a class="a_btn">修改维修价格</a>
		<#elseif csRepair.status=2>
			<a href="<@spring.url "/cs/repair/${csRepair.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onCancel(${csRepair.id});">取消</a>
       	<#elseif csRepair.status=3>
			<a href="<@spring.url "/cs/repair/${csRepair.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onFinish(${csRepair.id});">标记为维修完成</a>
		<#elseif csRepair.status=4>
			<a href="<@spring.url "/cs/repair/${csRepair.id}/info" />" class="a_btn">查看详情</a>
		<#elseif csRepair.status=5>
			<a href="<@spring.url "/cs/repair/${csRepair.id}/info" />" class="a_btn">查看详情</a>
       	</#if>
	</td>
</tr>