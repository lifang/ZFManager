<#import "../../page.ftl" as pager>
<#import "../assign.ftl" as assign />
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
				<th><input name="cb_all" type="checkbox" value="" /></th>
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
		<#if (csReturns.content)??>
			<#list csReturns.content as csReturn>
				<#include "row.ftl" />
			</#list>
		</#if>
		</tbody> 
	</table>
</div>
<@assign.assign name="return" page=csReturns.currentPage/>
<@pager.p page=csReturns.currentPage totalPages=csReturns.totalPage functionName="pageChange"/>	