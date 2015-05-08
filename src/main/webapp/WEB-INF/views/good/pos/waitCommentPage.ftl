<#import "../../page.ftl" as pager>
<div class="content clear">
    <div class="user_title"><h1>待审核的评论（${comments.total}）</h1>
    </div>
    <input type="hidden" id = "currentPage" value="${comments.currentPage}"/>
    <#list comments.content as comment>
    <div class="evaluate_item">
        <div class="td_proBox clear">
            <#if (comment.good.pictures)?? && ((comment.good.pictures?size) > 0)>
            <a href="#" class="cn_img"><img src="${(comment.good.pictures[0]).urlPath}"></a>
            </#if>
            <div class="td_proBox_info">
                <h1><a href="#">${comment.good.title}</a></h1>
                <h3>${comment.good.secondTitle}</h3>
                <ul>
                    <li><span>品牌型号：</span><div class="c_text"><#if comment.good.goodBrand??>${comment.good.goodBrand.name!}</#if>${comment.good.modelNumber!}</div></li>
                </ul>
            </div>
        </div>
        <div class="evaluate_star">
            <ul>
            <#list 0..4 as i>
            <#if (comment.score)?? && (i < comment.score/10) >
                </li><li class="p_li_o"></li>
            <#else>
                <li></li>
            </#if>
            </#list>
            </ul>
        </div>
        <div class="evaluate_text">
            ${comment.content!""}
        </div>
        <div class="evaluate_name">
            <h3>${comment.customer.username}</h3>
            <span>${comment.createdAt?string("yyyy-MM-dd   HH:mm:ss")}</span>
        </div>
        <div class="evaluate_item_btn">
            <a onclick="check(${comment.id})" class="whiteBtn">审核通过</a>
            <a onclick="del(${comment.id})" class="whiteBtn">删除</a>
        </div>
    </div>
    </#list>
    <@pager.p page=comments.currentPage totalPages=comments.totalPage functionName="commentPageChange"/>
</div>