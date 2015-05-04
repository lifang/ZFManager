<#import "../../page.ftl" as pager> 
<#import "assign.ftl" as assign />
<div class="user_table">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
		<colgroup>
			<col width="30" />
			<col />
			<col />
			<col />
			<col />
			<col width="160" />
		</colgroup>
		<thead>
			<tr>
				<th><input name="cb_all" type="checkbox" value="" /></th>
				<th>意向类型</th>
				<th>意向人</th>
				<th>申请日期</th>
				<th>电话</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<tbody><#if (intentions.content)??> <#list intentions.content as one> <#include "pageRow.ftl" /> </#list> </#if>
		</tbody>
	</table> 
</div>  
<@assign.assign name="intention" page=intentions.currentPage suspend=1/>
<@pager.p page=intentions.currentPage totalPages=intentions.totalPage functionName="intentionPageChange"/> 

