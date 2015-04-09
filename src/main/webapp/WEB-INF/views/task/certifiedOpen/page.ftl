<#import "../../page.ftl" as pager>
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
				<th><input name="" type="checkbox" value="" /></th>
				<th>终端号</th>
				<th>申请日期</th>
				<th>状态</th>
				<th>视频认证状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<#if (apply.content)??> <#list apply.content as one> <#include "pageRow.ftl" /> </#list> </#if>
		</tbody>
	</table>
</div>

<@pager.p page=apply.currentPage totalPages=apply.totalPage functionName="certifiedOpenPageChange"/>
