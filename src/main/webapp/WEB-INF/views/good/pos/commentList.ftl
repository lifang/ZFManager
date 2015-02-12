<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">商品</a></li>
        <li><a href="#">POS机管理</a></li>
        <li><a href="#">管理评论</a></li>
    </ul>
</div>
<script type="text/javascript">
    function check(id){
        $.get('<@spring.url "/good/pos/waitComment/" />'+id+'/check',
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
        $.get('<@spring.url "/good/pos/comment/page" />',
                {"page": page},
                function (data) {
                    $("div[class='content clear']").replaceWith(data);
                });
    }


</script>
</@c.html>