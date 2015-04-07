<#import "../../page.ftl" as pager>
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
          <tr id="row_${good.id}">
              <td>${good.title!}</td>
              <td> ${(good.goodBrand.name)!""}&nbsp;${good.modelNumber!""}</td>
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
              <td><#if good.hasLease??>
                  <#if good.hasLease>是<#else>否</#if>
              </#if></td>
              <td><#if good.hasPurchase??>
                  <#if good.hasPurchase>是<#else>否</#if>
              </#if></td>
              <td>
                  <#if good.status=1>
                      <a onClick="firstCheck(${good.id})" class="a_btn">初审通过</a>
                      <a onClick="firstUnCheck(${good.id})" class="a_btn">初审不通过</a>
                      <a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn">编辑</a>
                      <a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>

                  <#elseif good.status=2>
                      <a onClick="firstCheck(${good.id})" class="a_btn">初审通过</a>
                      <a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn">编辑</a>
                      <a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>

                  <#elseif good.status=3>
                      <a onClick="checkBtn(${good.id})" class="a_btn approve_a">审核通过</a>
                      <a onClick="unCheck(${good.id})" class="a_btn">审核不通过</a>
                      <a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn">编辑</a>
                      <a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>

                  <#elseif good.status=4>
                      <a onClick="checkBtn(${good.id})" class="a_btn approve_a">审核通过</a>
                      <a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn">编辑</a>
                      <a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>

                  <#elseif good.status=5>
                      <#if good.isPublished?? && good.isPublished>
                          <a onClick="unPublish(${good.id})" class="a_btn">下架</a>
                      <#else>
                          <a onClick="publish(${good.id})" class="a_btn">上架</a>
                      </#if>
                      <#if good.hasLease?? && good.hasLease>
                          <a onClick="unLease(${good.id})" class="a_btn">不可租赁</a>
                      <#else>
                          <a onClick="lease(${good.id})" class="a_btn">可租赁</a>
                      </#if>
                      <#if good.hasPurchase?? && good.hasPurchase>
                          <a onClick="unPurchase(${good.id})" class="a_btn">不可批购</a>
                      <#else>
                          <a onClick="purchase(${good.id})" class="a_btn">可批购</a>
                      </#if>
                      <a href="#" class="a_btn putStorage_a">入库</a>
                      <a href="<@spring.url "/good/pos/${good.id}/comments" />" class="a_btn">评论管理</a>
                      <a onClick="stop(${good.id})" class="a_btn">停用</a>
                      <a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>

                  <#elseif good.status=6>
                      <a onClick="start(${good.id})" class="a_btn">启用</a>
                      <a href="<@spring.url "/good/pos/${good.id}/edit" />" class="a_btn">编辑</a>
                      <a href="<@spring.url "/good/pos/${good.id}/info" />" class="a_btn">查看详情</a>
                  </#if>

              </td>
          </tr>
          </#list>
		</#if>
        </tbody> 
       </table> 
	</div> 
      
	<@pager.p page=goods.currentPage totalPages=goods.totalPage functionName="posPageChange"/>	