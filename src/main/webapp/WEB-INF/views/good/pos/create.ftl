<#import "../../common.ftl" as c />
<@c.html>

	<div class="breadcrumb">
        <ul>
            <li>商品</li>
            <li><a href="<@spring.url "/good/pos/list"/>">POS机管理</a></li>
            <#if good??>
            <li><a href="<@spring.url "/good/pos/${good.id}/edit" />">编辑POS机</a></li>
            <#else>
            <li><a href="<@spring.url "/good/pos/create"/>">创建POS机</a></li>
            </#if>
        </ul>
    </div>
    <#include "../../common/pos/create_part.ftl"/>
</@c.html>