<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">用户</a></li>
        <li><a href="#">创建</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>创建用户</h1>
    </div>
    <div class="attributes_box">
        <form action="<@spring.url "/user/create"/>" method="post">
            <div class="item_list clear">
                <ul>
                    <li class="block select2"><span class="labelSpan">所在地区：</span>

                        <div class="text">
                            <select name="province">
                                <#list cities as city>
                                    <option value="${city.id}">${city.name}</option>
                                </#list>
                            </select>
                            <select name="city">
                            </select>
                        </div>
                    </li>
                    <li class="block"><span class="labelSpan">手机号码：</span>

                        <div class="text"><input name="phone" type="text"/></div>
                    </li>
                    <li class="block"><span class="labelSpan">用户名（选填）：</span>

                        <div class="text"><input name="passport" type="text"/></div>
                    </li class="block">
                    <li class="block"><span class="labelSpan">密码：</span>

                        <div class="text"><input name="password" type="password"/></div>
                    </li>
                    <li class="block"><span class="labelSpan">确认密码：</span>

                        <div class="text"><input name="repassword" type="password"/></div>
                    </li>
                </ul>
            </div>
            <div class="btnBottom">
                <button type="submit" class="blueBtn">创建</button>
            </div>
        </form>
    </div>
</div>
<script>
    $(function () {
        $("select[name=province]").change(function () {
            $.post("<@spring.url "/common/cities"/>",
                    {id: $(this).selected().val()},
                    function (data) {
                        $("select[name=city]").html(data);
                    });
        });
    });
</script>
</@c.html>