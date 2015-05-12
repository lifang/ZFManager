<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li>商品</li>
        <li><a href="<@spring.url "/good/pos/list"/>">POS机管理</a></li>
        <li><a href="<@spring.url "/good/pos/waitComment/list"/>">待审核的评论</a></li>
    </ul>
</div>
    <#include "waitCommentPage.ftl" />
<script type="text/javascript">
    function check(id){
        $.get('<@spring.url "/good/pos/comment/" />'+id+'/check',
                function (data) {
                    if(data.code == 1){
                        var page = $("#currentPage").attr("value");
                        commentPageChange(page);
                    }
                });
    }
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
        $.get('<@spring.url "/good/pos/waitComment/page" />',
                {"page": page},
                function (data) {
                    $("div[class='content clear']").replaceWith(data);
                });
    }


</script>
</@c.html>