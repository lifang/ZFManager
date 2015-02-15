<#import "../../page.ftl" as pager>
<div class="content clear">
    <div class="user_title"><h1>管理评论</h1>
    </div>
    <input type="hidden" id = "currentPage" value="${comments.currentPage}"/>
    <div class="evaluateBox">
        <div class="td_proBox clear">
            <div class="td_proBox_info">
                <h1><a href="#">${good.title!""}</a></h1>
                <h3>${good.secondTitle!""}</h3>
                <ul>
                    <li><span>品牌型号：</span><div class="c_text">${good.goodBrand.name}${good.modelNumber}</div></li>
                </ul>
            </div>
        </div>
        <div class="eb_write">
            <div class="halfShow_star" style="cursor: pointer;"></div>
            <div class="eb_write_textarea"><textarea id="content" cols="" rows=""></textarea></div>
            <div class="btnBottom"><button class="whiteBtn" onclick="submitComment()">添加评论</button></div>
            <script>
                $('.halfShow_star').raty({hints: ['1', '2', '3', '4', '5'],  number: 5, score: 5.0,  path: "/resources/images"});
            </script>
        </div>

        <div class="evaluate_title"><i></i>综合评分
            <#if good.totalComment?? && (good.totalComment) gt 0>${((good.totalScore)/(good.totalComment))?string("0.0")}<#else>0.0 </#if>
        </div>
        <#list comments.content as comment>
        <div class="evaluate_item">
            <div class="evaluate_star">
                <ul>
                <#list 0..4 as i>
                    <#if (comment.score)?? && (i < comment.score) >
                        </li><li class="p_li_o"></li>
                    <#else>
                        <li></li>
                    </#if>
                </#list>
                </ul>
            </div>
            <div class="evaluate_text">${comment.content}</div>
            <div class="evaluate_name">
                <h3>${comment.customer.username}</h3>
                <span>${comment.createdAt?string("yyyy-MM-dd   HH:mm:ss")}</span>
            </div>
            <div class="evaluate_item_btn"><a onclick="del(${comment.id})" class="whiteBtn">删除</a></div>
        </div>
        </#list>
        <@pager.p page=comments.currentPage totalPages=comments.totalPage functionName="commentPageChange"/>
    </div>
</div>