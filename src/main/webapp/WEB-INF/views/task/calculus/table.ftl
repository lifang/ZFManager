<#import "../../page.ftl" as pager>
<div class="user_table">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
		<colgroup>
			<col width="30" />
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
				<th>用户姓名</th>
				<th>用户电话</th>
				<th>申请日期</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody> 
		<#if (integral.content)??>
			<#list integral.content as integral>
				<#include "row.ftl" />
			</#list>
		</#if>
		</tbody> 
	</table>
</div>
<@pager.p page=integral.currentPage totalPages=integral.totalPage functionName="pageChange"/>	