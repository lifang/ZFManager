<#import "../../common.ftl" as c />
<@c.html>
<div class="right" >
	<div class="breadcrumb">
        <ul>
            <li>订单</li>
            <li><a href="<@spring.url "/order/agent/list"/>">代理商代购</a></li>
            <li><a href="<@spring.url "/order/agent/${order.id}/info"/>">订单详情</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title">
        	<h1>订单详情</h1>
        </div>
        <div class="detail_panel" id="infoUp_fresh">
        	<input id="hidden_belongsTo_${order.id}" type="hidden" value="${order.belongsTo!0}" />
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
	                    <td>
	                    	<strong>￥
	                    		<#if orderGood.payChannel??>
	                    			<#if order.types==2 || order.types==4>
	                    				${(((orderGood.good.leaseDeposit!0)+(orderGood.payChannel.openingCost!0))/100)?string("0.00")}
	                    			<#else>
	                    				${(((orderGood.good.retailPrice!0)+(orderGood.payChannel.openingCost!0))/100)?string("0.00")}
	                    			</#if>
	                    		</#if>
	                    	</strong>
	                    </td>
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

<div class="tab priceOrder_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabHead">修改订单价格</div>
    <div class="tabBody">
    	<div class="item_list">
        	<ul>
            	<li><span class="labelSpan">订单价格</span><div class="text" id="order_price"><strong>￥0.00</strong></div></li>
                <li><span class="labelSpan">新价格</span><div class="text"><input name="" type="text" id="actual_price"/></div></li>
            </ul>
        </div>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="priceSure">确定</button></div>
</div>

<div class="tab paymentRecord_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabHead">增加付款记录</div>
    <div class="tabBody">
    	<div class="item_list">
        	<ul>
            	<li><span class="labelSpan">付款金额</span><div class="text" id="pay_price"><strong>￥0.00</strong></div></li>
                <li><span class="labelSpan">付款方式</span><div class="text">
                    <select name="" id="pay_type">
                      <option value="1">支付宝</option>
                      <option value="2">银联</option>
                      <option value="3">现金</option>
                    </select>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="paySure">确定</button></div>
</div>

<#include "../tabInfo.ftl" />
<script type="text/javascript">
	function createOrderMark(){
		var content = $("#order_mark_content").val();
		if(null==content||""==content){
			alert("备注内容不能为空！");
			return;
		}
		var orderId = $("#hidden_order_id").val();
		$.post('<@spring.url "/order/mark/agent/create" />',
	            {"content": content,
	             "orderId": orderId
	            },
	            function (data) {
	               $('#order_mark_fresh').html(data);
	               $("#order_mark_content").val("");
	            });
	};
	
	function popupPage(){
        popup(".priceOrder_tab",".priceOrder_a");//修改价格
        popup(".paymentRecord_tab",".paymentRecord_a");//确认支付
        //popup(".deliver_tab",".deliver_a");//发货
        
	}
	
	function orderPriceBtn(id,price){
		$("#order_price").html("<strong>￥"+price+"</strong>");
 		$("#priceSure").unbind().bind('click',function(){priceSure(id)});
    }
    
    function priceSure(id){
		var actualPrice = $('#actual_price').val();
		$.post('<@spring.url "" />'+'/order/agent/info/'+id+'/save',
				{"orderId":id,
				"actualPrice":actualPrice
				},
	            function (data) {
	           		$('#infoUp_fresh').html(data);
					$('.priceOrder_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
    function cancel(id){
    	$.post('<@spring.url "" />'+'/order/agent/info/'+id+'/cancel',
				{
				},
	            function (data) {
	           		$('#infoUp_fresh').html(data);
					$('.priceOrder_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
    function payPriceBtn(id,price){
		$("#pay_price").html("<strong>￥"+price+"</strong>");
		$("#paySure").unbind().bind('click',function(){paySure(id)});
    }
    
    function paySure(id){
		var payType = $('#pay_type').val();
		$.post('<@spring.url "" />'+'/order/payment/agent/info/create',
				{"orderId":id,
				"payType":payType
				},
	            function (data) {
	           		$('#infoUp_fresh').html(data);
					$('.paymentRecord_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
    function deliverBtn(id,size){
		var belongsTo = $('#hidden_belongsTo_'+id).val();
    	if(belongsTo==0){
    		$('.deliver_tab').hide();
    		$.post('<@spring.url "" />'+'/order/logistic/agent/info/create',
				{
				"orderId":id
				},
	            function (data) {
	            	if(data.indexOf("-1")==0){
	            		alert(data.substring(2));
	            		return;
	            	}
	           		$('#row_'+id).replaceWith(data);
					$('.deliver_tab').hide();
					$('.mask').hide();
	            	alert("已生成一张条发货记录，请及时处理");
					popupPage();
	            });
	    	return;
    	}else{
    		popupT(".deliver_tab")
    	}
    	var htmlStr='';
    	for(var i=0;i<size;i++){
    		var hidden_good_title = $('#hidden_good_title_'+i).val();
    		var hidden_quantity = $('#hidden_quantity_'+i).val();
    		htmlStr+="<p>POS机名称："+hidden_good_title+"</p>"+
	        "<p>POS机数量："+hidden_quantity+"</p>";
    	}
		$("#pos_info").html(htmlStr);
		$("#deliverSure").unbind().bind('click',function(){deliverSure(id)});
    }
    
    function deliverSure(id){
		var terminalSerialNum = $('#terminal_serial_num').val();
		var logisticsName = $('#logistics_name').val();
		var logisticsNumber = $('#logistics_number').val();
		var reserver2 = $('#reserver2').val();
		$.post('<@spring.url "" />'+'/order/logistic/agent/info/create',
				{
				"orderId":id,
				"terminalSerialNum":terminalSerialNum,
				"logisticsName":logisticsName,
				"logisticsNumber":logisticsNumber,
				"reserver2":reserver2
				},
	            function (data) {
	           		$('#infoUp_fresh').html(data);
					$('.deliver_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
</script>
</@c.html>