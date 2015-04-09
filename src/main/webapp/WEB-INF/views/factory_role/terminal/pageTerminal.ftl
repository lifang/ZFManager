<#import "../../page.ftl" as pager>
<div class="uesr_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col width="150">
            <col width="150">
            <col width="150">
            <col>
            <col>
            <col width="80">
            <col width="150">
        </colgroup>
        <thead>
        <tr>
            <th>终端号</th>
            <th>POS产品</th>
            <th>支付通道</th>
            <th>商户名称</th>
            <th>商户电话</th>
            <th>开通状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#if (terminals.content)??>
            <#list terminals.content as terminal>
            <td>${terminal.serialNum!""}</td>
            <td>${(terminal.good.title)!""}</td>
            <td>${(terminal.payChannel.name)!""}</td>
            <td>${(terminal.merchant.title)!""}</td>
            <td>${(terminal.merchant.phone)!""}</td>
            <td><strong class="strong_status">
                <#if (terminal.status)??>
                <#if terminal.status=1>已开通
                <#elseif terminal.status=2>部分开通
                <#elseif terminal.status=3>未开通
                <#elseif terminal.status=4>已注销
                <#elseif terminal.status=5>已停用
                </#if>
                </#if>
            </strong></td>
            <td><a href="<@spring.url "/factory/terminal/${terminal.id}/info" />" class="a_btn">查看详情</a></td>
            </tr>
            </#list>
        </#if>
        <tr>
        </tbody>
    </table>
</div>
<@pager.p page=terminals.currentPage totalPages=terminals.totalPage functionName="terminalPageChange"/>