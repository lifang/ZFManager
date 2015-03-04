<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">系统</a></li>
        <li><a href="#">运营账号</a></li>
        <li><a href="#">管理运营账号</a></li>
        <li><a href="#">创建</a></li>
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

    <form method="post" action="<@spring.url "/system/operate/account/create"/>">
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
        <input type="hidden" name="roles">
        <div class="btnBottom"><button class="blueBtn" type="submit">创建</button></div>
    </form>
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

        $("form").submit(function(){
            var name=$("input[name=name]").val();
            if(!name){
                alert("姓名不能为空！");
                return false;
            }
            var account=$("input[name=account]").val();
            if(!account){
                alert("帐号ID不能为空！");
                return false;
            }
            var password=$("input[name=password]").val();
            if(!password){
                alert("密码不能为空！");
                return false;
            }
            var re_password=$("input[name=re_password]").val();
            if(!re_password&&password!=re_password){
                alert("两次密码输入不一致");
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
            $("input[name=roles]").val(roles);
            return true;
        });

        $("a.dele").click(function(){
            $(this).parent().remove();
        });
    })
</script>
</@c.html>