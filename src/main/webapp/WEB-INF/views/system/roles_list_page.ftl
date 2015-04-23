<#import "../page.ftl" as pager>
<div class="user_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col width="200">
            <col>
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>角色名称</th>
            <th>创建日期</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#if (page.content)??>
            <#list page.content as role>
                <#include "roles_list_page_row.ftl" />
            </#list>
        </#if>
        </tbody>
    </table>
</div>
<@pager.p page=page.currentPage totalPages=page.totalPage functionName="pageChange"/>