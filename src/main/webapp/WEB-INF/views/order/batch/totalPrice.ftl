<#if order??>
	${(order.totalPrice/100)?string("0.00")}
<#else>
	${(((good.purchasePrice!0)+(payChannel.openingCost!0))*quantity/100)?string("0.00")}
</#if>