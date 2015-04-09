
<#if customerAddress??>
	<a href="#" class="close">关闭</a>
	<div class="tabHead">修改</div>
	<div class="tabBody">
		<input id="receiverTab" name="" type="text" value="${customerAddress.receiver!""}" />
		<select name="" id="provinceSelectTab">
		  <option>省</option>
		  <#include "../common/city_option.ftl" />
		</select>
	    <select name="" id="citySelectTab">
		  <option>市</option>
		</select>
		<input id="addressTab" name="" type="text" class="w" value="${customerAddress.address!""}" />
		<input id="zip_codeTab" name="" type="text" value="${customerAddress.zipCode!""}" />
		<input id="moble_phoneTab" name="" type="text" value="${customerAddress.moblephone!""}" />
	</div>
	<div class="tabFoot"><button class="blueBtn" id="markSure">确定</button></div>
</#if>