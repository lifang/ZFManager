  <tr>
    <td>
    	<div class="td_proBox clear">
        	<a href="<@spring.url "/good/batch/${good.id}/detail" />" class="cn_img">
        		<#if good.pictures??>
        			<#list good.pictures as picture>
        				<#if picture_index==0>
        					<!--<img src="images/c.jpg" />-->
        					<img src="${picture.urlPath}" style="width:130px;height:130px;" />
        				</#if>
        			</#list>
        		</#if>
        	</a>
            <div class="td_proBox_info">
            	<h1><a href="<@spring.url "/good/batch/${good.id}/detail" />">${good.title!""}</a></h1>
                <h3>${good.secondTitle!""}</h3>
                <ul>
                	<li><span>品牌型号：</span><div class="c_text"><#if good.goodBrand??>${good.goodBrand.name!""}</#if></div></li>
                    <li>
                    	<span>支付通道：</span>
                    	<#if good.channels??>
                    		<#list good.channels as channel>
                    			<div class="c_text">${channel.name!""}&nbsp;</div>
                    		</#list>
                    	</#if>
                    </li>
                </ul>
            </div>
        </div>
    </td>
    <td><a href="#">
    		<strong>￥${((good.purchasePriceDisplay!0)/100)?string("0.00")}</strong>
    		<p class="original">零售价：￥${((good.retailPriceDisplay!0)/100)?string("0.0")}</p>
    	</a>
    </td>
    <td><a href="#"><em>${good.floorPurchaseQuantity!""}</em>件</a></td>
    <td><a href="#">月销量<em>${good.purchaseNumber!""}</em>件</a></td>
  </tr>