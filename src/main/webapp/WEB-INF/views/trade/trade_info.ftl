<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">交易</a></li>
        <li><a href="#">详情</a></li>
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
            <li>商户名称：${tradeRecord.merchantName}</li>
            <li>商户电话：29283736465</li>
            <li>所属代理商：${tradeRecord.agent.companyName}</li>
            <li>付款银行卡：622848563987143666</li>
            <li>转入银行卡：62284866976842</li>
            <li>支付通道：收账宝</li>
            <li>交易批次号：${tradeRecord.batchNumber}</li>
            <li>交易流水号：${tradeRecord.tradeNumber}</li>
        </ul>
    </div>
</div>
</@c.html>