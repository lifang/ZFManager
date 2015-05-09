<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
	<ul>
		<li>售后</li>
		<li><a href="<@spring.url "/cs/repair/list"/>">维修</a></li>
		<li><a href="<@spring.url "/cs/repair/bill/edit"/>">创建维修单号</a></li>
	</ul>
</div>
<div class="content clear">
	<div class="user_title">
		<h1>创建维修单号</h1>
	</div>
	<div class="attributes_box">
		<div class="item_list clear">
			<ul>

				<li class="block"><span class="labelSpan">设备号：</span>
					<div class="text">
						<input name="terminalNum" type="text" />
					</div></li>
				<li class="block"><span class="labelSpan">维修价格：</span>
					<div class="text">
						<input name="repairPrice" type="text" />
					</div></li>
				<li class="block"><span class="labelSpan">故障描述：</span>
					<div class="text">
						<textarea name="description" cols="" rows=""></textarea>
					</div></li>
				<li class="block"><span class="labelSpan">收件人：</span>
					<div class="text">
						<input name="receiver" type="text" />
					</div></li>
				<li class="block"><span class="labelSpan">电话：</span>
					<div class="text">
						<input name="phone" type="text" />
					</div></li>
				<li class="block"><span class="labelSpan">邮编：</span>
					<div class="text">
						<input name="zipCode" type="text" />
					</div></li>
				<li class="block"><span class="labelSpan">地址：</span>
					<div class="text">
						<input name="address" type="text" />
					</div></li>
			</ul>
		</div>

	</div>
	<div class="btnBottom">
		<button class="blueBtn" onClick="onCreateBill();">创建</button>
	</div>
</div>

<script type="text/javascript">

	function onCreateBill() {
		var terminalNum = $("input[name='terminalNum']").val();
		var repairPrice = $("input[name='repairPrice']").val();
		var description = $("textarea[name='description']").val();
	
		var receiver = $("input[name='receiver']").val();
		var phone = $("input[name='phone']").val();
		var zipCode = $("input[name='zipCode']").val();
		var address = $("input[name='address']").val();
		
		$.post('<@spring.url "/cs/repair/bill/create" />',
			{'terminalNum':terminalNum,
			 'repairPrice':repairPrice*100,
			 'description':description,
			 'receiver':receiver, 
			 'phone':phone,
			 'zipCode':zipCode,
			 'address':address
			 }, function(data) {
			 	if(data.length > 0) {
			 		alert(data);
			 	} else {
			 		location.href='<@spring.url "/cs/repair/list" />';
			 	}
			 });
	}
</script>
</@c.html>