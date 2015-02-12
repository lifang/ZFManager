<#import "../../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">商品</a></li>
            <li><a href="#">POS机管理</a></li>
            <li><a href="#">管理评论</a></li>
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


</script>
</@c.html>