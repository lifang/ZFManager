<tr>
	<td><input name="re_row" cs_id="${refu.id!}" cs_status="${refu.status!}" cs_num="${refu.applyNum!}" type="checkbox" value="" /></td>
	<td>${(refu.applyNum)!''}</td>
	<td>${(refu.createdAt)!''}</td>
	<td>${(refu.returnApplyNum)!''}</td>
	<td><strong class="strong_status">
	<#if refu.status == 1>
	待处理
	<#elseif refu.status == 2>
	处理完成
	<#elseif refu.status == 3>
	已取消
	</#if>
	</strong>
	</td>
	<td>
	<#if refu.status == 1>
		<a href="<@spring.url '/task/refund/refundeDetails/${refu.id}' />" class="a_btn">查看详情</a>
		<#if Roles.hasRole("REFUND_CANCEL")><a href="<@spring.url '/task/refund/${refu.id}/updsateRefundDeStatus' />" class="a_btn">取消</a></#if>
		<#if Roles.hasRole("REFUND_MARK_FINISH")><a href="<@spring.url '/task/refund/${refu.id}/updsateListRefundStatus' />" class="a_btn">标记为退款完成</a></#if>
	<#elseif refu.status == 2>
		<a href="<@spring.url '/task/refund/refundeDetails/${refu.id}' />" class="a_btn">查看详情</a>
		<#if Roles.hasRole("REFUND_CANCEL")><a href="<@spring.url '/task/refund/${refu.id}/updsateRefundDeStatus' />" class="a_btn">取消</a></#if>
	<#elseif refu.status == 3>
		<a href="<@spring.url '/task/refund/refundeDetails/${refu.id}' />" class="a_btn">查看详情</a>
	</#if>
	</td>
</tr>