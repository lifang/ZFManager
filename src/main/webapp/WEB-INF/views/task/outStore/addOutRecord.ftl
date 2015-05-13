<#import "../../common.ftl" as c />
<@c.html>
     <div class="breadcrumb"> 
      <ul> 
       <li>售后</li> 
       <li><a href="<@spring.url "/task/outStore/list"/>" class="hover" >出库</a></li>
       <li><a href="<@spring.url "/task/outStore/${outStorageId}/add"/>">添加出库记录</a></li> 
      </ul> 
     </div> 
     <div class="content clear"> 
      <div class="user_title">
       <#if (outStorageId)??>
      	<input type="text" id="outStorageId" style="display: none;" value="${outStorageId}"/>
      </#if>	
       <h1>添加出库记录</h1> 
      </div>
       <div class="uesr_table"> 
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table"> 
		    <colgroup>
		      <col width="510"> 
		      <col />
              <col />
              <col />
		    </colgroup> 
		    <thead> 
		     <tr>
		      <th>商品</th> 
		      <th>数量</th> 
		      <th>终端号</th>
		      <th>激动码</th>
		     </tr> 
		    </thead> 
		    <tbody> 
		     <#if (goods)??>
			  <#list goods as good>
			  	  <tr id="row_${good.id}">
			  	  	<td>
                        <div class="td_proBox clear">
                            <a href="#" class="cn_img"><img src="<#if (good.urlPath)??>${good.urlPath}</#if>"  style="width:130px;height:130px" /></a>
                            <div class="td_proBox_info">
                                <h1><a href="#">${good.title}</a></h1>
                                <h3>热销5000件</h3>
                                <ul>
                                	<li><span>品牌型号：</span><div class="c_text"><#if (good.brandName)??>${good.brandName}</#if></div></li>
                                    <li><span>支付通道：</span><div class="c_text"><#if (good.payChannelName)??>${good.payChannelName}</#if></div></li>
                                </ul>
                            </div>
                        </div>
                    </td>
			      	<td>${good.quantity}</td> 
			      	<td class="text">
			      		<textarea name="" cols="" rows="" class="textarea_l" id="terminal_${good.id}" style="width:150px"></textarea>
			      	</td>
			      	<td class="text">
			      		<textarea name="" cols="" rows="" class="textarea_l" id="checkCode_${good.id}"
			      		 style="width:150px;color:darkgrey" onfocus="if(value=='终端激活码（无需激活码则不填写）')value=''"
			      		 onblur="if (value ==''){value='终端激活码（无需激活码则不填写）'}">终端激活码（无需激活码则不填写）</textarea>
			      	</td>
				   </tr>
			  </#list>
			</#if>
		    </tbody> 
		 </table> 
		</div>
		<div class="attributes_box">
        	<div class="item_list clear">
            	<ul>
                	<li class="b"><span class="labelSpan">收货地址：</span>
                    <div class="text"> <#if (address.address)??>${address.address}</#if></div></li>
                    
                    <li class="b"><span class="labelSpan">收货人：</span>
                    <div class="text"> <#if (address.receiver)??>${address.receiver}</#if></div></li>
                    <li class="b"><span class="labelSpan">收货人电话：</span>
                    <div class="text"> <#if (address.moblephone)??>${address.moblephone}</#if></div></li>
                    <li class="b"><span class="labelSpan">邮编：</span>
                    <div class="text"> <#if (address.zipCode)??>${address.zipCode}</#if></div></li>
                    
                    <li class="block"><span class="labelSpan">物流公司：</span>
                    <div class="text"><input name="" type="text" id="wlCompany" /></div></li>
                    <li class="block"><span class="labelSpan">物流单号：</span>
                    <div class="text"><input name="" type="text" id="wlNumStr" /></div></li>
                    
                    
                </ul>
            </div>
        </div>
        <div class="btnBottom"><button class="blueBtn" onClick="submitData()">确认</button></div>
      </div>
     </div>

<script type="text/javascript">

	function submitData(){
		//物流公司
		var wlCompanyStr=$("#wlCompany").val();
		//物流单号
		var wlNumStr=$("#wlNumStr").val();
		
		var checkCodes=$("textarea[id^='checkCode_']");
		
	 	var terminals=$("textarea[id^='terminal_']");
	 	
	 	var temp=""; 
	 	var goodIds = new Array();
	 	var quantities = new Array();
		for(var i=0;i<terminals.length;i++){
			var id=$(terminals[i]).attr("id");
			var goodId=id.substr(9,id.length-9);
			var value=$(terminals[i]).val();
			
			var codes=$(checkCodes[i]).val();
			if(codes=='终端激活码（无需激活码则不填写）'){
				goodIds.push(goodId);
				quantities.push($(terminals[i]).parent().prev().html());
				if(temp.length<1){
					temp=goodId+"_"+value+"_无";
				}else{
					temp=temp+"|"+goodId+"_"+value+"_无";
				}
			}else{
				goodIds.push(goodId);
				quantities.push($(terminals[i]).parent().prev().html());
				if(temp.length<1){
					temp=goodId+"_"+value+"_"+codes;
				}else{
					temp=temp+"|"+goodId+"_"+value+"_"+codes;
				}
			}
		}
		var outStorageIdStr=$("#outStorageId").val();
		
		$.post('<@spring.url "/task/outStore/save" />',
	        {   "id": outStorageIdStr,
	        "wlCompany":wlCompanyStr,
	        "wlNum":wlNumStr,
	        "terminalNums":temp,
	        "goodIds":goodIds.join("-"),
	        "quantities":quantities.join("-")},
	        function (data) {
	        	if(data.code==-1){
            		alert("操作出错，错误信息为："+data.message);
            	}else{
            		alert("创建出库记录成功！");
            		//跳转
            		window.location.href="<@spring.url "/task/outStore/list" />";
            	}
	        });	
	}
</script>    
</@c.html>
