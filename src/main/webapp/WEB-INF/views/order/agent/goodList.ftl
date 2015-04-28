<#import "../commonOrder.ftl" as c />
<@c.html>
<div id="page_fresh">
	<#include "goodListFresh.ftl" />
</div>
<script type="text/javascript">
	function goodPageChange(page) {
			var goodBrandsId = $("#hidden_good_brand_id").val();
			var posCategoryId = $("#hidden_pos_category_id").val();
			var payChannelId=$("#hidden_pay_channel_id").val();
			var signOrderWayId=$("#hidden_sign_order_way_id").val();
			var cardTypeId=$("#hidden_card_type_id").val();
			var tradeTypeId=$("#hidden_trade_type_id").val();
			var billingCycleId=$("#billingCycleId").val();
			var minPrice=$("#minPrice").val();
			var maxPrice=$("#maxPrice").val();
			var hasLease=$("#hasLease").is(':checked');
			var orderBy=$("#orderBy").val();
			var orderType=$("#orderType").val();
		    $.post('<@spring.url "/good/agent/page" />',
		            {"page": page,
		             "goodBrandsId": goodBrandsId,
		             "posCategoryId": posCategoryId,
		             "payChannelId": payChannelId,
		             "cardTypeId": cardTypeId,
		             "tradeTypeId": tradeTypeId,
		             "signOrderWayId":signOrderWayId,
		             "billingCycleId": billingCycleId,
		             "minPrice": minPrice,
		             "maxPrice": maxPrice,
		             "hasLease":hasLease,
		             "orderBy":orderBy,
		             "orderType":orderType
		            },
		            function (data) {
		                $('#page_fresh').html(data);
		            });
		      
		}
		
	function goodBrandSelect(id){
		//var goodBrandsIdLast = $("#hidden_good_brand_id").val();
		$("#hidden_good_brand_id").val(id);
		//$("#good_brand_"+id).addClass("hover");
		//$("#good_brand_"+goodBrandsIdLast).removeClass("hover");
		//add("good_brand",id,goodBrandsIdLast,brandName);
		goodPageChange(1);
	}
	
	function goodBrandRemove(){
		$("#hidden_good_brand_id").val("");
		goodPageChange(1);
	}
	
	
	function posCategorySelect(id){
		$("#hidden_pos_category_id").val(id);
		goodPageChange(1);
	}
	function posCategoryRemove(){
		$("#hidden_pos_category_id").val("");
		goodPageChange(1);
	}
	
	function payChannelSelect(id){
		$("#hidden_pay_channel_id").val(id);
		goodPageChange(1);
	}
	function payChannelRemove(){
		$("#hidden_pay_channel_id").val("");
		goodPageChange(1);
	}
	
	function cardTypeSelect(id){
		$("#hidden_card_type_id").val(id);
		goodPageChange(1);
	}
	function cardTypeRemove(){
		$("#hidden_card_type_id").val("");
		goodPageChange(1);
	}
	
	function tradeTypeSelect(id){
		$("#hidden_trade_type_id").val(id);
		goodPageChange(1);
	}
	function tradeTypeRemove(){
		$("#hidden_trade_type_id").val("");
		goodPageChange(1);
	}
	
	function signOrderWaySelect(id){
		$("#hidden_sign_order_way_id").val(id);
		goodPageChange(1);
	}
	function signOrderWayRemove(){
		$("#hidden_sign_order_way_id").val("");
		goodPageChange(1);
	}
	
	function orderSelect(orderBy,orderType){
		$("#orderBy").val(orderBy);
		$("#orderType").val(orderType);
		goodPageChange(1);
	}
	
</script>
</@c.html>