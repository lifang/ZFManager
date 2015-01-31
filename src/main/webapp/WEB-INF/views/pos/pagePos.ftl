<#import "../page.ftl" as pager>
<div class="uesr_table"> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table"> 
        <colgroup> 
         <col width="200"> 
         <col width="150"> 
         <col> 
         <col> 
         <col> 
         <col width="80"> 
         <col width="80"> 
         <col width="80"> 
         <col width="150"> 
        </colgroup> 
        <thead> 
         <tr> 
          <th>POS机名称</th> 
          <th>品牌型号</th> 
          <th>库存数</th> 
          <th>第三方供货</th> 
          <th>状态</th> 
          <th>上架</th> 
          <th>可租赁</th> 
          <th>可批购</th> 
          <th>操作</th> 
         </tr> 
        </thead> 
        <tbody> 
         <#if (goods.content)??>
		  <#list goods.content as good>
		         <tr> 
		          <td>${good.title!}</td> 
		          <td><#if good.goodBrand??>${good.goodBrand.name!}</#if>&nbsp;${good.modelNumber!}</td> 
		          <td>${good.quantity}</td> 
		          <td><#if good.belongsTo??>是</#if></td> 
		          <td><strong class="strong_status">
		           <#if good.status=1>待审核
			       <#elseif good.status=2>初审不通过
			       <#elseif good.status=3>初审通过   
			       <#elseif good.status=4>审核不通过
			       <#elseif good.status=5>正常
			       <#elseif good.status=6>已停用
			       </#if>
		          </strong></td> 
		          <td><#if good.isPublished??>
		          	<#if good.isPublished>是<#else>否</#if>
		          </#if></td> 
		          <td><#if good.has_lease??>
		          	<#if good.has_lease>是<#else>否</#if>
		          </#if></td> 
		          <td><#if good.hasPurchase??>
		          	<#if good.hasPurchase>是<#else>否</#if>
		          </#if></td> 
		          <td> <a href="#" class="a_btn">停用</a> <a href="#" class="a_btn">下架</a> <a href="#" class="a_btn">入库</a> <a href="#" class="a_btn">评论管理</a> <a href="#" class="a_btn">不可租赁</a> <a href="#" class="a_btn">不可批购</a> <a href="#" class="a_btn">查看详情</a> </td> 
		         </tr> 
		  </#list>
		</#if>
        
        </tbody> 
       </table> 
	</div> 
      
	<@pager.p page=goods.currentPage totalPages=goods.totalPage functionName="posPageChange"/>	