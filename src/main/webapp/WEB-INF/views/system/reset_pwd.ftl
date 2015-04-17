<div class="content clear">
    <div class="user_title"><h1>重置密码</h1>
        <div class="userTopBtnBox">
            <a href="javascript:history.go(-1);" class="ghostBtn">返回</a>
        </div>
    </div>
    <div class="item_list clear">
        <ul>
            <li><span class="labelSpan">新密码：</span>
                <div class="text"><input name="password" type="password"></div>
            </li>
            <li><span class="labelSpan">确认密码：</span>
                <div class="text"><input name="confirmPassword" type="password"></div>
            </li>
        </ul>
    </div>
    <div class="btnBottom"><button class="blueBtn" onclick="submitData()">保存</button></div>
</div>
<script>
    function submitData() {
        var password=$("input[name='password']").val();
        var confirmPassword=$("input[name='confirmPassword']").val();
        if(isNull(password, "密码不能为空!")){return false;}
        if(password!=confirmPassword){
            showErrorTip("密码和确认密码必须相同");
            return false;
        }

        if(!checkPassword(password)){
            return false;
        }

        $.post("<@spring.url "/system/operate/account/${customer.id}/resetpwd" />",
                { 'password': password},
                function(data){
                    if(data.code==1){
                        showErrorTip("修改成功！");
                    }
                }
        );
    }

</script>