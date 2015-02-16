<#import "../../page.ftl" as pager>
<div class="uesr_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col />
            <col />
            <col width="250" />
        </colgroup>
        <thead>
        <tr>
            <th>支付通道</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#if (channels.content)??>
            <#list channels.content as good>
                <#include "pageRowChannel.ftl" />
            </#list>
        </#if>
        </tbody>
    </table>
</div>
<@pager.p page=channels.currentPage totalPages=channels.totalPage functionName="channelPageChange"/>