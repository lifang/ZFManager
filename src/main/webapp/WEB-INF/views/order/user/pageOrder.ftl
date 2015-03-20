<#import "../../page.ftl" as pager>
<div class="uesr_table">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
            <colgroup>
                <col width="300" />
                <col width="80" />
                <col width="60" />
                <col width="80" />
                <col width="80" />
                <col />
            </colgroup>
            <thead>
            <tr>
                <th>商品</th>
                <th>单价</th>
                <th>数量</th>
                <th>总金额</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
          
 		<#if (orders.content)??>
		  <#list orders.content as order>
       		<#include "pageRowOrder.ftl" />
		  </#list>
		</#if>
        </table>
    </div>
<@pager.p page=orders.currentPage totalPages=orders.totalPage functionName="orderPageChange"/>