<#if order??>
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
	 <#if order.orderGoods??>
	 	<#list order.orderGoods as orderGood>
		  <tr>
		    <td>
		        <div class="td_proBox">
		        	<#if type==1|| type==2>
		        		<a href="<@spring.url "/good/user/${orderGood.good.id}/detail" />" class="cn_img">
		        	<#elseif type==3||type==4>
		        		<a href="<@spring.url "/good/agent/${orderGood.good.id}/detail" />" class="cn_img">
		        	<#elseif type==5>
		        		<a href="<@spring.url "/good/batch/${orderGood.good.id}/detail" />" class="cn_img">
		        	</#if>
                    	<#if orderGood.good??>
                    		<#if orderGood.good.pictures??>
                    			<#list orderGood.good.pictures as picture>
                    				<#if picture_index==0>
                    					<!--<img src="<@spring.url "${picture.urlPath}"/>" />-->
                    					<img src="${picture.urlPath}" style="width:130px;height:130px;" />
                    				</#if>
                    			</#list>
                    		</#if>
                    	</#if>
                    </a>
		            <div class="td_proBox_info">
		                <h1>
		                	<#if type==1|| type==2>
				        		<a href="<@spring.url "/good/user/${orderGood.good.id}/detail" />">
				        	<#elseif type==3||type==4>
				        		<a href="<@spring.url "/good/agent/${orderGood.good.id}/detail" />">
				        	<#elseif type==5>
				        		<a href="<@spring.url "/good/batch/${orderGood.good.id}/detail" />">
				        	</#if>
			                		${orderGood.good.title!""}
			                	</a>
		                </h1>
		                <h3>${orderGood.good.secondTitle!""}</h3>
		                <ul>
		                    <li><span>品牌型号：</span><div class="c_text"><#if orderGood.good.goodBrand??>${orderGood.good.goodBrand.name!""}</#if> ${orderGood.good.modelNumber!""}</div></li>
		                    <li><span>支付通道：</span>
		                    	<div class="c_text">
		                    		<#if payChannel??>${payChannel.name!""}</#if>
		                    		<input id="payChannelId" type="hidden" name="payChannelId" value="<#if payChannel??>${payChannel.id!""}</#if>" />
		                    	</div>
		                    	
		                    </li>
		                    <li><span>月租金：</span><div class="c_text">￥${(orderGood.good.leasePrice/100)?string("0.00")}</div></li>
		                    <li><span>最短租赁：</span><div class="c_text">${orderGood.good.leaseTime!""}个月</div></li>
		                    <li><span>最上租赁：</span><div class="c_text">${orderGood.good.returnTime!""}个月</div></li>
		                </ul>
		            </div>
		        </div>
		    </td>
		    <#if orderGood.good?? && orderGood.payChannel??>
		    	<#if type==2||type==4>
		    		<td><a href="#"><strong>￥${(((orderGood.good.leaseDeposit!0)+(orderGood.payChannel.openingCost!0))/100)?string("0.0")}</strong></a></td>
				    <td>
				    	<div class="choose_amount">
				    		<a href="javascript:void(0);" onclick="reduceQuantityEx(${orderGood.good.id},${((orderGood.good.leaseDeposit!0)+(orderGood.payChannel.openingCost!0))});">-</a>
				    		<input id="quantity_${orderGood.good.id}" name="quantity" type="text" value="${orderGood.quantity!""}" />
				    		<input id="floorPurchaseQuantity_${orderGood.good.id}" type="hidden" name="floorPurchaseQuantity" value="${orderGood.good.floorPurchaseQuantity!0}" />
				    		<input id="" type="hidden" name="price" value="${orderGood.good.leaseDeposit!""}" />
				    		<a href="javascript:void(0);"  onclick="addQuantityEx(${orderGood.good.id},${((orderGood.good.leaseDeposit!0)+(orderGood.payChannel.openingCost!0))});">+</a>
				    	</div>
				    </td>
				    <td><a href="#"><strong id="goodPriceStrong_${orderGood.good.id}">￥${(((orderGood.good.leaseDeposit!0)+(orderGood.payChannel.openingCost!0))*orderGood.quantity/100)?string("0.00")}</strong></a></td>
		    	<#else>
				    <td><a href="#"><strong>￥${(((orderGood.good.retailPrice!0)+(orderGood.payChannel.openingCost!0))/100)?string("0.0")}</strong></a></td>
				    <td>
				    	<div class="choose_amount">
				    		<a href="javascript:void(0);" onclick="reduceQuantityEx(${orderGood.good.id},${((orderGood.good.retailPrice!0)+(orderGood.payChannel.openingCost!0))});">-</a>
				    		<input id="quantity_${orderGood.good.id}" name="quantity" type="text" value="${orderGood.quantity!""}" />
				    		<input id="floorPurchaseQuantity_${orderGood.good.id}" type="hidden" name="floorPurchaseQuantity" value="${orderGood.good.floorPurchaseQuantity!0}" />
				    		<input id="" type="hidden" name="price" value="${orderGood.good.retailPrice!""}" />
				    		<a href="javascript:void(0);"  onclick="addQuantityEx(${orderGood.good.id},${((orderGood.good.retailPrice!0)+(orderGood.payChannel.openingCost!0))});">+</a>
				    	</div>
				    </td>
				    <td><a href="#"><strong id="goodPriceStrong_${orderGood.good.id}">￥${(((orderGood.good.retailPrice!0)+(orderGood.payChannel.openingCost!0))*orderGood.quantity/100)?string("0.00")}</strong></a></td>
				</#if>
			 </#if>
		  </tr>
		 </#list>
	  </#if>
	</table>
