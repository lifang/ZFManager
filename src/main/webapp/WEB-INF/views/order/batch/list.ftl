<#import "../../common.ftl" as c />
<@c.html>
	<div class="breadcrumb">
        <ul>
            <li>订单</li>
            <li><a href="<@spring.url "/order/batch/list"/>">代理商批购</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title">
        	<h1>代理商批购订单列表</h1>
            <div class="userTopBtnBox">
	<#if Roles.hasRole("AGENT_BATCH_ORDER_CREATE")>
            	<a href="<@spring.url "/good/batch/page"/>" class="ghostBtn">创建批购订单</a>
	</#if>
         	</div>
        </div>
        <div class="seenBox clear">
        	<ul>
            	<li><div class="user_search">
		            <input id="search_keys" name="" type="text" placeholder="按订单号查询"/>
		            <input id="hidden_keys" type="hidden" name="keys" value="" />
		            <input id="hidden_status" type="hidden" name="status" value="" />
		            <button id="btn_search"></button>
		            </div>
	            </li>
                <li><div class="user_select">
                	<label>状态筛选</label>
                	<select id="select_status"> 
                     	  <option value="0">全部</option> 
				          <option value="1">未付款</option> 
				          <option value="2">已付订金</option>
				          <option value="7">已付款</option> 
				          <option value="3">已完成</option> 
				          <option value="4">已评价</option> 	         
				          <option value="5">已取消</option> 
				          <option value="6">交易关闭</option> 
			        </select> 
                </div></li>
            </ul>
        </div>
      <div id="page_fresh">
      		<#include "page.ftl" />
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

<div class="tab paymentRecordFront_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabHead">支付定金</div>
    <div class="tabBody">
    	<div class="item_list">
        	<ul>
            	<li><span class="labelSpan">付款金额</span><div class="text" id="pay_price_front"><strong>￥0.00</strong></div></li>
                <li><span class="labelSpan">付款方式</span><div class="text">
                    <select name="" id="pay_type_front">
                      <option value="1">支付宝</option>
                      <option value="2">银联</option>
                      <option value="3">现金</option>
                    </select>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="paySureFront">确定</button></div>
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

<div class="tab remark_tab">
    	<a href="#" class="close">关闭</a>
        <div class="tabHead">备注</div>
        <div class="tabBody">
        	<textarea id="mark_content" name="" cols="" rows=""></textarea>
        </div>
        <div class="tabFoot"><button class="blueBtn" id="markSure">确定</button></div>
</div>

<!--
	<div class="tab deliver_tab">
		<a href="#" class="close">关闭</a>
	    <div class="tabHead">添加第三方库存发货信息</div>
	    <div class="tabBody">
	    	<p>POS机名称：汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装 点餐机奶茶店 </p>
	        <div class="deliver_numb"><label>POS机数量：</label><input name="" type="text" class="input_m" /></div> 
	    	<textarea name="" cols="" rows="" placeholder="输入终端号"></textarea>
	        <input name="" type="text" placeholder="物流公司" />
	        <input name="" type="text" placeholder="物流单号" />
	    </div>
	    <div class="tabFoot"><button class="blueBtn">确定</button></div>
	</div>
-->

<div class="tab deliver_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabHead">添加第三方库存发货信息</div>
    <div class="tabBody">
    	<div id="pos_info">
	    	<p>POS机名称：汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装 点餐机奶茶店 </p>
	    	<div class="deliver_numb"><label>POS机数量：</label><input name="" type="text" class="input_m"  id="pos_num"  /></div> 
	    </div>
    	<textarea name="" cols="" rows="" id="terminal_serial_num" placeholder="输入终端号以逗号分隔"></textarea>
    	<textarea name="" cols="" rows="" id="reserver2" placeholder="中汇终端激活码（非中汇终端无需填写）"></textarea>
        <input name="" type="text" value="" id="logistics_name" placeholder="物流公司"/>
        <input name="" type="text" value="" id="logistics_number" placeholder="物流单号"/>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="deliverSure">确定</button></div>
