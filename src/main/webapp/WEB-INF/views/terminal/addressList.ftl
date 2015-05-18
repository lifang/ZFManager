<h3>确认收货地址</h3>
<input type="text" value="${addressListLength}" id="hdAddressListLength" style="display:none"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
 <colgroup>
    <col width="30" />
    <col width="90" />
    <col width="240" />
    <col width="180" />
    <col width="80" />
    <!--<col width="80" />-->
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
    <!--<th>操作</th>-->
    <th>&nbsp;</th>
  </tr>
  </thead>
  
  <#list address as ad>
  <tr>
    <td>
    	<#if (ad.isDefault==1)><input name="addressRadio" type="radio" value="${ad.id}" checked /></#if>
        <#if (ad.isDefault!=1)><input name="addressRadio" type="radio" value="${ad.id}" /></#if>
    </td>
    <td>${ad.receiver}</td>
    <td>${ad.parentName+ad.sonName}</td>
    <td>${ad.address}</td>
    <td>${ad.zipCode}</td>
    <td>${ad.moblephone}</td>
    
    <!--<td><a href="#" class="a_btn">修改</a><a href="#" class="a_btn">删除</a></td>-->
    
    <td>
    	<#if (ad.isDefault==1)><span class="defaultAddr">默认地址</span></#if>
    </td>
  </tr>
  </#list>
  
  <tr class="addAddr_box">
    <td>&nbsp;</td>
    <td><input name="" type="text" value="收件人姓名" id="receiver"/></td>
    <td>
        <select id="provinceSelect" onchange="bindCity()">
            <#list provinces as province>
                <option value="${province.id}">${province.name}</option>
            </#list>
        </select>
        <select id="citySelect">
        </select>
    </td>
    <td><input name="" type="text" value="详细地址" class="l" id="address"/></td>
    <td><input name="" type="text" value="邮编" id="zipCode"/></td>
    <td><input name="" type="text" value="手机号码" id="moblephone"/></td>
    <td><a href="#" class="a_btn" onclick="addCostometAddress()">确定</a></td>
  </tr>
</table>
<div class="addAddr_btn"><button>使用新地址</button></div>

