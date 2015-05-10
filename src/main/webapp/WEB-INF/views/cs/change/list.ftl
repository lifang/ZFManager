<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb"> 
	<ul> 
    	<li>售后</li> 
    	<li><a href="<@spring.url "/cs/change/list"/>">换货</a></li> 
	</ul> 
</div> 

<div class="content clear"> 
	<div class="user_title">
    	<h1>换货申请列表</h1> 
		<div class="userTopBtnBox">
	<#if Roles.hasRole("CS_CHANGE_ASSIGN")>
			<a id="btn_dispatch" class="ghostBtn assign_a">分派</a>
	</#if>
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
						<option value="1">待处理</option> 
						<option value="2">换货中</option> 
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

<div class="tab replace_tab">
	<a href="" class="close">关闭</a>
	<div class="tabHead">退换地址电话</div>
	<div class="tabBody">
		<div class="item_list">
			<ul>
				<li>
					<span class="labelSpan">收件人：</span>
					<div class="text"><input name="receiver" type="text" /></div>
				</li>
				<li>
					<span class="labelSpan">电话：</span>
					<div class="text"><input name="phone" type="text" /></div>
				</li>
				<li>
					<span class="labelSpan">邮编：</span>
					<div class="text"><input name="zipCode" type="text" /></div>
				</li>
				<li>
					<span class="labelSpan">地址：</span>
					<div class="text"><input name="address" type="text" /></div>
				</li>
			</ul>
		</div>
	</div>
	<div class="tabFoot">
		<button class="blueBtn" onClick="onConfirm();">确定</button>
	</div>
</div>

<div class="tab exchangeGoods_tab">
	<a class="close">关闭</a>
	<div class="tabHead">添加换货出库记录</div>
	<div class="tabBody">
		<div style="margin:-10px 0px 5px 0px;"><font id="errMsg" color="red"></font></div>
		<textarea id="output_content" name="" cols="40" rows="2" class="textarea_pe" style="padding:5px;font-size:13px;" placeholder="请输入终端号，以逗号(,)分隔"></textarea>
	</div>
	<div class="user_select"> 
		<label>请选择支付通道</label> 
		<select id="payChannelSelect">
		<#if (payChannelList)??>
			<#list payChannelList as one>
				<option value="${one.id}">${one.name}</option> 
			</#list>
		</#if>
		</select> 
	</div>
	<div class="tabFoot">
		<button class="blueBtn" onClick="onOutput();">确定</button>
	</div>
</div>
<script type="text/javascript">
	var keyword;
	var status;
	var outputId;
	function onPreOutput(csChangeId) {
		outputId = csChangeId;
		$("#errMsg").html("");
		$("#output_content").val("");
		$(".exchangeGoods_tab").show();
		var doc_height = $(document).height();
		$(".mask").css({display:'block',height:doc_height});
	}
	
	function onOutput() {
		var payChannelId=$("#payChannelSelect").val();
		var terminalList = $("#output_content").val();
		
		$.post('<@spring.url "" />'+'/cs/change/'+outputId+'/output',
            {"terminalList": terminalList,
           	"payChannelId":payChannelId
            }, 
            function (data) {
            	if(data.code==-1){
					$("#errMsg").html(data.message);
					return;
            	}
            	$(".exchangeGoods_tab").hide();
    			$(".mask").hide();
    			showErrorTip("添加换货出库记录成功");
        });
	}

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
		popup(".exchangeGoods_tab",".exchangeGoods_a");//添加换货出库记录
	});
	
	function pageChange(page) {
	    $.get('<@spring.url "/cs/change/page" />',
	            {"page": page,
	            "keyword": keyword,
	            "status": status},
	            function (data) {
	                $('#page_fresh').html(data);
	            });
	}
	
	function onCancel(csChangeId) {
		$.post('<@spring.url "" />'+'/cs/change/'+csChangeId+'/cancel',
	            {}, function (data) {
	            	$("#operation_"+csChangeId).html('<a href="<@spring.url "" />'+'/cs/change/'+csChangeId+'/info" class="a_btn">查看详情</a>');
	            	$("#status_"+csChangeId).text("已取消");
	            });
	}
	
	function onFinish(csChangeId) {
		$.post('<@spring.url "" />'+'/cs/change/'+csChangeId+'/finish',
	            {}, function (data) {
	            	$("#operation_"+csChangeId).html('<a href="<@spring.url "" />'+'/cs/change/'+csChangeId+'/info" class="a_btn">查看详情</a>');
	            	$("#status_"+csChangeId).text("处理完成");
	            });
	}
	
	function onHandle(csChangeId) {
		$.post('<@spring.url "" />'+'/cs/change/'+csChangeId+'/handle',
	            {}, function (data) {
	            	$("#operation_"+csChangeId).html(
	            		'<a href="<@spring.url "" />'+'/cs/change/'+csChangeId+'/info" class="a_btn">查看详情</a>'
	            		+'<a class="a_btn" onClick="onCancel('+csChangeId+');">取消</a>'
						+'<a class="a_btn" onClick="onFinish('+csChangeId+');">标记为换货完成</a>'
	            	);
	            	$("#status_"+csChangeId).text("处理完成");
	            	
	            });
	}
	
	var confirmId;
	
	function onPreConfirm(csChangeId) {
		confirmId = csChangeId;
		
	}
	
	function onConfirm() {
		var receiver = $("input[name='receiver']").val();
		var phone = $("input[name='phone']").val();
		var zipCode = $("input[name='zipCode']").val();
		var address = $("input[name='address']").val();
		
		$.post('<@spring.url "" />'+'/cs/change/'+confirmId+'/confirm',
			{'receiver':receiver, 
			 'phone':phone,
			 'zipCode':zipCode,
			 'address':address
			 }, function(data) {
			 	location.reload();
			 });
	}

</script>	
</@c.html>