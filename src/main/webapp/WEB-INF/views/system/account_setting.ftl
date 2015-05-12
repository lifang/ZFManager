<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="javascript:void(0)" onclick="reload()">账户设置</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>修改密码</h1>
    </div>
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
            <button class="blueBtn" onclick="submitData()">保存修改</button>
        </div>
    </div>
</div>

<script>
    function submitData() {
        var oldPwd=$("input[name='old_pwd']").val();
        var newPwd=$("input[name='new_pwd']").val();
        var confirmPwd=$("input[name='re_new_pwd']").val();
        if(isNull(oldPwd, "原密码不能为空!")){return false;}
        if(isNull(newPwd, "新密码不能为空!")){return false;}
        if(newPwd!=confirmPwd){
            showErrorTip("新密码和确认密码必须相同");
            return false;
        }

        $.post("<@spring.url "/system/account/setting/modify" />",
                { oldPwd: oldPwd,
                    newPwd: newPwd
                },
                function(data){
                    if(data.code==1){
                        showErrorTip("修改成功！");
                    } else {
                        showErrorTip(data.message);
                    }
                }
        );
    }

</script>
</@c.html>