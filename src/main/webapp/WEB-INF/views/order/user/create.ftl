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
                        <#include "../customerSearch.ftl" />
                    </div>
                </div>
                <div>
               	  <div class="suc_selectInput">
                    	<select name="" id="provinceCreateSelect">
                    	  <option>省</option>
                    	  <#include "../../common/city_option.ftl" />
                   	  </select>
                      <select name="" id="cityCreateSelect">
                    	  <option>市/地区</option>
                   	  </select>
                  </div>
                  <div class="suc_selectInput">
                    	<input id="phone" name="" type="text" placeholder="手机号 / 邮箱" />
                        <input id="passport" name="" type="text" placeholder="用户姓名（可选）" />
                        <input id="password" name="" type="password" placeholder="密码" />
                        <input id="repassword" name="" type="password" placeholder="确认密码" />
                        <button onclick="saveCustomer();">创建</button>
                  </div>
                  <div class="su_s_box" id="customer_save_fresh">
                    	<#include "../customer.ftl" />
                    </div>
                </div>
            </div>
        </div>
      <div class="myAddress">
      	<h3>确认收货地址</h3>
      		<div id="customer_address_fresh">
			  	<#include "../customerAddress.ftl" />
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
            <#include "../customerGood.ftl" />
        </div>
        <div class="total_info">含配送费合计
        	<strong id="totalStrong">￥${(good.price*quantity/100)?string("0.00")}</strong>
        	（配送费￥0.00）
        </div>
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
                	<textarea id="invoiceInfo" name="" cols="" rows="" class="invoice_area" disabled="disabled" placeholder="发票抬头"></textarea>
                </div>
            </div>
        </div>
        <div class="settleAccount">
        	<p>实付：<strong id="actualStrong">￥${(good.price*quantity/100)?string("0.00")}</strong></p>
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
	

        
 	 $('#provinceCreateSelect').change(function(){
        var provinceId = $(this).children('option:selected').val();
        if(isNotNull(provinceId)){
            $.post('<@spring.url "/common/cities" />',
                    {'id':provinceId},
                    function (data) {
                        $("#cityCreateSelect").empty();
                        $("#cityCreateSelect").append("<option>市</option>");
                        $("#cityCreateSelect").append(data);
                    });
        } else {
            $("#cityCreateSelect").empty();
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
		var customerId=$("#customerId").val();
		if(null==customerId || ''==customerId){
			alert("请先选择用户");
			return;
		}
		$.get('<@spring.url "/order/customer/address/saveOrUpdate" />',
	            {"cityId": cityId,
	             "receiver": receiver,
	             "address": address,
	             "moblephone": moblephone,
	             "zipCode": zipCode,
	             "customerId": customerId
	            },
	            function (data) {
	               $('#customer_address_fresh').html(data);
	            });
	}
	
	function queryAddress(customerId){
		$("a[name=customerName]").removeClass("hover");
		$("#customer_"+customerId).addClass("hover");
		$("#customerId").val(customerId);
		$.get('<@spring.url "/order/customer/address/query" />',
		            {"customerId": customerId
		            },
		            function (data) {
		               $('#customer_address_fresh').html(data);
		            });
	}
	
	function addAddress(){
        $("#add_address_box").show();
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
		var quantity = $("#quantity_"+goodId).val();
		var comment=$("#comment").val();
		var customerAddressId=$("#customerAddressId").val();
		var allCustomerAddress=document.getElementsByName("customerAddressId");
		for(var i=0,size=allCustomerAddress.length;i<size;i++){
			if(allCustomerAddress[i].checked){
				customerAddressId=allCustomerAddress[i].id.replace("customerAddressId_","");
				break;
			}
		}
		if(null==customerAddressId||''==customerAddressId||'0'==customerAddressId){
			alert("请选择地址");
			return;
		}
		var invoiceInfo=$("#invoiceInfo").val();
		var needInvoice=$("#needInvoice").prop("checked");
		var type=$("#type").val();
		if("0"==type){
			alert("请选择订单类型");
			return;
		}
		var payChannelId=$("#payChannelId").val();
		if(''==payChannelId){
			alert("请选择支付通道");
			return;
		}
		var customerId= $("#customerId").val();
		if(null==customerId||'undefined'==customerId){
			alert("请确定用户");
			return;
		}
		var invoiceType= $("#invoiceType").val();
		var param='?goodId='+goodId+
					'&quantity='+quantity+
					'&comment='+comment+
					'&customerAddressId='+customerAddressId+
					'&invoiceInfo='+invoiceInfo+
					'&needInvoice='+needInvoice+
					'&type='+type+
					'&payChannelId='+payChannelId+
					'&customerId='+customerId+
					'&invoiceType='+invoiceType;
		location.href='<@spring.url "" />'+'/order/user/createSure'+param;
	}
	
	function saveCustomer(){
		var cityCreateSelect = $("#cityCreateSelect").val();
		var phone = $("#phone").val();
		var passport = $("#passport").val();
		var password = $("#password").val();
		var repassword = $("#repassword").val();
		if(password!=repassword){
			alert("两次输入密码不同");
			return;
		}
		$.get('<@spring.url "/order/customer/saveOrUpdate" />',
		            {"phone": phone,
		            "passport": passport,
		            "city": cityCreateSelect,
		            "password": password,
		            "repassword": repassword
		            },
		            function (data) {
		               $('#customer_save_fresh').html(data);
		            });
	}
</script>
</@c.html>