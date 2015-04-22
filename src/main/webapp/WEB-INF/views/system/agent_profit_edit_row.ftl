<tr>
    <td>
        <select name="" class="select_s">
        <#list payChannels as payChannel>
            <option value="${payChannel.id}"
            ${(agentPayChannel?? && (payChannel.id)=(agentPayChannel.id))?string("selected","")}
                    >${(payChannel.name)!""}</option>
        </#list>
        </select>
    </td>
    <td>
        <div class="rate_agent">
            <table width="100%" border="0" cellspacing="1" cellpadding="0"  style="table-layout: fixed;">
                <tbody><tr>
                <#list tradeTypes as tradeType>
                    <td>${tradeType.tradeValue}</td>
                </#list>
                </tr>
                <tr>
                <#list tradeTypes as tradeType>
                    <td value="${tradeType.id}">
                        <#if profitSettingMap?? && agentPayChannel??>
                        <#assign profitSettingList = profitSettingMap[agentPayChannel.id+"_"+tradeType.id]/>
                        </#if>
                        <#if profitSettingList?? && (profitSettingList?size) gt 0>
                        <#list profitSettingList as profitSetting>
                            <#if profitSetting.floorNumber==0>
                            <p> <input name="" type="text"  value="${profitSetting.percent/10}" class="input_s">%</p>
                            <#else>
                                <p><input name="" type="text"  value="${(profitSetting.floorNumber)/1000}" class="input_xs">
                                    <input name="" type="text"  value="${profitSetting.percent/10}" class="input_xs">%
                                </p>
                            </#if>
                        </#list>
                        <#else>
                        <p><input name="" type="text"  class="input_s">%</p>
                        </#if>
                        <p><a class="a_btn addEchelonProfit">增加梯队分润</a></p>
                    </td>
                </#list>
                </tr>
                </tbody></table>
        </div>
    </td>
    <td><a  value="${(agentPayChannel.id)!""}" class="a_btn saveProfit">保存</a></td>
</tr>
