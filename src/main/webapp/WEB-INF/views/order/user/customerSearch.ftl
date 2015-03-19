<#if customers??>
	<#list customers as customer>
		<a href="#">${customer.name!"没名字"}</a>
	</#list>
</#if>