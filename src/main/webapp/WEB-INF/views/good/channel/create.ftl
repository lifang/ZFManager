<#import "../../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">商品</a></li>
            <li><a href="#">支付通道</a></li>
            <#if channel??>
                <li><a href="#">编辑支付通道</a></li>
            <#else>
                <li><a href="#">创建支付通道</a></li>
            </#if>
        </ul>
    </div>
    <#include "../../common/channel/create_part.ftl"/>
</@c.html>
