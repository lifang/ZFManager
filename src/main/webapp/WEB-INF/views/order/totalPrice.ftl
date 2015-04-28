<#if order??>
	${(order.totalPrice/100)?string("0.00")}
<#else>
	<#if type==2 || type==4>
		${(((good.leaseDeposit!0)+(payChannel.openingCost!0))*quantity/100)?string("0.00")}
	<#else>
		${(((good.retailPrice!0)+(payChannel.openingCost!0))*quantity/100)?string("0.00")}
	</#if>
</#if>