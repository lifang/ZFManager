<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${one.id!}" cs_status="${one.status!}" cs_num="${one.name!}"/></td>
	<td>${one.name!}</td>
	<td>${one.date!}</td>
	<td>${one.phone!}</td>
	<td><strong class="strong_status">
		   <#if one.status=1>待处理
	       <#elseif one.status=2>处理中
	       <#elseif one.status=3>处理完成
       	   </#if></strong></td> 
	<td><a href="<@spring.url "/task/intention/${one.id}/info" />" class="a_btn">查看详情</a> 
	<#if one.status=1><a onclick="ups(${one.id!},2,${intentions.currentPage!})" class="a_btn">标记为处理中</a>
   	<#elseif one.status=2><a onclick="ups(${one.id!},3,${intentions.currentPage!})" class="a_btn">标记为已处理</a></td>
  	</#if>
</tr>

