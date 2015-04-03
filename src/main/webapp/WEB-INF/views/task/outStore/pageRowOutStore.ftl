     <tr id="row_${outStore.id}"> 
      <td><input type="checkBox" value=${outStore.id}></td> 
      <td>${outStore.id}</td>
      <td>${outStore.createdAt}</td> 
      <td>${outStore.orderId}</td> 
      <td><strong class="strong_status">
       <#if outStore.status=1>待处理
       <#elseif outStore.status=2>已取消
       <#elseif outStore.status=3>处理完成
       </#if>
      </strong></td> 
      <td>
       <#if outStore.status=1>
       		<a href="<@spring.url "/task/outStore/${outStore.id}/info" />" class="a_btn">查看详情</a><a href="#" class="a_btn">备注</a><br/>
       		<a href="<@spring.url "/task/outStore/${outStore.id}/add" />" class="a_btn">添加出库记录</a>
       	<#elseif outStore.status=2>
       		<a href="<@spring.url "/task/outStore/${outStore.id}/info" />" class="a_btn">查看详情</a><a href="#" class="a_btn">备注</a><br/>
       	<#elseif outStore.status=3>
       		<a href="<@spring.url "/task/outStore/${outStore.id}/info" />" class="a_btn">查看详情</a><a href="#" class="a_btn">备注</a><br/>
       	</#if>	
	 </td> 
   </tr>