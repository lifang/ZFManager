<#import "../page.ftl" as pager>
<div class="uesr_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col width="150"/>
            <col width="150"/>
            <col width="150"/>
            <col/>
            <col/>
            <col width="80"/>
            <col width="150"/>
        </colgroup>
        <thead>
        <tr>
            <th>姓名</th>
            <th>电话</th>
            <th>邮件</th>
            <th>拥有终端数</th>
            <th>上次登录时间</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#if (customers.content)??>
            <#list customers.content as customer>
                <#include "row.ftl" />
            </#list>
        </#if>
        </tbody>
    </table>
</div>
<@pager.p page=customers.currentPage totalPages=customers.totalPage functionName="posPageChange"/>