<#import "../../page.ftl" as pager>
<div class="uesr_table"> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table"> 
        <colgroup> 
         <col width="50"> 
         <col width="120"> 
         <col width="120"> 
         <col > 
         <col width="120"> 
         <col width="300">
        </colgroup> 
        <thead> 
         <tr>
          <th><input type="checkBox"/></th> 
          <th>出库单号</th> 
          <th>申请日期</th> 
          <th>相关业务</th> 
          <th>状态</th>
          <th>操作</th> 
         </tr> 
        </thead> 
        <tbody> 
         <#if (outStores.content)??>
		  <#list outStores.content as outStore>
       		<#include "pageRowOutStore.ftl" />
		  </#list>
		</#if>
        </tbody> 
       </table> 
	</div> 
      
	<@pager.p page=outStores.currentPage totalPages=outStores.totalPage functionName="posPageChange"/>	