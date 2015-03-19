<tr>
	<td><input name="" type="checkbox" value="" /></td>
	<td>${csAgent.applyNum!}</td>
	<td>${csAgent.createdAt?datetime}</td>
	<td>${csAgent.terminalsQuantity!}</td>
	<td>
		<strong class="strong_status">
		<#if csAgent.status=0>待处理
       	<#elseif csAgent.status=1>处理中
       	<#elseif csAgent.status=2>已取消
		<#elseif csAgent.status=3>已完成
		</#if>
		</strong>
	</td>
	<td>
		<#if csAgent.status=0>
			<a href="<@spring.url "/cs/agent/${csAgent.id}/info" />" class="a_btn">查看详情</a>
			<a href="#" class="a_btn">取消</a>
		<#elseif csAgent.status=1>
			<a href="<@spring.url "/cs/agent/${csAgent.id}/info" />" class="a_btn">查看详情</a>
			<a href="#" class="a_btn">取消</a>
			<a href="#" class="a_btn">同步</a>
			<a href="#" class="a_btn">添加换货出库记录</a>
			<a href="#" class="a_btn">标记为处理完成</a>
       	<#elseif csAgent.status=2>
			<a href="<@spring.url "/cs/agent/${csAgent.id}/info" />" class="a_btn">查看详情</a>
		<#elseif csAgent.status=3>
			<a href="<@spring.url "/cs/agent/${csAgent.id}/info" />" class="a_btn">查看详情</a>
       	</#if>
	</td>
</tr>