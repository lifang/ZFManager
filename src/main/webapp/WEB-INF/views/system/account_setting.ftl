<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">账户设置</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>修改密码</h1>
    </div>
    <form action="<@spring.url "/system/account/setting/modify"/>" method="post">
        <#if result?? && result>
            密码修改成功！
        <#elseif result?? && !result>
            原密码不正确！
        </#if>
    <div class="attributes_box">
        <div class="item_list clear">
            <ul>
                <li class="block"><span class="labelSpan">原密码：</span>
                    <div class="text"><input name="old_pwd" type="password"></div>
                </li>
                <li class="block"><span class="labelSpan">新密码：</span>
                    <div class="text"><input name="new_pwd" type="password"></div>
                </li>
                <li class="block"><span class="labelSpan">确认密码：</span>
                    <div class="text"><input name="re_new_pwd" type="password"></div>
                </li>
            </ul>
        </div>
        <div class="btnBottom">
            <button class="blueBtn">保存修改</button>
        </div>
    </div>
    </form>
</div>
</@c.html>