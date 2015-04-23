<tr id="row_${channel.id}">
<td>${channel.name}</td>
    <td><strong class="strong_status">
    <#if channel.status=1>待审核
    <#elseif channel.status=2>初审不通过
    <#elseif channel.status=3>初审通过
    <#elseif channel.status=4>审核不通过
    <#elseif channel.status=5>正常
    <#elseif channel.status=6>已停用
    </#if>
    </strong></td>
    <td>
    <#if channel.status=1>
        <#if Roles.hasRole("PAY_CHANNEL_FIRST_VERIFY")>
        <#if channel.needPreliminaryVerify>
            <a onClick="firstCheck(${channel.id})" class="a_btn">初审通过</a>
            <a onClick="firstUnCheck(${channel.id})" class="a_btn">初审不通过</a>
        </#if>
        </#if>
        <#if Roles.hasRole("PAY_CHANNEL_VERIFY")>
        <a onClick="check(${channel.id})" class="a_btn">审核通过</a>
        <a onClick="unCheck(${channel.id})" class="a_btn">审核不通过</a>
        </#if>
        <#if Roles.hasRole("PAY_CHANNEL_SETTING_PROFIT")><a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a></#if>
        <#if Roles.hasRole("PAY_CHANNEL_EDIT")><a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a></#if>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>

    <#elseif channel.status=2>
        <#if Roles.hasRole("PAY_CHANNEL_FIRST_VERIFY")><a onClick="firstCheck(${channel.id})" class="a_btn">初审通过</a></#if>
        <#if Roles.hasRole("PAY_CHANNEL_SETTING_PROFIT")><a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a></#if>
        <#if Roles.hasRole("PAY_CHANNEL_EDIT")><a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a></#if>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>

    <#elseif channel.status=3>
        <#if Roles.hasRole("PAY_CHANNEL_VERIFY")>
        <a onClick="check(${channel.id})" class="a_btn">审核通过</a>
        <a onClick="unCheck(${channel.id})" class="a_btn">审核不通过</a>
        </#if>
        <#if Roles.hasRole("PAY_CHANNEL_SETTING_PROFIT")><a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a></#if>
        <#if Roles.hasRole("PAY_CHANNEL_EDIT")><a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a></#if>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>

    <#elseif channel.status=4>
        <#if Roles.hasRole("PAY_CHANNEL_VERIFY")><a onClick="check(${channel.id})" class="a_btn">审核通过</a></#if>
        <#if Roles.hasRole("PAY_CHANNEL_SETTING_PROFIT")><a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a></#if>
        <#if Roles.hasRole("PAY_CHANNEL_EDIT")><a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a></#if>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>

    <#elseif channel.status=5>
        <#if Roles.hasRole("PAY_CHANNEL_STOP_START")><a onClick="stop(${channel.id})" class="a_btn">停用</a></#if>
        <#if Roles.hasRole("PAY_CHANNEL_SETTING_PROFIT")><a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a></#if>
        <#if Roles.hasRole("PAY_CHANNEL_EDIT")><a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a></#if>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>
    <#elseif channel.status=6>
        <#if Roles.hasRole("PAY_CHANNEL_STOP_START")><a onClick="start(${channel.id})" class="a_btn">启用</a></#if>
        <#if Roles.hasRole("PAY_CHANNEL_SETTING_PROFIT")><a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a></#if>
        <#if Roles.hasRole("PAY_CHANNEL_EDIT")><a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a></#if>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>
    </#if>

    </td>
</tr>