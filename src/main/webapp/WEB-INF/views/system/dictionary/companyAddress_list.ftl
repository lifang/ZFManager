<div class="attributes_box">
    <h2>公司地址</h2>
    <div class="dataDictionary" id="companyAddressTable">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="50%">
                <col width="50%">
            </colgroup>
            <tbody><tr>
                <td>地址电话</td>
                <td>操作</td>
            </tr>
            <#list companyAddresses as companyAddress>
                <#include "companyAddress_info.ftl"/>
            </#list>
            </tbody></table>
        <a id="addCompanyAddress" class="pay_add_a">+</a>
    </div>
</div>
<script>
    $(function(){
        $("#addCompanyAddress").click(function(){
            $.get('<@spring.url "/system/dictionary/companyAddress/0/edit" />',
                    function (data) {
                        $("#companyAddressTable").find("tbody").append(data);
                    });
        });

        $(document).delegate(".saveCompanyAddress", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            var name = $tr.find("input").val();
            if(checkNull(name, "地址不能为空！")){
                return false;
            }
            if(!isNotNull(value)){
                value = 0;
            }
            $.post('<@spring.url "/system/dictionary/companyAddress/" />'+value+'/edit',
                    {name: name},
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

        $(document).delegate(".delCompanyAddress", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/companyAddress/" />'+value+'/delete',
                    function (data) {
                        if(data.code==1) {
                            $tr.remove();
                        }
                    });
        });

        $(document).delegate(".editCompanyAddress", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/companyAddress/" />'+value+'/edit',
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

    });

</script>

