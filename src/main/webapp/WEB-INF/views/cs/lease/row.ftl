<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${csLease.id!}" cs_status="${csLease.status!}" cs_num="${csLease.applyNum!}"/></td>
	<td>${csLease.applyNum!}</td>
	<td>${csLease.createdAt?datetime}</td>
	<td>${csLease.processUserName!}</td>
	<td><#if csLease.terminal??>${csLease.terminal.serialNum!}</#if><br />
	<#if csLease.good??>${csLease.good.title!}</#if> 
	<#if csLease.payChannel??>${csLease.payChannel.name!}</#if> 
	</td>
	<td><#if csLease.merchant??>${csLease.merchant.title!}</#if><br />
	<#if csLease.merchant??>${csLease.merchant.phone!}</#if>
	</td>
	<td>
		<strong id="status_${csLease.id}" class="strong_status">
		<#if csLease.status=1>待处理
       	<#elseif csLease.status=2>退还中
       	<#elseif csLease.status=4>处理完成
		<#elseif csLease.status=5>已取消
		</#if>
		</strong>
	</td>
	<td id="operation_${csLease.id}">
		<#if csLease.status=1>
			<a href="<@spring.url "/cs/lease/${csLease.id}/info" />" class="a_btn" target="_blank">查看详情</a>
			<#if Roles.hasRole("CS_LEASE_RETURN_CONFIRM")><a class="a_btn replace_a" onClick="onPreConfirm(${csLease.id});">确认退还</a></#if>
			<#if Roles.hasRole("CS_LEASE_RETURN_CANCEL")><a class="a_btn" onClick="onCancel(${csLease.id});">取消</a></#if>
			<#if Roles.hasRole("CS_LEASE_RETURN_MARK_RETURNING")><a class="a_btn" onClick="onHandle(${csLease.id});">标记为退还中</a></#if>
		<#elseif csLease.status=2>
			<a href="<@spring.url "/cs/lease/${csLease.id}/info" />" class="a_btn" target="_blank">查看详情</a>
			<a href="javascript:void(0);" onClick="synize(${csLease.terminalId});" class="a_btn">同步终端状态</a>
			<a href="javascript:void(0);" class="a_btn" onClick="createReturn(${csLease.id})">生成退款单</a>
			<#if Roles.hasRole("CS_LEASE_RETURN_MARK_FINISH")><a class="a_btn" onClick="onFinish(${csLease.id});">标记为退还完成</a></#if>
       	<#elseif csLease.status=4>
			<a href="<@spring.url "/cs/lease/${csLease.id}/info" />" class="a_btn" target="_blank">查看详情</a>
		<#elseif csLease.status=5>
			<a href="<@spring.url "/cs/lease/${csLease.id}/info" />" class="a_btn" target="_blank">查看详情</a>
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