<#import "../common.ftl" as c />
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
                <select name="">
                    <option>已付款</option>
                    <option>未付款</option>
                    <option>已付定金</option>
                </select>
            </div></li>
        </ul>
    </div>

    <div class="uesr_table">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
            <colgroup>
                <col />
                <col />
                <col width="250" />
            </colgroup>
            <thead>
            <tr>
                <th>支付通道</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>收账宝</td>
                <td><strong class="strong_status">待审核</strong></td>
                <td><a href="#" class="a_btn">审核</a><a href="#" class="a_btn">设置分润</a>
                    <a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">查看详情</a></td>
            </tr>
            <tr>
                <td>收账宝</td>
                <td><strong class="strong_status">正常</strong></td>
                <td><a href="#" class="a_btn">停用</a><a href="#" class="a_btn">设置分润</a>
                    <a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">查看详情</a></td>
            </tr>
            <tr>
                <td>收账宝</td>
                <td><strong class="strong_status">已停用</strong></td>
                <td><a href="#" class="a_btn">启用</a><a href="#" class="a_btn">设置分润</a>
                    <a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">查看详情</a></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="pageTurn">
        <div class="p_num">
            <a href="#" class="disabled">上一页</a>
            <a href="#" class="current">1</a>
            <a href="#?page=2">2</a>
            <a href="#?page=3">3</a>
            <a href="#?page=4">4</a>
            <a href="#?page=5">5</a>
            ...
            <a href="#?page=199">199</a>
            <a href="#?page=200">200</a>
            <a href="#?page=2">下一页</a>
        </div>
        <div class="p_skip">
            <span>共24页</span>
            <span>到第&nbsp;&nbsp;<input name="" type="text" />&nbsp;&nbsp;页</span>
            <button>确定</button>
        </div>
    </div>
</div>
</@c.html>