<#import "../../page.ftl" as pager>
<div class="uesr_table">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
            <colgroup>
                <col width="300" />
                <col width="80" />
                <col width="60" />
                <col width="80" />
                <col width="80" />
                <col />
            </colgroup>
            <thead>
            <tr>
                <th>商品</th>
                <th>单价</th>
                <th>数量</th>
                <th>总金额</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
          
 		<#if (orders.content)??>
		  <#list orders.content as order>
       		<tbody>
            <tr class="order_hd">
                <td colspan="6"><span>订单号 ${order.orderNumber!""}</span><span>${order.createdAt?datetime}</span>
                    <span>类型：
                    	<#if order.types??>
					      	<#if order.types==1>用户订购
						       <#elseif order.types==2>用户租赁 
						       <#elseif order.types==3>代理商代购
						       <#elseif order.types==4>代理商代租赁
						       <#elseif order.types==5>代理商批购
						     </#if>
						</#if>
					</span>
                    <span>用户：<#if order.customer??>${order.customer.name!""}</#if></span><span>电话：<#if order.customer??>${order.customer.phone!""}</#if></span></td>
            </tr>
            <#if order.orderGoods??>
            	<#list order.orderGoods as orderGood>
	            <tr>
	                <td>
	                    <div class="td_proBox clear">
	                        <a href="#" class="cn_img">
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
	                            <h1><a href="#"><#if orderGood.good??>${orderGood.good.title!""}</#if></a></h1>
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
	                <#if (order.orderGoods?size>1)& orderGood_index==0>
		                <td rowspan="${order.orderGoods?size}" class="left_border"><strong>￥${(order.totalPrice/100)?string("0.00")} </strong></td>
	              		<#if order.status??>
					      	<#if order.status==1><td rowspan="${order.orderGoods?size}"><strong class="strong_status">未付款</strong></td>
					      			<td rowspan="${order.orderGoods?size}"><a href="#" class="a_btn priceOrder_a">修改价格</a>
			                    		<a href="#" class="a_btn">取消</a><a href="#" class="a_btn paymentRecord_a">增加付款记录</a>
			                    		<a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn remark_a">备注</a></td>
						       <#elseif order.status==2><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已付款</strong></td>
						       		<td rowspan="${order.orderGoods?size}"><a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn">取消</a>
                   						<a href="#" class="a_btn deliver_a">发货</a><a href="#" class="a_btn">备注</a></td>
						       <#elseif order.status==3><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已发货</strong></td>
						       		<td rowspan="${order.orderGoods?size}"><a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn">备注</a></td>
						       <#elseif order.status==4><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已评价</strong></td>
						       		<td rowspan="${order.orderGoods?size}"><a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn">备注</a></td>
						       <#elseif order.status==5><td rowspan="${order.orderGoods?size}"><strong class="strong_status">已取消</strong></td>
						       		<td rowspan="${order.orderGoods?size}"><a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn">备注</a></td>
						       <#elseif order.status==6><td rowspan="${order.orderGoods?size}"><strong class="strong_status">交易关闭</strong></td>
						       		<td rowspan="${order.orderGoods?size}"><a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn">备注</a></td>
						       		
						       <#else><td rowspan="${order.orderGoods?size}"><strong class="strong_status">状态不明</strong></td>	
						     </#if>
						</#if>
		            <#elseif (order.orderGoods?size=1)>
			            <td><strong>￥${(order.totalPrice/100)?string("0.00")}</strong></td>
			            <#if order.status??>
					      	<#if order.status==1><td><strong class="strong_status">未付款</strong></td>
						      		<td><a href="#" class="a_btn priceOrder_a">修改价格</a>
			                    		<a href="#" class="a_btn">取消</a>
			                    		<a href="#" class="a_btn paymentRecord_a">增加付款记录</a>
			                    		<a href="<@spring.url "/order/user/${order.id}/info" />" class="a_btn">查看详情</a>
			                    		<a href="#" class="a_btn remark_a">备注</a>
			                    	</td>
						       <#elseif order.status==2><td><strong class="strong_status">已付款 </strong></td>
						       		<td><a href="#" class="a_btn">查看详情</a>
						       			<a href="#" class="a_btn">取消</a>
                   						<a href="#" class="a_btn deliver_a">发货</a>
                   						<a href="#" class="a_btn">备注</a>
                   					</td>
						       <#elseif order.status==3><td><strong class="strong_status">已发货</strong></td>
						       		<td>
						       			<a href="<@spring.url "/order/user/${order.id}/info" />" class="a_btn">查看详情</a>
						       			<a href="#" class="a_btn">备注</a>
						       		</td>
						       <#elseif order.status==4><td><strong class="strong_status">已评价</strong></td>
						       		<td>
						       			<a href="#" class="a_btn">查看详情</a>
						       			<a href="#" class="a_btn">备注</a>
						       		</td>
						       <#elseif order.status==5><td><strong class="strong_status">已取消</strong></td>
						       		<td>
						       			<a href="#" class="a_btn">查看详情</a>
						       			<a href="#" class="a_btn">备注</a>
						       		</td>
						       <#elseif order.status==6><td><strong class="strong_status">交易关闭</strong></td>
						       		<td>
							       		<a href="#" class="a_btn">查看详情</a>
							       		<a href="#" class="a_btn">备注</a>
						       		</td>
						       <#else><td><strong class="strong_status">状态不明</strong></td>
						     </#if>
						</#if>
	                </#if>
	            </tr>
	            </#list>
            </#if>
            </tbody>
		  </#list>
		</#if>
        </table>
    </div>
<@pager.p page=orders.currentPage totalPages=orders.totalPage functionName="orderPageChange"/>