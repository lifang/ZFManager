<#import "../../page.ftl" as pager>
 <div class="commodityBox">
	<table width="100%" cellspacing="0" cellpadding="0" class="b_table">
    <colgroup>
        <col width="640" />
        <col />
        <col />
        <col />
        <col />
     </colgroup>
     <thead>
      <tr>
        <th width="640">商品</th>
        <th>价格</th>
        <th>支持租赁</th>
        <th>销量</th>
        <th>评价</th>
      </tr>
     </thead>
     <#if (goods.content)??>
		  <#list goods.content as good>
       		<#include "goodPageRow.ftl" />
		  </#list>
		</#if> 
    </table>
</div>
<@pager.p page=goods.currentPage totalPages=goods.totalPage functionName="goodPageChange"/>