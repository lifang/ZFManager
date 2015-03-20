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
                <li><a href="<@spring.url "/system/content/webmessage" />">首页公告</a></li>
                <li><a class="hover">轮播图</a></li>
                <li><a href="<@spring.url "/system/content/activity" />">活动页面</a></li>
            </ul>
        </div>
        <div class="user_title"><h1>首页轮播图</h1></div>
        <div class="slideShow">
        <#list 0..4 as i>
            <div class="ss_list">
                <div class="ssl_box">
                    <div class="ss_img"><img src="<@spring.url "/resources/images/zp.jpg" />" class="cover"></div>
                    <div class="ss_upImg">
                        <a href="javascript:void(0);" class="informImg_a">
                            <span>重新上传</span><input name="" multiple="" type="file">
                        </a>
                    </div>
                    <div class="ss_url">
                        <label>URL：</label><input name="" type="text">
                    </div>
                </div>
                <div class="ssl_btn"><a href="#" class="ghostBtn">确定</a></div>
            </div>
        </#list>
        </div>
        <div class="img_info"><img src=""></div>
    </div>
<script>

</script>
</@c.html>