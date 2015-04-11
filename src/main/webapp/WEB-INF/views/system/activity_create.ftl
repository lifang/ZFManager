<#import "../common.ftl" as c/>
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">系统</a></li>
            <li><a href="#">网站内容</a></li>
            <li><a href="#">活动页面</a></li>
            <li><a href="#">编辑</a></li>
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

        <div class="user_title"><h1>${activity???string("编辑","创建")}活动页面</h1>
            <div class="userTopBtnBox">
                <a href="javascript:history.go(-1);" class="ghostBtn">返回</a>
            </div>
        </div>
        <div class="item_list clear">
            <ul>
                <li><span class="labelSpan">活动标题：</span>
                    <div class="text"><input id="title" value="${(activity.title)!""}" type="text" class="xll"></div></li>
                <li><span class="labelSpan">上传资源：</span>
                    <form id="fileForm" action="<@spring.url "/system/content/activity/uploadZip" />" method="post" enctype="multipart/form-data">
                    <div class="text">
                        <a href="javascript:void(0);" class="informImg_a">
                            <span>上传ZIP资源包</span><input onChange="fileChange()" name="file" multiple="" type="file" accept=".zip" value="${(activity.url)!""}">
                        </a>
                    </div>
                    </form>
                </li>
            </ul>
        </div>
        <div class="btnBottom"><button class="blueBtn" onclick="submitData(false)">保存</button><button class="blueBtn" onclick="submitData(true)">保存并预览</button></div>
    </div>
<script>
    function submitData(needShow){
        <#if activity??>
            var url = '<@spring.url "/system/content/activity/${activity.id}/edit" />';
        <#else>
            var url = '<@spring.url "/system/content/activity/create" />';
        </#if>
        var title = $("#title").val();
        var webUrl = $(":file").attr("value");
        if(isNull(title, "标题不能为空！")
        || isNull(webUrl, "资源包不能为空!")){
            return false;
        }
        if(title.length > 50){
            showErrorTip("标题不能超过50个字！");
        }
        $.post(url,
                {title: title,
                 url: webUrl},
                function (data) {
                    if(data.code == 1){
                        if(needShow){
                            window.open(webUrl);
                        }
                        window.location.href="<@spring.url "/system/content/activity" />"
                    }
                });
    }

    function fileChange(){
        var options = {
            success: function(data){
                if(data.code==1){
                    var $zipFile = $("#fileForm").find(":file");
                    if($zipFile.length > 0){
                        $zipFile.attr("value", data.result);
                    }
                    alert("上传成功!");
                }
            },
            resetForm: true,
            dataType: 'json'
        };
        $("#fileForm").ajaxSubmit(options);
        return false;
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