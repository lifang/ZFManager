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
                    <div class="text"><input name="" value="${(activity.title)!""}" type="text" class="xll"></div></li>
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
        <div class="btnBottom"><button class="blueBtn">保存</button><button class="blueBtn">保存并预览</button></div>
    </div>
<script>
    function fileChange(){
        var options = {
            success: function(data){
                if(data.code==1){
                    var $zipFile = $("#fileForm").find(":file");
                    if($zipFile.length > 0){
                        $zipFile.attr("value", data.result);
                    }
                }
            },
            resetForm: true,
            dataType: 'json'
        };
        $("#fileForm").ajaxSubmit(options);
        return false;
    }
</script>
</@c.html>