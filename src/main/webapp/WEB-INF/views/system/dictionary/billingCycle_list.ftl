<div class="attributes_box">
    <h2>结算周期</h2>
    <div class="dataDictionary" id="billingCycleTable">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="25%">
                <col width="25%">
                <col width="25%">
                <col width="25%">
            </colgroup>
            <tbody><tr>
                <td>结算周期</td>
                <td>费率(‰)</td>
                <td>说明</td>
                <td>操作</td>
            </tr>
            <#list billingCycles as billingCycle>
                <#include "billingCycle_info.ftl"/>
            </#list>
            </tbody></table>
        <a id="addBillingCycle" class="pay_add_a">+</a>
    </div>
</div>
<script>
    $(function(){
        $("#addBillingCycle").click(function(){
            $.get('<@spring.url "/system/dictionary/billingCycle/0/edit" />',
                    function (data) {
                        $("#billingCycleTable").find("tbody").append(data);
                    });
        });

        $(document).delegate(".saveBillingCycle", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            var name = $tr.find("input").eq(0).val();
            var rate = $tr.find("input").eq(1).val();
            var description = $tr.find("input").eq(2).val();
            if(isNull(name, "结算周期不能为空！")
                    ||isNull(rate, "费率不能为空！")){
                return false;
            }
            var re = /^\d+(\.\d)?$/;
            if(!re.test(rate)){
                showErrorTip("费率最多为1位小数！");
                return false;
            }

            if(!isNotNull(value)){
                value = 0;
            }
            $.post('<@spring.url "/system/dictionary/billingCycle/" />'+value+'/edit',
                    {name: name,
                        rate: rate*10,
                        description: description},
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

        $(document).delegate(".delBillingCycle", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/billingCycle/" />'+value+'/delete',
                    function (data) {
                        if(data.code==1) {
                            $tr.remove();
                        }
                    });
        });

        $(document).delegate(".editBillingCycle", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/billingCycle/" />'+value+'/edit',
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

    });

</script>