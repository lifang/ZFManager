<#import "commonOrder.ftl" as c />
<@c.html>
<div class="main mshop">
	<div class="box">
    <div class="product_left clear">
    	<div class="pro_head clear">
        <div class="pro_show">
                    <div class="bigImg jqzoom">
                    	<#if good.pictures??>
                         	<#list good.pictures as picture>
                         		<#if picture_index==1>
                         			<img src="${picture.urlPath}" jqimg = "${picture.urlPath}" />
                         		</#if>
                         	</#list>
                         </#if>
                    	<img src="images/mt_big.jpg" jqimg = "images/mt_show.jpg" />
                    </div>
                    <div class="picBox">
                        <div class="prev">Prev</div>
                        <div class="smallImg">
                             <ul class="pic_show">
                                 <#if good.pictures??>
                                 	<#list good.pictures as picture>
                                 		<#if picture_index==1>
                                 			<li class="hover"><img src="${picture.urlPath}" width="55" /></li>
                                 		<#else>
                                 			<li><img src="${picture.urlPath}" width="55" /></li>
                                 		</#if>
                                 	</#list>
                                 </#if>
                             </ul>
                        </div>
                        <div class="next">Next</div>
                     </div>
               </div>	
            <script>
            	$(document).ready(function(){
					$(".jqzoom").jqueryzoom({
									xzoom: 388, 
									yzoom: 330, 
									offset: 10, 
									position: "right", 
									preload:1,
									lens:1
								});
					});
            </script>
            

        <div class="proInfo_area">
        	<h1>${good.title!""}</h1>
            <h3>${good.secondTitle!""}</h3>
            <div class="pac_summary">
            	<ul>
                	<li class="price_li"><span>现价</span><div class="text"><strong>￥${(good.price/100)?string("0.00")}</strong></div></li>
                    <li class="deposit_li"><span>租赁押金</span><div class="text"><strong>￥${(good.leasePrice/100)?string("0.00")}</strong></div></li>
                    <li class="selected_li"><span>支付通道</span>
                    	<div class="text">
                    		<input id="payChannelId" type="hidden" name="payChannelId" value="" />
                    		<#if good.channels??>
                    			<#list good.channels as channel>
                    				<a href="#" onclick="selectChannel(${channel.id!""});">${channel.name!""}</a>
                    			</#list>
                    		</#if>
                        </div>
                    </li>
                    <li class="selected_li"><span>购买/租赁</span>
                    	<div class="text">
                        	<a href="#" class="buy_a hover">购买</a>
                        	<a href="#" class="lease_a">租赁</a>
                        </div>
                    </li>
                    <li><span>购买数量</span>
                    	<div class="text">
                    		<div class="buy_numb"><a href="#" onclick="reduceQuantity();">-</a><input id="quantity" type="text" value="1" /><a href="#" onclick="addQuantity();">+</a>&nbsp;&nbsp;件</div>
                            <em class="buy_stock">库存${good.quantity!""}件</em>
                    	</div>
                    </li>
             	</ul>
        	</div>
            <div class="buy_action">
            	<a href="#" class="buy_btn" onclick="createOrder(${good.id!""});">创建用户订单</a>
                <a href="#" class="lease_btn">创建租赁订单</a>
            </div>
        </div>
        </div>
        <div class="pro_body">
        	<div class="pro_detail_title">
            	<ul>
                	<li class="hover"><a href="javascript:void(0);">商品描述</a></li>
                    <li><a href="javascript:void(0);">交易费率信息</a></li>
                    <li><a href="javascript:void(0);">开通申请条件</a></li>
                    <li><a href="javascript:void(0);">开通所需材料</a></li>
                    <li><a href="javascript:void(0);">评价(<strong>208</strong>)</a></li>
                    <li><a href="javascript:void(0);">租赁说明</a></li>
                </ul>
            </div>
            <div class="pro_detail_con">
            	<div>
                	<div class="pro_attributes">
                    	<h2>POS机信息</h2>
                        <ul>
                        	<li><span>品牌：</span><div class="text"><#if good.goodBrand??>${good.goodBrand.name!""}</#if></div></li>
                            <li><span>型号：</span><div class="text">${good.modelNumber!""}</div></li>
                            <li><span>外壳型号：</span><div class="text">${good.shellMaterial!""}</div></li> 
                            <li><span>电池信息：</span><div class="text">${good.batteryInfo!""}</div></li>
                            <li><span>签购单打印方式：</span><div class="text"><#if good.signOrderWay??>${good.signOrderWay.signOrderWay!""}</#if></div></li>
                            <li><span>加密卡方式：</span><div class="text"><#if good.encryptCardWay??>${good.encryptCardWay.encryptCardWay!""}</#if><</div></li>
                            <li><span>支持银行卡类型：</span>
                            	<#if good.cardTypes??>
                            		<#list good.cardTypes as cardType >
                            			<div class="text">${cardType.cardType!""}</div>
                            		</#list>
                            	</#if>
                            </li>
                        </ul>
                    </div>
                    <div class="pro_attributes">
                    	<h2>支付平台信息</h2>
                        <ul>
                        	<li><span>支持区域：</span><div class="text">全国</div></li>
                            <li><span>到账时间：</span><div class="text">2天内</div></li>
                            <li><span>是否支持注销：</span><div class="text">支持</div></li> 
                        </ul>
                    </div>
                    <div class="pro_attributes">
                    	<h2>刷卡交易标准手续费</h2>
                        <table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <colgroup>
                        	<col width="25%" />
                            <col width="25%" />
                            <col width="50%" />
                         </colgroup>
                          <thead>
                          <tr>
                            <th>商户类型</th>
                            <th>费率</th>
                            <th>说明</th>
                          </tr>
                          </thead>
                          <tr>
                            <td>全部</td>
                            <td>0.5%</td>
                            <td><p>这里是说明...</p></td>
                          </tr>
                          <tr>
                            <td>全部</td>
                            <td>0.5%</td>
                            <td><p>这里是说明...</p></td>
                          </tr>
                          <tr>
                            <td>全部</td>
                            <td>0.5%</td>
                            <td><p>这里是说明...</p></td>
                          </tr>
                          <tr>
                            <td>全部</td>
                            <td>0.5%</td>
                            <td><p>这里是说明...</p></td>
                          </tr>
                          <tr>
                            <td>全部</td>
                            <td>0.5%</td>
                            <td><p>这里是说明...</p></td>
                          </tr>
                        </table>
                    </div>
                    <div class="pro_attributes">
                    	<h2>资金服务费</h2>
                        <table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <colgroup>
                        	<col width="25%" />
                            <col width="25%" />
                            <col width="50%" />
                        </colgroup>
                          <thead>
                          <tr>
                            <th>结算周期</th>
                            <th>费率</th>
                            <th>说明</th>
                          </tr>
                          </thead>
                          <tr>
                            <td>T+1</td>
                            <td>0.5%</td>
                            <td><p>这里是说这里是说明这里是说这里是说明这里是说明这里是说明这里是说明这里是说明这里是说明明...</p></td>
                          </tr>
                          <tr>
                            <td>T+1</td>
                            <td>0.5%</td>
                            <td><p>这里是说这里是说明这里是说这里是说明这里是说明这里是说明这里是说明这里是说明这里是说明明...</p></td>
                          </tr>
                          <tr>
                            <td>T+1</td>
                            <td>0.5%</td>
                            <td><p>这里是说这里是说明这里是说这里是说明这里是说明这里是说明这里是说明这里是说明这里是说明明...</p></td>
                          </tr>
                          <tr>
                            <td>T+1</td>
                            <td>0.5%</td>
                            <td><p>这里是说这里是说明这里是说这里是说明这里是说明这里是说明这里是说明这里是说明这里是说明明...</p></td>
                          </tr>
                        </table>
                    </div>
                    <div class="pro_attributes">
                    	<h2>其他交易费率</h2>
                        <table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <colgroup>
                        	<col width="25%" />
                            <col width="25%" />
                            <col width="50%" />
                        </colgroup>
                          <thead>
                          <tr>
                            <th>交易类型</th>
                            <th>费率</th>
                            <th>说明</th>
                          </tr>
                          </thead>
                          <tr>
                            <td>转账</td>
                            <td>0.5%</td>
                            <td><p>这里是说这里是说明这里是说这里是说明这里是说明这里是说明这里是说明这里是说明这里是说明明...</p></td>
                          </tr>
                          <tr>
                            <td>转账</td>
                            <td>0.5%</td>
                            <td><p>这里是说这里是说明这里是说这里是说明这里是说明这里是说明这里是说明这里是说明这里是说明明...</p></td>
                          </tr>
                          <tr>
                            <td>转账</td>
                            <td>0.5%</td>
                            <td><p>这里是说这里是说明这里是说这里是说明这里是说明这里是说明这里是说明这里是说明这里是说明明...</p></td>
                          </tr>
                          <tr>
                            <td>转账</td>
                            <td>0.5%</td>
                            <td><p>这里是说这里是说明这里是说这里是说明这里是说明这里是说明这里是说明这里是说明这里是说明明...</p></td>
                          </tr>
                          <tr>
                            <td>转账</td>
                            <td>0.5%</td>
                            <td><p>这里是说这里是说明这里是说这里是说明这里是说明这里是说明这里是说明这里是说明这里是说明明...</p></td>
                          </tr>
                        </table>
                    </div>
                    <div class="pro_attributes">
                    	<h2>详细说明</h2>
                        <div class="text">包括硬件描述（不变）和软件描述（根据上面的平台变化而变化）</div>
                    </div>
                </div>
                <div>2222</div>
                <div>
                	<div class="pro_attributes">
                        <div class="text">
                        	1.年龄在18～65岁间的中国公民；<br />
                        	2.必须有稳定职业；<br />
                        	3.无信用不良记录；
                        </div>
                    </div>
                </div>
                <div>
                	<div class="pro_attributes">
                    	<h2>对个人开通</h2>
                        <div class="text">
                        	1.身份证正反面照片；<br />
                            2.手持身份证上半身照片；<br />
                            3.开户行的银行卡照片（支持银行有：中国银行、工商银行……）；<br />
                            4.手持协议拍照
                        </div>
                    </div>
                    <div class="pro_attributes">
                    	<h2>对公开通</h2>
                        <div class="text">
                        	1.法人身份证正反面照片；<br />
                            2.组织机构照片；<br />
                            3.公司结算银行账户照片（法人的开户行照片）；<br />
                            4.税务登记照片
                        </div>
                    </div>
                </div>
                
                
              <!--评论模块-->  
                <div>
                	<div class="pro_evaluate">
                    	<div class="evaluate_title"><i></i>综合评分${(good.totalScore/good.totalComment/10)?string("0.00")}</div>
                    	
                        <div class="evaluate_item">
                        	<div class="evaluate_star">
                            	<ul>
                                    <li class="p_li_o">
                                    </li><li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li></li>
                                </ul>
                            </div>
                            <div class="evaluate_text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus 
