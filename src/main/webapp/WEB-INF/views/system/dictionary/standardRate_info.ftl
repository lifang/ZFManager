<tr>
    <td>${(standardRate.merchantTypeName)!""}</td>
    <td>${(((standardRate.baseRate)!"0")?number/10)}</td>
    <td>${(standardRate.description)!""}</td>
    <td><a value="${standardRate.id}" class="a_btn editStandardRate">编辑</a>
        <a value="${standardRate.id}" class="a_btn delStandardRate">删除</a></td>
</tr>