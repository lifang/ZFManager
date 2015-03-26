<div class="main mshop">
	<div class="box">
	<div class="category_group">
    	<div class="crumbs_nav">
        	<ul id="select_ul">
            	<li><a href="#">您当前选择</a><i></i></li>
            	 <#if goodBrandSelected??>
	                <li class="crumbs_nav_drop">
	                	<a href="#" class="hover" onclick="goodBrandRemove();">
		                <span class="cnd_p">POS机品牌：${goodBrandSelected.name!""}</span>
		                <span class="cnd_x"></span></a><i></i>
	                </li>
	             </#if>
	             <#if posCategorySelected??>
	                <li class="crumbs_nav_drop">
	                	<a href="#" class="hover" onclick="posCategoryRemove();">
		                <span class="cnd_p">POS机类型：${posCategorySelected.name!""}</span>
		                <span class="cnd_x"></span></a><i></i>
	                </li>
	             </#if>
	             <#if payChannelSelected??>
	                <li class="crumbs_nav_drop">
	                	<a href="#" class="hover" onclick="payChannelRemove();">
		                <span class="cnd_p">支付通道：${payChannelSelected.name!""}</span>
		                <span class="cnd_x"></span></a><i></i>
	                </li>
	             </#if>
	             <#if cardTypeSelected??>
	                <li class="crumbs_nav_drop">
	                	<a href="#" class="hover" onclick="cardTypeRemove();">
		                <span class="cnd_p">支持卡类型：${cardTypeSelected.cardType!""}</span>
		                <span class="cnd_x"></span></a><i></i>
	                </li>
	             </#if>
	             <#if dictionarySignOrderWaySelected??>
	                <li class="crumbs_nav_drop">
	                	<a href="#" class="hover" onclick="signOrderWayRemove();">
		                <span class="cnd_p">签购单方式：${dictionarySignOrderWaySelected.signOrderWay!""}</span>
		                <span class="cnd_x"></span></a><i></i>
	                </li>
	             </#if>
            </ul>
        </div>
    	<div class="category_item">
        	<h4>POS机品牌：</h4>
        	<input id="hidden_good_brand_id" type="hidden" name="hidden_good_brand_id" value="<#if goodBrandSelected??>${goodBrandSelected.id!""}</#if>" />
            <a href="javascript:void(0);" class="more">更多<i></i></a>
            <div class="category_item_con">
            	<ul>
            		<#if goodBrands??>
	                    <#list goodBrands as goodBrand>
	                    	<#if goodBrandSelected?? && goodBrandSelected.id=goodBrand.id>
	                    		<li><a href="#" class="hover" onclick="goodBrandSelect(${goodBrand.id!""});" id="good_brand_${goodBrand.id!""}">${goodBrand.name!""}</a></li>
		                    <#else>
		                    	<li><a href="#" onclick="goodBrandSelect(${goodBrand.id!""});" id="good_brand_${goodBrand.id!""}">${goodBrand.name!""}</a></li>
		                    </#if>
	                    </#list>
                    </#if>
                 </ul>
            </div>
        </div>
        <div class="category_item">
        	<h4>POS机类型：</h4>
        	<input id="hidden_pos_category_id" type="hidden" name="hidden_pos_category_id" value="<#if posCategorySelected??>${posCategorySelected.id!""}</#if>" />
            <div class="category_item_con">
            	<ul>
            		<#if posCategorys??>
	                    <#list posCategorys as posCategory>
	                    	<#if posCategorySelected?? && posCategorySelected.id=posCategory.id>
		                    	<li><a href="#" class="hover" onclick="posCategorySelect(${posCategory.id!""});" id="pos_category_${posCategory.id!""}">${posCategory.name!""}</a></li>
	                    	<#else>
		                    	<li><a href="#" onclick="posCategorySelect(${posCategory.id!""});" id="pos_category_${posCategory.id!""}">${posCategory.name!""}</a></li>
	                    	</#if>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="category_item">
        	<h4>支付通道：</h4>
        	<input id="hidden_pay_channel_id" type="hidden" name="hidden_pay_channel_id" value="<#if payChannelSelected??>${payChannelSelected.id!""}</#if>" />
            <div class="category_item_con">
            	<ul>
            		<#if payChannels??>
	                    <#list payChannels as payChannel>
	                    	<#if payChannelSelected?? && payChannelSelected.id=payChannel.id>
		                    	<li><a href="#" class="hover" onclick="payChannelSelect(${payChannel.id!""});" id="pay_channel_${payChannel.id!""}">${payChannel.name!""}</a></li>
	                    	<#else>
		                    	<li><a href="#" onclick="payChannelSelect(${payChannel.id!""});" id="pay_channel_${payChannel.id!""}">${payChannel.name!""}</a></li>
	                    	</#if>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="category_item">
        	<h4>支持卡类型：</h4>
        	<input id="hidden_card_type_id" type="hidden" name="hidden_card_type_id" value="<#if cardTypeSelected??>${cardTypeSelected.id!""}</#if>" />
            <div class="category_item_con">
            	<ul>
            		<#if dictionaryCardTypes??>
	                    <#list dictionaryCardTypes as dictionaryCardType>
	                    	<#if cardTypeSelected?? && cardTypeSelected.id=dictionaryCardType.id>
		                    	<li><a href="#" class="hover" onclick="cardTypeSelect(${dictionaryCardType.id!""});" id="card_type_${dictionaryCardType.id!""}">${dictionaryCardType.cardType!""}</a></li>
	                    	<#else>
		                    	<li><a href="#" onclick="cardTypeSelect(${dictionaryCardType.id!""});" id="card_type_${dictionaryCardType.id!""}">${dictionaryCardType.cardType!""}</a></li>
	                    	</#if>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="category_item">
        	<h4>支持交易类型：</h4>
            <div class="category_item_con">
            	<ul>
                	<#if dictionaryCardTypes??>
	                    <#list dictionaryTradeTypes as dictionaryTradeType>
		                    <li><a href="#">${dictionaryTradeType.tradeValue!""}</a></li>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="category_item category_item_noBorder">
        	<h4>签购单方式：</h4>
        	<input id="hidden_sign_order_way_id" type="hidden" name="hidden_sign_order_way_id" value="<#if dictionarySignOrderWaySelected??>${dictionarySignOrderWaySelected.id!""}</#if>" />
            <div class="category_item_con">
            	<ul>
            		<#if dictionarySignOrderWays??>
	                    <#list dictionarySignOrderWays as dictionarySignOrderWay>
	                    	<#if dictionarySignOrderWaySelected?? && dictionarySignOrderWaySelected.id=dictionarySignOrderWay.id>
		                    	<li><a href="#" class="hover" onclick="signOrderWaySelect(${dictionarySignOrderWay.id!""});" id="sign_order_way_${dictionarySignOrderWay.id!""}">${dictionarySignOrderWay.signOrderWay!""}</a></li>
	                    	<#else>
		                    	<li><a href="#" onclick="signOrderWaySelect(${dictionarySignOrderWay.id!""});" id="sign_order_way_${dictionarySignOrderWay.id!""}">${dictionarySignOrderWay.signOrderWay!""}</a></li>
	                    	</#if>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
    <div class="sortbar clear">
    	<div class="sortbar_ul">
            <ul>
                <li class="default_sort hover"><a href="javasrcipt:void(0);">综合排序</a></li>
                <li class="on_1"><span>按价格</span>
                    <div class="droplist">
                        <a href="javascript:void(0);" class="dashed">按价格从高到低</a>
                        <a href="javascript:void(0);">按价格从低到高</a>
                    </div>
                </li>
                <li class="default_sort" title="按销量从高到低"><a href="javascript:void(0);">按销量</a></li>
                <li class="default_sort" title="按评价从多到少"><a href="javascript:void(0);">按评价</a></li>
            </ul>
        </div>
        <div class="accountTime">
        	<label>到账时间</label>
            <div class="selectBox">
            	<i></i>
            	<div class="tag_select"><span>选择到账时间选择到账时间</span></div>
                <input name="" type="text" value="" class="tag_input">
                <ul>
                	<li>T+1<input name="" type="text" value="1"></li>
                	<li>T+2<input name="" type="text" value="2"></li>
                	<li>T+4<input name="" type="text" value="3"></li>
                	<li>T+3<input name="" type="text" value="4"></li>
            	</ul>
           </div>
        </div>
        <div class="price">
        	<label>价格</label>
            <input name="" type="text" placeholder="¥" /> - <input name="" type="text" placeholder="¥" />
        </div>
        <div class="lease"><input name="" type="checkbox" value="" /> 支持租赁</div>
        <div class="sortBtn"><button class="btn">确定</button></div>
        <div class="page_top">
        	<div class="page_info"><span>2</span>/20</div>
            <a href="javascript:void(0);" class="page_prev"><i></i></a>
            <a href="javascript:void(0);" class="page_next"><i></i></a>
        </div>
    </div>
    
    <div id="page_fresh">
      	<#include "pageGood.ftl" />
     </div>
    
</div>
</div>

