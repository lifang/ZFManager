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
		<#if csUpdate.status=1>待处理
       	<#elseif csUpdate.status=2>处理中
       	<#elseif csUpdate.status=4>处理完成
		<#elseif csUpdate.status=5>已取消
		</#if>
		</strong>
	</td>
	<td id="operation_${csUpdate.id}">
		<#if csUpdate.status=1>
			<a href="<@spring.url "/cs/update/${csUpdate.id}/info" />" class="a_btn" target="_blank">查看详情</a>
			<#if Roles.hasRole("CS_UPDATE_INFO_CANCEL")><a class="a_btn" onClick="onCancel(${csUpdate.id});">取消</a></#if>
			<#if Roles.hasRole("CS_UPDATE_INFO_PROCESSING")><a class="a_btn" onClick="onHandle(${csUpdate.id});">标记为处理中</a></#if>
		<#elseif csUpdate.status=2>
			<a href="<@spring.url "/cs/update/${csUpdate.id}/info" />" class="a_btn" target="_blank">查看详情</a>
			<a href="javascript:void(0);" onClick="synize(${csUpdate.terminalId});" class="a_btn">同步终端状态</a>
			<#if Roles.hasRole("CS_UPDATE_INFO_FINISH")><a class="a_btn" onClick="onFinish(${csUpdate.id});">标记为处理完成</a></#if>
       	<#elseif csUpdate.status=4>
			<a href="<@spring.url "/cs/update/${csUpdate.id}/info" />" class="a_btn" target="_blank">查看详情</a>
		<#elseif csUpdate.status=5>
			<a href="<@spring.url "/cs/update/${csUpdate.id}/info" />" class="a_btn" target="_blank">查看详情</a>
       	</#if>
	</td>
</tr>
<script>
	function synize(temp){
		$.post('<@spring.url "/terminal/syncStatus" />',
			{"terminalId":temp},
			function(data){
			var data = eval("("+data+")");
				if(data.code==1){
					alert("同步成功");
					location.reload();
				}else{
					alert(data.message);
					return false;
				}
			});
	}
</script>