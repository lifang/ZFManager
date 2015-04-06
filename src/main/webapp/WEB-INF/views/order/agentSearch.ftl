<#if agents??>
	<#list agents as agent>
		<a href="#" onclick="agentSelected(${agent.customerId!""});" id="agentCustomer_${agent.customerId!""}" name="agentCompanyName">${agent.companyName!""}</a>
	</#list>
</#if>