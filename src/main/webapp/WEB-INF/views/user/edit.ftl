<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">用户</a></li>
        <li><a href="#">编辑</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>编辑用户</h1>
    </div>
    <div class="attributes_box">
        <div class="item_list clear">
            <ul>
                <li class="block select2"><span class="labelSpan">所在地区：</span>
                    <div class="text">
                        <select name="province">
                            <#list provinces as province>
                                <option value="${province.id}" <#if province.id==city.parentId>selected="selected"</#if>>${province.name}</option>
                            </#list>
                        </select>
                        <select name="city">
                            <#list cities as cityOption>
                                <option value="${cityOption.id}" <#if cityOption.id==city.id>selected="selected"</#if>>${cityOption.name}</option>
                            </#list>
                        </select>
                    </div>
                </li>
                <li class="block"><span class="labelSpan">手机号码：</span>
                    <div class="text"><input name="phone" type="text" value="${customer.phone!""}"></div>
                </li>
                <li class="block"><span class="labelSpan">用户名（选填）：</span>
                    <div class="text"><input name="passport" type="text" value="${customer.username!""}"></div>
                </li>
                <li class="block"><span class="labelSpan">密码：</span>
                    <div class="text"><input name="password" type="password"></div>
                </li>
                <li class="block"><span class="labelSpan">确认密码：</span>
                    <div class="text"><input name="repassword" type="password"></div>
                </li>
            </ul>
        </div>
        <div class="btnBottom">
            <button class="blueBtn">保存</button>
        </div>
    </div>
</div>
</@c.html>