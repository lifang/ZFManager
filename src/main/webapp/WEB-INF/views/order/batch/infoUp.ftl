<div class="detailPanel_status">
	<#if order.status==1>
    	<div class="payWarning">
    			<i class="no"></i>未付款
    	</div>
        <div class="dp_status_btn">
        	<a href="#" class="ghostBtn priceOrder_a" onclick="orderPriceBtn(${order.id},${(order.actualPrice/100)?string("0.00")});">修改订单价格</a>
        	<a href="#" class="ghostBtn priceEarnest_a" onclick="priceEarnestBtn(${order.id},<#if order.frontMoney??>${(order.frontMoney/100)?string("0.00")}<#else>0.00</#if>);">修改定金价格</a>
        	<a href="#" class="ghostBtn paymentRecord_a" onclick="payPriceBtn(${order.id},${(order.actualPrice/100)?string("0.00")});">增加付款记录</a>
            <a href="#" class="ghostBtn" onclick="cancel(${order.id});">取消</a>
        </div>
    <#elseif order.status==2 && (!order.payStatus?? || order.payStatus!=2)>
    	<div class="payWarning">
    			已付定金
    	</div>
        <div class="dp_status_btn">
        	<a href="#" class="ghostBtn deliver_a" onclick="deliverBtn(${order.id},${order.orderGoods?size});">发货</a>
            <a href="#" class="ghostBtn" onclick="cancel(${order.id});">取消</a>
        </div>
    <#elseif order.status==2 && (order.payStatus?? && order.payStatus==2)>
    	<div class="payWarning">
    			已付款
    	</div>
        <div class="dp_status_btn">
        	<a href="#" class="ghostBtn deliver_a" onclick="deliverBtn(${order.id},${order.orderGoods?size});">发货</a>
            <a href="#" class="ghostBtn" onclick="cancel(${order.id});">取消</a>
        </div>
	<#elseif order.status==3>
		<div class="payWarning">已完成</div>
	<#elseif order.status==4>
		<div class="payWarning">已评价</div>
	<#elseif order.status==5>
		<div class="payWarning">已取消</div>
	<#elseif order.status==6>
		<div class="payWarning">交易关闭</div>
	<#else>
		<div class="payWarning">状态不明</div>
	</#if>
</div>
<div class="detailPanel_info detailDl">
    <dl>
    	<dt>收货地址：</dt><dd><#if order.customerAddress??>${order.customerAddress.address!""} ${order.customerAddress.receiver!""}</#if></dd>
    </dl>
    <dl class="leaveWord">
    	<dt>留言：</dt><dd>${order.comment!""}</dd>
    </dl>
    <dl>
    	<dt>订单类型：</dt>
    	<dd>
			<#if order.types==1>用户订购
			<#elseif order.types==2>用户租赁
			<#elseif order.types==3>代理商代购
			<#elseif order.types==4>代理商代租赁
			<#elseif order.types==5>代理商批购
    		</#if>
    	</dd>
    	<dt>订单编号：</dt><dd>${order.orderNumber!""}</dd>
    </dl>
    <dl>
        <dt>购买日期：</dt><dd>${order.createdAt?datetime}</dd>
    </dl>
    <dl>
    	<dt>支付类型：</dt>
    	<dd>
    		<#if order.orderPayments??>
    			<#list order.orderPayments as orderPayment>
    				<#if orderPayment_index==0>
    					<#if orderPayment.payType==1>支付宝
    					<#elseif orderPayment.payType==2>银联
    					<#elseif orderPayment.payType==3>现金	
    					</#if>
    				</#if>
                </#list>
    		</#if>
    	</dd>
    </dl>
    <dl>
    	<dt>代理商：</dt>
    	<dd>
    		<#if order.agent??>${order.agent.companyName!""}</#if>
    	</dd>
    	<dt>代购用户：</dt><dd><#if order.customer??>${order.customer.name!"未知"}(${order.customer.phone!""})</#if></dd>
    </dl>
    <dl>
    	<dt>供货商：</dt>
    	<dd>
    		<#if order.factory??>
    			${order.factory.name!""}
    		<#else>
    			掌富
    		</#if>
    	</dd>
    </dl>
    <dl>
    	<dt>订单原金额：</dt><dd class="line_through">￥${(order.totalPrice/100)?string("0.00")}</dd><dt>订单金额：</dt><dd><strong>￥${(order.actualPrice/100)?string("0.00")}</strong></dd>
    </dl>
     <dl>
        <dt>定金金额：</dt><dd class="line_through">￥<#if order.frontMoney??>${(order.frontMoney/100)?string("0.00")}<#else>0.00</#if></dd><dt>已付金额：</dt><dd><strong>￥${(order.orderPaymentTotal/100)?string("0.00")}</strong></dd>
    </dl>
</div>