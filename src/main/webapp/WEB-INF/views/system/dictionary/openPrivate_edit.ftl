<tr>
    <td>
        <select>
            <option ${((openPrivate.infoType)??&&openPrivate.infoType==1)?string("selected='selected'","")} value="1">文本</option>
            <option ${((openPrivate.infoType)??&&openPrivate.infoType==2)?string("selected='selected'","")} value="2">图片</option>
            <option ${((openPrivate.infoType)??&&openPrivate.infoType==3)?string("selected='selected'","")} value="3">查询</option>
        </select>
    </td>
    <td><input value="${(openPrivate.name)!""}" type="text"></td>
    <td><input value="${(openPrivate.introduction)!""}" type="text"></td>
    <td><input value="${(openPrivate.queryMark)!""}" type="text"></td>
    <td><a value="${(openPrivate.id)!""}" class="a_btn saveOpenPrivate">确定</a>
    <a href="javascript:void(0)" class="a_btn canceltr">取消</a></td>
</tr>