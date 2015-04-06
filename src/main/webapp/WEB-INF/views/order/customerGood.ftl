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
		        	<a href="<@spring.url "/good/agent/${orderGood.good.id}/detail" />" class="cn_img">
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
		                <h1><a href="<@spring.url "/good/user/${orderGood.good.id}/detail" />">${orderGood.good.title!""}</a></h1>
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
		    <td><a href="#"><strong>￥${(orderGood.good.price/100)?string("0.00")}</strong></a></td>
		    <td><div class="choose_amount"><a href="javascript:void(0);" onclick="reduceQuantity();">-</a><input id="quantity_${orderGood.good.id}" type="text" value="${orderGood.quantity!""}" /><a href="javascript:void(0);"  onclick="addQuantity(${orderGood.good.id});">+</a></div></td>
		    <td><a href="#"><strong>￥${(orderGood.good.price*orderGood.quantity/100)?string("0.00")}</strong></a></td>
		  </tr>
		 </#list>
	  </#if>
	</table>
<#else>
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
	            <a href="<@spring.url "/good/user/${good.id}/detail" />" class="cn_img">
	            	<#if good.pictures??>
	                 	<#list good.pictures as picture>
	                 		<#if picture_index==1>
	                 			<img src="${picture.urlPath}"  style="width:130px;height:130px;"/>
	                 		</#if>
	                 	</#list>
	                 </#if>
	            </a>
	            <div class="td_proBox_info">
	                <h1><a href="<@spring.url "/good/user/${good.id}/detail" />">${good.title!""}</a></h1>
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
	    <td><a href="#"><strong>￥${(good.price/100)?string("0.00")}</strong></a></td>
	    <td><div class="choose_amount"><a href="javascript:void(0);" onclick="reduceQuantity();">-</a><input id="quantity_${good.id}" type="text" value="${quantity!""}" /><a href="javascript:void(0);"  onclick="addQuantity(${good.id});">+</a></div></td>
	    <td><a href="#"><strong>￥${(good.price*quantity/100)?string("0.00")}</strong></a></td>
	  </tr>
	</table>
</#if>	
<script type="text/javascript">	
	function addQuantity(goodId) {
		var quantity = $("#quantity_"+goodId).val();
		$("#quantity_"+goodId).val(parseInt(quantity)+1);
	}
	
	function reduceQuantity(goodId) {
		var quantity = $("#quantity_"+goodId).val();
		var result=parseInt(quantity)-1;
		if(result<1)result=1;
		$("#quantity_"+goodId).val(result);
	}
</script>	