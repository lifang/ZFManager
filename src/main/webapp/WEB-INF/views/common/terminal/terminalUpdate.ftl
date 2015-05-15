<div class="content clear">
                    <div class="user_title">
                    	<h1>申请更新资料</h1>
                    </div>
                    <div class="attributes_box">
                    	<h2>终端信息</h2>
                        <div class="attributes_list clear">
                            <ul>
                                <li>品牌型号：${applyDetails.brandName + applyDetails.model_number}</li>
                                <li>支付通道：${applyDetails.channelName}</li>
                                <li>终端号：${applyDetails.serial_num}</li>
                                <li>商户名称：${applyDetails.title}</li>
                                <li>商户电话：${applyDetails.phone}</li>
                            </ul>
                        </div> 
                    </div>
                    <div class="attributes_box">
                    	<h2>更新申请资料</h2>
                        <div class="applyFor_list clear">
                        <#list ReModel as re>
                           <div class="af_con">
                            	<div class="af_con_n">${re.title}<a href="${re.templet_file_path}" target="Blank" class="a_btn">下载模版</a></div>
                                <div class="af_con_b">
                                	<i></i>
                                	<form action="<@spring.url "/terminalCs/upload/tempUpdateFile/${applyDetails.id}" />" method="post" enctype="multipart/form-data">
                                	<a href="javascript:void(0);" class="informImg_a">
                                        <span>上传</span>
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
            	
<script>
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
	
	$scope.array = [];
		 for(var i=0;i<$scope.ReModel.length;i++){
			 if($("#up_"+i).val() !=null && $("#up_"+i).val() != ""){
				 $scope.subtruefalse = false;
				 $scope.array[i] = {
							id:$("#upId_"+i).val(),
							path:$("#up_"+i).val()
					};
			 }
		 }
		
		$scope.message = {
				terminalsId:Math.ceil($scope.terminalId),
				customerId:Math.ceil($scope.customerId),
				status:1,
				templeteInfoXml :JSON.stringify($scope.array),
				};
	
	
	if(subtruefalse == true){
			alert("请先上传资料！");
		}else{
			$http.post("api/terminal/getApplyToUpdate", $scope.message).success(function (data) {  //绑定
			      if (data != null && data != undefined) {
			    	  if(data.code == 1){
			    		  window.location.href ='#/terminalDetail?terminalId='+$scope.terminalId;
			    	  }else{
			    		  alert("更新失败！");
			    	  }
			        
			      }
			  }).error(function (data) {
				  alert("操作失败");
			  });
		}
	
}


	
</script>