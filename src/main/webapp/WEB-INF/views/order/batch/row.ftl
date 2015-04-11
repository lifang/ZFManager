<tbody id="row_${order.id}">
      <tr class="order_hd">
        <td colspan="7"><span>订单号 ${order.orderNumber!""}</span><span>${order.createdAt?datetime}</span>
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
                     <a href="<@spring.url "/good/batch/${orderGood.good.id}/detail" />" class="cn_img">
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
                    
                    
                     <div class="td_proBox_info">
                        <h1><a href="<@spring.url "/good/batch/${orderGood.good.id}/detail" />"><#if orderGood.good??>${orderGood.good.title!""}</#if></a></h1>
                        <h3><#if orderGood.good??>${orderGood.good.secondTitle!""}</#if></h3>
                        <ul>
                            <li><span>品牌型号：</span><div class="c_text"><#if orderGood.goodBrand??>${orderGood.goodBrand.name!""}</#if></div></li>
                            <li><span>支付通道：</span><div class="c_text"><#if orderGood.payChannel??>${orderGood.payChannel.name!""}</#if></div></li>
                        </ul>
                    </div>
                </div>
            </td>
            <td>
            	<strong>￥${(orderGood.price/100)?string("0.00")}</strong>
            	<p class="original">零售价：￥<#if orderGood.good??>${(orderGood.good.retailPrice/100)?string("0.0")}</#if></p>
            </td>
            <td>${orderGood.quantity!0}</td>
            <input id="hidden_good_title_${orderGood_index}" type="hidden" value="<#if orderGood.good??>${orderGood.good.title!""}</#if>" />
		    <input id="hidden_quantity_${orderGood_index}" type="hidden" value="${orderGood.quantity!0}" />
            <#if (order.orderGoods?size>1)& orderGood_index==0>
                <td rowspan="${order.orderGoods?size}" class="left_border"><strong>￥${(order.actualPrice/100)?string("0.00")} </strong></td>
                <td rowspan="${order.orderGoods?size}">
                	<strong>￥<#if order.frontMoney??>${(order.frontMoney/100)?string("0.00")}<#else>0.00</#if> </strong>
                </td>
          		<#if order.status??>
			      	<#if order.status==1><td rowspan="${order.orderGoods?size}"><strong class="strong_status">未付款</strong></td>
			      			<td rowspan="${order.orderGoods?size}">
			      				<a href="#" class="a_btn priceOrder_a" onclick="orderPriceBtn(${order.id},${(order.actualPrice/100)?string("0.00")});">修改价格</a>
	                    		<a href="#" class="a_btn" onclick="cancel(${order.id});">取消</a>
	                    		<a href="#" class="a_btn paymentRecord_a" onclick="payPriceBtn(${order.id},${(order.actualPrice/100)?string("0.00")});">增加付款记录</a>
	                    		<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
	                    		<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></td>
				       <#elseif order.status==2><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已付款</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
				       			<a href="#" class="a_btn" onclick="cancel(${order.id});">取消</a>
           						<a href="#" class="a_btn deliver_a" onclick="deliverBtn(${order.id},${order.orderGoods?size});">发货</a>
           						<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a></td>
				       <#elseif order.status==3><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已发货</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
				       			<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a>
				       			<a href="<@spring.url "/order/batch/${order.id}/createAgain" />" class="a_btn">再次代购</a>
				       		</td>
				       <#elseif order.status==4><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已评价</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
				       			<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a>
				       			<a href="<@spring.url "/order/batch/${order.id}/createAgain" />" class="a_btn">再次代购</a>
				       		</td>
				       <#elseif order.status==5><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已取消</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
				       			<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a>
				       			<a href="<@spring.url "/order/batch/${order.id}/createAgain" />" class="a_btn">再次代购</a>
				       		</td>
				       <#elseif order.status==6><td rowspan="${order.orderGoods?size}"><strong class="strong_status">交易关闭</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
				       			<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a>
				       			<a href="<@spring.url "/order/batch/${order.id}/createAgain" />" class="a_btn">再次代购</a>
				       		</td>
				       <#else><td rowspan="${order.orderGoods?size}"><strong class="strong_status">状态不明</strong></td>	
				     </#if>
				</#if>
            <#elseif (order.orderGoods?size=1)>
	            <td><strong>￥${(order.actualPrice/100)?string("0.00")}</strong></td>
	            <td><strong>￥<#if order.frontMoney??>${(order.frontMoney/100)?string("0.00")}<#else>0.00</#if></strong></td>
	            <#if order.status??>
			      	<#if order.status==1><td><strong class="strong_status">未付款</strong></td>
				      		<td><a href="#" class="a_btn priceOrder_a" onclick="orderPriceBtn(${order.id},${(order.actualPrice/100)?string("0.00")});">修改价格</a>
	                    		<a href="#" class="a_btn" onclick="cancel(${order.id});">取消</a>
	                    		<a href="#" class="a_btn paymentRecord_a" onclick="payPriceBtn(${order.id},${(order.actualPrice/100)?string("0.00")});">增加付款记录</a>
	                    		<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
	                    		<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a>
	                    	</td>
				       <#elseif order.status==2><td><strong class="strong_status">已付款 </strong></td>
				       		<td><a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
				       			<a href="#" class="a_btn" onclick="cancel(${order.id});">取消</a>
           						<a href="#" class="a_btn deliver_a" onclick="deliverBtn(${order.id},${order.orderGoods?size});">发货</a>
           						<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a>
           					</td>
				       <#elseif order.status==3><td><strong class="strong_status">已发货</strong></td>
				       		<td>
				       			<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
				       			<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a>
				       			<a href="<@spring.url "/order/batch/${order.id}/createAgain" />" class="a_btn">再次代购</a>
				       		</td>
				       <#elseif order.status==4><td><strong class="strong_status">已评价</strong></td>
				       		<td>
				       			<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
				       			<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a>
				       			<a href="<@spring.url "/order/batch/${order.id}/createAgain" />" class="a_btn">再次代购</a>
				       		</td>
				       <#elseif order.status==5><td><strong class="strong_status">已取消</strong></td>
				       		<td>
				       			<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
				       			<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a>
				       			<a href="<@spring.url "/order/batch/${order.id}/createAgain" />" class="a_btn">再次代购</a>
				       		</td>
				       <#elseif order.status==6><td><strong class="strong_status">交易关闭</strong></td>
				       		<td>
					       		<a href="<@spring.url "/order/batch/${order.id}/info" />" class="a_btn">查看详情</a>
					       		<a href="#" class="a_btn remark_a" onclick="markBtn(${order.id});">备注</a>
				       		</td>
				       <#else><td><strong class="strong_status">状态不明</strong></td>
				     </#if>
				</#if>
            </#if>
          </tr>
      </#list>
    </#if>
 </tbody>