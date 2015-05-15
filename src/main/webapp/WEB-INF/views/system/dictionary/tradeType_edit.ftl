<tr>
    <td>
        <select>
            <option ${((tradeType.tradeType)??&&tradeType.tradeType==1)?string("selected='selected'","")} value="1">交易</option>
            <option ${((tradeType.tradeType)??&&tradeType.tradeType==2)?string("selected='selected'","")} value="2">其他</option>
        </select>
    </td>
    <td><input value="${(tradeType.tradeValue)!""}" type="text"></td>
    <td><a value="${(tradeType.id)!""}" class="a_btn saveTradeType">确定</a>
    <a href="javascript:void(0)" class="a_btn canceltr">取消</a></td>
</tr>