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
            <li><a class="hover">管理运营账号</a></li>
            <li><a href="<@spring.url "/system/operate/roles"/>">管理角色</a></li>
        </ul>
    </div>

    <div class="user_title">
        <h1>创建运营账号</h1>
    </div>

    <div class="item_list clear">
        <ul>
            <li><span class="labelSpan">姓名：</span>
                <div class="text"><input name="" type="text"></div>
            </li>
            <li><span class="labelSpan">帐号ID：</span>
                <div class="text"><input name="" type="text"></div>
            </li>
            <li><span class="labelSpan">密码：</span>
                <div class="text"><input name="" type="text"></div>
            </li>
            <li><span class="labelSpan">确认密码：</span>
                <div class="text"><input name="" type="text"></div>
            </li>
            <li><span class="labelSpan">角色：</span>
                <div class="text">
                    <select name="" class="select_default">
                        <option>运营</option>
                    </select>
                    <a href="#" class="pay_add_a">+</a>
                    <div class="ia_area">
                        <span class="iaa_c">运营<a href="#" class="dele">删除</a></span>
                        <span class="iaa_c">管理<a href="#" class="dele">删除</a></span>
                        <span class="iaa_c">运营<a href="#" class="dele">删除</a></span>
                        <span class="iaa_c">运营<a href="#" class="dele">删除</a></span>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="btnBottom"><button class="blueBtn">创建</button></div>
</div>
</@c.html>