<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">交易</a></li>
        <li><a href="#">转账</a></li>
        <li><a href="#">统计</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title">
        <h1>交易流水统计</h1>
    </div>
    <div class="user_statistics">${tradedDateRange.minTradedAt?string("yyyy年MM月dd日")} - ${tradedDateRange.maxTradedAt?string("yyyy年MM月dd日")}   交易类型：${tradeType.tradeValue}</div>
    <div class="attributes_table">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="20%" />
                <col width="20%" />
                <col width="20%" />
                <col width="20%" />
                <col width="20%" />
            </colgroup>
            <thead>
            <tr>
                <th>代理商</th>
                <th>总笔数</th>
                <th>总金额</th>
                <th>产出分润</th>
                <th>需支付的分润</th>
            </tr>
            </thead>
            <#list statistics as s>
                <tr>
                    <td>${s.agent.companyName}</td>
                    <td>${s.nums}</td>
                    <td><strong>￥${(s.amounts/100)?string("0.00")}</strong></td>
                    <td><strong>￥${(s.gets/100)?string("0.00")}</strong></td>
                    <td><strong>￥${(s.pays/100)?string("0.00")}</strong></td>
                </tr>
            </#list>
        </table>
    </div>
</div>
</@c.html>