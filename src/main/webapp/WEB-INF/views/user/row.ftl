<tr>
    <td>${customer.name!"- -"}</td>
    <td>${customer.phone!"- -"}</td>
    <td>${customer.email!"- -"}</td>
    <td>${terminals[customer_index]}</td>
    <td>${customer.lastLoginedAt!"- -"}</td>
    <td><strong class="strong_status"><#if customer.status==1>正常<#else>停用</#if></strong></td>
    <td><a href="#" class="a_btn">重置密码</a>
        <a href="<@spring.url "/user/${customer.id}/edit"/>" class="a_btn">编辑</a>
        <a href="#" class="a_btn"><#if customer.status==1>停用<#else>启用</#if></a>
        <a href="#" class="a_btn">查看详情</a>
    </td>
</tr>
