<tr>
    <td>
        <#if openPrivate.infoType == 1>
            文本
        <#elseif openPrivate.infoType == 2>
            图片
        <#elseif openPrivate.infoType == 3>
            查询
        </#if>
    </td>
    <td>${(openPrivate.name)!""}</td>
    <td>${openPrivate.introduction!""}</td>
    <td>${openPrivate.queryMark!""}</td>
    <td><a value="${openPrivate.id}" class="a_btn editOpenPrivate">编辑</a>
        <a value="${openPrivate.id}" class="a_btn delOpenPrivate">删除</a>
    </td>
</tr>