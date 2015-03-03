<#import "../page.ftl" as pager>
<div class="user_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col>
            <col>
            <col>
            <col width="200">
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>帐号</th>
            <th>姓名</th>
            <th>创建日期</th>
            <th>角色</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#if (page.content)??>
            <#list page.content as customer>
                <#include "accounts_list_page_row.ftl" />
            </#list>
        </#if>
        </tbody>
    </table>
</div>
<@pager.p page=page.currentPage totalPages=page.totalPage functionName="pageChange"/>
