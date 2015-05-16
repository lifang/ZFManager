<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">我的终端</a></li>
            <li><a href="#">退货</a></li>
        </ul>
    </div>
     <div class="content clear">
     	<input type="text" value="${applyDetails.id}" id="hdTerminalId" style="display:none"/>
	    <input type="text" value="${applyDetails.customerId}" id="hdCustomerId" style="display:none"/>
        <div class="user_title">
        	<h1>申请退货</h1>
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
	        	 <h2>退货信息</h2>
	             <div class="ab_b">
	                <dl>
	                	<dt>退货原因：</dt>
	                    <dd><input name="" type="text" value="<#if (reason)??>${reason}</#if>" id="returnReason"/></dd>
	                </dl>
	                <!--<dl>
	                	<dt>退款金额：</dt>
	                    <dd><strong>￥458.00</strong></dd>
	                </dl>-->
	                <dl>
	                	<dt>联系人：</dt>
	                    <dd><input name="" type="text" id="relationPeople" /></dd>
	                </dl>
	                <dl>
	                	<dt>联系电话：</dt>
	                    <dd><input name="" type="text" id="relationPhone" /></dd>
	                </dl>
	             </div>
		 	</div>
	         <div class="attributes_box">
	         <h2>注销申请资料（终端未申请开通无需提交）</h2>
	            <div class="applyFor_list clear">
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
    var url='<@spring.url "/terminalCs/subReturn" />';
      $.post(url,
		   {'terminalsId':Math.ceil($("#hdTerminalId").val()),'customerId':Math.ceil($("#hdCustomerId").val()),
		   'status':1,'templeteInfoXml':JSON.stringify(arrTemp),'reason':$("#returnReason").val(),
		   'relationPeople':$("#relationPeople").val(),
		   'relationPhone':$("#relationPhone").val()},
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