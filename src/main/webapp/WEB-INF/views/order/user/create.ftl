<#import "commonOrder.ftl" as c />
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
            <select name="" class="select_default">
        	  <option>选择订单类型</option>
        	  <option value="1">用户订购</option>
        	  <option value="2">用户租赁</option>
        	  <option value="3">代理商代购</option>
        	  <option value="4">代理商代租赁</option>
        	  <option value="5">代理商批购</option>
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
                        <a href="#" class="cn_img"><img src="images/c.jpg" /></a>
                        <div class="td_proBox_info">
                            <h1><a href="#">汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装点餐机奶茶店</a></h1>
                            <h3>热销5000件</h3>
                            <ul>
                                <li><span>品牌型号：</span><div class="c_text">掌富ZF-300</div></li>
                                <li><span>支付通道：</span><div class="c_text">收账宝</div></li>
                                <li><span>月租金：</span><div class="c_text">￥200.00</div></li>
                                <li><span>最短租赁：</span><div class="c_text">12个月</div></li>
                                <li><span>最上租赁：</span><div class="c_text">12个月</div></li>
                            </ul>
                        </div>
                    </div>
                </td>
                <td><a href="#"><strong>￥458.00</strong></a></td>
                <td><div class="choose_amount"><a href="javascript:void(0);">-</a><input type="text" value="1" /><a href="javascript:void(0);">+</a></div></td>
                <td><a href="#"><strong>￥458.00</strong></a></td>
              </tr>
            </table>
        </div>
        <div class="total_info">含配送费合计<strong>￥9.99</strong>（配送费￥0.00）</div>
        <div class="other_info">
        	<div class="oi_left">
            	<div class="oi_title">留言</div>
                <div class="oi_area">
                	<textarea name="" cols="" rows=""></textarea>
                    <p>留言最多100个汉字</p>
                </div>
            </div>
            <div class="oi_right">
            	<div class="oi_title">
                	<div class="invoice_checkbox"> <input name="" type="checkbox" value=""  /> 需要发票</div>
                	<div class="invoice"><span>类型：</span>
                    	<a href="javascript:void(0);" class="hover">个人</a>
                        <a href="javascript:void(0);">公司</a>
                    </div>
                </div>
                <div class="oi_area">
                	<textarea name="" cols="" rows="" class="invoice_area" disabled="disabled">发票抬头</textarea>
                </div>
            </div>
        </div>
        <div class="settleAccount">
        	<p>实付：<strong>￥1377.00</strong></p>
        	<button class="blueBtn">创建订单</button>
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
		$.get('<@spring.url "/order/customer/address/create" />',
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
	
	function addAddress(){
        $("#add_address_box").show();
    }
</script>
</@c.html>