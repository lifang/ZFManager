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
        <#if channel.needPreliminaryVerify>
            <a onClick="firstCheck(${channel.id})" class="a_btn">初审通过</a>
            <a onClick="firstUnCheck(${channel.id})" class="a_btn">初审不通过</a>
        </#if>
        <a onClick="check(${channel.id})" class="a_btn">审核通过</a>
        <a onClick="unCheck(${channel.id})" class="a_btn">审核不通过</a>
        <a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>

    <#elseif channel.status=2>
        <a onClick="firstCheck(${channel.id})" class="a_btn">初审通过</a>
        <a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>

    <#elseif channel.status=3>
        <a onClick="check(${channel.id})" class="a_btn">审核通过</a>
        <a onClick="unCheck(${channel.id})" class="a_btn">审核不通过</a>
        <a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>

    <#elseif channel.status=4>
        <a onClick="check(${channel.id})" class="a_btn">审核通过</a>
        <a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>

    <#elseif channel.status=5>
        <a onClick="stop(${channel.id})" class="a_btn">停用</a>
        <a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>
    <#elseif channel.status=6>
        <a onClick="start(${channel.id})" class="a_btn">启用</a>
        <a href="<@spring.url "/good/channel/${channel.id}/profit" />" class="a_btn">设置分润</a>
        <a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="a_btn">编辑</a>
        <a href="<@spring.url "/good/channel/${channel.id}/info" />" class="a_btn">查看详情</a>
    </#if>

    </td>
</tr>