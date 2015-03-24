<tr>
    <td>
    <#if tradeType.tradeType == 1>
        交易
    <#elseif tradeType.tradeType == 2>
        其他
    </#if></td>
    <td>${tradeType.tradeValue}</td>
    <td><a value="${tradeType.id}" class="a_btn editTradeType">编辑</a>
        <a value="${tradeType.id}" class="a_btn delTradeType">删除</a>
    </td>
</tr>