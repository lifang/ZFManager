<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li>系统</li>
        <li>运营账号</li>
        <li><a href="<@spring.url "/system/operate/accounts"/>">管理运营账号</a></li>
        <li><a href="<@spring.url "/system/operate/account/create"/>">创建</a></li>
    </ul>
</div>
<div class="content clear">

    <div class="myInfoNav">
        <ul>
            <li><a href="<@spring.url "/system/operate/accounts"/>" class="hover">管理运营账号</a></li>
            <li><a href="<@spring.url "/system/operate/roles"/>">管理角色</a></li>
        </ul>
    </div>

    <div class="user_title">
        <h1>创建运营账号</h1>
    </div>

        <div class="item_list clear">
            <ul>
                <li><span class="labelSpan">姓名：</span>
                    <div class="text"><input name="name" type="text"></div>
                </li>
                <li><span class="labelSpan">帐号ID：</span>
                    <div class="text"><input name="account" type="text"></div>
                </li>
                <li><span class="labelSpan">密码：</span>
                    <div class="text"><input name="password" type="password"></div>
                </li>
                <li><span class="labelSpan">确认密码：</span>
                    <div class="text"><input name="re_password" type="password"></div>
                </li>
                <li><span class="labelSpan">角色：</span>
                    <div class="text">
                        <select name="role" class="select_default">
                            <#list roleList as role>
                                <option value="${role.id}">${role.roleName}</option>
                            </#list>
                        </select>
                        <a class="pay_add_a">+</a>
                        <div class="ia_area"></div>
                    </div>
                </li>
            </ul>
        </div>
        <div class="btnBottom">
            <button class="blueBtn" onclick="submitData()">创建</button>
        </div>
</div>
<script>
    $(function(){
        $("a.pay_add_a").click(function(){
            var s=$("select option:selected");
            var v=s.val();
            var t;
            $("div.ia_area > span").each(function(){
                if($(this).attr("data-id")==v){
                    alert("该角色已添加！");
                    t=true;
                }
            });
            if(t){return;}
            var span=$("<span class='iaa_c' data-id='"+v+"'>"+ s.text()+"<a class='dele'>删除</a></span>");
            span.find("a").click(function(){
                span.remove();
            });
            $("div.ia_area").append(span);
        });

        $("a.dele").click(function(){
            $(this).parent().remove();
        });
    })

    function submitData(){
        var name=$("input[name=name]").val();
        if(isNull(name,"姓名不能为空!")){
            return false;
        }
        var account=$("input[name=account]").val();
        if(isNull(account,"帐号ID不能为空!")){
            return false;
        }
        var password=$("input[name=password]").val();
        if(isNull(password,"密码不能为空!")){
            return false;
        }
        var re_password=$("input[name=re_password]").val();
        if(password!=re_password){
            showErrorTip("两次密码输入不一致!");
            return false;
        }
        if(!checkPassword(password)){
            return false;
        }

        var roles;
        $("div.ia_area > span").each(function(){
            var v=$(this).attr("data-id");
            if(!roles){
                roles = v;
            }else{
                roles = roles+","+v;
            }
        });
        if(isNull(roles,"角色不能为空！")){
            return false;
        }

        $.post("<@spring.url "/system/operate/account/create" />",
                {name: name,
                    account: account,
                    password: password,
                    roles: roles
                },
                function(data){
                    if(data.code==1){
                        window.location.href="<@spring.url "/system/operate/accounts" />";
                    }else{
                        showErrorTip(data.message);
                    }
                }
        );
    }
</script>
</@c.html>