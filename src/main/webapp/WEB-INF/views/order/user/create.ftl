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
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
             <colgroup>
                <col width="30" />
                <col width="90" />
                <col width="250" />
                <col width="320" />
                <col width="90" />
                <col width="90" />
                <col width="120" />
                <col />
              </colgroup>
             <thead>
              <tr>
                <th>&nbsp;</th>
                <th>收货人</th>
                <th>所在地区</th>
                <th>详细地址</th>
                <th>邮编</th>
                <th>电话</th>
                <th>操作</th>
                <th>&nbsp;</th>
              </tr>
              </thead>
              <#if customerAddresses??>
              	<#list customerAddresses as customerAddress>
	              <tr>
	                <td>
	                	<#if customerAddress.isDefault==1>
	                		<input name="" type="radio" value="" checked="ture" />
	                	<#else>
	                		<input name="" type="radio" value=""/>
	                	</#if>
	                </td>
	                <td>${customerAddress.receiver!""}</td>
	                <td><#if customerAddress.parentCity??>${customerAddress.parentCity.name!""}</#if><#if customerAddress.city??>${customerAddress.city.name!""}</#if></td>
	                <td>${customerAddress.address!""}</td>
	                <td>${customerAddress.zipCode!""}</td>
	                <td>${customerAddress.moblephone!""}</td>
	                <td><a href="#" class="a_btn">修改</a><a href="#" class="a_btn">删除</a></td>
	                <td>
	                	<#if customerAddress.isDefault==1>
	                		<span class="defaultAddr">默认地址</span>
	                	<#else>
	                		<a href="#" class="set_defaultAddr">设为默认地址</a>
	                	</#if>
	                </td>
	              </tr>
	             </#list>
	          </#if>
              <tr class="addAddr_box">
                <td>&nbsp;</td>
                <td><input name="" type="text" value="收件人姓名" /></td>
                <td>
                	<select name="" id="provinceSelect">
                	  <option>省</option>
                	  <#include "../../common/city_option.ftl">
                	</select>
                    <select name="" id="citySelect">
                	  <option>市</option>
                	  <option>南京市</option>
                	</select>
                    <select name="">
                	  <option>区/县</option>
                	  <option>姑苏区</option>
                	</select>
                </td>
                <td><input name="" type="text" class="w" value="详细地址" /></td>
                <td><input name="" type="text" value="邮编" /></td>
                <td><input name="" type="text" value="手机号码" /></td>
                <td><a href="#" class="a_btn">确定</a></td>
                <td>&nbsp;</td>
              </tr>
            </table>
			<div class="addAddr_btn"><button>使用新地址</button></div>
        </div>
        <div class="myShopOrder">
        	<h3>您的订单信息
            <select name="" class="select_default">
        	  <option>选择订单类型</option>
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
</script>
</@c.html>