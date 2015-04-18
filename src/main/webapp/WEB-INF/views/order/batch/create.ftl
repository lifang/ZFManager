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
        	<div class="su_title01">
            	<ul>
                	<li class="hover">确认代理商</li>
                </ul>
            </div>
            <div class="su_con01">
            	<div>
                	<div class="su_search">
                    	<input id="agentCompanyName" name="" type="text" placeholder="公司名称"/><button onclick="searchAgent();">搜索</button>
                    </div>
                    <input id="customerId" type="hidden" name="customerId" value="<#if customer??>${customer.id!""}</#if>" />
                    <div id="agent_fresh" class="su_s_box">
                    	<#include "../agentSearch.ftl" />
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
        	<h3>您的订单信息</h3>
            <#include "../customerGood.ftl" />
        </div>
        <div class="total_info">含配送费合计<strong id="totalStrong">￥<#include "../totalPrice.ftl" /></strong>（配送费￥0.00）</div>
        <div class="other_info">
        	<div class="oi_left">
            	<div class="oi_title">留言</div>
                <div class="oi_area">
                	<textarea id="comment" name="" cols="" rows=""></textarea>
                    <p>留言最多100个汉字</p>
                </div>
            </div>
        </div>
        <div class="settleAccount">
        	<p>实付：<strong id="actualStrong">￥<#include "../totalPrice.ftl" /></strong></p>
        	<#if order??>
        		<button class="blueBtn" onclick="createSureAgain(${order.id!""});">创建批购订单</button>
        	<#else>
        		<button class="blueBtn" onclick="createSure(${good.id!""});">创建批购订单</button>
        	</#if>
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
	
	function searchAgent(agentCompanyName) {
		var agentCompanyName = $("#agentCompanyName").val();
	    $.get('<@spring.url "/order/agent/search" />',
	            {
	            	"keys": agentCompanyName
	            },
	            function (data) {
	                $('#agent_fresh').html(data);
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
	
	function agentSelected(customerId,agentId){
		$("a[name=agentCompanyName]").removeClass("hover");
		$("#agentCustomer_"+customerId).addClass("hover");
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
		var leaseTime = $("#leaseTime_"+goodId).val();
		if(quantity<leaseTime){
			alert("所选批购数量不能小于最小批购量");
			return;
		}
		var comment=$("#comment").val();
		var customerAddressId=$("#customerAddressId").val();
		var allCustomerAddress=document.getElementsByName("customerAddressId");
		for(var i=0,size=allCustomerAddress.length;i<size;i++){
			if(allCustomerAddress[i].checked){
				customerAddressId=allCustomerAddress[i].id.replace("customerAddressId_","");
				break;
			}
		}
		if(null==customerAddressId||''==customerAddressId||'0'==customerAddressId || 'undefined'==customerAddressId){
			alert("请选择地址");
			return;
		}
		var invoiceInfo=$("#invoiceInfo").val();
		var needInvoice=$("#needInvoice").prop("checked");
		var type=5;
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
					//'&invoiceInfo='+invoiceInfo+
					//'&needInvoice='+needInvoice+
					//'&invoiceType='+invoiceType+
					'&type='+type+
					'&payChannelId='+payChannelId+
					'&customerId='+customerId;
		location.href='<@spring.url "" />'+'/order/batch/createSure'+param;
	}
	
	//再次订购
	function createSureAgain(orderId){
		var goodQuantity="";
		var allinput=document.getElementsByName("quantity");
		for(var i=0,size=allinput.length;i<size;i++){
			goodQuantity+=allinput[i].id+":"+allinput[i].value;
			var goodId=(allinput[i].id).replace("quantity_","");
			var leaseTime = $("#leaseTime_"+goodId).val();
			if(parseInt(allinput[i].value)<parseInt(leaseTime)){
				alert("商品购买数量"+allinput[i].value+"小于最小批购量："+leaseTime);
				return;
			}
			if(i<size-1){
				goodQuantity+=",";
			}
		}
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
		var invoiceInfo=null;
		var needInvoice=null;
		var invoiceType= null;
		var type=5;
		if("0"==type){
			alert("请选择订单类型");
			return;
		}
		var customerId= $("#customerId").val();
		if(null==customerId||'undefined'==customerId){
			alert("请确定用户");
			return;
		}
		var param='?orderId='+orderId+
					'&goodQuantity='+goodQuantity+
					'&comment='+comment+
					'&customerAddressId='+customerAddressId+
					//'&invoiceInfo='+invoiceInfo+
					//'&needInvoice='+needInvoice+
					//'&invoiceType='+invoiceType+
					'&type='+type+
					'&customerId='+customerId;
		location.href='<@spring.url "" />'+'/order/batch/createSureAgain'+param;
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