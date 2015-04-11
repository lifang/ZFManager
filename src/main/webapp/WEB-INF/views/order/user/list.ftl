<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">订单</a></li>
        <li><a href="#">用户订单</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title">
        <h1>用户订单列表</h1>
        <div class="userTopBtnBox">
          	<!--<a href="<@spring.url "/order/user/create"/>" class="ghostBtn">创建订单</a>-->
          	<a href="<@spring.url "/good/user/create"/>" class="ghostBtn">创建订单</a>
        </div>
    </div>
    <div class="seenBox clear">
        <ul>
            <li><div class="user_search">
		            <input id="search_keys" name="" type="text" />
		            <input id="hidden_keys" type="hidden" name="keys" value="" />
		            <input id="hidden_status" type="hidden" name="status" value="" />
		            <input id="hidden_factory_id" type="hidden" name="factoryId" value="" />
		            <button id="btn_search"></button>
	            </div>
            </li>
            <li><div class="user_select">
                <label>状态筛选</label>
                     <select id="select_status"> 
                     	  <option value="0">全部</option> 
				          <option value="1">未付款</option> 
				          <option value="2">已付款</option> 
				          <option value="3">已发货</option> 
				          <option value="4">已评价</option> 	         
				          <option value="5">已取消</option> 
				          <option value="6">交易关闭</option> 
			          </select> 
	                <select id="select_factory">
	                    <option value="0">全部供应商</option>
	                	<#if factories??>
	                		<#list factories as factory>
		                    	<option value="${factory.id!0}"> ${factory.name!""}</option>
		                    </#list>
	                    </#if>
	                </select>
            </div></li>
        </ul>
    </div>
    <div id="page_fresh">
      	<#include "pageOrder.ftl" />
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

<div class="tab remark_tab">
    	<a href="#" class="close">关闭</a>
        <div class="tabHead">备注</div>
        <div class="tabBody">
        	<textarea id="mark_content" name="" cols="" rows=""></textarea>
        </div>
        <div class="tabFoot"><button class="blueBtn" id="markSure">确定</button></div>
</div>

<div class="tab deliver_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabHead">添加第三方库存发货信息</div>
    <div class="tabBody">
    	<div id="pos_info">
	    	<p>POS机名称：汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装 点餐机奶茶店 </p>
	        <p>POS机数量：10</p>
	    </div>
    	<textarea name="" cols="" rows="" id="terminal_serial_num">输入终端号</textarea>
        <input name="" type="text" value="物流公司" id="logistics_name" />
        <input name="" type="text" value="物流单号" id="logistics_number"/>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="deliverSure">确定</button></div>
</div>
<script type="text/javascript">

	$(function(){
			$('#select_status').change(function(){
				var status = $(this).children('option:selected').val();
				$("#hidden_status").val(status);
				orderPageChange(1);
			});
			
			$("#btn_search").bind("click",
		        function() {
				var keys = $("#search_keys").val();
				$("#hidden_keys").val(keys);
				orderPageChange(1);
		    });
		    
		    $('#select_factory').change(function(){
				var factoryId = $(this).children('option:selected').val();
				$("#hidden_factory_id").val(factoryId);
				orderPageChange(1);
			});
	       
	});

	function orderPageChange(page) {
		var keys = $("#hidden_keys").val();
		var status = $("#hidden_status").val();
		var factoryId=$("#hidden_factory_id").val();
	    $.get('<@spring.url "/order/user/page" />',
	            {"page": page,
	             "keys": keys,
	             "status": status,
	             "factoryId":factoryId
	            },
	            function (data) {
	                $('#page_fresh').html(data);
	                popupPage();
	            });
	}
	
	function popupPage(){
		popup(".remark_tab",".remark_a");//备注
        popup(".priceOrder_tab",".priceOrder_a");//修改价格
        popup(".paymentRecord_tab",".paymentRecord_a");//确认支付
        popup(".deliver_tab",".deliver_a");//发货
        
	}
	
	function markBtn(id){
 		$("#markSure").click(function(){markSure(id)});
    }
    
    function markSure(id){
		var content = $('#mark_content').val();
		$.get('<@spring.url "" />'+'/order/mark/user/create',
				{"orderId":id,
				"content":content
				},
	            function (data) {
					$('.remark_tab').hide();
					$('.mask').hide();
	            });
	}
	
	function orderPriceBtn(id,price){
		$("#order_price").html("<strong>￥"+price+"</strong>");
 		$("#priceSure").click(function(){priceSure(id)});
    }
    
    function priceSure(id){
		var actualPrice = $('#actual_price').val();
		$.get('<@spring.url "" />'+'/order/user/'+id+'/save',
				{"orderId":id,
				"actualPrice":actualPrice
				},
	            function (data) {
	           		$('#row_'+id).replaceWith(data);
					$('.priceOrder_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
    function cancel(id){
    	$.get('<@spring.url "" />'+'/order/user/'+id+'/cancel',
				{
				},
	            function (data) {
	           		$('#row_'+id).replaceWith(data);
					$('.priceOrder_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
    function payPriceBtn(id,price){
		$("#pay_price").html("<strong>￥"+price+"</strong>");
 		$("#paySure").click(function(){paySure(id)});
    }
    
    function paySure(id){
		var payType = $('#pay_type').val();
		$.get('<@spring.url "" />'+'/order/payment/user/create',
				{"orderId":id,
				"payType":payType
				},
	            function (data) {
	           		$('#row_'+id).replaceWith(data);
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
    		htmlStr+="<p>POS机名称："+hidden_good_title+"</p>"+
	        "<p>POS机数量："+hidden_quantity+"</p>";
    	}
		$("#pos_info").html(htmlStr);
 		$("#deliverSure").click(function(){deliverSure(id)});
    }
    
    function deliverSure(id){
    	var terminalSerialNum = $('#terminal_serial_num').val();
		var logisticsName = $('#logistics_name').val();
		var logisticsNumber = $('#logistics_number').val();
		$.get('<@spring.url "" />'+'/order/logistic/create',
				{
				"orderId":id,
				"logisticsName":logisticsName,
				"logisticsNumber":logisticsNumber
				},
	            function (data) {
	           		$('#row_'+id).replaceWith(data);
					$('.deliver_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
	
</script>
</@c.html>