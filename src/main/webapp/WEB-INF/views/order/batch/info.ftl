<#import "../../common.ftl" as c />
<@c.html>
<div class="right" >
	<div class="breadcrumb">
        <ul>
            <li><a href="#">订单</a></li>
            <li><a href="#">代理商批购</a></li>
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
                <col />
                <col />
             </colgroup>
             <thead>
              <tr>
                <th>商品</th>
                <th>单价</th>
                <th>数量</th>
                <th>金额</th>
                <th>已发货数量</th>
                <th>操作</th>
              </tr>
              </thead>
              <#if order.orderGoods??>	
              	<tbody>
              	<#list order.orderGoods as orderGood>
              		<input id="hidden_good_title_${order.id}_${orderGood_index}" type="hidden" value="<#if orderGood.good??>${orderGood.good.title!""}</#if>" />
		    		<input id="hidden_quantity_${order.id}_${orderGood_index}" type="hidden" value="${orderGood.quantity!0}" />
	               
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
	                    <td><strong>￥<#if orderGood.good??>${(orderGood.good.purchasePrice!0/100)?string("0.0")}</#if></strong><p class="original">零售价：￥<#if orderGood.good??>${(orderGood.good.retailPrice/100)?string("0.0")}</#if></p></td>
	                    <td>${orderGood.quantity!0}</td>
	                    <#if (order.orderGoods?size>1) && orderGood_index==0>
	                    	<td rowspan="${order.orderGoods?size}" class="left_border"><strong>￥${(orderGood.actualPrice/100)?string("0.00")}</strong></td>
		                    <td rowspan="${order.orderGoods?size}">${order.totalOutQuantity!0}</td>
		                    <td rowspan="${order.orderGoods?size}">
		                    	<#if order.status==2>
		                    		<a href="#" class="a_btn deliver_a" onclick="deliverBtn(${order.id},${order.orderGoods?size});">发货</a>
		                    	</#if>
		                    </td>
		                <#elseif (order.orderGoods?size=1)>
		                    <td><strong>￥${(orderGood.actualPrice/100)?string("0.00")}</strong></td>
		                    <td>${order.totalOutQuantity!0}</td>
		                    <td>
		                    	<#if order.status==2>
		                    		<a href="#" class="a_btn deliver_a" onclick="deliverBtn(${order.id},${order.orderGoods?size});">发货</a>
		                    	</#if>
		                    </td>
		                </#if>
	                  </tr>
	             	</#list>
	            </tbody>
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

<div class="tab priceEarnest_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabHead">修改定金价格</div>
    <div class="tabBody">
    	<div class="item_list">
        	<ul>
            	<li><span class="labelSpan">定金价格</span><div class="text" id="priceEarnestDiv"><strong>￥500.00</strong></div></li>
                <li><span class="labelSpan">新价格</span><div class="text"><input name="" type="text" id="priceEarnestText"/></div></li>
            </ul>
        </div>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="priceEarnestSure">确定</button></div>
</div>

<div class="tab paymentRecord_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabHead">增加付款记录</div>
    <div class="tabBody">
    	<div class="item_list">
        	<ul>
            	<li><span class="labelSpan">付款金额</span><div class="text"><input id="payPrice" name="" type="text" placeholder="元" /></div></li>
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

<div class="tab deliver_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabHead">添加第三方库存发货信息</div>
    <div class="tabBody">
    	<div id="pos_info">
	    	<p>POS机名称：汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装 点餐机奶茶店 </p>
	        <p>POS机数量：10</p>
	    </div>
    	<textarea name="" cols="" rows="" id="terminal_serial_num" placeholder="输入终端号"></textarea>
    	<textarea name="" cols="" rows="" id="reserver2" placeholder="中汇终端激活码（非中汇终端无需填写）"></textarea>
        <input name="" type="text" value="物流公司" id="logistics_name" />
        <input name="" type="text" value="物流单号" id="logistics_number"/>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="deliverSure">确定</button></div>
