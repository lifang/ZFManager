<#if order??>
	${(order.totalPrice/100)?string("0.00")}
<#else>
	${(good.price*quantity/100)?string("0.00")}
</#if>