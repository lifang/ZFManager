
<tr>
    <td>${(agent.companyName)!""}</td>
    <td>${(agent.terminalCount)!""}</td>
    <td>${(agent.terminalOpenCount)!""}</td>
    <td>${(agent.createdAt)?string("yyyy/MM/dd HH:mm:ss")}</td>
    <td><#if agent.status == 1>未激活
        <#elseif agent.status == 2>正常
        <#elseif agent.status == 2>停用
    </#if></td>
    <td>
        <a href="<@spring.url "/system/agent/${agent.id}/info"/>" class="a_btn">查看详情</a>
        <a href="<@spring.url "/system/agent/${agent.id}/edit"/>" class="a_btn">编辑</a>
        <a href="#" class="a_btn">密码重置</a>
        <a href="#" class="a_btn">初审</a>
        <a href="#" class="a_btn">审核</a>
        <a href="#" class="a_btn">设置分润比例</a>
    </td>
</tr>
