<#import "../../common.ftl" as c />
<@c.html>
<div class="right" >
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
        <div class="detail_panel" id="infoUp_fresh">
        	<#include "infoUp.ftl">
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
              		<input id="hidden_good_title_${orderGood_index}" type="hidden" value="<#if orderGood.good??>${orderGood.good.title!""}</#if>" />
		    		<input id="hidden_quantity_${orderGood_index}" type="hidden" value="${orderGood.quantity!0}" />
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
      
        <div class="user_record">
        	<h2>追踪记录</h2>
        	<div id="order_mark_fresh">
        		<#include "orderMark.ftl" />
        	</div>
        </div>
	</div>
</div>
 
</@c.html>