<#import "../../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">系统</a></li>
            <li><a href="#">数据字典</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>数据字典管理</h1>
        </div>
        <#include "creditType_list.ftl"/>
        <#include "openPrivate_list.ftl"/>

        <div class="attributes_box">
            <h2>用户订单类型</h2>
            <div class="dataDictionary">
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <colgroup>
                        <col width="50%">
                        <col width="50%">
                    </colgroup>
                    <tbody><tr>
                        <td>类型</td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>XXXXX</td>
                        <td><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">删除</a></td>
                    </tr>
                    <tr>
                        <td><input name="" type="text"></td>
                        <td><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">删除</a></td>
                    </tr>
                    </tbody></table>
                <a href="#" class="pay_add_a">+</a>
            </div>
        </div>

        <#include "encryptCardWay_list.ftl"/>

        <div class="attributes_box">
            <h2>银行卡类型</h2>
            <div class="dataDictionary">
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <colgroup>
                        <col width="50%">
                        <col width="50%">
                    </colgroup>
                    <tbody><tr>
                        <td>类型</td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>XXXXX</td>
                        <td><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">删除</a></td>
                    </tr>
                    <tr>
                        <td><input name="" type="text"></td>
                        <td><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">删除</a></td>
                    </tr>
                    </tbody></table>
                <a href="#" class="pay_add_a">+</a>
            </div>
        </div>

        <div class="attributes_box">
            <h2>交易类型</h2>
            <div class="dataDictionary">
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <colgroup>
                        <col width="40%">
                        <col width="40%">
                        <col>
                    </colgroup>
                    <tbody><tr>
                        <td>类型</td>
                        <td>类型值</td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>XXXXX</td>
                        <td>XXXXX</td>
                        <td><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">删除</a></td>
                    </tr>
                    <tr>
                        <td><input name="" type="text"></td>
                        <td><input name="" type="text"></td>
                        <td><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">删除</a></td>
                    </tr>
                    </tbody></table>
                <a href="#" class="pay_add_a">+</a>
            </div>
        </div>

        <div class="attributes_box">
            <h2>用户订单类型</h2>
            <div class="dataDictionary">
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <colgroup>
                        <col width="50%">
                        <col width="50%">
                    </colgroup>
                    <tbody><tr>
                        <td>类型</td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>XXXXX</td>
                        <td><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">删除</a></td>
                    </tr>
                    <tr>
                        <td><input name="" type="text"></td>
                        <td><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">删除</a></td>
                    </tr>
                    </tbody></table>
                <a href="#" class="pay_add_a">+</a>
            </div>
        </div>

        <div class="attributes_box">
            <h2>交易费率</h2>
            <div class="dataDictionary">
                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <colgroup>
                        <col width="25%">
                        <col width="25%">
                        <col width="25%">
                        <col width="25%">
                    </colgroup>
                    <tbody><tr>
                        <td>结算周期</td>
                        <td>费率</td>
                        <td>说明</td>
                        <td>操作</td>
                    </tr>
                    <tr>
                        <td>XXXXX</td>
                        <td>XXXXX</td>
                        <td>XXXXX</td>
                        <td><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">删除</a></td>
                    </tr>
                    <tr>
                        <td><input name="" type="text"></td>
                        <td><input name="" type="text"></td>
                        <td><input name="" type="text"></td>
                        <td><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">删除</a></td>
                    </tr>
                    </tbody></table>
                <a href="#" class="pay_add_a">+</a>
            </div>
        </div>
    </div>
<script>
    function checkNull(value, error){
        var result = isNotNull(value);
        if(!result){
            showErrorTip(error);
        }
        return !result;
    }

    function isNotNull(value){
        return value != "" && value != null && value != undefined;
    }
</script>
</@c.html>