<#import "../commonOrder.ftl" as c />
<@c.html>
<div class="main">
	<div class="box">
	<div class="breadcrumb">
    	<ul>
            <li><a href="#">用户</a></li>
            <li><a href="#">确认您的订单信息</a></li>
        </ul>
    </div>
    <div class="inner">
    	<div class="searchUser">
        	<div class="su_title">
            	<ul>
                	<li class="hover">搜索现有用户</li>
                    <li>创建新用户</li>
                </ul>
            </div>
            <div class="su_con">
            	<div>
                	<div class="su_search">
                    	<input id="customer_name" name="" type="text" /><button onclick="searchCustomer();">搜索</button>
                    </div>
                    <div id="customer_fresh" class="su_s_box">
                    	<!--<a href="#" class="hover">张三</a>-->
                        <#include "customerSearch.ftl" />
                    </div>
                </div>
                <div>
               	  <div class="suc_selectInput">
                    	<select name="">
                    	  <option>省</option>
                   	  </select>
                      <select name="">
                    	  <option>市/地区</option>
                   	  </select>
                  </div>
                  <div class="suc_selectInput">
                    	<input name="" type="text" value="手机号 / 邮箱" />
                        <input name="" type="text" value="用户姓名（可选）" />
                        <input name="" type="text" value="密码" />
                        <input name="" type="text" value="确认密码" />
                        <button>创建</button>
                  </div>
                  <div class="su_s_box">
                    	<a href="#" class="hover">张三</a>
                    </div>
                </div>
            </div>
        </div>
      <div class="myAddress">
      	<h3>确认收货地址</h3>
      		<div id="customer_address_fresh">
			  	<#include "customerAddress.ftl" />
            </div>
			<div class="addAddr_btn"><button onclick="addAddress();">使用新地址</button></div>
        </div>
        <div class="myShopOrder">
        	<h3>您的订单信息
            <select id="type" name="" class="select_default">
            	<#if type==1>
            		<option value="1">用户订购</option>
        	  		<option value="2">用户租赁</option>
            	<#elseif type==2>
        	  		<option value="2">用户租赁</option>
            		<option value="1">用户订购</option>
            	</#if>
        	</select></h3>
            <table width="100%" cellspacing="0" cellpadding="0" class="b_table">
             <colgroup>
                <col width="640" />
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
              <tr>
                <td>
                    <div class="td_proBox">
                        <a href="#" class="cn_img">
                        	<#if good.pictures??>
	                         	<#list good.pictures as picture>
	                         		<#if picture_index==1>
	                         			<img src="${picture.urlPath}"  style="width:130px;height:130px;"/>
	                         		</#if>
	                         	</#list>
	                         </#if>
                        </a>
                        <div class="td_proBox_info">
                            <h1><a href="#">${good.title!""}</a></h1>
                            <h3>${good.secondTitle!""}</h3>
                            <ul>
                                <li><span>品牌型号：</span><div class="c_text"><#if good.goodBrand??>${good.goodBrand.name!""}</#if> ${good.modelNumber!""}</div></li>
                                <li><span>支付通道：</span>
                                	<div class="c_text">
                                		<#if payChannel??>${payChannel.name!""}</#if>
                                		<input id="payChannelId" type="hidden" name="payChannelId" value="<#if payChannel??>${payChannel.id!""}</#if>" />
                                	</div>
                                	
                                </li>
                                <li><span>月租金：</span><div class="c_text">￥200.00</div></li>
                                <li><span>最短租赁：</span><div class="c_text">12个月</div></li>
                                <li><span>最上租赁：</span><div class="c_text">12个月</div></li>
                            </ul>
                        </div>
                    </div>
                </td>
                <td><a href="#"><strong>￥${(good.price/100)?string("0.00")}</strong></a></td>
                <td><div class="choose_amount"><a href="javascript:void(0);" onclick="reduceQuantity();">-</a><input id="quantity" type="text" value="${quantity!""}" /><a href="javascript:void(0);"  onclick="addQuantity();">+</a></div></td>
                <td><a href="#"><strong>￥${(good.price*quantity/100)?string("0.00")}</strong></a></td>
              </tr>
            </table>
        </div>
        <div class="total_info">含配送费合计<strong>￥9.99</strong>（配送费￥0.00）</div>
        <div class="other_info">
        	<div class="oi_left">
            	<div class="oi_title">留言</div>
                <div class="oi_area">
                	<textarea id="comment" name="" cols="" rows=""></textarea>
                    <p>留言最多100个汉字</p>
                </div>
            </div>
            <div class="oi_right">
            	<div class="oi_title">
                	<div class="invoice_checkbox"> <input id="needInvoice" name="" type="checkbox" value=""  /> 需要发票</div>
                	<div class="invoice"><span>类型：</span>
                		<input id="invoiceType" type="hidden" name="invoiceType" value="0" />
                    	<a href="javascript:void(0);" onclick="selectInvoiceType(0);" class="hover">个人</a>
                        <a href="javascript:void(0);" onclick="selectInvoiceType(0);">公司</a>
                    </div>
                </div>
                <div class="oi_area">
                	<textarea id="invoiceInfo" name="" cols="" rows="" class="invoice_area" disabled="disabled">发票抬头</textarea>
                </div>
            </div>
        </div>
        <div class="settleAccount">
        	<p>实付：<strong>￥1377.00</strong></p>
        	<button class="blueBtn" onclick="createSure(${good.id!""});">创建订单</button>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">
	function searchCustomer() {
		var customerName = $("#customer_name").val();
	    $.get('<@spring.url "/order/customer/search" />',
	            {
	            	"customerName": customerName
	            },
	            function (data) {
	                $('#customer_fresh').html(data);
	            });
	};
	
	 $('#provinceSelect').change(function(){
            var provinceId = $(this).children('option:selected').val();
            if(isNotNull(provinceId)){
                $.post('<@spring.url "/common/cities" />',
                        {'id':provinceId},
                        function (data) {
                            $("#citySelect").empty();
                            $("#citySelect").append("<option>市</option>");
                            $("#citySelect").append(data);
                            //$("#citySelect").append("<option></option>"+data);
                        });
            } else {
                $("#citySelect").empty();
            }
        });
        
    function isNotNull(value){
        return value != "" && value != null && value != undefined;
    }
    
    function createCustomerAddress(){
		var cityId = $("#citySelect").val();
		var receiver = $("#receiver").val();
		var address = $("#address").val();
		var moblephone = $("#moble_phone").val();
		var zipCode = $("#zip_code").val();
		$.get('<@spring.url "/order/customer/address/saveOrUpdate" />',
	            {"cityId": cityId,
	             "receiver": receiver,
	             "address": address,
	             "moblephone": moblephone,
	             "zipCode": zipCode
	            },
	            function (data) {
	               $('#customer_address_fresh').html(data);
	            });
	}
	
	function updateAddress(id,status){
		console.info("saveOrUpdate");
		$.get('<@spring.url "/order/customer/address/saveOrUpdate" />',
	            {"id": id,
	            "status":status
	            },
	            function (data) {
	               $('#customer_address_fresh').html(data);
	            });
	}
	
	function addAddress(){
        $("#add_address_box").show();
    }
    
    function addQuantity() {
		var quantity = $("#quantity").val();
		$("#quantity").val(parseInt(quantity)+1);
	}
	
	function reduceQuantity() {
		var quantity = $("#quantity").val();
		var result=parseInt(quantity)-1;
		if(result<1)result=1;
		$("#quantity").val(result);
	}
	
	function selectCustomerAddress(id){
		//$("input[name=customerAddressId]").removeAttr("checked");
		$("#customerAddressId_"+id).attr("checked","checked");
		$("#customerAddressId").val(id);
	}
	
	function selectInvoiceType(invoiceType){
		$("#invoiceType").val(invoiceType);
	}
	
	function createSure(goodId){
		var quantity = $("#quantity").val();
		var comment=$("#comment").val();
		var customerAddressId=$("#customerAddressId").val();
		if(''==customerAddressId||'0'==customerAddressId){
			alert("请选择地址");
			return;
		}
		var invoiceInfo=$("#invoiceInfo").val();
		var needInvoice=$("#needInvoice").prop("checked");
		var type=$("#type").val();
		if("0"==type){
			alert("请选择订单类型");
		}
		var payChannelId=$("#payChannelId").val();
		if(''==payChannelId){
			alert("请选择支付通道");
			return;
		}
		var invoiceType= $("#invoiceType").val()
		var param='?goodId='+goodId+
					'&quantity='+quantity+
					'&comment='+comment+
					'&customerAddressId='+customerAddressId+
					'&invoiceInfo='+invoiceInfo+
					'&needInvoice='+needInvoice+
					'&type='+type+
					'&payChannelId='+payChannelId+
					'&invoiceType='+invoiceType;
		location.href='<@spring.url "" />'+'/order/user/createSure'+param;
	}
	
</script>
</@c.html>