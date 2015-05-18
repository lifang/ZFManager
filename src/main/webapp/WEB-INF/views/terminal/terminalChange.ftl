<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="<@spring.url "/terminal/list"/>">终端</a></li>
            <li><a href="javascript:void(0);">申请换货</a></li>
        </ul>
    </div>
     <div class="content clear">
     	<input type="text" value="${applyDetails.id}" id="hdTerminalId" style="display:none"/>
	    <input type="text" value="${applyDetails.customerId}" id="hdCustomerId" style="display:none"/>
	    <input type="text" value="${addressListLength}" id="hdAddressListLength" style="display:none"/>
        <div class="user_title">
        	<h1>申请换货</h1>
        </div>
	    <div class="attributes_box">
	     	<h2>终端信息</h2>
	        <div class="attributes_list clear">
	            <ul>
	                <li>品牌型号：<#if (applyDetails.model_number)??>${applyDetails.brandName + applyDetails.model_number}</#if></li>
	                <li>支付通道：${applyDetails.channelName}</li>
	                <li>终端号：${applyDetails.serial_num}</li>
	                <li>商户名称：${applyDetails.title}</li>
	                <li>商户电话：${applyDetails.phone}</li>
	            </ul>
	        </div> 
		    <div class="attributes_box">
	        	 <h2>换货信息</h2>
	             <div class="ab_b">
	                <dl>
	                	<dt>换货原因：</dt>
	                    <dd><input name="" type="text" value="<#if (reason)??>${reason}</#if>" id="ChangeReason"/></dd>
	                </dl>
	             </div>
		 	</div>
		 	
		 	 <div class="attributes_box">
                	<div class="myAddress">
                        <h3>确认收货地址</h3>
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
                                    <select id="provinceSelect">
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
                        </div>
                </div>
		         <div class="attributes_box">
		         <h2>注销申请资料（终端未申请开通无需提交）</h2>
		            <div class="applyFor_list clear">
		            <input type="hidden" id="modelStatus" value="0"/>
		            <#list ReModel as re>
		               <div class="af_con">
			                 <div class="af_con_n">${re.title}<a href="${re.templet_file_path}" target="Blank" class="a_btn">下载模版</a>
			                 </div>
			            	<div class="af_con_b">
			                 <form action="<@spring.url "/terminalCs/upload/tempUpdateFile/${applyDetails.id}" />" method="post" enctype="multipart/form-data">
			                 	<i></i>
			                 	<a href="javascript:void(0);" class="informImg_a">
			                        <span>上传</span>
			                        <input type="text" id="up_${re.id}"/>
			                        <input name="updatefile" multiple type="file" onchange="setSpanName(this)">
			                    </a>
			                   </form>
			                </div>
		               </div>
		            </#list>
		            </div>
		            <div class="btnBottom"><button class="blueBtn" onclick="subToUpdate()">提交申请</button></div>
		        </div>
	        </div>  
		    </div>

<script type="text/javascript">
	function addCostometAddress(){
		var addressListLength=$("#hdAddressListLength").val();
		if(addressListLength<10){
			var temp={
					"cityId" :Math.ceil($("#citySelect").val()),
  	  				"receiver" :$("#receiver").val(),
  	  				"address" :$("#address").val(),
  	  				"moblephone" :$("#moblephone").val(),
  	  				"zipCode" :$("#zipCode").val(),
  	  				"customerId" :Math.ceil($("#hdCustomerId").val())
			};
			$.post('<@spring.url "/terminalCs/addCostometAddress" />',
                    temp,
                    function (data) {
                    	if(data.code==-1){
                    		alert("新增地址出错");
                    	}else{
                    		$("#citySelect").val(1);
                    		$("#receiver").val("");
                    		$("#address").val("");
                    		$("#moblephone").val("");
                    		$("#zipCode").val("");
                    		history.go(0);
                    		//window.location.href = "../../terminalCs/getWebApplyDetails?terminalId="+terminalId+"&type=2";
                    	}
                    });
			
		}else{
			alert("收货地址已满十条！");
		}
	}

$(function(){
	$('#provinceSelect').change(function(){
            var provinceId = $(this).children('option:selected').val();
            $.post('<@spring.url "/common/cities" />',
                    {'id':provinceId},
                    function (data) {
                        $("#citySelect").empty();
                        $("#citySelect").append(data);
                        $("#citySelect").find("option[value='${(agent.customer.cityId)!""}']").attr("selected",true);
                    });
        });
        $("#provinceSelect").find("option[value='${(agent.customer.city.parentId)!""}']").attr("selected",true);
        $("#provinceSelect").trigger("change");
})


function setSpanName(obj){
 $(obj).parent("a").parent("form").ajaxSubmit({
  success : function(data) {
   if(data.code == -1){
    alert(data.message);
   }else if(data.code == 1){
   $("#modelStatus").val(1);
    $(obj).siblings("span").parent("a").siblings("i").attr("class","on");
    $(obj).parent("a").children("span").html("重新上传")
    $(obj).siblings("input").val(data.result);
   }
  }
 });
}
function subToUpdate(){
	
	var subtruefalse=true;
	var upFileInputs=$("[id^=up_]");
	
	var arrTemp=[];
	var index=0;
	for(var i=0;i<upFileInputs.length;i++){
		var inputTemp=$(upFileInputs[i]);
		var idTemp=inputTemp.attr("id").substring(3);
		arrTemp[index]={
			id:idTemp,
			path:inputTemp.val()
		}
		index++;
	}
	var radioTemp=$('input:radio[name="addressRadio"]:checked').val();
	
	var temp={
		terminalsId:Math.ceil($("#hdTerminalId").val()),
		customerId:Math.ceil($("#hdCustomerId").val()),
		status:1,
		templeteInfoXml :JSON.stringify(arrTemp)
	}
	var terminalId=$("#hdTerminalId").val();
    var url='<@spring.url "/terminalCs/subChange" />';
      $.post(url,
		   {'terminalsId':Math.ceil($("#hdTerminalId").val()),'customerId':Math.ceil($("#hdCustomerId").val()),
		   'status':1,'templeteInfoXml':JSON.stringify(arrTemp),'reason':$("#ChangeReason").val(),
		   'modelStatus':$("#modelStatus").val(),'type':3,'addressId':radioTemp},
		   function(data){
			   	if (data != null && data != undefined) {
	             if(data.code == 1){
	             	var urlTemp='<@spring.url "/terminal/'+ terminalId +'/info" />';
	             	window.location.href = urlTemp;
	             }else{
	             	alert("更新失败！");
	             }
	            }
		   }
	  );
}
</script>
</@c.html>