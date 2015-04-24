<tbody id="row_${order.id}">
      <tr class="order_hd">
      	<input id="hidden_belongsTo_${order.id}" type="hidden" value="${order.belongsTo!0}" />
        <td colspan="7"><span>订单号 ${order.orderNumber!""}</span><span><#if order.createdAt??>${order.createdAt?datetime}</#if></span>
        	<span>类型：<#if order.types??>
				      	<#if order.types==1>用户订购
					       <#elseif order.types==2>用户租赁 
					       <#elseif order.types==3>代理商代购
					       <#elseif order.types==4>代理商代租赁
					       <#elseif order.types==5>代理商批购
					     </#if>
					</#if>
			</span>
			<span>代理商：<#if order.agent??>${order.agent.companyName!""}</#if></span><span>电话：<#if order.agent??>${order.agent.phone!""}</#if></span></td>
      </tr>
      
      <#if order.orderGoods??>
    	<#list order.orderGoods as orderGood>
          <tr>
            <td>
            	<div class="td_proBox clear">
            		<#if orderGood.good??>
	                     <a href="<@spring.url "/good/agent/${orderGood.good.id}/detail" />" class="cn_img">
	                    	<#if orderGood.good??>
	                    		<#if orderGood.good.pictures??>
	                    			<#list orderGood.good.pictures as picture>
	                    				<#if picture_index==0>
	                    					<!--<img src="<@spring.url "${picture.urlPath}"/>" />-->
	                    					<img src="${picture.urlPath}" style="width:130px;height:130px;" />
	                    				</#if>
	                    			</#list>
	                    		</#if>
	                    	</#if>
	                    </a>
                    </#if>
                    
                     <div class="td_proBox_info">
                        <h1><#if orderGood.good??><a href="<@spring.url "/good/agent/${orderGood.good.id}/detail" />">${orderGood.good.title!""}</a></#if></h1>
                        <h3><#if orderGood.good??>${orderGood.good.secondTitle!""}</#if></h3>
                        <ul>
                            <li><span>品牌型号：</span><div class="c_text"><#if orderGood.goodBrand??>${orderGood.goodBrand.name!""}</#if></div></li>
                            <li><span>支付通道：</span><div class="c_text"><#if orderGood.payChannel??>${orderGood.payChannel.name!""}</#if></div></li>
                        </ul>
                    </div>
                </div>
            </td>
            <td><strong>￥${(((orderGood.good.retailPrice!0)+(orderGood.payChannel.openingCost!0))/100)?string("0.00")}</strong></td>
            <td>${orderGood.quantity!0}</td>
            <input id="hidden_good_title_${order.id}_${orderGood_index}" type="hidden" value="<#if orderGood.good??>${orderGood.good.title!""}</#if>" />
		    <input id="hidden_quantity_${order.id}_${orderGood_index}" type="hidden" value="${orderGood.quantity!0}" />
            <!-- <td><strong>￥918.00</strong></td>
            <td>三峡格格</td>
            <td><strong class="strong_status">未付款</strong></td>
            <td><a href="#" class="a_btn priceOrder_a">修改价格</a>
            <a href="#" class="a_btn">取消</a><a href="#" class="a_btn paymentRecord_a">增加付款记录</a>
            <a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn remark_a">备注</a></td>
            -->
            <#if (order.orderGoods?size>1)& orderGood_index==0>
                <td rowspan="${order.orderGoods?size}" class="left_border"><strong>￥${(order.actualPrice/100)?string("0.00")} </strong></td>
                <td rowspan="${order.orderGoods?size}"><#if order.customer??>${order.customer.name!"未知"}</#if></td>
          		<#if order.status??>
			      	<#if order.status==1><td rowspan="${order.orderGoods?size}"><strong class="strong_status">未付款</strong></td>
			      			<td rowspan="${order.orderGoods?size}">
						<#if Roles.hasRole("AGENT_ORDER_RPRICE")><a href="#" class="a_btn priceOrder_a" onclick="orderPriceBtn(${order.id},${(order.actualPrice/100)?string("0.00")});">修改价格</a></#if>
						<#if Roles.hasRole("AGENT_ORDER_CANCEL")><a href="#" class="a_btn" onclick="cancel(${order.id});">取消</a></#if>
						<#if Roles.hasRole("AGENT_ORDER_ADD_PAY_RECORD")><a href="#" class="a_btn paymentRecord_a" onclick="payPriceBtn(${order.id},${(order.actualPrice/100)?string("0.00")});">增加付款记录</a></#if>
	                    		<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
						<#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></td></#if>
				       <#elseif order.status==2><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已付款</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
						   <#if Roles.hasRole("AGENT_ORDER_CANCEL")><a href="#" class="a_btn" onclick="cancel(${order.id});">取消</a></#if>
						   <#if Roles.hasRole("AGENT_ORDER_OUT_STORAGE")><a href="#" class="a_btn deliver_a" onclick="deliverBtn(${order.id},${order.orderGoods?size});">发货</a></#if>
						   <#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></td></#if>
				       <#elseif order.status==3><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已发货</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
						<#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></#if>
						<#if Roles.hasRole("AGENT_ORDER_AGAIN")><a href="<@spring.url "/order/agent/${order.id}/createAgain" />" class="a_btn">再次代购</a></td></#if>
				       <#elseif order.status==4><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已评价</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
						<#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></#if>
						<#if Roles.hasRole("AGENT_ORDER_AGAIN")><a href="<@spring.url "/order/agent/${order.id}/createAgain" />" class="a_btn">再次代购</a></#if>
				       		</td>
				       <#elseif order.status==5><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已取消</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
						<#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></#if>
						   <#if Roles.hasRole("AGENT_ORDER_AGAIN")><a href="<@spring.url "/order/agent/${order.id}/createAgain" />" class="a_btn">再次代购</a></#if>
				       		</td>
				       <#elseif order.status==6><td rowspan="${order.orderGoods?size}"><strong class="strong_status">交易关闭</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
						<#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></#if>
						   <#if Roles.hasRole("AGENT_ORDER_AGAIN")><a href="<@spring.url "/order/agent/${order.id}/createAgain" />" class="a_btn">再次代购</a></#if>
				       		</td>
				       <#else><td rowspan="${order.orderGoods?size}"><strong class="strong_status">状态不明</strong></td>	
				     </#if>
				</#if>
            <#elseif (order.orderGoods?size=1)>
	            <td>
	             	<#if order.actualPrice??>
	                	<strong>￥${(order.actualPrice/100)?string("0.00")} </strong>
	                <#else>
	                	<strong>￥${(order.totalPrice/100)?string("0.00")} </strong>
	                </#if>
	            </td>
	            <td><#if order.customer??>${order.customer.name!""}</#if></td>
	            <#if order.status??>
			      	<#if order.status==1><td><strong class="strong_status">未付款</strong></td>
				      		<td>
						        <#if Roles.hasRole("AGENT_ORDER_RPRICE")><a href="#" class="a_btn priceOrder_a" onclick="orderPriceBtn(${order.id},${(order.actualPrice/100)?string("0.00")});">修改价格</a></#if>
					            <#if Roles.hasRole("AGENT_ORDER_CANCEL")><a href="#" class="a_btn" onclick="cancel(${order.id});">取消</a></#if>
								<#if Roles.hasRole("AGENT_ORDER_ADD_PAY_RECORD")><a href="#" class="a_btn paymentRecord_a" onclick="payPriceBtn(${order.id},${(order.actualPrice/100)?string("0.00")});">增加付款记录</a></#if>
	                    		<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
				                <#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></#if>
	                    	</td>
				       <#elseif order.status==2><td><strong class="strong_status">已付款 </strong></td>
				       		<td><a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
								<#if Roles.hasRole("AGENT_ORDER_CANCEL")><a href="#" class="a_btn" onclick="cancel(${order.id});">取消</a></#if>
	                            <#if Roles.hasRole("AGENT_ORDER_OUT_STORAGE")><a href="#" class="a_btn deliver_a" onclick="deliverBtn(${order.id},${order.orderGoods?size});">发货</a></#if>
	                            <#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></#if>
           					</td>
				       <#elseif order.status==3><td><strong class="strong_status">已发货</strong></td>
				       		<td>
				       			<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
								<#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></#if>
								<#if Roles.hasRole("AGENT_ORDER_AGAIN")><a href="<@spring.url "/order/agent/${order.id}/createAgain" />" class="a_btn">再次代购</a></#if>
				       		</td>
				       <#elseif order.status==4><td><strong class="strong_status">已评价</strong></td>
				       		<td>
				       			<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
								<#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></#if>
						        <#if Roles.hasRole("AGENT_ORDER_AGAIN")><a href="<@spring.url "/order/agent/${order.id}/createAgain" />" class="a_btn">再次代购</a></#if>
				       		</td>
				       <#elseif order.status==5><td><strong class="strong_status">已取消</strong></td>
				       		<td>
				       			<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
								<#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></#if>
						        <#if Roles.hasRole("AGENT_ORDER_AGAIN")><a href="<@spring.url "/order/agent/${order.id}/createAgain" />" class="a_btn">再次代购</a></#if>
				       		</td>
				       <#elseif order.status==6><td><strong class="strong_status">交易关闭</strong></td>
				       		<td>
					       		<a href="<@spring.url "/order/agent/${order.id}/info" />" class="a_btn">查看详情</a>
								<#if Roles.hasRole("AGENT_ORDER_REMARK")><a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></#if>
						        <#if Roles.hasRole("AGENT_ORDER_AGAIN")><a href="<@spring.url "/order/agent/${order.id}/createAgain" />" class="a_btn">再次代购</a></#if>
				       		</td>
				       <#else><td><strong class="strong_status">状态不明</strong></td>
				     </#if>
				</#if>
            </#if>
          </tr>
      </#list>
    </#if>
 </tbody>
