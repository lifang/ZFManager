<tr data-id="${customer.id}">
    <td>${customer.name!"- -"}</td>
    <td>${customer.username!"- -"}</td>
    <td>${customer.phone!"- -"}</td>
    <td>${customer.email!"- -"}</td>
    <td>${terminal}</td>
    <td><#if customer.lastLoginedAt??>${customer.lastLoginedAt?datetime}<#else>- -</#if></td>
    <td><strong class="strong_status">
    <#if customer.status==1>未激活
    <#elseif customer.status==2>正常
    <#elseif customer.status==3>停用
    </#if></strong></td>
    <td>
    <#if Roles.hasRole("USER_PSW_RESET")><a href="<@spring.url "/user/${customer.id}/resetpwd"/>" class="a_btn">重置密码</a></#if>
    <#if Roles.hasRole("USER_EDIT")><a href="<@spring.url "/user/${customer.id}/edit"/>" class="a_btn">编辑</a></#if>
    <#if customer.status==1>
    <#elseif customer.status==2>
        <#if Roles.hasRole("USER_STOP_START")><a href="javascript:userStatus(${customer.id});" class="a_btn">停用</a></#if>
    <#elseif customer.status==3>
        <#if Roles.hasRole("USER_STOP_START")><a href="javascript:userStatus(${customer.id});" class="a_btn">启用</a></#if>
    </#if>
    <a href="<@spring.url "/user/${customer.id}/info"/>" class="a_btn">查看详情</a>
    </td>
</tr>
