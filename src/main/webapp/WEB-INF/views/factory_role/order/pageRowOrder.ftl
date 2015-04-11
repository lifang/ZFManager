<tbody id="row_${order.id}">
    <tr class="order_hd">
        <td colspan="6"><span>订单号 ${order.orderNumber!""}</span><span>${order.createdAt?datetime}</span>
            <span>用户：<#if order.customer??>${order.customer.name!""}</#if></span><span>电话：<#if order.customer??>${order.customer.phone!""}</#if></span></td>
    </tr>
    <#if order.orderGoods??>
    	<#list order.orderGoods as orderGood>
        <tr>
            <td>
                <div class="td_proBox clear">
                    <a class="cn_img" href="<@spring.url "/good/user/${orderGood.good.id}/detail" />">
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
                        <h1><a href="<@spring.url "/good/user/${orderGood.good.id}/detail" />"><#if orderGood.good??>${orderGood.good.title!""}</#if></a></h1>
                        <h3><#if orderGood.good??>${orderGood.good.secondTitle!""}</#if></h3>
                        <ul>
                            <li><span>品牌型号：</span><div class="c_text"><#if orderGood.goodBrand??>${orderGood.goodBrand.name!""}</#if></div></li>
                            <li><span>支付通道：</span><div class="c_text"><#if orderGood.payChannel??>${orderGood.payChannel.name!""}</#if></div></li>
                        </ul>
                    </div>
                </div>
            </td>
            <td><strong>￥${(orderGood.price/100)?string("0.00")}</strong></td>
            <td>${orderGood.quantity!0}</td>
            <input id="hidden_good_title_${orderGood_index}" type="hidden" value="<#if orderGood.good??>${orderGood.good.title!""}</#if>" />
		    <input id="hidden_quantity_${orderGood_index}" type="hidden" value="${orderGood.quantity!0}" />
            <#if (order.orderGoods?size>1)& orderGood_index==0>
                <td rowspan="${order.orderGoods?size}" class="left_border">
	                <#if order.actualPrice??>
	                	<strong>￥${(order.actualPrice/100)?string("0.00")} </strong>
	                <#else>
	                	<strong>￥${(order.totalPrice/100)?string("0.00")} </strong>
	                </#if>
                </td>
          		<#if order.status??>
			      	<#if order.status==1><td rowspan="${order.orderGoods?size}"><strong class="strong_status">未付款</strong></td>
			      			<td rowspan="${order.orderGoods?size}">
	                    		<a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
	                    	</td>
				       <#elseif order.status==2><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已付款</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       					<a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
				       	 </td>
				       <#elseif order.status==3><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已发货</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       					<a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
				       			</td>
				       <#elseif order.status==4><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已评价</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       				<a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
				       		 </td>
				       <#elseif order.status==5><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已取消</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       				<a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
				       		 </td>
				       <#elseif order.status==6><td rowspan="${order.orderGoods?size}"><strong class="strong_status">交易关闭</strong></td>
				       		<td rowspan="${order.orderGoods?size}">
				       			 <a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
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
	            <#if order.status??>
			      	<#if order.status==1><td><strong class="strong_status">未付款</strong></td>
				      		<td>
	                    		 <a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
	                    	</td>
				       <#elseif order.status==2><td><strong class="strong_status">已付款 </strong></td>
				       		<td> <a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
           					</td>
				       <#elseif order.status==3><td><strong class="strong_status">已发货</strong></td>
				       		<td>
				       			 <a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
				       		</td>
				       <#elseif order.status==4><td><strong class="strong_status">已评价</strong></td>
				       		<td>
				       			 <a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
				       		</td>
				       <#elseif order.status==5><td><strong class="strong_status">已取消</strong></td>
				       		<td>
				       		 <a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
				       		</td>
				       <#elseif order.status==6><td><strong class="strong_status">交易关闭</strong></td>
				       		<td>
					       		 <a href="<@spring.url "/factory/order/${order.id}/info" />" class="a_btn">查看详情</a>
				       		</td>
				       <#else><td><strong class="strong_status">状态不明</strong></td>
				     </#if>
				</#if>
            </#if>
        </tr>
        </#list>
    </#if>
    </tbody>