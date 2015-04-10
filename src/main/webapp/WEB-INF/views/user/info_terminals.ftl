<#import "../page.ftl" as pager>
<div class="uesr_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col>
            <col>
            <col>
            <col>
            <col>
            <col width="80">
        </colgroup>
        <thead>
        <tr>
            <th>终端号</th>
            <th>品牌型号</th>
            <th>支付通道</th>
            <th>商户名称</th>
            <th>商户电话</th>
            <th>状态</th>
        </tr>
        </thead>
        <tbody>
        <#if (terminals.content)??>
            <#list terminals.content as terminal>
            <tr>
                <td>${(terminal.serialNum)!"- -"}</td>
                <td>${(terminal.good.goodBrand.name)!"- -"}  ${terminal.good.modelNumber!"- -"}</td>
                <td>${(terminal.payChannel.name)!"- -"}</td>
                <td>${(terminal.merchant.title)!"- -"}</td>
                <td>${terminal.merchant.phone!"- -"}</td>
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
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>
<@pager.p page=terminals.currentPage totalPages=terminals.totalPage functionName="terminalPageChange"/>