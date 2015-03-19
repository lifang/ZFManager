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
          	<a href="<@spring.url "/order/user/create"/>" class="ghostBtn">创建订单</a>
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
            	<li><span class="labelSpan">订单价格</span><div class="text" id="order_price"><strong>￥500.00</strong></div></li>
                <li><span class="labelSpan">新价格</span><div class="text"><input name="" type="text" /></div></li>
            </ul>
        </div>
    </div>
    <div class="tabFoot"><button class="blueBtn">确定</button></div>
</div>

<div class="tab paymentRecord_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabHead">增加付款记录</div>
    <div class="tabBody">
    	<div class="item_list">
        	<ul>
            	<li><span class="labelSpan">付款金额</span><div class="text"><strong>￥500.00</strong></div></li>
                <li><span class="labelSpan">付款方式</span><div class="text">
                    <select name="">
                      <option>转账</option>
                    </select>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="tabFoot"><button class="blueBtn">确定</button></div>
</div>

<div class="tab remark_tab">
    	<a href="#" class="close">关闭</a>
        <div class="tabHead">备注</div>
        <div class="tabBody">
        	<textarea id="mark_content" name="" cols="" rows=""></textarea>
        </div>
        <div class="tabFoot"><button class="blueBtn" id="markSure">确定</button></div>
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
	                popup(".remark_tab",".remark_a");//备注
	                popup(".priceOrder_tab",".priceOrder_a");//修改价格
	                popup(".paymentRecord_tab",".paymentRecord_a");//修改价格
	            });
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
 		//$("#markSure").click(function(){markSure(id)});
    }
	
</script>
</@c.html>