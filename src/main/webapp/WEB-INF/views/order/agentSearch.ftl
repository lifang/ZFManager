<#if agents??>
	<#list agents as agent>
		<a href="#" onclick="agentSelected(${agent.customerId!""},${agent.id!""});" id="agentCustomer_${agent.customerId!""}" name="agentCompanyName">${agent.companyName!""}</a>
	</#list>
</#if>