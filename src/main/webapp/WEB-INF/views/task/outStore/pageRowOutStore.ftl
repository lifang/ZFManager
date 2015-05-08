     <tr id="row_${outStore.id}"> 
      <td><input name="cb_row" type="checkBox" cs_id="${outStore.id}" cs_processUserId="${outStore.processUserId}" cs_status="${outStore.status}"></td> 
      <td><#if (outStore.applyNum)??>${outStore.applyNum}</#if></td>
      <td>${outStore.createdAt}</td> 
      <td>${outStore.orderNumber}</td> 
      <td><strong class="strong_status">
       <#if outStore.status=1>待处理
       <#elseif outStore.status=2>已取消
       <#elseif outStore.status=3>处理完成
       </#if>
      </strong></td> 
      <td>${outStore.processUserName}</td>
      <td>
       <#if outStore.status=1>
       		<a href="<@spring.url "/task/outStore/${outStore.id}/info" />" class="a_btn">查看详情</a>
           <#if Roles.hasRole("OUT_STORE_ASSIGN")><a href="#" class="a_btn" onclick="changeStatus(${outStore.id})">取消</a><br/></#if>
           <#if Roles.hasRole("OUT_STORE_ADD_OUT_RECORD")><a href="<@spring.url "/task/outStore/${outStore.id}/add" />" class="a_btn">添加出库记录</a></#if>
       	<#elseif outStore.status=2>
       		<a href="<@spring.url "/task/outStore/${outStore.id}/info" />" class="a_btn">查看详情</a>
       	<#elseif outStore.status=3>
       		<a href="<@spring.url "/task/outStore/${outStore.id}/info" />" class="a_btn">查看详情</a>
       	</#if>	
	 </td> 
   </tr>
   
   <script>
   function changeStatus(val){
   		$.post('<@spring.url "/task/outStore/checkCancel" />',
                {   "id": val},
                function (data) {
                    if(data.code=="-1"){
            			alert("操作出错，错误信息为："+data.resultInfo);
	            	}else if(data.code=="1"){
	            		window.location.reload();
	            	}
                });
   
        return false;
   }
   </script>