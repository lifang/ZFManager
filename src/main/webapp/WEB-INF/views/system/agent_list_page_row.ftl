<tr id="row_${agent.id}">
    <td>${(agent.companyName)!""}</td>
    <td>${(agent.terminalCount)!""}</td>
    <td>${(agent.terminalOpenCount)!""}</td>
    <td>${(agent.createdAt)?string("yyyy/MM/dd HH:mm:ss")}</td>
    <td>
    <#if agent.status=1>待审核
    <#elseif agent.status=2>初审不通过
    <#elseif agent.status=3>初审通过
    <#elseif agent.status=4>审核不通过
    <#elseif agent.status=5>正常
    <#elseif agent.status=6>已停用
    </#if>
    </td>
    <td>
    <#if agent.status=1>
        <#if Roles.hasRole("AGENT_FIRST_VERIFY")>
        <a onClick="firstCheck(${agent.id})" class="a_btn">初审通过</a>
        <a onClick="firstUnCheck(${agent.id})" class="a_btn">初审不通过</a>
        </#if>
        <#if Roles.hasRole("AGENT_VERIFY")>
        <a onClick="check(${agent.id})" class="a_btn">审核通过</a>
        <a onClick="unCheck(${agent.id})" class="a_btn">审核不通过</a>
        </#if>
    <#elseif agent.status=2>
        <#if Roles.hasRole("AGENT_FIRST_VERIFY")>
        <a onClick="firstCheck(${agent.id})" class="a_btn">初审通过</a>
        </#if>
    <#elseif agent.status=3>
        <#if Roles.hasRole("AGENT_VERIFY")>
        <a onClick="check(${agent.id})" class="a_btn">审核通过</a>
        <a onClick="unCheck(${agent.id})" class="a_btn">审核不通过</a>
        </#if>
    <#elseif agent.status=4>
        <#if Roles.hasRole("AGENT_VERIFY")>
        <a onClick="check(${agent.id})" class="a_btn">审核通过</a>
        </#if>
    <#elseif agent.status=5>
        <#if Roles.hasRole("AGENT_STOP_START")><a onClick="stop(${agent.id})" class="a_btn">停用</a></#if>
    <#elseif agent.status=6>
        <#if Roles.hasRole("AGENT_STOP_START")><a onClick="start(${agent.id})" class="a_btn">启用</a></#if>
    </#if>
<#if Roles.hasRole("AGENT_SET_PROFIT")>
        <a href="<@spring.url "/system/agent/${agent.id}/profit" />" class="a_btn" target="_blank">设置分润</a>
</#if>
<#if Roles.hasRole("AGENT_CREATE_EDIT")>
        <a href="<@spring.url "/system/agent/${agent.id}/edit" />" class="a_btn" target="_blank">编辑</a>
</#if>
        <a href="<@spring.url "/system/agent/${agent.id}/info" />" class="a_btn" target="_blank">查看详情</a>
<#if Roles.hasRole("AGENT_RESET_PWD")>
        <a href="<@spring.url "/system/agent/${agent.id}/resetpwd" />" class="a_btn" target="_blank">密码重置</a>
</#if>
    </td>
</tr>
