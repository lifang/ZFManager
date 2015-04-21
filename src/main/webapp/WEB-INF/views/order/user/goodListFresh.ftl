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
	             <#if tradeTypeSelected??>
	                <li class="crumbs_nav_drop">
	                	<a href="#" class="hover" onclick="tradeTypeRemove();">
		                <span class="cnd_p">支持交易类型：${tradeTypeSelected.tradeValue!""}</span>
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
             <a href="javascript:void(0);" class="more">更多<i></i></a>
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
             <a href="javascript:void(0);" class="more">更多<i></i></a>
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
             <a href="javascript:void(0);" class="more">更多<i></i></a>
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
        	<input id="hidden_trade_type_id" type="hidden" name="hidden_trade_type_id" value="<#if tradeTypeSelected??>${tradeTypeSelected.id!""}</#if>" />
             <a href="javascript:void(0);" class="more">更多<i></i></a>
            <div class="category_item_con">
            	<ul>
                	<#if dictionaryCardTypes??>
	                    <#list dictionaryTradeTypes as dictionaryTradeType>
	                    	<#if tradeTypeSelected?? && tradeTypeSelected.id=dictionaryTradeType.id>
		                    	<li><a href="#" class="hover" onclick="tradeTypeSelect(${dictionaryTradeType.id!""});" id="trade_type_${dictionaryTradeType.id!""}">${dictionaryTradeType.tradeValue!""}</a></li>
	                    	<#else>
		                    	<li><a href="#" onclick="tradeTypeSelect(${dictionaryTradeType.id!""});" id="trade_type_${dictionaryTradeType.id!""}">${dictionaryTradeType.tradeValue!""}</a></li>
	                    	</#if>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="category_item category_item_noBorder">
        	<h4>签购单方式：</h4>
        	<input id="hidden_sign_order_way_id" type="hidden" name="hidden_sign_order_way_id" value="<#if dictionarySignOrderWaySelected??>${dictionarySignOrderWaySelected.id!""}</#if>" />
             <a href="javascript:void(0);" class="more">更多<i></i></a>
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
        	<input id="orderBy" type="hidden" name="orderBy" value="${orderBy!""}" />
        	<input id="orderType" type="hidden" name="orderType" value="${orderType!""}" />
            <ul>
        		<#if !orderBy?? || orderBy=='created_at'><li class="default_sort hover"><#else><li class="default_sort"></#if>
        			<a href="#" onclick="orderSelect('created_at','desc');">综合排序</a></li>
                <#if orderBy?? && orderBy=='price'><li class="on_1 hover"><#else><li class="on_1"></#if>
                	<span>按价格</span>
                    <div style="display: none;" class="droplist">
                        <a href="javascript:void(0);" class="dashed" onclick="orderSelect('price','desc');">按价格从高到低</a>
                        <a href="javascript:void(0);" onclick="orderSelect('price','asc');">按价格从低到高</a>
                    </div>
                </li>
                <#if orderBy?? && orderBy=='volume_number'><li class="default_sort hover" title="按销量从高到低"><#else><li class="default_sort" title="按销量从高到低"></#if>
                	<a href="javascript:void(0);" onclick="orderSelect('volume_number','desc');">按销量</a></li>
                <#if orderBy?? && orderBy=='total_score'><li class="default_sort hover" title="按评价从多到少"><#else><li class="default_sort" title="按评价从多到少"></#if>
                	<a href="javascript:void(0);" onclick="orderSelect('total_score','desc');">按评价</a></li>
            </ul>
        </div>
        <div class="accountTime">
        	<label>到账时间</label>
            <div class="selectBox">
            	<i></i>
            	<div class="tag_select"><span><#if dictionaryBillingCycleSelected??>${dictionaryBillingCycleSelected.name!""}<#else>选择到账时间选择到账时间</#if></span></div>
                <input name="" type="text" value="<#if dictionaryBillingCycleSelected??>${dictionaryBillingCycleSelected.name!""}</#if>" class="tag_input">
                <input id="billingCycleId" type="hidden" name="" value="<#if dictionaryBillingCycleSelected??>${dictionaryBillingCycleSelected.id!""}</#if>" />
                <ul>
                	<#if dictionaryBillingCycles??>
                		<#list dictionaryBillingCycles as dictionaryBillingCycle>
	                		<li onclick="billingCycleSelect(${dictionaryBillingCycle.id!""});">${dictionaryBillingCycle.name!""}<input name="" type="text" value="${dictionaryBillingCycle.id!""}"></li>
	                	</#list>
                	</#if>
            	</ul>
           </div>
        </div>
        <div class="price">
        	<label>价格</label>
            <input id="minPrice" name="" type="text" placeholder="¥" value="${minPrice!""}" /> - <input id="maxPrice" name="" type="text" placeholder="¥" value="${maxPrice!""}"/>
        </div>
        <div class="lease">
        	<#if hasLease??&&hasLease>
        		<input id="hasLease" name="" type="checkbox" value="" checked="true"/>
        	<#else>
        		<input id="hasLease" name="" type="checkbox" value="" />
        	</#if> 
        	支持租赁
        </div>
        <div class="sortBtn"><button class="btn" onclick="goodPageChange(1);">确定</button></div>
        <div class="page_top">
        	<div class="page_info"><span>${goods.currentPage!""}</span>/${goods.totalPage!""}</div>
            <a href="javascript:void(0);" class="page_prev" onclick="goodPageChange(${goods.currentPage!""}-1);"><i></i></a>
            <a href="javascript:void(0);" class="page_next" onclick="goodPageChange(${goods.currentPage!""}+1);"><i></i></a>
        </div>
    </div>
    
    <div id="page_fresh">
      	<#include "pageGood.ftl" />
     </div>
    
</div>
</div>
<script type="text/javascript">
		$(function(){
			$(".sortbar li").hover(
				function(){
					$(this).find(".droplist").css("display","block");
				},
				function(){
					$(this).find(".droplist").css("display","none");
				}
			)
		});
		//商品分类 category_item_con
		$(function(){
			var a=1;
			$(".category_item a.more").click(function(){
				if(a==1){
					$(this).parent(".category_item").addClass("category_item_maxHeight");
					$(this).addClass("up").html("收起<i></i>");
					a=0;
				}else if(a==0){
					$(this).parent(".category_item").removeClass("category_item_maxHeight");
					$(this).removeClass("up").html("更多<i></i>");
					a =1;
				}
				
			});
			
		});
		
		//selectBox div模拟select
		$(function(){
			$(".tag_select").click(function() {
				$(this).parent(".selectBox").find("ul").toggle();
			});
			$(".selectBox ul li").click(function() {
				var text = $(this).html();
				var $val = $(this).find("input").val();
				$(this).parents(".selectBox").find(".tag_select span").html(text);
				$(this).parents(".selectBox").find("input.tag_input").val($val);
				
				$(this).parents(".selectBox").find("ul").hide();
			});
			
			$(document).bind('click', function(e) {
				var $clicked = $(e.target);
				if (! $clicked.parents().hasClass("selectBox"))
				$(".selectBox ul").hide();
				
			});
		});
		
		function billingCycleSelect(id){
			$("#billingCycleId").val(id);
		}
</script>
