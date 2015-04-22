  <tr>
    <td>
    	<div class="td_proBox clear">
        	<a href="#" class="cn_img">
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
            	<h1><a href="<@spring.url "/good/user/${good.id}/detail" />">${good.title!""}</a></h1>
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
    <td><a href="#"><strong>￥${(good.retailPriceDisplay/100)?string("0.00")}</strong></a></td>
    <td><a href="#">月销量<em>${good.volumeNumber!""}</em>件</a></td>
    <td>
    	<div class="evaluate">
        	<a href="#">
            <ul>
            	<#if good.totalScore!=0&&good.totalComment!=0>
            		<#if good.totalScore/good.totalComment gte 5>
            			<li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li>
            		<#elseif good.totalScore/good.totalComment gte 4>	
            			<li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li></li>
            		<#elseif good.totalScore/good.totalComment gte 3>	
            			<li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li></li><li></li>
            		<#elseif good.totalScore/good.totalComment gte 2>	
            			<li class="p_li_o"></li><li class="p_li_o"></li><li></li><li></li><li></li>
            		<#elseif good.totalScore/good.totalComment gte 1>	
            			<li class="p_li_o"></li><li></li><li></li><li></li><li></li>
            		<#else>
            			<li></li><li></li> <li></li> <li></li> <li></li>
            		</#if>
            	<#else>
            		<li></li> <li></li> <li></li> <li></li> <li></li>
            	</#if>
            </ul>
            </a>
        </div>
    </td>
  </tr>