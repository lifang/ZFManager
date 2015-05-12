<#import "../../common.ftl" as c />
<@c.html>
<script src="<@spring.url "/resources/js/jquery.raty.js"/>"></script>
    <div class="breadcrumb">
        <ul>
            <li>商品</li>
            <li><a href="<@spring.url "/good/pos/list"/>">POS机管理</a></li>
            <li><a href="<@spring.url "/good/pos/${good.id}/comments" />">管理评论</a></li>
        </ul>
    </div>
    <#include "commentPage.ftl" />
<script type="text/javascript">
    function del(id){
        $.get('<@spring.url "/good/pos/comment/" />'+id+'/delete',
                function (data) {
                    if(data.code == 1){
                        var page = $("#currentPage").attr("value");
                        commentPageChange(page);
                    }
                });
    }

    function commentPageChange(page){
        $.get('<@spring.url "/good/pos/${good.id}/comments/page" />',
                {"page": page},
                function (data) {
                    $("div[class='content clear']").replaceWith(data);
                });
    }

    function submitComment(){
        var score = $("input[name='score']").val();
        var content = $("#content").val();
        if(content.length==0) {
            showErrorTip("请填写内容!");
            return;
        }
        $.post('<@spring.url "/good/pos/comment/create" />',
                {   "goodId": ${good.id},
                    "score": score,
                    "content": content},
                function (data) {
                    if(data.code == 1) {
                        var page = $("#currentPage").attr("value");
                        commentPageChange(page);
                    }
                });
    }


</script>
</@c.html>