<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li>系统</li>
            <li>网站内容</li>
            <li><a href="javascript:void(0)" onclick="reload()">轮播图</a></li>
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
    <#assign count = (sysShufflingFigures?size)/>

        <#list 0..4 as i>
            <div class="ss_list">
                <div class="ssl_box">
                    <form id="fileForm${i}" action="<@spring.url "/system/content/carousel/uploadImg" />" method="post" enctype="multipart/form-data">
                    <div class="ss_img">
                        <img src="<@spring.url "/resources/images/zp.jpg" />" class="cover" value="${(sysShufflingFigures[i].pictureUrl)!""}">
                    </div>
                    <div class="ss_upImg">
                        <a href="javascript:void(0);" class="informImg_a">
                            <span>重新上传</span><input name="file" onChange="fileChange(this)" index="${i}" multiple="" type="file">
                        </a>
                    </div>
                    <div class="ss_url">
                        <label>URL：</label><input id="input_${i}" name="" value="${(sysShufflingFigures[i].websiteUrl)!""}" type="text">
                    </div>
                    </form>
                </div>
                <div class="ssl_btn"><a onclick="submitData(${i})" class="ghostBtn">确定</a></div>
                <div class="ssl_btn"><a onclick="delData(${i})" class="ghostBtn">清除</a></div>
                <label id="label${i}" style="display:none">${(sysShufflingFigures[i].id)!""}</label>
            </div>
        </#list>
        </div>
        <div class="img_info"><img src=""></div>
    </div>
    
    <div class="upImgLoading" style="display: block;">
    	<span><img src="../../resources/images/loading.gif"/></span>
        <p>文件上传中...</p>
    </div>
<script>
	$(function(){
        closeMask();
	})
	
    function fileChange(obj){
        showMask();
        var index = $(obj).attr("index");
        var options = {
            success: function(data){
                closeMask();
                if(data.code==1){
                    var img = $('#fileForm'+index).find(".ss_img img");
                    if(img.length > 0){
                        img.attr("value", data.result);
                    }
                    alert("上传成功！");
                }else{
                	showErrorTip(data.message);
                }
            },
            resetForm: true,
            dataType: 'json'
        };
        $('#fileForm'+index).ajaxSubmit(options);
        return false;
    }
    function submitData(index){
        var $sslist = $(".ss_list").eq(index);
        var pictureUrl = $sslist.find("img").attr("value");
        var webSiteUrl = $sslist.find(":text").val();
        if(isNull(pictureUrl, "图片不能为空！")
        ||isNull(webSiteUrl, "URL不能为空！")){
            return false;
        }
    $.post("<@spring.url "/system/content/carousel/" />"+(index+1)+"/edit",
                {pictureUrl: pictureUrl,
                    webSiteUrl: webSiteUrl},
                function (data) {
                    if(data.code == 1){
                        alert("修改成功！");
                        window.reload();
                    } else{
                        showErrorTip(data.message);
                    }
                });
    }

    function delData(index){
    	var id=$("#label"+index).text();
        $.post("<@spring.url "/system/content/carousel/" />"+id+"/del",
                function (data) {
                    if(data.code == 1){
                    	//for(var i=0;i<5;i++){
                      	//	$("#fileForm"+i).find("input[type='text']").val("");
                      	//}
                      	$("#input_" + index).val("");
                        alert("清除成功！");
                        window.location.href="../content/carousel";
                    } else{
                        showErrorTip(data.message);
                    }
                });
    }



    function showMask(){
        var doc_height = $(document).height();
        $(".mask").css({
            display : 'block',
            height : doc_height
        }).show();
        $(".upImgLoading").show();
    }
    function closeMask(){
        $(".mask").hide();
        $(".upImgLoading").hide();
    }
</script>
</@c.html>