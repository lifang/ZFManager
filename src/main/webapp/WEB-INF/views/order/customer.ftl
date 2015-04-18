<input id="customerId" type="hidden" name="customerId" value="<#if customer??>${customer.id!""}</#if>" />
<input id="agentCustomerId" type="hidden" name="agentCustomerId" value="" />
<#if customer??>
	<a href="#" class="hover">${customer.username!""}</a>
</#if>