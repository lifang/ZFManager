<#import "../common.ftl" as c />
<@c.html>
<div class="content clear">
    <div class="user_title"><h1>管理账号</h1>
        <div class="userTopBtnBox">
        </div>
    </div>
    <div class="item_list clear">
        <ul>
            <li><span class="labelSpan">原密码：</span>
                <div class="text"><input name="oldPassword" type="password" class=""></div>
            </li>
            <li><span class="labelSpan">新密码：</span>
                <div class="text"><input name="newPassword" type="text"></div>
            </li>
            <li><span class="labelSpan">确认密码：</span>
                <div class="text"><input name="confirmPassword" type="text"></div>
            </li>
            <li><span class="labelSpan">售后收货地址：</span>
                <div class="text">
                    <select id="provinceSelect" class="select_l">
                    <#list provinces as province>
                        <option value="${province.id}">${province.name}</option>
                    </#list>
                    </select>
                    <select id="citySelect" class="select_l">
                    </select>
                    <input name="f_address" value="${(address.address)!""}" type="text" placeholder="详细地址">
                    <input name="f_cellphone" value="${(address.moblephone)!""}" type="text" placeholder="电话">
                </div>
            </li>

        </ul>
    </div>
    <div class="btnBottom"><button class="blueBtn" onclick="submitData()">保存</button></div>
</div>
<script>
    $(function(){
        $("input[name='f_types'][value='${(factory.types)!1}']").attr("checked", true);
        $('#provinceSelect').change(function(){
            var provinceId = $(this).children('option:selected').val();
            $.post('<@spring.url "/common/cities" />',
                    {'id':provinceId},
                    function (data) {
                        $("#citySelect").empty();
                        $("#citySelect").append(data);
                        $("#citySelect").find("option[value='${(address.city.id)!""}']").attr("selected",true);
                    });
        });
        $("#provinceSelect").find("option[value='${(address.parentCity.id)!""}']").attr("selected",true);
        $("#provinceSelect").trigger("change");
    })

    function submitData() {
        var oldPassword=$("input[name='oldPassword']").val();
        var newPassword=$("input[name='newPassword']").val();
        var confirmPassword=$("input[name='confirmPassword']").val();
        if(newPassword!=confirmPassword){
            showErrorTip("密码和确认密码必须相同");
            return false;
        }
        if(isNotNull(newPassword)){
            if(checkNull(oldPassword, "原密码不能为空!")){return false;}
        }

        var cityId=$("#citySelect").find("option:selected").val();
        var address=$("input[name='f_address']").val();
        if(checkNull(address, "详细地址不能为空!")){return false;}
        var cellphone=$("input[name='f_cellphone']").val();
        if(checkNull(cellphone, "电话不能为空!")){return false;}



        $.post("<@spring.url "/factory/user/edit" />",
                { oldPassword: oldPassword,
                    newPassword: newPassword,
                    cityId: cityId,
                    address: address,
                    cellphone: cellphone},
                function(data){
                    if(data.code==1){
                        showErrorTip("修改成功！");
                    } else{
                        showErrorTip(data.message);
                    }
                }
        );
    }

    function checkNull(value, error){
        var result = isNotNull(value);
        if(!result){
            showErrorTip(error);
        }
        return !result;
    }
    function isNotNull(value){
        return value != "" && value != null && value != undefined;
    }

</script>
</@c.html>