accumsan et viverra justo commodo. Proin sodales pulvinar tempor. Cum sociis natoque penatibus et magnis dis parturient 
montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien 
nunc eget odio.</div>
							<div class="evaluate_name">
                            	<h3>维尼熊</h3>
                                <span>2014-12-19   20：05：30</span>
                            </div>
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
                </div>
                <!--评论模块结束-->
                
                <div>
                    <div class="pro_attributes">
                        <h2>租赁最小期限</h2>
                        <div class="text">${good.leaseTime!""}月</div>
                    </div>
                    <div class="pro_attributes">
                        <h2>租赁说明</h2>
                        <div class="text">${good.leaseDescription!""}</div>
                    </div>
                    <div class="pro_attributes">
						<h2>租赁协议</h2>
                        <div class="text">${good.leaseAgreement!""}</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="product_right">
    	<div class="pro_manufacturer">
        	<div class="manufacturer">
        	<h2>生产厂商</h2>
        	<#if good.factory??>
        		 <div class="mf_title">
	            	<div class="mf_logo"><img src="${good.factory.logoFilePath}" width="89" /></div>
	                <div class="mf_name">${good.factory.name!""}</div>
	            </div>
	            <div class="mf_text">${good.factory.description!""}<br /><a href="#">${good.factory.websiteUrl!""}</a></div>
        	<#else>
        		 <div class="mf_title">
	            	<div class="mf_logo"><img src="images/mf_logo.jpg" /></div>
	                <div class="mf_name">拉卡拉集团拉卡拉集团拉卡拉集团</div>
	            </div>
	            <div class="mf_text">拉卡拉集团成立于2005年，是中国领先的互联网金融及社区电商公司，借助互联网技术，以便民服务及支付为手段，为商户及其用户提供金融及电子商务服务。<br /><a href="#">http://www.lakala.com/index.html</a></div>
        	</#if>
        </div>
        	<div class="manufacturer">
        	<h2>收单机构</h2>
            <div class="mf_title">
            	<div class="mf_logo"><img src="images/mf_logo.jpg" /></div>
                <div class="mf_name">拉卡拉集团拉卡拉集团拉卡拉集团</div>
            </div>
            <div class="mf_text">拉卡拉集团成立于2005年，是中国领先的互联网金融及社区电商公司，借助互联网技术，以便民服务及支付为手段，为商户及其用户提供金融及电子商务服务。<br /><a href="#">http://www.lakala.com/index.html</a></div>
        </div>
        </div>
        <div class="hot_product">
        	<h1>推荐商品</h1>
        	<ul>
        		<#if good.relativeGoods??>
        			<#list good.relativeGoods as relativeGood>
        				<li>
		                	<div class="hotPro_img"><a href="#"><img src="images/c.jpg" /></a></div>
		                    <h2><a href="<@spring.url "/good/user/${relativeGood.id}/detail" />">${relativeGood.title!""}</a></h2>
		                    <h2><a href="#" class="hp_price">￥${(good.price/100)?string("0.00")}</a></h2>
		                </li>
        			</#list>
        		</#if>
            </ul>
        </div>
    </div>
</div>
</div>
<script type="text/javascript">	
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
	
	function selectChannel(id){
		$("#payChannelId").val(id);	
	}
	
	function createOrder(id){
		var quantity = $("#quantity").val();
		var payChannelId=$("#payChannelId").val();
		if(""==payChannelId){
			alert("请选择支付通道");
			return;
		}
		location.href='<@spring.url "" />'+'/order/user/create?goodId='+id+'&quantity='+quantity+'&payChannelId='+payChannelId;
		//window.open('<@spring.url "" />'+'/order/user/create?goodId='+id);
	}
</script>
</@c.html>