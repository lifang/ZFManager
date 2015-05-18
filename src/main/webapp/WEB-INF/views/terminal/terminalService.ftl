<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
                    <ul>
                        <li><a href="<@spring.url "/terminal/list"/>">终端</a></li>
                        <li><a href="javascript:void(0);">申请代理商售后</a></li>
                    </ul>
                </div>
                <div class="content clear">
                <input type="text" id="hdCustomerId" style="display:none"/>
                <input type="text" id="hdAgentId" style="display:none"/>
                    <div class="user_title">
                    	<h1>申请代理商售后</h1>
                    </div>
                    <div class="attributes_box">
                        <div class="item_list clear">
                            <ul>
                               
                                <li class="block"><span class="labelSpan">填写终端号：</span>
                                	<div class="text"><textarea placeholder="多个终端请用逗号分隔..." name="" cols="" rows="" id="terminals"></textarea></div>
                                </li> 
                                <li class="block"><span class="labelSpan">售后原因：</span>
                                	<div class="text"><textarea name="" cols="" rows="" id="reasons"></textarea></div>
                                </li>
                            </ul>
                        </div> 
                    </div>
                    
                    
             <div class="searchUser">
	        	<div class="su_title01">
	            	<ul>
	                	<li class="hover">确认代理商</li>
	                </ul>
	            </div>
	            <div class="su_con01">
	            	<div>
	                	<div class="su_search">
	                    	<input id="agentCompanyName" name="" type="text" placeholder="公司名称"/><button onclick="searchAgent();">搜索</button>
	                    </div>
	                    <div id="agent_fresh" class="su_s_box">
	                    	<#include "agentSearch.ftl" />
	                    </div>
	                </div>
	            </div>
        	</div>    
                    <div class="attributes_box">
                	<div class="myAddress" id="address_fresh">
                      
                    </div>
                </div>
                    <div class="attributes_box">
                    	<h2>填写物流信息</h2>
                    	<div class="item_list clear">
                        	<ul>
                                <li class="block"><span class="labelSpan">物流公司：</span>
                                <div class="text"><input name="" type="text" id="wlCompany"/></div></li>
                                <li class="block"><span class="labelSpan">物流单号：</span>
                                <div class="text"><input name="" type="text" id="wlNums"/></div></li>
                            </ul>
                        </div>
                    </div>
                    <div class="btnBottom"><button class="blueBtn" onclick="terminalSub()">提交</button></div>

                </div>
            </div>
<script type="text/javascript">
function terminalSub(){
	var terminals=$("#terminals").val();
	var reasons=$("#reasons").val();
	var customerId=$("#hdCustomerId").val();
	var agentId=$("#hdAgentId").val();
	var radioTemp=$('input:radio[name="addressRadio"]:checked').val();
	var wlCompanyStr=$("#wlCompany").val();
	var wlNumsStr=$("#wlNums").val();
	
	if(terminals == undefined || terminals == "" ||terminals.replace(/(^\s*)|(\s*$)/g, "")==''){
			alert("请填写终端号！");
		}else if(reasons == undefined ||reasons == ""||reasons.replace(/(^\s*)|(\s*$)/g, "")==''){
			alert("请填写售后原因！");
		}else if(radioTemp==undefined){
				alert("请选择收货地址！");
		}else if(wlCompanyStr == undefined || wlCompanyStr == ""||wlCompanyStr.replace(/(^\s*)|(\s*$)/g, "")==''){
			alert("请填写物流公司！");
		}else if(wlNumsStr == undefined || wlNumsStr == ""||wlNumsStr.replace(/(^\s*)|(\s*$)/g, "")==''){
			alert("请填写物流单号！");
		}else{
			var tempArray={
				"customerId":customerId,
				"agentId":agentId,
				"content": "物流公司："+wlCompanyStr+","+"物流单号："+wlNumsStr,
				"addressId":radioTemp,
				"terminalsList":terminals,
				"reasons":reasons
			}
			$.post('<@spring.url "/terminalCs/submitAgent" />',
                    tempArray,
                    function (data) {
                    	if(data.code == 1){
							 alert(data.result);
							 window.location.href="#/cs_agent";
						 }else if(data.code == 2){
							 alert("终端号错误:"+data.result);
						 }else if(data.code == -1){
							 alert(data.message);
						 }
                    });
	 }
}


function addCostometAddress(){
		var addressListLength=$("#hdAddressListLength").val();
		if(addressListLength<10){
			var temp={
					cityId :Math.ceil($("#citySelect").val()),
  	  				receiver :$("#receiver").val(),
  	  				address :$("#address").val(),
  	  				moblephone :$("#moblephone").val(),
  	  				zipCode :$("#zipCode").val(),
  	  				customerId :Math.ceil($("#hdCustomerId").val())
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
                    		
                    		var customerId=$("#hdCustomerId").val();
							$.post('<@spring.url "/terminalCs/getCustomerAddress" />',
							            {"customerId": customerId
							            },
							            function (data) {
							               $('#address_fresh').html(data);
							               bindCity();
							            });
                    		//history.go(0);
                    		//window.location.href = "../../terminalCs/getWebApplyDetails?terminalId="+terminalId+"&type=2";
                    	}
                    });
			
		}else{
			alert("收货地址已满十条！");
		}
	}



function agentSelected(customerId,agentId){
		$("a[name=agentCompanyName]").removeClass("hover");
		$("#agentCustomer_"+customerId).addClass("hover");
		$("#customerId").val("");
		$("#agentCustomerId").val(customerId);
		$("#hdCustomerId").val(customerId);
		$("#hdAgentId").val(customerId);
		$.post('<@spring.url "/terminalCs/getCustomerAddress" />',
		            {"customerId": customerId
		            },
		            function (data) {
		               $('#address_fresh').html(data);
		               bindCity();
		            });
	}

function searchAgent(agentCompanyName) {
		var temp = $("#agentCompanyName").val();
		 if(temp==null||temp=='null'||temp==undefined||temp==''||temp.replace(/(^\s*)|(\s*$)/g, "")==''){
			 alert("请输入搜索用户的条件！");
		  }else{
		    $.post('<@spring.url "/order/agent/search" />',
	        {
	        	"keys": temp
	        },
	        function (data) {
	            $('#agent_fresh').html(data);
	        });
	      }
	};

function bindCity(){
var provinceId = $(provinceSelect).val();
$.post('<@spring.url "/common/cities" />',
        {'id':provinceId},
        function (data) {
            $("#citySelect").empty();
            $("#citySelect").append(data);
            $("#citySelect").find("option[value='${(agent.customer.cityId)!""}']").attr("selected",true);
        });
}

$(function(){
   $("#provinceSelect").find("option[value='${(agent.customer.city.parentId)!""}']").attr("selected",true);
	$("#provinceSelect").trigger("change");
})


function setSpanName(obj){
 $(obj).parent("a").parent("form").ajaxSubmit({
  success : function(data) {
   if(data.code == -1){
    alert(data.message);
   }else if(data.code == 1){
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
	var temp={
		terminalsId:Math.ceil($("#hdTerminalId").val()),
		customerId:Math.ceil($("#hdCustomerId").val()),
		status:1,
		templeteInfoXml :JSON.stringify(arrTemp)
	}
	var terminalId=$("#hdTerminalId").val();
    var url='<@spring.url "/terminalCs/getApplyToUpdate" />';
      $.post(url,
		   {'terminalsId':Math.ceil($("#hdTerminalId").val()),'customerId':Math.ceil($("#hdCustomerId").val()),
		   'status':1,'templeteInfoXml':JSON.stringify(arrTemp)},
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