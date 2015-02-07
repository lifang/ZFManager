<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">终端</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>终端列表</h1>
    </div>

    <div class="seenBox clear">
        <ul>
            <li><div class="user_search"><input name="" type="text" /><button></button></div></li>
            <li><div class="user_select">
                <label>状态筛选</label>
                <select name="">
                    <option>111</option>
                    <option>222</option>
                    <option>333</option>
                </select>
            </div></li>
        </ul>
    </div>

    <div class="uesr_table">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
            <colgroup>
                <col width="150" />
                <col width="150" />
                <col width="150" />
                <col />
                <col />
                <col width="80" />
                <col width="150" />
            </colgroup>
            <thead>
            <tr>
                <th>终端号</th>
                <th>POS产品</th>
                <th>支付通道</th>
                <th>商户名称</th>
                <th>商户电话</th>
                <th>开通状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>AC1254566</td>
                <td>掌富ZF300</td>
                <td>收账宝</td>
                <td>三峡格格</td>
                <td>0512-6589744</td>
                <td><strong class="strong_status">已开通</strong></td>
                <td><a href="#" class="a_btn">解除用户关联</a><a href="#" class="a_btn">查看详情</a></td>
            </tr>
            <tr>
                <td>AC1254566</td>
                <td>掌富ZF300</td>
                <td>收账宝</td>
                <td>三峡格格</td>
                <td>0512-6589744</td>
                <td><strong class="strong_status">未开通</strong></td>
                <td><a href="#" class="a_btn">解除用户关联</a>
                    <a href="#" class="a_btn">同步</a><a href="#" class="a_btn">查看详情</a>
                </td>
            </tr>
            <tr>
                <td>AC1254566</td>
                <td>掌富ZF300</td>
                <td>收账宝</td>
                <td>三峡格格</td>
                <td>0512-6589744</td>
                <td><strong class="strong_status">部分开通</strong></td>
                <td><a href="#" class="a_btn">解除用户关联</a>
                    <a href="#" class="a_btn">同步</a><a href="#" class="a_btn">查看详情</a>
                </td>
            </tr>
            <tr>
                <td>AC1254566</td>
                <td>掌富ZF300</td>
                <td>收账宝</td>
                <td>三峡格格</td>
                <td>0512-6589744</td>
                <td><strong class="strong_status">已停用</strong></td>
                <td><a href="#" class="a_btn">解除用户关联</a>
                    <a href="#" class="a_btn">同步</a><a href="#" class="a_btn">查看详情</a>
                </td>
            </tr>
            <tr>
                <td>AC1254566</td>
                <td>掌富ZF300</td>
                <td>收账宝</td>
                <td>三峡格格</td>
                <td>0512-6589744</td>
                <td><strong class="strong_status">已注销</strong></td>
                <td><a href="#" class="a_btn">解除用户关联</a>
                    <a href="#" class="a_btn">查看详情</a>
                </td>
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