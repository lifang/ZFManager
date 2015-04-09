<tr data-id="${customer.id}">
    <td>${customer.name!"- -"}</td>
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
    <#if customer.status==1>
        <a href="#" class="a_btn">重置密码</a>
        <a href="<@spring.url "/user/${customer.id}/edit"/>" class="a_btn">编辑</a>
        <a href="<@spring.url "/user/${customer.id}/info"/>" class="a_btn">查看详情</a>
    <#elseif customer.status==2>
        <a href="#" class="a_btn">重置密码</a>
        <a href="<@spring.url "/user/${customer.id}/edit"/>" class="a_btn">编辑</a>
        <a href="javascript:userStatus(${customer.id});" class="a_btn">停用</a>
        <a href="<@spring.url "/user/${customer.id}/info"/>" class="a_btn">查看详情</a>
    <#elseif customer.status==3>
        <a href="#" class="a_btn">重置密码</a>
        <a href="<@spring.url "/user/${customer.id}/edit"/>" class="a_btn">编辑</a>
        <a href="javascript:userStatus(${customer.id});" class="a_btn">启用</a>
        <a href="<@spring.url "/user/${customer.id}/info"/>" class="a_btn">查看详情</a>
    </#if>
    </td>
</tr>
