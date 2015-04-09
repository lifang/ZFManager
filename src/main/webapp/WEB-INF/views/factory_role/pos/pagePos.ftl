<#import "../../page.ftl" as pager>
<div class="uesr_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col width="200">
            <col width="150">
            <col>
            <col>
            <col>
            <col width="80">
            <col width="150">
        </colgroup>
        <thead>
        <tr>
            <th>POS机名称</th>
            <th>品牌型号</th>
            <th>库存数</th>
            <th>自有库存</th>
            <th>状态</th>
            <th>上架</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#if (goods.content)??>
            <#list goods.content as good>
        <tr>
            <td>${(good.title)!}</td>
            <td>${(good.goodBrand.name)!""}&nbsp;${good.modelNumber!""}</td>
            <td>${(good.quantity)!0}</td>
            <td>${(good.belongsTo)???string("是","")}</td>
            <td><strong class="strong_status">
            <#if good.status=1>待审核
            <#elseif good.status=2>初审不通过
            <#elseif good.status=3>初审通过
            <#elseif good.status=4>审核不通过
            <#elseif good.status=5>正常
            <#elseif good.status=6>已停用
            </#if>
            </strong></td>
            <td>${good.isPublished???string("是","")}</td>
            <td>
                <#if good.status=1>
                    <a href="<@spring.url "/factory/pos/${good.id}/edit" />" class="a_btn">编辑</a>
                    <a href="<@spring.url "/factory/pos/${good.id}/info" />" class="a_btn">查看详情</a>

                <#elseif good.status=2>
                    <a href="<@spring.url "/factory/pos/${good.id}/edit" />" class="a_btn">编辑</a>
                    <a href="<@spring.url "/factory/pos/${good.id}/info" />" class="a_btn">查看详情</a>

                <#elseif good.status=3>
                    <a href="<@spring.url "/factory/pos/${good.id}/edit" />" class="a_btn">编辑</a>
                    <a href="<@spring.url "/factory/pos/${good.id}/info" />" class="a_btn">查看详情</a>

                <#elseif good.status=4>
                    <a href="<@spring.url "/factory/pos/${good.id}/edit" />" class="a_btn">编辑</a>
                    <a href="<@spring.url "/factory/pos/${good.id}/info" />" class="a_btn">查看详情</a>

                <#elseif good.status=5>
                    <a href="<@spring.url "/factory/pos/${good.id}/info" />" class="a_btn">查看详情</a>

                <#elseif good.status=6>
                    <a href="<@spring.url "/factory/pos/${good.id}/edit" />" class="a_btn">编辑</a>

                </#if>
            </td>
        </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>
      
	<@pager.p page=goods.currentPage totalPages=goods.totalPage functionName="posPageChange"/>	