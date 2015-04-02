     <tr id="row_${outStore.id}"> 
      <td><input type="checkBox" value=${outStore.id}></td> 
      <td> 字段暂无</td>
      <td>${outStore.createdAt}</td> 
      <td>${outStore.orderId}</td> 
      <td><strong class="strong_status">
       <#if outStore.status=1>待审核
       <#elseif outStore.status=2>初审不通过
       <#elseif outStore.status=3>初审通过   
       <#elseif outStore.status=4>审核不通过
       <#elseif outStore.status=5>正常
       <#elseif outStore.status=6>已停用
       </#if>
      </strong></td> 
      <td>
       		<a href="#" class="a_btn">查看详情</a><a href="#" class="a_btn">取消</a><br/>
       		<a href="#" class="a_btn">添加出库记录</a>
	 </td> 
   </tr>