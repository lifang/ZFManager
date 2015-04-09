<#if (marks)??> <#list marks as one>
<div class="ur_item">
	<div class="ur_item_text">${one.content!}</div>
	<div class="ur_item_name">
		${one.name!} <em>${one.created_at!}</em>
	</div>
</div>
</#list> </#if>
