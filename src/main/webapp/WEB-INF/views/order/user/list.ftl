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
            <li><div class="user_search"><input name="" type="text" /><button></button></div></li>
            <li><div class="user_select">
                <label>状态筛选</label>
                     <select id="select_status"> 
				          <option value="0">全部</option> 
				          <option value="1">待审核</option> 
				          <option value="2">初审不通过</option> 
				          <option value="3">初审通过</option> 
				          <option value="4">审核不通过</option> 	         
				          <option value="5">正常</option> 
				          <option value="6">已停用</option> 
			          </select> 
                <select name="">
                    <option>全部供应商</option>
                    <option>222</option>
                    <option>333</option>
                </select>
            </div></li>
        </ul>
    </div>
    <div class="uesr_table">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
            <colgroup>
                <col width="300" />
                <col width="80" />
                <col width="60" />
                <col width="80" />
                <col width="80" />
                <col />
            </colgroup>
            <thead>
            <tr>
                <th>商品</th>
                <th>单价</th>
                <th>数量</th>
                <th>总金额</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr class="order_hd">
                <td colspan="6"><span>订单号 1265974136684</span><span>2014/12/12 14:22:23</span>
                    <span>类型：租赁</span><span>用户：王晓晓</span><span>电话：13298653829</span></td>
            </tr>
            <tr>
                <td>
                    <div class="td_proBox clear">
                        <a href="#" class="cn_img"><img src="<@spring.url "/resources/images/c.jpg"/>" /></a>
                        <div class="td_proBox_info">
                            <h1><a href="#">汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装点餐机奶茶店</a></h1>
                            <h3>热销5000件</h3>
                            <ul>
                                <li><span>品牌型号：</span><div class="c_text">掌富ZF-300</div></li>
                                <li><span>支付通道：</span><div class="c_text">收账宝</div></li>
                            </ul>
                        </div>
                    </div>
                </td>
                <td><strong>￥459.00</strong></td>
                <td>2</td>
                <td><strong>￥918.00</strong></td>
                <td><strong class="strong_status">未付款</strong></td>
                <td><a href="#" class="a_btn priceOrder_a">修改价格</a>
                    <a href="#" class="a_btn">取消</a><a href="#" class="a_btn paymentRecord_a">增加付款记录</a>
                    <a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn remark_a">备注</a></td>
            </tr>
            </tbody>
          
 		<#if (orders.content)??>
		  <#list orders.content as order>
       		<tbody>
            <tr class="order_hd">
                <td colspan="6"><span>订单号 ${order.orderNumber!""}</span><span>${order.createdAt?datetime}</span>
                    <span>类型：
                    	<#if order.types??>
					      	<#if order.types==1>用户订购
						       <#elseif order.types==2>用户租赁 
						       <#elseif order.types==3>代理商代购
						       <#elseif order.types==4>代理商代租赁
						       <#elseif order.types==5>代理商批购
						     </#if>
						</#if>
					</span>
                    <span>用户：<#if order.customer??>${order.customer.name!""}</#if></span><span>电话：<#if order.customer??>${order.customer.phone!""}</#if></span></td>
            </tr>
            <tr>
                <td>
                    <div class="td_proBox clear">
                        <a href="#" class="cn_img"><img src="<@spring.url "/resources/images/c.jpg"/>" /></a>
                        <div class="td_proBox_info">
                            <h1><a href="#">汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装点餐机奶茶店</a></h1>
                            <h3>热销5000件</h3>
                            <ul>
                                <li><span>品牌型号：</span><div class="c_text">掌富ZF-300</div></li>
                                <li><span>支付通道：</span><div class="c_text">收账宝</div></li>
                            </ul>
                        </div>
                    </div>
                </td>
                <td><strong>￥459.00</strong></td>
                <td>2</td>
                <td><strong>￥918.00</strong></td>
                <td><strong class="strong_status">未付款</strong></td>
                <td><a href="#" class="a_btn priceOrder_a">修改价格</a>
                    <a href="#" class="a_btn">取消</a><a href="#" class="a_btn paymentRecord_a">增加付款记录</a>
                    <a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn remark_a">备注</a></td>
            </tr>
            </tbody>
		  </#list>
		</#if>

	

            <tbody>
            <tr class="order_hd">
                <td colspan="6"><span>订单号 1265974136684</span><span>2014/12/12 14:22:23</span>
                    <span>类型：租赁</span><span>用户：王晓晓</span><span>电话：13298653829</span></td>
            </tr>
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
                            </ul>
                        </div>
                    </div>
                </td>
                <td><strong>￥459.00</strong></td>
                <td>2</td>
                <td rowspan="2" class="left_border"><strong>￥1836.00</strong></td>
                <td rowspan="2"><strong class="strong_status">已发货</strong></td>
                <td rowspan="2"><a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn">备注</a></td>
            </tr>
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
                            </ul>
                        </div>
                    </div>
                </td>
                <td><strong>￥459.00</strong></td>
                <td>2</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="pageTurn">
        <div class="p_num">
            <a href="#" class="disabled">上一页</a>
            <a href="#" class="current">1</a>
            <a href="#?page=2">2</a>
            <a href="#?page=3">3</a>
            <a href="#?page=4">4</a>
            <a href="#?page=5">5</a>
            ...
            <a href="#?page=199">199</a>
            <a href="#?page=200">200</a>
            <a href="#?page=2">下一页</a>
        </div>
        <div class="p_skip">
            <span>共24页</span>
            <span>到第&nbsp;&nbsp;<input name="" type="text" />&nbsp;&nbsp;页</span>
            <button>确定</button>
        </div>
    </div>
</div>

</@c.html>