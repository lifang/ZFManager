<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb"> 
	<ul> 
    	<li>售后</li> 
    	<li><a href="<@spring.url "/cs/repair/list"/>">维修</a></li> 
	</ul> 
</div> 

<div class="content clear"> 
	<div class="user_title">
    	<h1>维修申请列表</h1> 
		<div class="userTopBtnBox">
	<#if Roles.hasRole("CS_REPAIR_ASSIGN")><a id="btn_dispatch" class="ghostBtn assign_a">分派</a></#if>
	<#if Roles.hasRole("CS_REPAIR_CREATE")><a class="ghostBtn" href="<@spring.url "/cs/repair/bill/edit"/>">创建维修单</a></#if>
		</div> 
	</div>
	<div class="seenBox clear"> 
		<ul> 
			<li>
				<div class="user_search">
					<input id="search_keyword" name="" type="text" class="" placeholder="请输入售后单号"/>
					<button id="btn_search"></button>
				</div>
			</li> 
        	<li>
				<div class="user_select"> 
					<label>状态筛选</label> 
					<select id="select_status"> 
						<option value="-1">全部</option> 
						<option value="1">未付款</option>
						<option value="2">待发回</option> 
						<option value="3">维修中</option> 
						<option value="4">处理完成</option> 
						<option value="5">已取消</option> 
					</select> 
				</div>
			</li> 
		</ul> 
	</div> 
	<div id="page_fresh">
		<#include "table.ftl" />
	</div>
</div>

<div class="tab priceOrder_tab">
	<a href="#" class="close">关闭</a>
	<div class="tabHead">修改维修价格</div>
	<div class="tabBody">
		<div class="item_list">
			<ul>
				<li><span class="labelSpan">原价格</span>
				<div class="text">
						<strong id="update_repair_price"></strong>
					</div></li>
				<li><span class="labelSpan">新价格</span>
				<div class="text">
						<input name="update_repair_price" type="text" />
					</div></li>
			</ul>
		</div>
	</div>
	<div class="tabFoot">
		<button class="blueBtn" onClick="onUpdatePay();">确定</button>
	</div>
</div>

<div class="tab paymentRecord_tab">
	<a href="#" class="close">关闭</a>
	<div class="tabHead">添加付款记录</div>
	<div class="tabBody">
		<div class="item_list">
			<ul>
				<li><span class="labelSpan">付款金额</span>
				<div class="text">
						<strong id="add_repair_price"></strong>
					</div></li>
				<li><span class="labelSpan">付款方式</span>
				<div class="text">
						<select id="repair_price_type" name="">
							<option value="1">支付宝</option>
							<option value="2">银联</option>
							<option value="3">现金</option>
						</select>
					</div></li>
			</ul>
		</div>
	</div>
	<div class="tabFoot">
		<button class="blueBtn" onClick="onAddPay();">确定</button>
	</div>
</div>

<script type="text/javascript">

	var keyword;
	var status;
	var currentPage;

	$(function(){
		$('#select_status').change(function(){
			status = $(this).children('option:selected').val();
			pageChange(1);
		});
		
		$("#btn_search").bind("click", function() {
			keyword = $("#search_keyword").val();
			pageChange(1);
	    });
	    
	    $('#search_keyword').keydown(function(e){
			if(e.keyCode==13){
   				keyword = $("#search_keyword").val();
				pageChange(1);
			}
		});
	});
	
	function pageChange(page) {
		currentPage = page;
	
	    $.get('<@spring.url "/cs/repair/page" />',
	            {"page": page,
	            "keyword": keyword,
	            "status": status},
	            function (data) {
	                $('#page_fresh').html(data);
	            });
	}
	
	function onCancel(csRepairId) {
		$.post('<@spring.url "" />'+'/cs/repair/'+csRepairId+'/cancel',
	            {}, function (data) {
	            	$("#operation_"+csRepairId).html('<a href="<@spring.url "" />'+'/cs/repair/'+csRepairId+'/info" class="a_btn">查看详情</a>');
	            	$("#status_"+csRepairId).text("已取消");
	            });
	}
	
	function onFinish(csRepairId) {
		$.post('<@spring.url "" />'+'/cs/repair/'+csRepairId+'/finish',
	            {}, function (data) {
	            	$("#operation_"+csRepairId).html('<a href="<@spring.url "" />'+'/cs/repair/'+csRepairId+'/info" class="a_btn">查看详情</a>');
	            	$("#status_"+csRepairId).text("处理完成");
	            });
	}
	
	function onHandle(csRepairId) {
		$.post('<@spring.url "" />'+'/cs/repair/'+csRepairId+'/handle',
	            {}, function (data) {
	            	$("#operation_"+csRepairId).html(
	            		'<a href="<@spring.url "" />'+'/cs/repair/'+csRepairId+'/info" class="a_btn">查看详情</a>'
						+'<a class="a_btn" onClick="onFinish('+csRepairId+');">标记为维修完成</a>	'
	            	);
	            	$("#status_"+csRepairId).text("维修中");
	            });
	}

	var repairId;

	function onPreAddPay(csRepairId, repairPrice, payType) {
		repairId = csRepairId;
		$('#add_repair_price').text('￥'+repairPrice / 100);
		$("#repair_price_type").val(payType);
	}
	
	function onAddPay() {
		var payType = $("#repair_price_type").val();
		$.post('<@spring.url "" />'+'/cs/repair/'+repairId+'/addPay',
			{'payType':payType}, function(){
				$(".paymentRecord_tab").css('display','none');
				$(".mask").css('display','none');
				pageChange(currentPage);
			});
	}
	
	function onPreUpdatePay(csRepairId, repairPrice) {
		repairId = csRepairId;
		$('#update_repair_price').text('￥'+repairPrice / 100);
	}
	
	function onUpdatePay() {
		var repairPrice = $("input[name='update_repair_price']").val();
		repairPrice *= 100;
		$.post('<@spring.url "" />'+'/cs/repair/'+repairId+'/updatePay',
			{'repairPrice':repairPrice}, function(){
				$(".priceOrder_tab").css('display','none');
				$(".mask").css('display','none');
				$("input[name='update_repair_price']").val("");
				pageChange(currentPage);
			});
	}

</script>	
</@c.html>