</div>
<script type="text/javascript">
 
	$(function(){
			$('#select_status').change(function(){
				var status = $(this).children('option:selected').val();
				$("#hidden_status").val(status);
				orderBatchPageChange(1);
			});
			
			$("#btn_search").bind("click",
		        function() {
				var keys = $("#search_keys").val();
				$("#hidden_keys").val(keys);
				orderBatchPageChange(1);
		    });
	});

	function orderBatchPageChange(page) {
		var keys = $("#hidden_keys").val();
		var status = $("#hidden_status").val();
	    $.post('<@spring.url "/order/batch/page" />',
	            {"page": page,
	             "keys": keys,
	             "status": status,
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
        //popup(".deliver_tab",".deliver_a");//发货
        popup(".priceEarnest_tab",".priceEarnest_a");//修改定金价格
        popup(".paymentRecordFront_tab",".paymentRecordFront_a");//支付定金
	}
	
	function markBtn(id){
		$("#markSure").unbind().bind('click',function(){markSure(id)});
    }
    
    function markSure(id){
		var content = $('#mark_content').val();
		if(null==content||""==content){
			alert("备注内容不能为空！");
			return;
		}
		$.post('<@spring.url "" />'+'/order/mark/batch/create',
				{"orderId":id,
				"content":content
				},
	            function (data) {
					$('.remark_tab').hide();
					$('.mask').hide();
	            });
	}
	
	function priceEarnestBtn(id,price){
		$("#priceEarnestDiv").html("<strong>￥"+price+"</strong>");
 		$("#priceEarnestSure").unbind().bind('click',function(){priceEarnestSure(id)});
    }
    function priceEarnestSure(id){
		var priceEarnestText = $('#priceEarnestText').val();
		$.post('<@spring.url "" />'+'/order/batch/'+id+'/save',
				{"orderId":id,
				"frontMoney":priceEarnestText
				},
	            function (data) {
	           		$('#row_'+id).replaceWith(data);
					$('.priceEarnest_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
	
	function orderPriceBtn(id,price){
		$("#order_price").html("<strong>￥"+price+"</strong>");
		$("#priceSure").unbind().bind('click',function(){priceSure(id)});
    }
    
    function priceSure(id){
		var actualPrice = $('#actual_price').val();
		$.post('<@spring.url "" />'+'/order/batch/'+id+'/save',
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
    	$.post('<@spring.url "" />'+'/order/batch/'+id+'/cancel',
				{
				},
	            function (data) {
	           		$('#row_'+id).replaceWith(data);
					$('.priceOrder_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
    function payPriceFrontBtn(id,price){
		$("#pay_price_front").html("<strong>￥"+price+"</strong>");
		$("#paySureFront").unbind().bind('click',function(){paySureFront(id)});
    }
    
    function paySureFront(id){
		var payType = $('#pay_type_front').val();
		$.post('<@spring.url "" />'+'/order/payment/batch/create/front',
				{"orderId":id,
				"payType":payType
				},
	            function (data) {
	            	if(!checkException(data)){
	            		return;
	            	}
	           		$('#row_'+id).replaceWith(data);
					$('.paymentRecordFront_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
    function payPriceBtn(id,price){
    	$("#paySure").unbind().bind('click',function(){paySure(id)});
    }
    
    function paySure(id){
		var payType = $('#pay_type').val();
		var payPrice=$('#payPrice').val();
		if(null==payPrice || payPrice.replace(/(^s*)|(s*$)/g, "").length ==0){
			alert("请输入支付金额");
			return false;
		}
		$.post('<@spring.url "" />'+'/order/payment/batch/create',
				{"orderId":id,
				"payType":payType,
				"payPrice":payPrice
				},
	            function (data) {
	            	if(!checkException(data)){
	            		return;
	            	}
	           		$('#row_'+id).replaceWith(data);
					$('.paymentRecord_tab').hide();
					$('.mask').hide();
					popupPage();
	            });
    }
    
    function deliverBtn(id,size){
    	var belongsTo = $('#hidden_belongsTo_'+id).val();
    	if(belongsTo==0){
    		$.post('<@spring.url "" />'+'/order/logistic/batch/create',
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
    	var num_wh = "";
		var allinput=document.getElementsByName("deliverNum");
		for(var i=0,size=allinput.length;i<size;i++){
			goodQuantity+=allinput[i].id+":"+allinput[i].value;
			num_wh = allinput[i].value;
			if(i<size-1){
				goodQuantity+=",";
			}
		}
		var belongsTo = $('#hidden_belongsTo_'+id).val();
		var terminalSerialNum = $('#terminal_serial_num').val();
		  var reg = new RegExp("^[0-9]*\.[0-9]{0,1}$");
		  if(!reg.test(num_wh)){
			  alert("pos机数量必须是数字");
			 return false;
		  }
		 
		var ts = terminalSerialNum.split(",");
		var length=0;
		for(var i=0,size=ts.length;i<size;i++){
			var tsItem=ts[i];
			var tsI=tsItem.split("\n");
			for(var j=0,sizeJ=tsI.length;j<sizeJ;j++){
				if(tsI[j].replace(/(^s*)|(s*$)/g, "").length >0){
					length++;
				}
			}
			//length=length+tsI.length;
		}
		var logisticsName = $('#logistics_name').val();
		var pos_num = $('#deliverNum_'+id).val();
		var logisticsNumber = $('#logistics_number').val();
		if(parseInt(num_wh)!=parseInt(length)){
			alert("pos机数量与终端号数量不一致");
			return false;
		}
		var reserver2 = $('#reserver2').val();
		$.post('<@spring.url "" />'+'/order/logistic/batch/create',
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
	           		$('#row_'+id).replaceWith(data);
					$('.deliver_tab').hide();
					$('.mask').hide();
					if(belongsTo==0){
	            		alert("已生成一张条发货记录，请及时处理");
	            	}
					popupPage();
	            });
    }

</script>
</@c.html>
