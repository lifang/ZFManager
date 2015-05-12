<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li>系统</li>
            <li><a href="<@spring.url "/system/agent/list" />">代理商</a></li>
            <li><a href="javascript:void(0)" onclick="reload()">重置密码</a></li>
        </ul>
    </div>
<#include "reset_pwd.ftl"/>
</@c.html>