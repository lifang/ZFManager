<#import "../page.ftl" as pager>
<div class="uesr_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col>
            <col>
            <col width="250">
        </colgroup>
        <thead>
        <tr>
            <th>商户名称</th>
            <th>法人姓名</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#if (merchants.content)??>
            <#list merchants.content as merchant>
            <tr>
                <td>${(merchant.title)!"- -"}</td>
                <td>${(merchant.legalPersonName)!"- -"}</td>
                <td><a href="<@spring.url "/user/merchant/${merchant.id}/info"/>" class="a_btn">查看详情</a></td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>
<@pager.p page=merchants.currentPage totalPages=merchants.totalPage functionName="merchantPageChange"/>