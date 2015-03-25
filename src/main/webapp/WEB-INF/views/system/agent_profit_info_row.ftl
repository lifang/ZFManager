<tr>
    <td class="channelId" value="${agentPayChannel.id}">${agentPayChannel.name}</td>
    <td>
        <div class="rate_agent">
            <table width="100%" border="0" cellspacing="1" cellpadding="0"  style="table-layout: fixed;">
                <tbody>
                <tr>
                <#list tradeTypes as tradeType>
                    <td>${tradeType.tradeValue}</td>
                </#list>
                </tr>
                <tr>
                <#list tradeTypes as tradeType>
                    <td>
                        <#list (profitSettingMap[agentPayChannel.id+"_"+tradeType.id]) as profitSetting>
                            <p>${(profitSetting.floorNumber==0)?string("",((profitSetting.floorNumber)/100)+"-")}${profitSetting.percent}‰</p>
                        </#list>
                    </td>
                </#list>
                </tr>
                </tbody></table>
        </div>
    </td>
    <td><a value="${agentPayChannel.id}" class="a_btn editProfit">编辑</a></td>
</tr>