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
	<div class="myAddress">
		<h3>确认收货地址</h3>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="b_table">
			<colgroup>
				<col width="30" />
				<col width="90" />
				<col width="240" />
				<col width="180" />
				<col width="80" />
				<col width="80" />
				<col />
			</colgroup>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>收货人</th>
					<th>所在地区</th>
					<th>详细地址</th>
					<th>邮编</th>
					<th>电话</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tr>
				<td><input name="" type="radio" value="" /></td>
				<td>张三三</td>
				<td>江苏省苏州市吴中区</td>
				<td>
					东方大道1788号7栋311室东方大道1788号7栋311室东方大道1788号7栋311室东方大道1788号7栋311室东方大道1788号7栋311室东方大道</td>
				<td>215000</td>
				<td>123456789</td>
				<td><span class="defaultAddr">默认地址</span></td>
			</tr>
			<tr>
				<td><input name="" type="radio" value="" /></td>
				<td>张三三</td>
				<td>江苏省苏州市吴中区</td>
				<td>东方大道1788号7栋311室</td>
				<td>215000</td>
				<td>123456789</td>
				<td><a href="#" class="set_defaultAddr">设为默认地址</a></td>
			</tr>
			<tr>
				<td><input name="" type="radio" value="" /></td>
				<td>张三三</td>
				<td>江苏省苏州市吴中区</td>
				<td>东方大道1788号7栋311室</td>
				<td>215000</td>
				<td>123456789</td>
				<td><a href="#" class="set_defaultAddr">设为默认地址</a></td>
			</tr>
			<tr class="addAddr_box">
				<td>&nbsp;</td>
				<td><input name="" type="text" value="收件人姓名" /></td>
				<td><select name="">
						<option>省</option>
						<option>江苏省</option>
				</select> <select name="">
						<option>市</option>
						<option>南京市</option>
				</select> <select name="">
						<option>区/县</option>
						<option>姑苏区</option>
				</select></td>
				<td><input name="" type="text" value="详细地址" class="l" /></td>
				<td><input name="" type="text" value="邮编" /></td>
				<td><input name="" type="text" value="手机号码" /></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div class="addAddr_btn">
			<button>使用新地址</button>
		</div>
	</div>
	<div class="btnBottom">
		<button class="blueBtn">创建</button>
	</div>
</div>

<script type="text/javascript">

</script>
</@c.html>