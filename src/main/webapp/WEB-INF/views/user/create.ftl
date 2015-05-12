<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="<@spring.url "/user/list"/>">用户</a></li>
        <li><a href="<@spring.url "/user/create"/>">创建</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>创建用户</h1>
    </div>
    <div class="attributes_box">
            <div class="item_list clear">
                <ul>
                    <li class="block select2"><span class="labelSpan">所在地区：</span>

                        <div class="text">
                            <select id="provinceSelect">
                                <#list cities as city>
                                    <option value="${city.id}">${city.name}</option>
                                </#list>
                            </select>
                            <select id="citySelect">
                            </select>
                        </div>
                    </li>
                    <li class="block"><span class="labelSpan">手机号码：</span>
                        <div class="text"><input name="phone" type="text"/></div>
                    </li>
                    <li class="block"><span class="labelSpan">邮箱：</span>
                        <div class="text"><input name="email" type="text"/></div>
                    </li>
                    <li class="block"><span class="labelSpan">姓名（选填）：</span>
                        <div class="text"><input name="name" type="text"/></div>
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
                <button onclick="submitData()" class="blueBtn">创建</button>
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
        $("#provinceSelect").trigger("change");
    });
    function submitData(){
        var password=$("input[name='password']").val();
        var repassword=$("input[name='repassword']").val();
        if(password!=repassword){
            showErrorTip("密码和确认密码必须相同");
            return false;
        }
        if(isNull(password, "密码不能为空！")){
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

        var email=$("input[name='email']").val();
        if(isNull(email, "邮箱不能为空！")){
            return false;
        }

        if(!checkEmail(email)){
            showErrorTip("邮箱格式不正确！");
            return false;
        }

        var name=$("input[name='name']").val();
        var cityId=$("#citySelect").find("option:selected").val();
        $.post("<@spring.url "/user/create" />",
                {name: name,
                    email: email,
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

</script>
</@c.html>