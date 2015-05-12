<#import "../common.ftl" as c />
<@c.html>
<div class="right">
    <div class="breadcrumb">
        <ul>
            <li>系统</li>
            <li>网站内容</li>
            <li><a href="javascript:void(0)" onclick="reload()">首页公告</a></li>
        </ul>
    </div>
    <div class="content clear">

        <div class="myInfoNav">
            <ul>
                <li><a class="hover">首页公告</a></li>
                <li><a href="<@spring.url "/system/content/carousel" />">轮播图</a></li>
                <li><a href="<@spring.url "/system/content/activity" />">活动页面</a></li>
            </ul>
        </div>

        <div class="user_title"><h1>首页公告列表</h1>
            <div class="userTopBtnBox">
                <a href="<@spring.url "/system/content/webmessage/create" />" class="ghostBtn">新建公告</a>
            </div>
        </div>
    <div id="page_fresh">
        <#include "web_message_list_page.ftl"/>
    </div>
    </div>
</div>
<script>
    function topMessage(id){
        $.get('<@spring.url "/system/content/webmessage/" />'+id+'/top',
                function (data) {
                    if(data.code == 1){
                        pageChange($("#pageNum").val());
                    }
                });
    }
    function cancelTopMessage(id){
        $.get('<@spring.url "/system/content/webmessage/" />'+id+'/canceltop',
                function (data) {
                    if(data.code == 1){
                        pageChange($("#pageNum").val());
                    }
                });
    }
    function deleteMessage(id){
        $.get('<@spring.url "/system/content/webmessage/" />'+id+'/delete',
                function (data) {
                    if(data.code == 1){
                        pageChange($("#pageNum").val());
                    }
                });
    }
    function pageChange(page) {
        $.get('<@spring.url "/system/content/webmessage/page" />',
                {"page": page},
                function (data) {
                    $('#page_fresh').html(data);
                });
    }
</script>
</@c.html>