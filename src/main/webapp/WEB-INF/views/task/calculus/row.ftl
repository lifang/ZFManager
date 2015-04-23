<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${integral.id!}" cs_status="${integral.status!}" cs_num="${integral.applyNum!}"/></td>
	<td>${integral.applyNum!}</td>
	<td>${integral.name!}</td>
	<td>${integral.phone!}</td>
	<td>${integral.createdAt?datetime}</td>
	<td>
		<strong id="status_${integral.id}" class="strong_status">
		<#if integral.status=1>待处理
       	<#elseif integral.status=2>处理完成
       	<#elseif integral.status=3>已取消
		</#if>
		</strong>
	</td>
	<td id="operation_${integral.id}">
		<#if integral.status=1>
			<a href="<@spring.url "/task/calculus/${integral.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onCancel(${integral.id});">取消</a>
			<a class="a_btn" onClick="onFinish(${integral.id});">标记完成</a>
			<a class="a_btn" onClick="onbeizhu(${integral.id});">备注</a>
		<#elseif integral.status=2>
			<a href="<@spring.url "/task/calculus/${integral.id}/info" />" class="a_btn">查看详情</a>
				<a class="a_btn" onClick="onbeizhu(${integral.id});">备注</a>
       	<#elseif integral.status=3>
			<a href="<@spring.url "/task/calculus/${integral.id}/info" />" class="a_btn">查看详情</a>
			<a class="a_btn" onClick="onbeizhu(${integral.id});">备注</a>
	       	</#if>
	</td>
</tr>