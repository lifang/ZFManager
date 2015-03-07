<#import "../page.ftl" as pager>
<div class="user_statistics">交易总金额：<strong>￥${((profits.amounts!0)/100)?string("0.00")}</strong>&nbsp;&nbsp;交易总笔数：<strong>${recordPage.total}笔</strong>&nbsp;&nbsp;
    产出分润：<strong>￥${((profits.gets!0)/100)?string("0.00")}</strong>&nbsp;&nbsp;需支付分润：<strong>￥${((profits.pays!0)/100)?string("0.00")}</strong></div>

<div class="user_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col>
            <col>
            <col>
            <col>
            <col>
            <col>
            <col>
        </colgroup>
        <thead>
        <tr>
            <th>交易号</th>
            <th>交易时间</th>
            <th>终端号</th>
            <th>所属代理商</th>
            <th>交易金额</th>
            <th>手续费</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list recordPage.content as record>
        <tr>
            <td>${record.tradeNumber}</td>
            <td>${record.tradedAt?datetime}</td>
            <td>${record.terminalNumber}</td>
            <td>华润</td>
            <td><strong>￥${(record.amount/100)?string("0.00")}</strong></td>
            <td><strong>￥${(record.poundage/100)?string("0.00")}</strong></td>
            <td><strong class="strong_status">${record.tradeStatusName}</strong></td>
            <td><a href="<@spring.url "/trade/${record.id}/info"/>" class="a_btn">查看详情</a></td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<@pager.p page=recordPage.currentPage totalPages=recordPage.totalPage functionName="pageChange"/>