<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">商品</a></li>
        <li><a href="#">支付通道</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>支付通道列表</h1>
        <div class="userTopBtnBox">
            <a href="#" class="ghostBtn">创建支付通道</a>
        </div>
    </div>

    <div class="seenBox clear">
        <ul>
            <li><div class="user_search"><input name="" type="text" /><button></button></div></li>
            <li><div class="user_select">
                <label>状态筛选</label>
                <select id="select_status">
                    <option value="0">全部</option>
                    <option value="1">待审核</option>
                    <option value="2">初审不通过</option>
                    <option value="3">初审通过</option>
                    <option value="4">审核不通过</option>
                    <option value="5">正常</option>
                    <option value="6">已停用</option>
                </select>
            </div></li>
        </ul>
    </div>
    <div id="page_fresh">
        <#include "pageChannel.ftl" />
    </div>
</div>
</@c.html>