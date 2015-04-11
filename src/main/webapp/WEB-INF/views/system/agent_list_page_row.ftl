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
        <a onClick="firstCheck(${agent.id})" class="a_btn">初审通过</a>
        <a onClick="firstUnCheck(${agent.id})" class="a_btn">初审不通过</a>
        <a onClick="check(${agent.id})" class="a_btn">审核通过</a>
        <a onClick="unCheck(${agent.id})" class="a_btn">审核不通过</a>
        <a href="<@spring.url "/system/agent/${agent.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/system/agent/${agent.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/system/agent/${agent.id}/info" />" class="a_btn">查看详情</a>
        <a href="<@spring.url "/system/agent/${agent.id}/resetpwd" />" class="a_btn">密码重置</a>
    <#elseif agent.status=2>
        <a onClick="firstCheck(${agent.id})" class="a_btn">初审通过</a>
        <a href="<@spring.url "/system/agent/${agent.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/system/agent/${agent.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/system/agent/${agent.id}/info" />" class="a_btn">查看详情</a>
        <a href="<@spring.url "/system/agent/${agent.id}/resetpwd" />" class="a_btn">密码重置</a>

    <#elseif agent.status=3>
        <a onClick="check(${agent.id})" class="a_btn">审核通过</a>
        <a onClick="unCheck(${agent.id})" class="a_btn">审核不通过</a>
        <a href="<@spring.url "/system/agent/${agent.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/system/agent/${agent.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/system/agent/${agent.id}/info" />" class="a_btn">查看详情</a>
        <a href="<@spring.url "/system/agent/${agent.id}/resetpwd" />" class="a_btn">密码重置</a>

    <#elseif agent.status=4>
        <a onClick="check(${agent.id})" class="a_btn">审核通过</a>
        <a href="<@spring.url "/system/agent/${agent.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/system/agent/${agent.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/system/agent/${agent.id}/info" />" class="a_btn">查看详情</a>
        <a href="#" class="a_btn">密码重置</a>

    <#elseif agent.status=5>
        <a onClick="stop(${agent.id})" class="a_btn">停用</a>
        <a href="<@spring.url "/system/agent/${agent.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/system/agent/${agent.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/system/agent/${agent.id}/info" />" class="a_btn">查看详情</a>
        <a href="<@spring.url "/system/agent/${agent.id}/resetpwd" />" class="a_btn">密码重置</a>

    <#elseif agent.status=6>
        <a onClick="start(${agent.id})" class="a_btn">启用</a>
        <a href="<@spring.url "/system/agent/${agent.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/system/agent/${agent.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/system/agent/${agent.id}/info" />" class="a_btn">查看详情</a>
        <a href="<@spring.url "/system/agent/${agent.id}/resetpwd" />" class="a_btn">密码重置</a>

    </#if>

    </td>
</tr>
