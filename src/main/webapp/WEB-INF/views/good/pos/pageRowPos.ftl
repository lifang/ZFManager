     <tr id="row_${good.id}"> 
      <td>${good.title!}</td> 
      <td> ${(good.goodBrand.name)!""}&nbsp;${good.modelNumber!""}</td>
      <td>${good.quantity!0}</td>
      <td><#if good.belongsTo??>是<#else>否</#if></td> 
      <td><strong class="strong_status">
          <#assign status = (good.status)!1/>
      <#if status=1>待审核
       <#elseif status=2>初审不通过
       <#elseif status=3>初审通过   
       <#elseif status=4>审核不通过
       <#elseif status=5>正常
       <#elseif status=6>已停用
       </#if>
      </strong></td> 
      <td><#if good.isPublished??>
      	<#if good.isPublished>是<#else>否</#if>
      </#if></td> 
      <td><#if good.hasLease??>
      	<#if good.hasLease>是<#else>否</#if>
      </#if></td> 
      <td><#if good.hasPurchase??>
      	<#if good.hasPurchase>是<#else>否</#if>
      </#if></td> 
      <td>
	   <#if status=1>
		   <#if Roles.hasRole("POS_FIRST_VERIFY")>
       		<a onClick="firstCheck(${good.id})" class="a_btn">初审通过</a>
       		<a onClick="firstUnCheck(${good.id})" class="a_btn">初审不通过</a>
		   </#if>
		   <#if Roles.hasRole("POS_EDIT")>
       		<a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn">编辑</a>
		   </#if>
       		<a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       	   <#if Roles.hasRole("POS_EDIT")>
       		<a href="<@spring.url "/good/pos/${good.id}/imgInfo" />" class="a_btn">图片详情</a>
		   </#if>
       <#elseif status=2>
		   <#if Roles.hasRole("POS_FIRST_VERIFY")>
            <a onClick="firstCheck(${good.id})" class="a_btn">初审通过</a>
		   </#if>
		   <#if Roles.hasRole("POS_EDIT")>
       		<a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn">编辑</a>
		   </#if>
       		<a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       		
       <#elseif status=3>
		   <#if Roles.hasRole("POS_SECOND_VERIFY")>
       		<a onClick="checkBtn(${good.id})" class="a_btn approve_a">审核通过</a> 
       		<a onClick="unCheck(${good.id})" class="a_btn">审核不通过</a>
		   </#if>
		   <#if Roles.hasRole("POS_EDIT")>
       		<a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn">编辑</a>
		   </#if>
       		<a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       		
       <#elseif status=4>
		   <#if Roles.hasRole("POS_SECOND_VERIFY")>
       		<a onClick="checkBtn(${good.id})" class="a_btn approve_a">审核通过</a>
		   </#if>
		   <#if Roles.hasRole("POS_EDIT")>
       		<a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn">编辑</a>
		   </#if>
       		<a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       <#elseif status=5>
		   <#if Roles.hasRole("POS_STOP_START")>
			<#if good.isPublished?? && good.isPublished>
           	<a onClick="unPublish(${good.id})" class="a_btn">下架</a> 
      		<#else>
           	<a onClick="publish(${good.id})" class="a_btn">上架</a>		          		
      		</#if>
		   </#if>
		   <#if Roles.hasRole("POS_LEASE")>
			<#if good.hasLease?? && good.hasLease>
           	<a onClick="unLease(${good.id})" class="a_btn">不可租赁</a>
      		<#else>
           	<a onClick="lease(${good.id})" class="a_btn">可租赁</a>
      		</#if>
		   </#if>
		   <#if Roles.hasRole("POS_CANGENT_BUY")>
			<#if good.hasPurchase?? && good.hasPurchase>
           	<a onClick="unPurchase(${good.id})" class="a_btn">不可批购</a> 
      		<#else>
           	<a onClick="purchase(${good.id})" class="a_btn">可批购</a> 		          		
      		</#if>
		   </#if>
		   <#if Roles.hasRole("POS_INSTORE")><a onClick="importTerminal(${good.id})" class="a_btn putStorage_a">入库</a></#if>
		   <#if Roles.hasRole("POS_COMMENT_MANAGE")><a href="<@spring.url "/good/pos/${good.id}/comments" />" class="a_btn">评论管理</a></#if>
		   <#if Roles.hasRole("POS_TOP_START")><a onClick="stop(${good.id})" class="a_btn">停用</a></#if>
       		<a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       		
       <#elseif status=6>
		   <#if Roles.hasRole("POS_TOP_START")><a onClick="start(${good.id})" class="a_btn">启用</a></#if>
		   <#if Roles.hasRole("POS_EDIT")><a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn">编辑</a></#if>
       		<a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>
       </#if>

	 </td> 
   </tr>