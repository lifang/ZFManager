<input id="customerId" type="hidden" name="customerId" value="<#if customer??>${customer.id!""}</#if>" />
<#if customer??>
	<a href="#" class="hover">${customer.username!""}</a>
</#if>