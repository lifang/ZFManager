<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">系统</a></li>
        <li><a href="#">运营账号</a></li>
        <li><a href="#">管理角色</a></li>
        <li><a href="#">创建</a></li>
    </ul>
</div>
<div class="content clear">

    <div class="myInfoNav">
        <ul>
            <li><a href="<@spring.url "/system/operate/accounts"/>">管理运营帐号</a></li>
            <li><a class="hover">管理角色</a></li>
        </ul>
    </div>

    <div class="user_title">
        <h1>创建角色</h1>
    </div>
    <div class="item_list clear">
        <ul>
            <li><span class="labelSpan">角色名称：</span><div class="text"><input name="" type="text"></div></li>
            <li><span class="labelSpan">功能列表：</span>
                <div class="text">
                    <div class="creationRole">
                        <div class="cr_title cr_first">
                            <input name="" type="checkbox" value=""><i></i><span>顶级权限</span>
                        </div>
                        <div class="cr_first_con">
                            <div class="cr_one">
                                <ul>
                                    <li><input name="" type="checkbox" value="">权限01-01</li>
                                    <li><input name="" type="checkbox" value="">权限01-02</li>
                                    <li><input name="" type="checkbox" value="">权限01-03</li>
                                    <li><input name="" type="checkbox" value="">权限01-04</li>
                                </ul>
                            </div>
                            <div class="cr_title cr_second">
                                <input name="" type="checkbox" value=""><i></i><span>二级权限</span>
                            </div>
                            <div class="cr_second_con">
                                <div class="cr_two">
                                    <ul>
                                        <li><input name="" type="checkbox" value="">权限02-01</li>
                                        <li><input name="" type="checkbox" value="">权限02-02</li>
                                        <li><input name="" type="checkbox" value="">权限02-03</li>
                                        <li><input name="" type="checkbox" value="">权限02-04</li>
                                    </ul>
                                </div>
                                <div class="cr_title cr_third">
                                    <input name="" type="checkbox" value=""><i></i><span>三级权限</span>
                                </div>
                                <div class="cr_three">
                                    <ul>
                                        <li><input name="" type="checkbox" value="">权限03-01</li>
                                        <li><input name="" type="checkbox" value="">权限03-02</li>
                                        <li><input name="" type="checkbox" value="">权限03-03</li>
                                        <li><input name="" type="checkbox" value="">权限03-04</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div class="btnBottom"><button class="blueBtn">创建</button></div>
</div>
</@c.html>