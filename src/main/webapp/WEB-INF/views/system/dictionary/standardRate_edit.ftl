<tr>
    <td><input value="${(standardRate.merchantTypeName)!""}" type="text"></td>
    <td><input value="${(((standardRate.baseRate)!"0")?number/10)}" type="text"></td>
    <td><input value="${(standardRate.description)!""}" type="text"></td>
    <td><a value="${(standardRate.id)!""}" class="a_btn saveStandardRate">确定</a>
    </td>
</tr>