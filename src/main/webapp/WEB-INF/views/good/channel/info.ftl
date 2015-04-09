<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">商品</a></li>
        <li><a href="#">支付通道</a></li>
        <li><a href="#">支付通道详情</a></li>
    </ul>
</div>
<#include "../../common/channel/info_part.ftl"/>

<script type="text/javascript">

    function firstUnCheck(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/firstUnCheck?source=info',
                function (data) {
                    $('.userTopBtnBox').replaceWith(data);
                });
    };

    function firstCheck(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/firstCheck?source=info',
                function (data) {
                    $('.userTopBtnBox').replaceWith(data);
                });
    };

    function unCheck(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/unCheck?source=info',
                function (data) {
                    $('.userTopBtnBox').replaceWith(data);
                });
    };

    function check(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/check?source=info',
                function (data) {
                    $('.userTopBtnBox').replaceWith(data);
                });
    };

    function stop(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/stop?source=info',
                function (data) {
                    $('.userTopBtnBox').replaceWith(data);
                });
    };

    function start(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/start?source=info',
                function (data) {
                    $('.userTopBtnBox').replaceWith(data);
                });
    };

</script>
</@c.html>