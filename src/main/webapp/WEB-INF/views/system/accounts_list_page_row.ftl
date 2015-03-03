<tr>
    <td>${customer.username!"- -"}</td>
    <td>${customer.name!"- -"}</td>
    <td>${customer.createdAt?datetime}</td>
    <td>运营</td>
    <td><#if customer.status==2>已启用<#elseif customer.status==3>已停用</#if></td>
    <td><a href="#" class="a_btn">启用</a><a href="#" class="a_btn">密码重置</a><a href="#" class="a_btn">编辑</a></td>
</tr>
