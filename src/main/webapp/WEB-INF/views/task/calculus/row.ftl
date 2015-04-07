<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${integral.id!}" cs_status="${integral.status!}" cs_num="${integral.applyNum!}"/></td>
	<td>${integral.id!}</td>
	<td>${integral.name!}</td>
	<td>${integral.phone!}</td>
	<td>${integral.createdAt?datetime}</td>
	<td>
		<strong id="status_${integral.id}" class="strong_status">
		<#if integral.status=1>待处理
       	<#elseif integral.status=2>处理中
       	<#elseif integral.status=4>处理完成
		<#elseif integral.status=5>已取消
		</#if>
		</strong>
	</td>
	<td id="operation_${integral.id}">
		<#if integral.status=1>
			<a href="<@spring.url "/task/calculus/${integral.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onCancel(${integral.id});">取消</a>
			<a class="a_btn" onClick="onHandle(${integral.id});">标记为处理中</a>
		<#elseif integral.status=2>
			<a href="<@spring.url "/task/calculus/${integral.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onFinish(${integral.id});">标记为处理完成</a>
       	<#elseif integral.status=4>
			<a href="<@spring.url "/task/calculus/${integral.id}/info" />" class="a_btn">查看详情</a>
		<#elseif integral.status=5>
			<a href="<@spring.url "/task/calculus/${integral.id}/info" />" class="a_btn">查看详情</a>
       	</#if>
	</td>
</tr>