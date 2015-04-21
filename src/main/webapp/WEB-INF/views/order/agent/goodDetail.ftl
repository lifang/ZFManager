<#import "../commonOrder.ftl" as c />
<@c.html>
<div class="main mshop">
	<div class="box">
    <div class="product_left clear">
    	<div class="pro_head clear">
        <div class="pro_show">
                        <div class="bigImg jqzoom">
                        	<!--<img src="<@spring.url "/resources/images/mt_big.jpg"/>" jqimg = "<@spring.url "/resources/images/mt_show.jpg"/>" />-->
                        	<#if good.pictures??>
	                             <#list good.pictures as picture>
	                                 <#if picture_index==1>
	                                     <img src="${picture.urlPath}" jqimg = "${picture.urlPath}" style="width:388px;height:330px;"/>
	                                 </#if>
	                             </#list>
	                         </#if> 
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
        	<input id="good_id" type="hidden" name="good_id" value="${good.id!""}" />
        	<h1>${good.title!""}</h1>
            <h3>${good.secondTitle!""}</h3>
            <div class="pac_summary">
            	<ul>
                	<li class="price_li"><span>现价</span><div class="text"><strong>￥${(good.price/100)?string("0.00")}</strong></div></li>
                    <li class="deposit_li"><span>租赁押金</span><div class="text"><strong>￥${(good.leasePrice/100)?string("0.00")}</strong></div></li>
                    <li class="selected_li"><span>支付通道</span>
                    	<div class="text">
                    		<input id="payChannelId" type="hidden" name="payChannelId" value="<#if payChannel??>${payChannel.id!""}</#if>" />
                    		<#if good.channels??>
                    			<#list good.channels as channel>
                    				<#if channel.id==payChannel.id>
                    					<a href="#" onclick="selectChannel(${good.id!""},${channel.id!""});" class="hover">${channel.name!""}</a>
                    				<#else>
                    					<a href="#" onclick="selectChannel(${good.id!""},${channel.id!""});">${channel.name!""}</a>
                    				</#if>
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
            	<a href="#" class="buy_btn" onclick="createOrder(${good.id!""},3);">创建代购订单</a>
                <a href="#" class="lease_btn" onclick="createOrder(${good.id!""},4);">租赁</a>
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
                    <li><a href="javascript:void(0);">评价(<strong>${good.totalComment!"0"}</strong>)</a></li>
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
                            <li><span>加密卡方式：</span><div class="text"><#if good.encryptCardWay??>${good.encryptCardWay.encryptCardWay!""}</#if></div></li>
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
                        	<li><span>支持区域：</span>
                        		<div class="text">
                        			<#if payChannel?? &&(payChannel.areas)??>
			                            <#list payChannel.areas as area>
			                            	${area.name}
			                            </#list>
			                        </#if>
                        		</div>
                        	</li>
                            <li><span>到账时间：</span>
                            	<div class="text">
                            		<#if payChannel?? &&payChannel.billingCycles??>
			                          	<#list payChannel.billingCycles as billingCycle>
			                          		${billingCycle.dictionaryBillingCycle.name!""}
			                          	</#list>
			                          </#if>
                            	</div>
                            </li>
                            <li><span>是否支持注销：</span><div class="text"><#if payChannel?? &&payChannel.supportCancelFlag>支持<#else>不支持</#if></div></li> 
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
                          <#if payChannel?? &&payChannel.standardRates??>
                          	<#list payChannel.standardRates as standardRate>
                          		<tr>
		                            <td>${standardRate.dictionaryTradeStandardRate.merchantTypeName!""}</td>
		                            <td>${(standardRate.standardRate/100)?string("0.00")}%</td>
		                            <td><p>${standardRate.description!""}</p></td>
		                          </tr>
                          	</#list>
                          </#if>
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
                          <#if payChannel?? &&payChannel.billingCycles??>
                          	<#list payChannel.billingCycles as billingCycle>
                          		<tr>
		                            <td>${billingCycle.dictionaryBillingCycle.name!""}</td>
		                            <td><#if billingCycle.rate??>${(billingCycle.rate/100)?string("0.00")}%<#else>0.0%</#if></td>
		                            <td><p>${billingCycle.description!""}</p></td>
		                          </tr>
                          	</#list>
                          </#if>
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
                          <#if payChannel?? &&payChannel.supportTradeTypes??>
                          	<#list payChannel.supportTradeTypes as supportTradeType>
                          		<tr>
		                            <td><#if supportTradeType.dictionaryTradeType??>${supportTradeType.dictionaryTradeType.tradeValue!""}</#if></td>
		                            <td>${((supportTradeType.serviceRate!0)/100)?string("0.00")}%</td>
		                            <td><p>${supportTradeType.description!""}</p></td>
		                          </tr>
                          	</#list>
                          </#if>
                        </table>
                    </div>
                    <div class="pro_attributes">
                    	<h2>详细说明</h2>
                        <div class="text">
                        	电池信息：${good.description!""}<br />
                         	外壳材质：${good.shellMaterial!""}<br />
                         	开通协议：${payChannel.openingProtocol!""}
                        </div>
                    </div>
                </div>
                <div>
                	<div class="pro_attributes">
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
		                      <#if payChannel?? &&payChannel.billingCycles??>
		                      	<#list payChannel.billingCycles as billingCycle>
		                      		<tr>
			                            <td>${billingCycle.dictionaryBillingCycle.name!""}</td>
			                            <td><#if billingCycle.rate??>${(billingCycle.rate/100)?string("0.00")}%<#else>0.0%</#if></td>
			                            <td><p>${billingCycle.description!""}</p></td>
			                          </tr>
		                      	</#list>
		                      </#if>
		                    </table>
		              </div>
				</div>
                <div>
                	<div class="pro_attributes">
                        <div class="text">
                        	<#if payChannel??>${payChannel.openingRequirement!""}</#if>
                        </div>
                    </div>
                </div>
                <div>
                	<div class="pro_attributes">
                		<div class="text">
                        	<#if payChannel??>${payChannel.openingDatum!""}</#if>
                        </div>
                    </div>
                </div>
                
                
             	<!--评论模块-->  
            	<div class="pro_evaluate">
					<div class="evaluate_title"><i></i>综合评分<#if good.totalComment?? &&good.totalComment!=0>${(good.totalScore/good.totalComment/10)?string("0.00")}<#else>0.00</#if></div>
            	 	<div id="page_fresh">
            			<#include "goodCommentPage.ftl"/>
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
	            <div class="mf_text">${good.factory.description!""}<br /><a href="${good.factory.websiteUrl!""}" target="_blank">${good.factory.websiteUrl!""}</a></div>
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
            <#if payChannel??>
	            <div class="mf_title">
	            	<div class="mf_logo">
	            		<img src="${payChannel.factory.logoFilePath!""}" width="89"/>
	            	</div>
	                <div class="mf_name">${payChannel.factory.name!""}</div>
	            </div>
	            <div class="mf_text">${payChannel.factory.description!""}<br /><a href="${payChannel.factory.websiteUrl!""}" target="_blank">${payChannel.factory.websiteUrl!""}</a></div>
	        </#if>
        </div>
        </div>
        <div class="hot_product">
        	<h1>推荐商品</h1>
        	<ul>
        		<#if good.relativeGoods??>
        			<#list good.relativeGoods as relativeGood>
        				<li>
		                	<div class="hotPro_img">
		                		<a href="#">
			                		<#if relativeGood.pictures??>
		                    			<#list relativeGood.pictures as picture>
		                    				<#if picture_index==0>
		                    					<!--<img src="images/c.jpg" />-->
		                    					<img src="${picture.urlPath}" style="width:130px;height:130px;" />
		                    				</#if>
		                    			</#list>
		                    		</#if>
	                    		</a>
		                	</div>
		                    <h2><a href="<@spring.url "/good/agent/${relativeGood.id}/detail" />">${relativeGood.title!""}</a></h2>
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
	
	function selectChannel(goodId,payChannelId){
		$("#payChannelId").val(payChannelId);	
		location.href='<@spring.url "" />'+'/good/agent/'+goodId+'/detail?payChannelId='+payChannelId;
	}
	
	function createOrder(id,type){
		var quantity = $("#quantity").val();
		var payChannelId=$("#payChannelId").val();
		if(""==payChannelId){
			alert("请选择支付通道");
			return;
		}
		location.href='<@spring.url "" />'+'/order/agent/create?goodId='+id+'&quantity='+quantity+'&payChannelId='+payChannelId+'&type='+type;
		//window.open('<@spring.url "" />'+'/order/agent/create?goodId='+id);
	}
	
	function goodCommentPageChange(page) {
		var id=$("#good_id").val()
	    $.get('<@spring.url "/good/agent/comment/"+id+"/page" />',
	            {"page": page
	            },
	            function (data) {
	                $('#page_fresh').html(data);
	            });
	}
</script>
</@c.html>