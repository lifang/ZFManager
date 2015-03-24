<#import "../page.ftl" as pager>
<div class="uesr_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col>
            <col>
            <col width="150">
        </colgroup>
        <thead>
        <tr>
            <th>机构名称</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list page.content as factory>
        <#include "factory_list_page_row.ftl"/>
        </#list>
        </tbody>
    </table>
</div>
<@pager.p page=page.currentPage totalPages=page.totalPage functionName="pageChange"/>