</div>
<script type="text/javascript">
	function createOrderMark(){
		var content = $("#order_mark_content").val();
		if(null==content||""==content){
			alert("备注内容不能为空！");
			return;
		}
		var orderId = $("#hidden_order_id").val();
		$.get('<@spring.url "/order/mark/batch/create" />',
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
        popup(".deliver_tab",".deliver_a");//发货
        popup(".priceEarnest_tab",".priceEarnest_a");//修改定金价格
        
	}
	
	function orderPriceBtn(id,price){
		$("#order_price").html("<strong>￥"+price+"</strong>");
 		$("#priceSure").unbind().bind('click',function(){priceSure(id)});
    }
    
    function priceSure(id){
		var actualPrice = $('#actual_price').val();
		$.get('<@spring.url "" />'+'/order/batch/info/'+id+'/save',
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
    
    function priceEarnestBtn(id,price){
		$("#priceEarnestDiv").html("<strong>￥"+price+"</strong>");
 		$("#priceEarnestSure").unbind().bind('click',function(){priceEarnestSure(id)});
    }
    function priceEarnestSure(id){
		var priceEarnestText = $('#priceEarnestText').val();
		$.get('<@spring.url "" />'+'/order/batch/info/'+id+'/save',
				{"orderId":id,
				"frontMoney":priceEarnestText
				},
	            function (data) {
	           		$('#infoUp_fresh').html(data);
					$('.priceEarnest_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
    function cancel(id){
    	$.get('<@spring.url "" />'+'/order/batch/info/'+id+'/cancel',
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
		//$("#pay_price").html("<strong>￥"+price+"</strong>");
 		$("#paySure").unbind().bind('click',function(){paySure(id)});
    }
    
    function paySure(id){
		var payType = $('#pay_type').val();
		var payPrice=$('#payPrice').val();
		$.get('<@spring.url "" />'+'/order/payment/batch/info/create',
				{"orderId":id,
				"payType":payType,
				"payPrice":payPrice
				},
	            function (data) {
	           		$('#infoUp_fresh').html(data);
					$('.paymentRecord_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
    
    function deliverBtn(id,size){
    	var htmlStr='';
    	for(var i=0;i<size;i++){
    		var hidden_good_title = $('#hidden_good_title_'+id+'_'+i).val();
    		var hidden_quantity = $('#hidden_quantity_'+id+'_'+i).val();
    		var hidden_order_good_id = $('#hidden_order_good_id_'+id+'_'+i).val();
    		htmlStr+="<p>POS机名称："+hidden_good_title+"</p>"+
	        "<div class='deliver_numb'><label>POS机数量：</label><input name='deliverNum' id='deliverNum_"+hidden_order_good_id+"' type='text' class='input_m' /></div> ";
    	}
		$("#pos_info").html(htmlStr);
 		$("#deliverSure").unbind().bind('click',function(){deliverSure(id)});
    }
    
    function deliverSure(id){
    	var goodQuantity="";
		var allinput=document.getElementsByName("deliverNum");
		for(var i=0,size=allinput.length;i<size;i++){
			goodQuantity+=allinput[i].id+":"+allinput[i].value;
			if(i<size-1){
				goodQuantity+=",";
			}
		}
		var terminalSerialNum = $('#terminal_serial_num').val();
		var logisticsName = $('#logistics_name').val();
		var logisticsNumber = $('#logistics_number').val();
		var reserver2 = $('#reserver2').val();
		$.get('<@spring.url "" />'+'/order/logistic/batch/info/create',
				{
				"orderId":id,
				"terminalSerialNum":terminalSerialNum,
				"logisticsName":logisticsName,
				"logisticsNumber":logisticsNumber,
				"goodQuantity":goodQuantity,
				"reserver2":reserver2
				},
	            function (data) {
	            	if(data.indexOf("-1")==0){
	            		alert(data.substring(2));
	            		return;
	            	}
	            	$('#infoUp_fresh').html(data);
					$('.deliver_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
</script>
</@c.html>