<div class="content clear">
    <div class="user_title"><h1>管理评论</h1>
    </div>
    <input type="hidden" id = "currentPage" value="${comments.currentPage}"/>
    <div class="evaluateBox">
        <div class="td_proBox clear">
            <#if (comment.good.pictures)?? && ((comment.good.pictures?size) > 0)>
            <a href="#" class="cn_img"><img src="<@spring.url "${(comment.good.pictures[0]).urlPath}" />"></a>
            </#if>
                <div class="${comment.good.title}">
                <h1><a href="#">汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装点餐机奶茶店</a></h1>
                <h3>热销5000件</h3>
                <ul>
                    <li><span>品牌型号：</span><div class="c_text">掌富ZF-300</div></li>
                    <li><span>支付通道：</span><div class="c_text">收账宝</div></li>
                </ul>
            </div>
        </div>
        <div class="eb_write">
            <div class="halfShow_star" style="cursor: pointer;"><img alt="1" src="images/star-on.png" title="bad">&nbsp;<img alt="2" src="images/star-on.png" title="poor">&nbsp;<img alt="3" src="images/star-on.png" title="regular">&nbsp;<img alt="4" src="images/star-half.png" title="good">&nbsp;<img alt="5" src="images/star-off.png" title="gorgeous"><input name="score" type="hidden" value="3.3"></div>
            <div class="eb_write_textarea"><textarea name="" cols="" rows=""></textarea></div>
            <div class="btnBottom"><button class="whiteBtn">添加评论</button></div>
            <script>
                $.fn.raty.defaults.path = 'images';
                $('.halfShow_star').raty({ score: 3.3 });
            </script>
        </div>

        <div class="evaluate_title"><i></i>综合评分4.5</div>
        <div class="evaluate_item">
            <div class="evaluate_star">
                <ul>
                    <li class="p_li_o">
                    </li><li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li></li>
                </ul>
            </div>
            <div class="evaluate_text">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin gravida dolor sit amet lacus
                accumsan et viverra justo commodo. Proin sodales pulvinar tempor. Cum sociis natoque penatibus et magnis dis parturient
                montes, nascetur ridiculus mus. Nam fermentum, nulla luctus pharetra vulputate, felis tellus mollis orci, sed rhoncus sapien
                nunc eget odio.</div>
            <div class="evaluate_name">
                <h3>维尼熊</h3>
                <span>2014-12-19   20：05：30</span>
            </div>
            <div class="evaluate_item_btn"><a href="#" class="whiteBtn">删除</a></div>
        </div>
    <#include "commentPage.ftl" />
    </div>
</div>