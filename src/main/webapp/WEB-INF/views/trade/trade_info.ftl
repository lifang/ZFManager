<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="<@spring.url "/trade/index"/>">交易</a></li>
        <li><a href="<@spring.url "/trade/${tradeRecord.id}/info"/>">详情</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title">
        <h1>${tradeType.tradeValue}交易流水详情</h1>
    </div>
    <div class="user_deal_text">
        <ul class="udt_strong">
            <li>交易金额：￥${(tradeRecord.amount/100)?string("0.00")}</li>
            <li>产出分润：<#if profit??>￥${(profit.profitsGet/100)?string("0.00")}<#else>- -</#if></li>
            <li>需付出分润：<#if profit??>￥${(profit.profitsPay/100)?string("0.00")}<#else>- -</#if></li>
            <li>交易状态：${tradeRecord.tradeStatusName}</li>
        </ul>
        <ul>
            <li>交易时间：${tradeRecord.tradedAt?datetime}</li>
            <li>终端号：${tradeRecord.terminalNumber}</li>
            <li>商户名称：${(merchant.title)!"- -"}</li>
            <li>商户电话：${(merchant.phone)!"- -"}</li>
            <li>所属代理商：${(tradeRecord.agent.companyName)!"- -"}</li>
            <#if tradeRecord.tradeTypeId == 1><li>付款银行卡：${(tradeRecord.payFromAccount)!"- -"}</li></#if>
            <#if tradeRecord.tradeTypeId == 2 || tradeRecord.tradeTypeId == 3>
                <li>付款银行卡：${(tradeRecord.payFromAccount)!"- -"}</li>
                <li>转入银行卡：${(tradeTransferRepaymentRecord.payIntoAccount)!"- -"}</li>
            </#if>
            <#if tradeRecord.tradeTypeId == 4><li>充值号码：${(tradeRechargeRecord.phone)!"- -"}</li></#if>
            <#if tradeRecord.tradeTypeId == 5>
                <li>账户名：${(tradeRecord.accountName)!"- -"}</li>
                <li>账户号码：${(tradeRecord.accountNumber)!"- -"}</li>
            </#if>
            <li>支付通道：${(payChannel.name)!"- -"}</li>
            <li>交易批次号：${tradeRecord.batchNumber!"- -"}</li>
            <li>交易流水号：${tradeRecord.tradeNumber!"- -"}</li>
        </ul>
    </div>
</div>
</@c.html>