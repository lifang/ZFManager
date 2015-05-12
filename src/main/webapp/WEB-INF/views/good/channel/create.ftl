<#import "../../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li>商品</li>
            <li><a href="<@spring.url "/good/channel/list"/>">支付通道</a></li>
            <#if channel??>
                <li><a href="<@spring.url "/good/channel/${channel.id}/edit" />">编辑支付通道</a></li>
            <#else>
                <li><a href="<@spring.url "/good/channel/create"/>">创建支付通道</a></li>
            </#if>
        </ul>
    </div>
    <#include "../../common/channel/create_part.ftl"/>
</@c.html>
