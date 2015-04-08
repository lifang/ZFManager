<#import "../../common.ftl" as c />
<@c.html>

	<div class="breadcrumb">
        <ul>
            <li><a href="#">POS机管理</a></li>
            <#if good??>
            <li><a href="#">编辑POS机</a></li>
            <#else>
            <li><a href="#">创建POS机</a></li>
            </#if>
        </ul>
    </div>
    <#include "../../common/pos/create_part.ftl"/>
</@c.html>