<#else>
	<input id="goodId" type="hidden" name="goodId" value="${good.id}" />
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
	        	<#if type==1|| type==2>
	        		<a href="<@spring.url "/good/user/${good.id}/detail" />" class="cn_img">
	        	<#elseif type==3||type==4>
	        		<a href="<@spring.url "/good/agent/${good.id}/detail" />" class="cn_img">
	        	<#elseif type==5>
	        		<a href="<@spring.url "/good/batch/${good.id}/detail" />" class="cn_img">
	        	</#if>
	            	<#if good.pictures??>
	                 	<#list good.pictures as picture>
	                 		<#if picture_index==1>
	                 			<img src="${picture.urlPath}"  style="width:130px;height:130px;"/>
	                 		</#if>
	                 	</#list>
	                 </#if>
	            </a>
	            <div class="td_proBox_info">
	                <h1>
	                	<#if type==1|| type==2>
			        		<a href="<@spring.url "/good/user/${good.id}/detail" />">
			        	<#elseif type==3||type==4>
			        		<a href="<@spring.url "/good/agent/${good.id}/detail" />">
			        	<#elseif type==5>
			        		<a href="<@spring.url "/good/batch/${good.id}/detail" />">
			        	</#if>
		                		${good.title!""}
		                	</a>
	                </h1>
	                <h3>${good.secondTitle!""}</h3>
	                <ul>
	                    <li><span>品牌型号：</span><div class="c_text"><#if good.goodBrand??>${good.goodBrand.name!""}</#if> ${good.modelNumber!""}</div></li>
	                    <li><span>支付通道：</span>
	                    	<div class="c_text">
	                    		<#if payChannel??>${payChannel.name!""}</#if>
	                    		<input id="payChannelId" type="hidden" name="payChannelId" value="<#if payChannel??>${payChannel.id!""}</#if>" />
	                    	</div>
	                    	
	                    </li>
	                    <li><span>月租金：</span><div class="c_text">￥${(good.leasePrice/100)?string("0.00")}</div></li>
	                    <li><span>最短租赁：</span><div class="c_text">${good.leaseTime!""}个月</div></li>
	                    <li><span>最上租赁：</span><div class="c_text">${good.returnTime!""}个月</div></li>
	                </ul>
	            </div>
	        </div>
	    </td>
	    <#if payChannel??>
	    	<#if type==2 || type==4>
	    		<td><a href="#"><strong>￥${(((good.leaseDeposit!0)+(payChannel.openingCost!0))/100)?string("0.0")}</strong></a></td>
			    <td>
			    	<div class="choose_amount">
			    		<a href="javascript:void(0);" onclick="reduceQuantity(${good.id},${((good.leaseDeposit!0)+(payChannel.openingCost!0))});">-</a>
			    		<input id="quantity_${good.id}" name="quantity" type="text" value="${quantity!""}" />
			    		<input id="floorPurchaseQuantity_${good.id}" type="hidden" name="floorPurchaseQuantity" value="${good.floorPurchaseQuantity!0}" />
			    		<a href="javascript:void(0);"  onclick="addQuantity(${good.id},${((good.leaseDeposit!0)+(payChannel.openingCost!0))});">+</a>
			    	</div>
			    </td>
			    <td><a href="#"><strong id="goodPriceStrong_${good.id}">￥${(((good.leaseDeposit!0)+(payChannel.openingCost!0))*quantity/100)?string("0.00")}</strong></a></td>
	    	<#else>
			    <td><a href="#"><strong>￥${(((good.retailPrice!0)+(payChannel.openingCost!0))/100)?string("0.0")}</strong></a></td>
			    <td>
			    	<div class="choose_amount">
			    		<a href="javascript:void(0);" onclick="reduceQuantity(${good.id},${((good.retailPrice!0)+(payChannel.openingCost!0))});">-</a>
			    		<input id="quantity_${good.id}" name="quantity" type="text" value="${quantity!""}" />
			    		<input id="floorPurchaseQuantity_${good.id}" type="hidden" name="floorPurchaseQuantity" value="${good.floorPurchaseQuantity!0}" />
			    		<a href="javascript:void(0);"  onclick="addQuantity(${good.id},${((good.retailPrice!0)+(payChannel.openingCost!0))});">+</a>
			    	</div>
			    </td>
			    <td><a href="#"><strong id="goodPriceStrong_${good.id}">￥${(((good.retailPrice!0)+(payChannel.openingCost!0))*quantity/100)?string("0.00")}</strong></a></td>
		    </#if>
		</#if>
	  </tr>
	</table>
