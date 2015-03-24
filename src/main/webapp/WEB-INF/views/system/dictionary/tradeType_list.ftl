<div class="attributes_box">
    <h2>交易类型</h2>
    <div class="dataDictionary" id="tradeTypeTable">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="40%">
                <col width="40%">
                <col>
            </colgroup>
            <tbody><tr>
                <td>类型</td>
                <td>类型值</td>
                <td>操作</td>
            </tr>
            <#list tradeTypes as tradeType>
                <#include "tradeType_info.ftl"/>
            </#list>
            </tbody></table>
        <a id="addTradeType" class="pay_add_a">+</a>
    </div>
</div>
<script>
    $(function(){
        $("#addTradeType").click(function(){
            $.get('<@spring.url "/system/dictionary/tradeType/0/edit" />',
                    function (data) {
                        $("#tradeTypeTable").find("tbody").append(data);
                    });
        });

        $(document).delegate(".saveTradeType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            var type = $tr.find("select").find("option:selected").val();
            var tradeValue = $tr.find("input").val();
            if(checkNull(tradeValue, "类型值不能为空！")){
                return false;
            }
            if(!isNotNull(value)){
                value = 0;
            }
            $.post('<@spring.url "/system/dictionary/tradeType/" />'+value+'/edit',
                    {type: type,
                        tradeValue: tradeValue},
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

        $(document).delegate(".delTradeType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/tradeType/" />'+value+'/delete',
                    function (data) {
                        if(data.code==1) {
                            $tr.remove();
                        }
                    });
        });

        $(document).delegate(".editTradeType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/tradeType/" />'+value+'/edit',
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

    });

</script>
