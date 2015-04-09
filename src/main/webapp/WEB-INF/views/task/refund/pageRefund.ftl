<#import "../../page.ftl" as pager>
<#import "assign.ftl" as assign />
<div class="user_table">
                    	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
                         <colgroup>
                         	<col width="30" />
                            <col />
                            <col />
                            <col />
                            <col />
                            <col width="160" />
                         </colgroup>
                         <thead>
                          <tr>
                            <th><input name="cb_all" type="checkbox" value="" /></th>
                            <th>退款单号</th>
                            <th>申请日期</th>
                            <th>相关业务</th>
                            <th>状态</th>
                            <th>操作</th>
                          </tr>
                          </thead>
                          <tbody>
                          <#if (refund.content)??>
                          	<#list refund.content as refu>
                          		<#include "pageRowOutStore.ftl" />
                          	</#list>
                          </#if>
                          </tbody>                          
                        </table>
                    </div> 
    <@assign.assign name="refund" page=refund.currentPage suspend=1/>
	<@pager.p page=refund.currentPage totalPages=refund.totalPage functionName="outRefundPageChange"/>	