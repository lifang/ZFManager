<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">系统</a></li>
            <li><a href="#">网站内容</a></li>
            <li><a href="#">首页公告</a></li>
            <li><a href="#">${message???string("编辑","创建")}</a></li>
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

        <div class="user_title"><h1>${message???string("编辑","创建")}首页公告</h1>
            <div class="userTopBtnBox">
                <a href="javascript:history.go(-1);" class="ghostBtn">返回</a>
            </div>
        </div>
        <div class="item_list clear">
            <ul>
                <li><span class="labelSpan">消息标题：</span>
                    <div class="text"><input id="title" type="text" class="xll" value="${(message.title)!""}"></div></li>
                <li><span class="labelSpan">消息内容：</span>
                    <div class="text"><textarea id="content" cols="" rows="" class="xxl">${(message.content)!""}</textarea></div>
                </li>
            </ul>
        </div>
        <div class="btnBottom"><button class="blueBtn" onclick="submitData()">${message???string("确定","创建")}</button></div>
    </div>
<script>
    function submitData(){
        var title = $("#title").val();
        if(title.length > 50){
            showErrorTip("标题不能超过50个字！");
            return false;
        }
        if(isNull(title, "标题不能为空！")){
            return false;
        }
        var content = $("#content").val();
        if(isNull(content, "内容不能为空！")){
            return false;
        }
        <#if message??>
            var url="<@spring.url "/system/content/webmessage/${message.id}/edit" />";
        <#else>
            var url="<@spring.url "/system/content/webmessage/create" />";
        </#if>
        $.post(url,
                {title: title,
                content: content},
                function (data) {
                    if(data.code == 1){
                        window.location.href="<@spring.url "/system/content/webmessage" />"
                    }
                });
    }

    function isNull(value, error){
        if(!isNotNull(value)){
            showErrorTip(error);
            return true;
        }
        return false;
    }

    function isNotNull(value){
        return value != "" && value != null && value != undefined;
    }
</script>
</@c.html>