</#if>	
<script type="text/javascript">	
	function addQuantity(goodId,price) {
		var quantity = parseInt($("#quantity_"+goodId).val())+1;
		var price=Math.round(price*quantity)/100;
		var goodPriceStrong="￥"+price;
		$("#totalStrong").html(goodPriceStrong);
		$("#actualStrong").html(goodPriceStrong);
		$("#goodPriceStrong_"+goodId).html(goodPriceStrong);
		$("#quantity_"+goodId).val(parseInt(quantity));
	}
	
	function reduceQuantity(goodId,price) {
		var quantity = $("#quantity_"+goodId).val();
		var result=parseInt(quantity)-1;
		if(result<1)result=1;
		var price=Math.round(price*result)/100;
		var goodPriceStrong="￥"+price;
		$("#totalStrong").html(goodPriceStrong);
		$("#actualStrong").html(goodPriceStrong);
		$("#goodPriceStrong_"+goodId).html(goodPriceStrong);
		$("#quantity_"+goodId).val(result);
	}
	
	function addQuantityEx(goodId,price) {
		var allPrice=document.getElementsByName("price");
		var allQuantity=document.getElementsByName("quantity");
		var total=price;
		for(var i=0,size=allPrice.length;i<size;i++){
			total+=allPrice[i].value*allQuantity[i].value;
		}
		total="￥"+Math.round(total)/100
	
		var quantity = parseInt($("#quantity_"+goodId).val())+1;
		var price=Math.round(price*quantity)/100;
		var goodPriceStrong="￥"+price;

		
		$("#totalStrong").html(total);
		$("#actualStrong").html(total);
		$("#goodPriceStrong_"+goodId).html(goodPriceStrong);
		$("#quantity_"+goodId).val(parseInt(quantity));
	}
	
	
	function reduceQuantityEx(goodId,price) {
		var quantity = $("#quantity_"+goodId).val();
		var result=parseInt(quantity)-1;
		var total=price*-1;
		if(result<1){
			result=1;
			total=0;
		}
		var allPrice=document.getElementsByName("price");
		var allQuantity=document.getElementsByName("quantity");
		
		for(var i=0,size=allPrice.length;i<size;i++){
			total+=allPrice[i].value*allQuantity[i].value;
		}
		total="￥"+Math.round(total)/100
		
		var price=Math.round(price*result)/100;
		var goodPriceStrong="￥"+price;
		$("#totalStrong").html(total);
		$("#actualStrong").html(total);
		$("#goodPriceStrong_"+goodId).html(goodPriceStrong);
		$("#quantity_"+goodId).val(result);
	}
</script>	