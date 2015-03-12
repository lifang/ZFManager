<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">系统</a></li>
            <li><a href="#">代理商</a></li>
            <li><a href="#">重置密码</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>重置密码</h1>
            <div class="userTopBtnBox">
                <a href="javascript:history.go(-1);" class="ghostBtn">返回</a>
            </div>
        </div>
        <div class="item_list clear">
            <ul>
                <li><span class="labelSpan">新密码：</span>
                    <div class="text"><input name="password" type="text"></div>
                </li>
                <li><span class="labelSpan">确认密码：</span>
                    <div class="text"><input name="confirmPassword" type="text"></div>
                </li>
            </ul>
        </div>
        <div class="btnBottom"><button class="blueBtn" onclick="submitData()">保存</button></div>
    </div>
<script>
    function submitData() {
        var password=$("input[name='password']").val();
        var confirmPassword=$("input[name='confirmPassword']").val();
        if(checkNull(password, "密码不能为空!")){return false;}
        if(password!=confirmPassword){
            showErrorTip("密码和确认密码必须相同");
            return false;
        }

        $.post("<@spring.url "/system/operate/account/${agent.customer.id}/resetpwd" />",
                { 'password': password},
                function(data){
                    if(data.code==1){
                        showErrorTip("修改成功！");
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