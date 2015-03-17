<#import "../../common.ftl" as c />
<@c.html>
<div class="right">
	<div class="breadcrumb">
        <ul>
            <li><a href="#">订单</a></li>
            <li><a href="#">订单详情</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title">
        	<h1>订单详情</h1>
        </div>
        <div class="detail_panel">
        	<div class="detailPanel_status">
            	<div class="payWarning"><i class="no"></i>未付款${order.status!""}</div>
                <div class="dp_status_btn">
                	<a href="#" class="ghostBtn priceOrder_a">修改订单价格</a><a href="#" class="ghostBtn paymentRecord_a">增加付款记录</a>
                    <a href="#" class="ghostBtn">取消</a>
                </div>
            </div>
            <div class="detailPanel_info detailDl">
                <dl>
                	<dt>收货地址：</dt><dd><#if order.customerAddress??>${order.customerAddress.address!""} ${order.customerAddress.receiver!""}</#if></dd>
                </dl>
                <dl>
                	<dt>发票类型：</dt>
                	<dd>
                		<#if order.invoiceType==0>
                			公司
                		<#elseif order.invoiceType==1>
                			个人
                		</#if>
                	</dd>
                	<dt>发票抬头：</dt><dd>${order.invoiceInfo!""}</dd>
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
                	<dt>购买人：</dt><dd><#if order.customer??>${order.customer.name!""}</#if></dd>
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
                	<dt>供货商：</dt>
                	<dd>
                		<#if order.factory??>
                			${order.factory.name!""}
                		<#else>
                			掌富
                		</#if>
                	</dd>
                	<dt>处理人：</dt><dd>${order.processUserName!""}</dd>
                </dl>
                <dl>
                	<dt>订单原金额：</dt><dd class="line_through">￥${(order.totalPrice/100)?string("0.00")}</dd><dt>订单金额：</dt><dd><strong>￥${(order.actualPrice/100)?string("0.00")}</strong></dd>
                </dl>
            </div>
        </div>
        <div class="uesr_table">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
             <colgroup>
             	<col width="300" />
                <col />
                <col />
                <col />
             </colgroup>
             <thead>
              <tr>
                <th>商品</th>
                <th>单价</th>
                <th>数量</th>
                <th>金额</th>
              </tr>
              </thead>
              <#if order.orderGoods??>	
              	<#list order.orderGoods as orderGood>
	               <tbody>
	                  <tr>
	                    <td>
	                        <div class="td_proBox clear">
	                            <a href="#" class="cn_img">
	                            <#if orderGood.good??>
	                        		<#if orderGood.good.pictures??>
	                        			<#list orderGood.good.pictures as picture>
	                        				<#if picture_index==0>
	                        					<!--<img src="images/c.jpg" />-->
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
	                                    <li><span>月租金：</span><div class="c_text">￥<#if orderGood.good??><#if orderGood.good.leasePrice??>${(orderGood.good.leasePrice/100)?string("0.00")}</#if></#if></div></li>
	                                    <li><span>最短租赁：</span><div class="c_text"><#if orderGood.good??><#if orderGood.good.leaseTime??>${orderGood.good.leaseTime!0}</#if></#if>个月</div></li>
	                                    <li><span>最长租赁：</span><div class="c_text"><#if orderGood.good??><#if orderGood.good.returnTime??>${orderGood.good.returnTime!0}</#if></#if>个月</div></li>
	                                </ul>
	                            </div>
	                        </div>
	                    </td>
	                    <td><strong>￥${(orderGood.price/100)?string("0.00")}</strong></td>
	                    <td>${orderGood.quantity!0}</td>
	                    <td><strong>￥${(orderGood.actualPrice/100)?string("0.00")}</strong></td>
	                  </tr>
	              </tbody>
	             </#list>
              </#if>
              
            </table>
        </div>
        <div class="user_remark">
        	<textarea id="order_mark_content" name="order_mark" cols="" rows=""></textarea>
        	<input id="hidden_order_id" type="hidden" name="hidden_order" value="${order.id!""}" />
            <button class="whiteBtn" onclick="createOrderMark();">备注</button>
        </div>
        <div class="user_record">
        	<h2>追踪记录</h2>
        	<div id="order_mark_fresh">
        		<#include "orderMark.ftl" />
        	</div>
        </div>
	</div>
</div>

<script type="text/javascript">
	function createOrderMark(){
		var content = $("#order_mark_content").val();
		var orderId = $("#hidden_order_id").val();
		$.get('<@spring.url "/order/mark/user/create" />',
	            {"content": content,
	             "orderId": orderId
	            },
	            function (data) {
	               $('#order_mark_fresh').html(data);
	               $("#order_mark_content").val("");
	            });
	};
</script>
</@c.html>