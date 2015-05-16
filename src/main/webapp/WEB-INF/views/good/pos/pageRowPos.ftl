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
     <td><#if good.isRecommend??>
         <#if good.isRecommend>是<#else>否</#if>
     </#if></td>
         <td>
	   <#if status=1>
		   <#if Roles.hasRole("POS_FIRST_VERIFY")>
       		<a onClick="firstCheck(${good.id})" class="a_btn">初审通过</a>
       		<a onClick="firstUnCheck(${good.id})" class="a_btn">初审不通过</a>
		   </#if>
		   <#if Roles.hasRole("POS_EDIT")>
       		<a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn" target="_blank">编辑</a>
            <a href="<@spring.url "/good/pos/${good.id}/imgInfo" />" class="a_btn" target="_blank">图片详情</a>
            <a href="<@spring.url "/good/pos/${good.id}/cellPhoneImgInfo" />" class="a_btn" target="_blank">手机商品图片</a>
           </#if>
       		<a href="http://www.ebank007.com/#/shopshow?goodId=${good.id}" class="a_btn" target="_blank">查看详情</a>
       <#elseif status=2>
		   <#if Roles.hasRole("POS_FIRST_VERIFY")>
            <a onClick="firstCheck(${good.id})" class="a_btn">初审通过</a>
		   </#if>
		   <#if Roles.hasRole("POS_EDIT")>
               <a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn" target="_blank">编辑</a>
               <a href="<@spring.url "/good/pos/${good.id}/imgInfo" />" class="a_btn" target="_blank">图片详情</a>
               <a href="<@spring.url "/good/pos/${good.id}/cellPhoneImgInfo" />" class="a_btn" target="_blank">手机商品图片</a>
           </#if>
       		<a href="http://www.ebank007.com/#/shopshow?goodId=${good.id}" class="a_btn" target="_blank">查看详情</a>
       		
       <#elseif status=3>
		   <#if Roles.hasRole("POS_SECOND_VERIFY")>
       		<a onClick="checkBtn(${good.id})" class="a_btn approve_a">审核通过</a> 
       		<a onClick="unCheck(${good.id})" class="a_btn">审核不通过</a>
		   </#if>
		   <#if Roles.hasRole("POS_EDIT")>
               <a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn" target="_blank">编辑</a>
               <a href="<@spring.url "/good/pos/${good.id}/imgInfo" />" class="a_btn" target="_blank">图片详情</a>
               <a href="<@spring.url "/good/pos/${good.id}/cellPhoneImgInfo" />" class="a_btn" target="_blank">手机商品图片</a>
		   </#if>
       		<a href="http://www.ebank007.com/#/shopshow?goodId=${good.id}" class="a_btn" target="_blank">查看详情</a>
       		
       <#elseif status=4>
		   <#if Roles.hasRole("POS_SECOND_VERIFY")>
       		<a onClick="checkBtn(${good.id})" class="a_btn approve_a">审核通过</a>
		   </#if>
		   <#if Roles.hasRole("POS_EDIT")>
               <a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn" target="_blank">编辑</a>               <a href="<@spring.url "/good/pos/${good.id}/imgInfo" />" class="a_btn" target="_blank">图片详情</a>
               <a href="<@spring.url "/good/pos/${good.id}/imgInfo" />" class="a_btn" target="_blank">图片详情</a>
               <a href="<@spring.url "/good/pos/${good.id}/cellPhoneImgInfo" />" class="a_btn" target="_blank">手机商品图片</a>
           </#if>
       		<a href="http://www.ebank007.com/#/shopshow?goodId=${good.id}" class="a_btn" target="_blank">查看详情</a>
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
		   <#if Roles.hasRole("POS_INSTORE")><a href="<@spring.url "/good/pos/${good.id}/store" />" target="_blank" class="a_btn">库存管理</a>
		   <a href="javascript:void(0)" onClick="outStorage(${good.id})" class="a_btn outStorage_a">清除库存</a></#if>
		   <#if Roles.hasRole("POS_COMMENT_MANAGE")><a href="<@spring.url "/good/pos/${good.id}/comments" />" class="a_btn" target="_blank">评论管理</a></#if>
		   <#if Roles.hasRole("POS_TOP_START")><a onClick="stop(${good.id})" class="a_btn">停用</a></#if>
           <#if Roles.hasRole("POS_EDIT")>
               <#if (good.isPublished)?? && !(good.isPublished)>
                   <a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn" target="_blank">编辑</a>
                   <a href="<@spring.url "/good/pos/${good.id}/imgInfo" />" class="a_btn" target="_blank">图片详情</a>
                   <a href="<@spring.url "/good/pos/${good.id}/cellPhoneImgInfo" />" class="a_btn" target="_blank">手机商品图片</a>
               </#if>
           </#if>

           <#if Roles.hasRole("POS_TOP_HOME")>
           <#if (good.isPublished)?? && (good.isPublished)>
           <#if (good.isRecommend)?? && (good.isRecommend)>
               <a onClick="unTopHome(${good.id})" class="a_btn">取消推荐到首页</a>
           <#else>
               <a onClick="topHome(${good.id})" class="a_btn">推荐到首页</a>
           </#if>
           </#if>
           </#if>
           <a href="http://www.ebank007.com/#/shopshow?goodId=${good.id}" class="a_btn" target="_blank">查看详情</a>
       		
       <#elseif status=6>
		   <#if Roles.hasRole("POS_TOP_START")><a onClick="start(${good.id})" class="a_btn">启用</a></#if>
		   <#if Roles.hasRole("POS_EDIT")>
               <a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn" target="_blank">编辑</a>
               <a href="<@spring.url "/good/pos/${good.id}/imgInfo" />" class="a_btn" target="_blank">图片详情</a>
               <a href="<@spring.url "/good/pos/${good.id}/cellPhoneImgInfo" />" class="a_btn" target="_blank">手机商品图片</a>
           </#if>
       		<a href="http://www.ebank007.com/#/shopshow?goodId=${good.id}" class="a_btn" target="_blank">查看详情</a>
       </#if>

	 </td> 
   </tr>