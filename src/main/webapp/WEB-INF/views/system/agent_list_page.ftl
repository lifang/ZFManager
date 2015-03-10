<#import "../page.ftl" as pager>
<div class="uesr_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col>
            <col>
            <col>
            <col>
            <col>
            <col width="150">
        </colgroup>
        <thead>
        <tr>
            <th>下级代理商名称</th>
            <th>终端进货量</th>
            <th>终端开通量</th>
            <th>开通日期</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#include "agent_list_page_row.ftl"/>
        </tbody>
    </table>
</div>
<#--<@pager.p page=page.currentPage totalPages=page.totalPage functionName="pageChange"/>-->
