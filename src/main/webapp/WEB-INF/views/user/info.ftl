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
         <#include "info_status.ftl"/>
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
<div class="tab creditsExchange_tab" style="top: 272.5px; left: 495px; display: none;">
    <a href="#" class="close">close</a>
    <div class="tabHead">调整积分</div>
    <div class="tabBody">
        <div class="item_list clear">
            <ul>
                <li><span class="labelSpan"><input name="types" type="radio" value="1" class=""> 增加：</span>
                    <div class="text"><input name="" type="text" class=""></div>
                </li>
                <li><span class="labelSpan"><input name="types" type="radio" value="2" class=""> 减少：</span>
                    <div class="text"><input name="" type="text" class=""></div>
                </li>
                <li><span class="labelSpan"> 调整原因：</span>
                    <div class="text"><textarea name="reason" cols="" rows=""></textarea></div>
                </li>
            </ul>
        </div>
    </div>
    <div class="tabFoot"><button class="blueBtn">确定</button></div>
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
    function agentPageChange(page){
        $.post("<@spring.url "/user/${customer.id}/info/agents"/>",
                {page:page},
                function(data){
                    $("#agents").html(data);
                });
    }
    function userStatus(id){
        $.post("<@spring.url "/user/"/>"+id+"/status?source=info",function(data){
            $(".userTopBtnBox").replaceWith(data);
        });
    }
    $(function(){
        merchantPageChange(0);
        terminalPageChange(0);
        agentPageChange(0);
    });
</script>
</@c.html>