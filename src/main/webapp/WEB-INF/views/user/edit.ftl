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
                            <select id="provinceSelect">
                                <#list provinces as province>
                                    <option value="${province.id}" <#if province.id==city.parentId>selected="selected"</#if>>${province.name}</option>
                                </#list>
                            </select>
                            <select id="citySelect">
                                <#list cities as cityOption>
                                    <option value="${cityOption.id}" <#if cityOption.id==city.id>selected="selected"</#if>>${cityOption.name}</option>
                                </#list>
                            </select>
                        </div>
                    </li>
                    <li class="block"><span class="labelSpan">手机号码：</span>
                        <div class="text"><input name="phone" type="text" value="${customer.phone!""}"></div>
                    </li>
                    <li class="block"><span class="labelSpan">姓名（选填）：</span>
                        <div class="text"><input name="name" type="text" value="${customer.name!""}"></div>
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
                <button onclick="submitData()" class="blueBtn">保存</button>
            </div>
    </div>
</div>
<script>
    $(function () {
        $("#provinceSelect").change(function () {
            $.post("<@spring.url "/common/cities"/>",
                    {id: $(this).selected().val()},
                    function (data) {
                        $("#citySelect").html(data);
                    });
        });
    });
    function submitData(){
        var password=$("input[name='password']").val();
        var repassword=$("input[name='repassword']").val();
        if(password!=repassword){
            showErrorTip("密码和确认密码必须相同");
            return false;
        }

        if(!checkPassword(password)){
            return false;
        }

        var phone=$("input[name='phone']").val();
        if(isNull(phone, "手机号码不能为空！")){
            return false;
        }
        if(!checkMobile(phone)){
            showErrorTip("手机号码格式不正确！");
            return false;
        }
        var name=$("input[name='name']").val();
        var cityId=$("#citySelect").find("option:selected").val();
        $.post("<@spring.url "/user/${customer.id}/edit" />",
                {name: name,
                    phone: phone,
                    password: password,
                    cityId: cityId
                },
                function(data){
                    if(data.code==1){
                        window.location.href="<@spring.url "/user/list" />";
                    }else{
                        showErrorTip(data.message);
                    }
                }
        );


    }

    function isNull(value, error){
        if(!isNotNull(value)){
            showErrorTip(error);
            return true;
        }
        return false;
    }

    function isNotNull(value){
        return value != "" && value != null && value != undefined;
    }
    function checkMobile(str) {
        var re = /^1\d{10}$/
        if (re.test(str)) {
            return true;
        } else {
            return false;
        }
    }

</script>
</@c.html>