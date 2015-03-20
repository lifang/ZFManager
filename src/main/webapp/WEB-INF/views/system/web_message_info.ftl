<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">系统</a></li>
            <li><a href="#">网站内容</a></li>
            <li><a href="#">首页公告</a></li>
            <li><a href="#">详情</a></li>
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

        <div class="message_con">
            <div class="mail_article">
                <div class="mail_article_title">
                    <h1>${message.title}</h1>
                    <p>${message.createAt?string("yyyy/MM/dd HH:mm:ss")}
                        <a href="deleteMessage(${message.id})">删除</a>
                        <a href="<@spring.url "/system/content/webmessage/${message.id}/edit" />">编辑</a>
                        <a href="<@spring.url "/system/content/webmessage" />">返回</a></p>
                </div>
                <div class="mail_article_text">
                    ${message.content!""}
                </div>
            </div>
        </div>
    </div>
<script>
    function deleteMessage(id){
        $.get('<@spring.url "/system/content/webmessage/" />'+id+'/delete',
                function (data) {
                    if(data.code == 1){
                        window.location.href="<@spring.url "/system/content/webmessage" />"
                    }
                });
    }
</script>
</@c.html>