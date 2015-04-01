<#import "../../page.ftl" as pager>
<div class="uesr_table">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
             <colgroup>
             	<col />
                <col width="130" />
                <col width="60" />
                <col width="70" />
                <col width="70" />
                <col width="160" />
                <col width="180" />
                <col />
             </colgroup>
             <thead>
              <tr>
                <th>商品</th>
                <th>批购价</th>
                <th>数量</th>
                <th>金额</th>
                <th>已付</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
              </thead>
         <#if (orders.content)??>
		  <#list orders.content as order> 
              <#include "row.ftl" />
           </#list>
		</#if>
	</table>
</div>
<@pager.p page=orders.currentPage totalPages=orders.totalPage functionName="orderBatchPageChange"/>