<div class="userTopBtnBox">
<#if customer.status==1>
    <a href="<@spring.url "/user/${customer.id}/edit"/>" class="ghostBtn">编辑用户信息</a>
    <a href="<@spring.url "/user/${customer.id}/resetpwd"/>" class="ghostBtn">重置密码</a>
    <a href="#" class="ghostBtn ce_a">调整积分</a>
<#elseif customer.status==2>
    <a href="<@spring.url "/user/${customer.id}/edit"/>" class="ghostBtn">编辑用户信息</a>
    <a href="<@spring.url "/user/${customer.id}/resetpwd"/>" class="ghostBtn">重置密码</a>
    <a href="#" class="ghostBtn ce_a">调整积分</a>
    <a href="javascript:userStatus(${customer.id});" class="ghostBtn">停用</a>
<#elseif customer.status==3>
    <a href="<@spring.url "/user/${customer.id}/edit"/>" class="ghostBtn">编辑用户信息</a>
    <a href="<@spring.url "/user/${customer.id}/resetpwd"/>" class="ghostBtn">重置密码</a>
    <a href="#" class="ghostBtn ce_a">调整积分</a>
    <a href="javascript:userStatus(${customer.id});" class="ghostBtn">启用</a>
</#if>
</div>