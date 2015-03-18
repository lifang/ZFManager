<#import "../../page.ftl" as pager>
<div class="uesr_table"> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table"> 
		<colgroup> 
			<col width="30"> 
			<col /> 
			<col /> 
			<col /> 
			<col /> 
			<col width="160" /> 
        </colgroup> 
		<thead> 
			<tr> 
				<th><input name="" type="checkbox" value="" /></th>
                <th>售后单号</th>
                <th>申请日期</th>
				<th>终端数量</th>
				<th>售后状态</th>
				<th>操作</th>
			</tr> 
		</thead> 
		<tbody> 
		<#if (csAgents.content)??>
			<#list csAgents.content as csAgent>
				<#include "row.ftl" />
			</#list>
		</#if>
		</tbody> 
	</table> 
</div>
<@pager.p page=csAgents.currentPage totalPages=csAgents.totalPage functionName="pageChange"/>	