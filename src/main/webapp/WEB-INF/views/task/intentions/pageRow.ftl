<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${one.id!}" cs_status="${one.status!}" cs_num="${one.name!}"/></td>
	<#if one.checktype=1>
		<td>购买pos机</td>
	</#if>  
	<#if one.checktype=2>
		<td>申请代理商: ${one.type!}</td>
	</#if>  
	<td>${one.name!}</td>
	<td>${one.date!}</td>
	<td>${one.phone!}</td>
	<td><strong class="strong_status">
		   <#if one.status=1>待处理
	       <#elseif one.status=2>处理中
	       <#elseif one.status=3>处理完成
       	   </#if></strong></td> 
	<td>
	<#if one.checktype=1>
		<a href='<@spring.url "/task/intention/${one.id}/info" />' class="a_btn" id="_details">查看详情</a>
	</#if>
	<#if one.checktype=2>
		<a href='<@spring.url "/task/agentjoin/${one.id}/getInfo" />' class="a_btn" id="_details">查看详情</a>
	</#if>
	<#if one.status=1>
		<#if Roles.hasRole("INTENTION_MARK_PROCESSING")><a onclick="ups(${one.id!},2,${intentions.currentPage!})" class="a_btn">标记为处理中</a></#if>
   	<#elseif one.status=2>
		<#if Roles.hasRole("INTENTION_MARK_FINISH")><a onclick="ups(${one.id!},3,${intentions.currentPage!})" class="a_btn">标记为已处理</a></#if>
  	</#if>
    </td>
</tr>


