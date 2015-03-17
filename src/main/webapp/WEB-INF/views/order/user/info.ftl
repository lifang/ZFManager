<#import "../../common.ftl" as c />
<@c.html>
<div class="right">
	<div class="breadcrumb">
        <ul>
            <li><a href="#">订单</a></li>
            <li><a href="#">订单详情</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title">
        	<h1>订单详情</h1>
        </div>
        <div class="detail_panel">
        	<div class="detailPanel_status">
            	<div class="payWarning"><i class="no"></i>未付款${order.status!""}</div>
                <div class="dp_status_btn">
                	<a href="#" class="ghostBtn priceOrder_a">修改订单价格</a><a href="#" class="ghostBtn paymentRecord_a">增加付款记录</a>
                    <a href="#" class="ghostBtn">取消</a>
                </div>
            </div>
            <div class="detailPanel_info detailDl">
                <dl>
                	<dt>收货地址：</dt><dd><#if order.customerAddress??>${order.customerAddress.address!""} ${order.customerAddress.receiver!""}</#if></dd>
                </dl>
                <dl>
                	<dt>发票类型：</dt>
                	<dd>
                		<#if order.invoiceType==0>
                			公司
                		<#elseif order.invoiceType==1>
                			个人
                		</#if>
                	</dd>
                	<dt>发票抬头：</dt><dd>${order.invoiceInfo!""}</dd>
                </dl>
                <dl class="leaveWord">
                	<dt>留言：</dt><dd>${order.comment!""}</dd>
                </dl>
                <dl>
                	<dt>订单类型：</dt>
                	<dd>
						<#if order.types==1>用户订购
            			<#elseif order.types==2>用户租赁
            			<#elseif order.types==3>代理商代购
            			<#elseif order.types==4>代理商代租赁
            			<#elseif order.types==5>代理商批购
                		</#if>
                	</dd>
                	<dt>订单编号：</dt><dd>${order.orderNumber!""}</dd>
                </dl>
                <dl>
                	<dt>购买人：</dt><dd><#if order.customer??>${order.customer.name!""}</#if></dd>
                    <dt>购买日期：</dt><dd>${order.createdAt?datetime}</dd>
                </dl>
                <dl>
                	<dt>支付类型：</dt><dd>支付宝</dd>
                </dl>
                <dl>
                	<dt>供货商：</dt>
                	<dd>
                		<#if order.factory??>
                			${order.factory.name!""}
                		<#else>
                			掌富
                		</#if>
                	</dd>
                	<dt>处理人：</dt><dd>${order.processUserName!""}</dd>
                </dl>
                <dl>
                	<dt>订单原金额：</dt><dd class="line_through">￥${(order.totalPrice/100)?string("0.00")}</dd><dt>订单金额：</dt><dd><strong>￥${(order.actualPrice/100)?string("0.00")}</strong></dd>
                </dl>
            </div>
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
              <tbody>
                  <tr>
                    <td>
                        <div class="td_proBox clear">
                            <a href="#" class="cn_img"><img src="images/c.jpg" /></a>
                            <div class="td_proBox_info">
                                <h1><a href="#">汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装点餐机奶茶店</a></h1>
                                <h3>热销5000件</h3>
                                <ul>
                                    <li><span>品牌型号：</span><div class="c_text">掌富ZF-300</div></li>
                                    <li><span>支付通道：</span><div class="c_text">收账宝</div></li>
                                    <li><span>月租金：</span><div class="c_text">￥200.00</div></li>
                                    <li><span>最短租赁：</span><div class="c_text">12个月</div></li>
                                    <li><span>最长租赁：</span><div class="c_text">12个月</div></li>
                                </ul>
                            </div>
                        </div>
                    </td>
                    <td><strong>￥459.00</strong></td>
                    <td>2</td>
                    <td><strong>￥918.00</strong></td>
                  </tr>
              </tbody>
            </table>
        </div>
        <div class="user_remark">
        	<textarea name="" cols="" rows=""></textarea>
            <button class="whiteBtn">备注</button>
        </div>
        <div class="user_record">
        	<h2>追踪记录</h2>
            <div class="ur_item">
            	<div class="ur_item_text">nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien nunc eget odio.</div>
                <div class="ur_item_name">备注人 <em>2014/12/24 20:12:00</em></div>
            </div>
            <div class="ur_item">
            	<div class="ur_item_text">nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien nunc eget odio.</div>
                <div class="ur_item_name">备注人 <em>2014/12/24 20:12:00</em></div>
            </div>
        </div>
	</div>
</div>
</@c.html>