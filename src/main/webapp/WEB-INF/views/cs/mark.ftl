<div id="mark_container" class="ur_item">
	<div class="ur_item_text">${mark.content!}</div>
	<div class="ur_item_name">
		<#if mark.customer??>${mark.customer.name!}</#if>
		<em>${mark.createdAt?datetime}</em>
	</div>
</div>