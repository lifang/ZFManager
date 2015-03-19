<#import "../../page.ftl" as pager>
<div class="user_table">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
		<colgroup>
			<col width="30" />
			<col />
			<col />
			<col />
			<col />
			<col width="200" />
			<col />
			<col width="160" />
		</colgroup>
		<thead>
			<tr>
				<th><input name="" type="checkbox" value="" /></th>
				<th>编号</th>
				<th>申请日期</th>
				<th>处理人</th>
				<th>终端</th>
				<th>商户</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody> 
		<#if (csChanges.content)??>
			<#list csChanges.content as csChange>
				<#include "row.ftl" />
			</#list>
		</#if>
		</tbody> 
	</table>
</div>
<@pager.p page=csChanges.currentPage totalPages=csChanges.totalPage functionName="pageChange"/>	