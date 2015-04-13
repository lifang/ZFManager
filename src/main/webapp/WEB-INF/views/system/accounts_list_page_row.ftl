<tr data-id="${customer.id}">
    <td>${customer.username!"- -"}</td>
    <td>${customer.name!"- -"}</td>
    <td>${customer.createdAt?datetime}</td>
    <td><#list role as r>${r.roleName}<#if r_has_next>,</#if></#list></td>
    <td><#if customer.status==2>已启用<#elseif customer.status==3>已停用</#if></td>
    <td>
        <a href="javascript:userStatus(${customer.id})" class="a_btn"><#if customer.status==2>停用<#elseif customer.status==3>启用</#if></a>
        <a href="<@spring.url "/system/operate/account/${customer.id}/resetpwd"/>" class="a_btn">密码重置</a>
        <a href="<@spring.url "/system/operate/account/${customer.id}/edit"/>" class="a_btn">编辑</a>
    </td>
</tr>
