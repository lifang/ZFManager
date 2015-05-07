<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="<@spring.url "/terminal/list"/>">终端</a></li>
            <li><a href="<@spring.url "/terminal/${terminal.id}/info" />">终端详情</a></li>
        </ul>
    </div>
    <#include "../common/terminal/info_part.ftl"/>
</@c.html>