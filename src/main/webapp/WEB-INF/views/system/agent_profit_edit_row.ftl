<tr>
    <td>
        <select name="" class="select_s">
        <#list payChannels as payChannel>
            <option value="${payChannel.id}"${(payChannel.id==1)?string("selected = "selected"","")}>${(payChannel.name)!""}</option>
        </#list>
        </select>
    </td>
    <td>
        <div class="rate_agent">
            <table width="100%" border="0" cellspacing="1" cellpadding="0" style="table-layout: fixed;">
                <tbody><tr>
                <#list tradeTypes as tradeType>
                    <td>${tradeType.tradeValue}</td>
                </#list>
                </tr>
                <tr>
                    <td><input name="" type="text" value="%" class="input_s">
                        <p><a href="#" class="a_btn">增加梯队分润</a></p>
                    </td>
                    <td><input name="" type="text" value="%" class="input_s">
                        <p><input name="" type="text" value="下限" class="input_xs">
                            <input name="" type="text" value="%" class="input_xs">
                        </p>
                        <p><a href="#" class="a_btn">增加梯队分润</a></p>
                    </td>
                    <td><input name="" type="text" value="%" class="input_s">
                        <p><a href="#" class="a_btn">增加梯队分润</a></p>
                    </td>
                    <td><input name="" type="text" value="%" class="input_s">
                        <p><a href="#" class="a_btn">增加梯队分润</a></p>
                    </td>
                    <td><input name="" type="text" value="%" class="input_s">
                        <p><a href="#" class="a_btn">增加梯队分润</a></p>
                    </td>
                </tr>
                </tbody></table>
        </div>
    </td>
    <td><a href="#" class="a_btn">保存</a></td>
</tr>