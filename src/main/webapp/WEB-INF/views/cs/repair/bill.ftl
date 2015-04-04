<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
	<ul>
		<li><a href="#">售后</a></li>
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
						<input name="" type="text" />
					</div></li>
				<li class="block"><span class="labelSpan">维修价格：</span>
					<div class="text">
						<input name="" type="text" />
					</div></li>
				<li class="block"><span class="labelSpan">故障描述：</span>
					<div class="text">
						<textarea name="" cols="" rows=""></textarea>
					</div></li>
				<li class="block"><span class="labelSpan">收件人：</span>
					<div class="text">
						<input name="" type="text" />
					</div></li>
				<li class="block"><span class="labelSpan">电话：</span>
					<div class="text">
						<input name="" type="text" />
					</div></li>
				<li class="block"><span class="labelSpan">邮编：</span>
					<div class="text">
						<input name="" type="text" />
					</div></li>
				<li class="block"><span class="labelSpan">地址：</span>
					<div class="text">
						<input name="" type="text" />
					</div></li>
			</ul>
		</div>

	</div>
	<div class="btnBottom">
		<button class="blueBtn">创建</button>
	</div>
</div>

<script type="text/javascript">

</script>
</@c.html>