<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">用户</a></li>
        <li><a href="#">用户详情</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>用户详情</h1>
        <div class="userTopBtnBox">
            <a href="#" class="ghostBtn">编辑用户信息</a>
            <a href="#" class="ghostBtn">重置密码</a><a href="#" class="ghostBtn ce_a">调整积分</a>
            <a href="#" class="ghostBtn">停用</a>
        </div>
    </div>
    <div class="detailCon">
        <div class="attributes_list clear">
            <ul>
                <li>姓名：${customer.name!"- -"}</li>
                <li>所在地：${address!"- -"}</li>
                <li>手机：${customer.phone!"- -"}</li>
                <li>邮箱：${customer.email!"- -"}</li>
                <li>上次登录：<#if customer.lastLoginedAt??>${customer.lastLoginedAt?datetime}<#else>- -</#if></li>
                <li>积分：${customer.integral}分</li>
            </ul>
        </div>
    </div>
    <div class="userDetail_tab">
        <div class="udt_title">
            <ul>
                <li class="hover">商户资料</li>
                <li>终端</li>
                <li>关联代理商</li>
            </ul>
        </div>
        <div class="udt_con">
            <div id="merchants"></div>
            <div id="terminals" style="display: none;"></div>
            <div id="agents" style="display: none;"></div>
        </div>
    </div>
</div>
<script>
    function merchantPageChange(page){
        $.post("<@spring.url "/user/${customer.id}/info/merchants"/>",
                {page:page},
                function(data){
                    $("#merchants").html(data);
                });
    }
    function terminalPageChange(page){
        $.post("<@spring.url "/user/${customer.id}/info/terminals"/>",
                {page:page},
                function(data){
                    $("#terminals").html(data);
                });
    }
    $(function(){
        merchantPageChange(0);
        terminalPageChange(0);
    });
</script>
</@c.html>