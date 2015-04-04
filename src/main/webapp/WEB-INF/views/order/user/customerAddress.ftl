<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
     <colgroup>
        <col width="30" />
        <col width="90" />
        <col width="250" />
        <col width="320" />
        <col width="90" />
        <col width="90" />
        <col width="120" />
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
	    <th>操作</th>
	    <th>&nbsp;</th>
	  </tr>
	  </thead>
		<#if customerAddresses??>
		<input id="customerAddressId" type="hidden" name="customerAddressId" value="" />
		<#list customerAddresses as customerAddress>
		  <tr>
		    <td>
		    	<#if customerAddress.isDefault??&&customerAddress.isDefault==1>
		    		<input id="customerAddressId_${customerAddress.id!""}" name="customerAddressId" type="radio" value="" onclick="selectCustomerAddress(${customerAddress.id!""});" checked="ture" />
		    		<script type="text/javascript">
		    			selectCustomerAddress(${customerAddress.id!""});
		    		</script>
		    	<#else>
		    		<input id="customerAddressId_${customerAddress.id!""}" name="customerAddressId" type="radio" value="" onclick="selectCustomerAddress(${customerAddress.id!""});" />
		    	</#if>
		    </td>
		    <td>${customerAddress.receiver!""}</td>
		    <td><#if customerAddress.parentCity??>${customerAddress.parentCity.name!""}</#if>
		    	<#if customerAddress.city??&&customerAddress.parentCity??&&customerAddress.city.name!=customerAddress.parentCity.name>
		    		${customerAddress.city.name!""}
		    	</#if>
		    </td>
		    <td>${customerAddress.address!""}</td>
		    <td>${customerAddress.zipCode!""}</td>
		    <td>${customerAddress.moblephone!""}</td>
		    <td><a href="#" class="a_btn">修改</a><a href="#" class="a_btn" onclick="updateAddress(${customerAddress.id!""},2);">删除</a></td>
		    <td>
		    	<#if customerAddress.isDefault??&&customerAddress.isDefault==1>
		    		<span class="defaultAddr">默认地址</span>
		    	<#else>
		    		<a href="#" class="set_defaultAddr">设为默认地址</a>
		    	</#if>
		    </td>
		  </tr>
		 </#list>
		  </#if>
	   	<tr id="add_address_box" class="addAddr_box" style="display:none">
		    <td>&nbsp;</td>
		    <td><input id="receiver" name="" type="text" value="收件人姓名" /></td>
		    <td>
		    	<select name="" id="provinceSelect">
		    	  <option>省</option>
		    	  <#include "../../common/city_option.ftl" />
		    	</select>
		        <select name="" id="citySelect">
		    	  <option>市</option>
		    	</select>
		    </td>
		    <td><input id="address" name="" type="text" class="w" value="详细地址" /></td>
		    <td><input id="zip_code" name="" type="text" value="邮编" /></td>
		    <td><input id="moble_phone" name="" type="text" value="手机号码" /></td>
		    <td><a href="#" class="a_btn" onclick="createCustomerAddress();">确定</a></td>
		    <td>&nbsp;</td>
		  </tr>
        </table>
