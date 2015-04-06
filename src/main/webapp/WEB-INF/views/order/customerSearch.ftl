<#if customers??>
	<#list customers as customer>
		<a href="#" onclick="queryAddress(${customer.id!""});" id="customer_${customer.id!""}" name="customerName">${customer.name!"没名字"}</a>
	</#list>
</#if>