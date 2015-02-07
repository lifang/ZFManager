<#import "common.ftl" as c />
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <title>运营中心</title>
    <link href="<@spring.url "/resources/style/login.css"/>" rel="stylesheet" type="text/css"/>
    <script src="<@spring.url "/resources/js/jquery-1.11.2.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/jquery.form.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/main.js"/>"></script>
</head>
<body>
<div class="login">
    <div class="login_left">
        <div class="login_logo"><img src="<@spring.url "/resources/images/login_logo.png"/>"/></div>
    </div>
    <div class="login_right">
        <div class="lr_h">
            <ul>
                <li class="hover">运营登录</li>
                <li>第三方机构登陆</li>
            </ul>
        </div>
        <div class="lr_b">
            <div class="login_area">
                <form action="<@spring.url "/login"/>" method="post">
                    <ul>
                        <li><input name="passport" type="text"/></li>
                        <li><input name="password" type="password"/></li>
                        <li><input name="captcha" type="text" value="输入图片校验码" class="l"/>
                            <div class="yzm"><img src="<@spring.url "/captcha"/>"/></div>
                        </li>
                    </ul>
                    <div class="forget">
                        <span><input name="remember" type="checkbox" value="remember"/>下次自动登陆</span>
                        <a href="#">忘记密码？</a>
                    </div>
                    <div class="btn">
                        <button>登录</button>
                    </div>
                </form>
            </div>
            <div class="login_area">
                <form action="<@spring.url "/login"/>" method="post">
                    <ul>
                        <li><input name="passport" type="text" value="ID"/></li>
                        <li><input name="password" type="text" value="密码"/></li>
                        <li><input name="captcha" type="text" value="输入图片校验码" class="l"/>
                            <div class="yzm"><img src="<@spring.url "/captcha"/>"/></div>
                        </li>
                    </ul>
                    <div class="forget">
                        <span><input name="remember" type="checkbox" value="remember"/>下次自动登陆</span>
                        <a href="#">忘记密码？</a>
                    </div>
                    <div class="btn">
                        <button>登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("form").ajaxForm({
            success: function (data) {
                if (data.code == 1) {
                    window.location.href = "<@spring.url ""/>" + data.result;
                }
                if (data.code == -1) {
                    alert(data.message);
                }
            }
        });
    });
</script>
</body>
</html>
