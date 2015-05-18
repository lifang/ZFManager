<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
                    <ul>
                        <li><a href="<@spring.url "/terminal/list"/>">终端</a></li>
                        <li><a href="javascript:void(0);">租赁退还申请</a></li>
                    </ul>
                </div>
                <div class="content clear">
                    <div class="user_title">
                    	<h1>租赁退还申请</h1>
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
                               
                                <li>租赁日期：${tenancy.created_at}</li>
                                <li>最短租赁时间：${tenancy.lease_time}</li>
                                <li>最长租赁时间：${tenancy.return_time}</li>
                                <li>租赁押金：<strong>￥${(tenancy.lease_deposit/100)}</strong></li>
                                <li>租赁时长：${tenancy.createdDay}天</li>
                                <li>租金：<strong>￥${tenancy.lease_price/100*tenancy.lease_time}</strong></li>
                            </ul>
                        </div> 
                    </div>
                    <div class="attributes_box">
                    	<h2>租赁退还信息</h2>
                         <div class="ab_b">
                            <dl>
                            	<dt>联系人：</dt>
                                <dd><input name="" type="text" id="connectName"/></dd>
                            </dl>
                            <dl>
                            	<dt>联系电话：</dt>
                                <dd><input name="" type="text" id="connectPhone"/></dd>
                            </dl>
                        </div>
                    </div>
                    
                    <div class="attributes_box">
     	<h2>租赁退还申请资料（终端未申请开通无需提交）</h2>
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

<script type="text/javascript">
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
	var temp={
		terminalsId:Math.ceil($("#hdTerminalId").val()),
		customerId:Math.ceil($("#hdCustomerId").val()),
		status:1,
		templeteInfoXml :JSON.stringify(arrTemp)
	}
	var terminalId=$("#hdTerminalId").val();
    var url='<@spring.url "/terminalCs/subLeaseReturn" />';
      $.post(url,
		   {'terminalsId':Math.ceil($("#hdTerminalId").val()),
		    'status':1,
		    'templeteInfoXml':JSON.stringify(arrTemp), 
		    'type':3,
		    'customerId':Math.ceil($("#hdCustomerId").val()),
		    'orderTypes':1,
		   	'relationPeople':$("#connectName").val(),
		   	'relationPhone':$("#connectPhone").val(),
		   	'modelStatus':$("#modelStatus").val()},
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