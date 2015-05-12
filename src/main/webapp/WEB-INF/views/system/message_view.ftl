<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li>系统</li>
        <li><a href="<@spring.url "/system/message/list" />">消息</a></li>
        <li><a href="javascript:void(0)" onclick="reload()">查看消息</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="message_con">
        <div class="mail_article">
            <div class="mail_article_title">
                <h1>${(message.title)!""}</h1>
                <p>${message.createdAt?string("yyyy/MM/dd HH:mm:ss")} <a id="deleteMessage">删除</a></p>
            </div>
            <div class="mail_article_text">
            ${(message.content)!""}
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $("#deleteMessage").click(function(){
            $.get('<@spring.url "/system/message/${message.id}/delete" />',
                    function (data) {
                        if(data.code == 1){
                            window.location.href="<@spring.url "/system/message/list" />"
                        }
                    });
        });
    })
</script>
</@c.html>