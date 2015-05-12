<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li>系统</li>
            <li>网站内容</li>
            <li><a href="javascript:void(0)" onclick="reload()">活动页面</a></li>
        </ul>
    </div>
    <div class="content clear">

        <div class="myInfoNav">
            <ul>
                <li><a href="<@spring.url "/system/content/webmessage" />">首页公告</a></li>
                <li><a href="<@spring.url "/system/content/carousel" />">轮播图</a></li>
                <li><a class="hover">活动页面</a></li>
            </ul>
        </div>

        <div class="user_title"><h1>活动页面列表</h1>
            <div class="userTopBtnBox">
                <a href="<@spring.url "/system/content/activity/create" />" class="ghostBtn">新建活动页</a>
            </div>
        </div>

        <div id="page_fresh">
            <#include "activity_list_page.ftl"/>
        </div>
    </div>
<script>

    function deleteActivity(id){
        $.get('<@spring.url "/system/content/activity/" />'+id+'/delete',
                function (data) {
                    if(data.code == 1){
                        pageChange($("#pageNum").val());
                    }
                });
    }
    function pageChange(page) {
        $.get('<@spring.url "/system/content/activity/page" />',
                {"page": page},
                function (data) {
                    $('#page_fresh').html(data);
                });
    }
</script>
</@c.html>