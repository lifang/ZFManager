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
			  <tr id="customerAddressTr_${customerAddress.id!""}">
			    <td>
			    	<#if customerAddress.isDefault??&&customerAddress.isDefault==1>
			    		<input id="customerAddressId_${customerAddress.id!""}" name="customerAddressId" type="radio" value="" onclick="selectCustomerAddress(${customerAddress.id!""});" checked="ture" />
			    		<!--
			    		<script type="text/javascript">
			    			selectCustomerAddress(${customerAddress.id!""});
			    		</script>-->
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
			    <td><a href="#" class="a_btn" onclick="updateAddressEx(${customerAddress.id!""});">修改</a><a href="#" class="a_btn" onclick="updateAddress(${customerAddress.id!""},2,${customerAddress.customerId!""});">删除</a></td>
			    <td>
			    	<#if customerAddress.isDefault??&&customerAddress.isDefault==1>
			    		<span class="defaultAddr">默认地址</span>
			    	<#else>
			    		<a href="#" class="set_defaultAddr">设为默认地址</a>
			    	</#if>
			    </td>
			  </tr>
			  
			  <tr id="add_address_box_${customerAddress.id!""}" class="addAddr_box" style="display:none">
			    <td>&nbsp;</td>
			    <td><input id="receiver_${customerAddress.id!""}" name="" type="text" value="${customerAddress.receiver!""}" /></td>
			    <td>
			    	<select name="" id="provinceSelect_${customerAddress.id!""}" onchange="provinceOnchange(${customerAddress.id!""});">
			    	  <#if customerAddress.parentCity??><option value="${customerAddress.parentCity.id!""}">${customerAddress.parentCity.name!""}</option></#if>
			    	  <#if customerAddress.city??&&customerAddress.parentCity??&&customerAddress.city.name!=customerAddress.parentCity.name>
			    	  		<option value="${customerAddress.city.id!""}">${customerAddress.city.name!""}</option>		
			    	  </#if>
			    	  <#include "../common/city_option.ftl" />
			    	</select>
			        <select name="" id="citySelect_${customerAddress.id!""}">
			    	  <#if customerAddress.city??&&customerAddress.parentCity??&&customerAddress.city.name!=customerAddress.parentCity.name>
			    	  		<option value="${customerAddress.city.id!""}">${customerAddress.city.name!""}</option>		
			    	  </#if>
			    	</select>
			    </td>
			    <td><input id="address_${customerAddress.id!""}" name="" type="text" class="w" value="${customerAddress.address!""}" /></td>
			    <td><input id="zip_code_${customerAddress.id!""}" name="" type="text" value="${customerAddress.zipCode!""}" /></td>
			    <td><input id="moble_phone_${customerAddress.id!""}" name="" type="text" value="${customerAddress.moblephone!""}" /></td>
			    <td><a href="#" class="a_btn" onclick="updateCustomerAddress(${customerAddress.id!""});">确定</a></td>
			    <td>&nbsp;</td>
			  </tr>
			 </#list>
		  </#if>
	   	<tr id="add_address_box" class="addAddr_box" style="display:none">
		    <td>&nbsp;</td>
		    <td><input id="receiver" name="" type="text" placeholder="收件人姓名" /></td>
		    <td>
		    	<select name="" id="provinceSelect">
		    	  <option>省</option>
		    	  <#include "../common/city_option.ftl" />
		    	</select>
		        <select name="" id="citySelect">
		    	  <option>市</option>
		    	</select>
		    </td>
		    <td><input id="address" name="" type="text" class="w" placeholder="详细地址" /></td>
		    <td><input id="zip_code" name="" type="text" placeholder="邮编" /></td>
		    <td><input id="moble_phone" name="" type="text" placeholder="手机号码" /></td>
		    <td><a href="#" class="a_btn" onclick="createCustomerAddress();">确定</a></td>
		    <td>&nbsp;</td>
		  </tr>
        </table>
<!--
<#if order??>
	<input id="customerId" type="hidden" name="customerId" value="${order.customerId!""}" />
<#else>
	<input id="customerId" type="hidden" name="customerId" value="<#if customer??>${customer.id!""}</#if>" />
</#if>    -->    
<script type="text/javascript">
	 $('#provinceSelect').change(function(){
        var provinceId = $(this).children('option:selected').val();
        if(isNotNull(provinceId)){
            $.post('<@spring.url "/common/cities" />',
                    {'id':provinceId},
                    function (data) {
                        $("#citySelect").empty();
                        $("#citySelect").append("<option>市</option>");
                        $("#citySelect").append(data);
                        //$("#citySelect").append("<option></option>"+data);
                    });
        } else {
            $("#citySelect").empty();
        }
    });
    
    function provinceOnchange(id){
    	var provinceId = $('#provinceSelect_'+id).children('option:selected').val();
        if(isNotNull(provinceId)){
            $.post('<@spring.url "/common/cities" />',
                    {'id':provinceId},
                    function (data) {
                        $("#citySelect_"+id).empty();
                        $("#citySelect_"+id).append("<option>市</option>");
                        $("#citySelect_"+id).append(data);
                        //$("#citySelect").append("<option></option>"+data);
                    });
        } else {
            $("#citySelect").empty();
        }
    }
    
    function updateAddress(id,status,customerId){
		$.get('<@spring.url "/order/customer/address/saveOrUpdate" />',
	            {"id": id,
	            "status":status,
	            "customerId":customerId
	            },
	            function (data) {
	               $('#customer_address_fresh').html(data);
	            });
	}
	
	function updateAddressEx(id){
		$("#customerAddressTr_"+id).hide();
		$("#add_address_box_"+id).show();
	}
	
	
	function updateCustomerAddress(id){
		var cityId = $("#citySelect_"+id).val();
		var receiver = $("#receiver_"+id).val();
		var address = $("#address_"+id).val();
		var moblephone = $("#moble_phone_"+id).val();
		var zipCode = $("#zip_code_"+id).val();
		var customerId=$("#customerId").val();
		//if(null==customerId || ''==customerId){
		//	alert("请先选择用户");
		//	return;
		//}
		$.get('<@spring.url "/order/customer/address/saveOrUpdate" />',
	            {"id": id,
	             "cityId": cityId,
	             "receiver": receiver,
	             "address": address,
	             "moblephone": moblephone,
	             "zipCode": zipCode,
	             "customerId": customerId
	            },
	            function (data) {
	               $('#customer_address_fresh').html(data);
	            });
	}
	

	
</script>