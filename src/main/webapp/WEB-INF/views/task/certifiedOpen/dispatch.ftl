<select id="customer_select" name="" class="select_default">
	<#if customers??>
		<#list customers as customer>
			<option name="option_customer" customer_id="${customer.id!}">${customer.name!}</option>
		</#list>
	</#if>			
</select>