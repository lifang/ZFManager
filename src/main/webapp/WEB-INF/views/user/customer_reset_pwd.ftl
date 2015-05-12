<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="<@spring.url "/user/list"/>">用户</a></li>
        <li><a href="<@spring.url "/user/${customer.id}/resetpwd"/>">重置密码</a></li>
    </ul>
</div>
    <#include "../system/reset_pwd.ftl"/>
</@c.html>