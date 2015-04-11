<div class="attributes_box">
    <h2>签购单类型</h2>
    <div class="dataDictionary" id="orderWayTable">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="50%">
                <col width="50%">
            </colgroup>
            <tbody><tr>
                <td>类型</td>
                <td>操作</td>
            </tr>
            <#list orderWays as orderWay>
                <#include "orderWay_info.ftl"/>
            </#list>
            </tbody></table>
        <a id="addOrderWay" class="pay_add_a">+</a>
    </div>
</div>
<script>
    $(function(){
        $("#addOrderWay").click(function(){
            $.get('<@spring.url "/system/dictionary/orderWay/0/edit" />',
                    function (data) {
                        $("#orderWayTable").find("tbody").append(data);
                    });
        });

        $(document).delegate(".saveOrderWay", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            var name = $tr.find("input").val();
            if(isNull(name, "类型不能为空！")){
                return false;
            }
            if(!isNotNull(value)){
                value = 0;
            }
            $.post('<@spring.url "/system/dictionary/orderWay/" />'+value+'/edit',
                    {name: name},
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

        $(document).delegate(".delOrderWay", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/orderWay/" />'+value+'/delete',
                    function (data) {
                        if(data.code==1) {
                            $tr.remove();
                        }
                    });
        });

        $(document).delegate(".editOrderWay", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/orderWay/" />'+value+'/edit',
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

    });

</script>