<div class="attributes_box">
    <h2>用户订单类型</h2>
    <div class="dataDictionary" id="orderTypeTable">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="50%">
                <col width="50%">
            </colgroup>
            <tbody><tr>
                <td>类型</td>
                <td>操作</td>
            </tr>
            <#list orderTypes as orderType>
                <#include "orderType_info.ftl"/>
            </#list>
            </tbody></table>
        <a id="addOrderType" class="pay_add_a">+</a>
    </div>
</div>
<script>
    $(function(){
        $("#addOrderType").click(function(){
            $.get('<@spring.url "/system/dictionary/orderType/0/edit" />',
                    function (data) {
                        $("#orderTypeTable").find("tbody").append(data);
                    });
        });

        $(document).delegate(".saveOrderType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            var name = $tr.find("input").val();
            if(isNull(name, "类型不能为空！")){
                return false;
            }
            if(!isNotNull(value)){
                value = 0;
            }
            $.post('<@spring.url "/system/dictionary/orderType/" />'+value+'/edit',
                    {name: name},
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

        $(document).delegate(".delOrderType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/orderType/" />'+value+'/delete',
                    function (data) {
                        if(data.code==1) {
                            $tr.remove();
                        }
                    });
        });

        $(document).delegate(".editOrderType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/orderType/" />'+value+'/edit',
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

    });

</script>