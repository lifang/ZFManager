<#import "../../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">商品</a></li>
            <li><a href="#">支付通道</a></li>
            <#if channel??>
                <li><a href="#">编辑支付通道</a></li>
            <#else>
                <li><a href="#">创建支付通道</a></li>
            </#if>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title">
            <h1>
            <#if channel??>
                编辑支付通道
            <#else>创建支付通道
            </#if></h1>
        </div>
        <div class="attributes_box">
            <h2>基础信息</h2>
            <div class="item_list clear">
                <ul>
                    <li><span class="labelSpan">名称：</span>
                        <div class="text"><input name="" type="text" value="${(channel.name)???string(channel.name, "")}"></div></li>
                    <li class="o"><span class="labelSpan">收单机构：</span>
                        <div class="text"><select name="">
                            <option>111</option>
                        </select></div>
                    </li>
                    <li class="b"><span class="labelSpan">支持区域：</span>
                        <div class="text">
                            <div class="supportArea">
                                <div class="sa_list">
                                    <span class="checkboxRadio_span"><input name="" type="radio" value=""> 全国</span>
                                </div>
                                <div class="sa_list">
                                    <span class="checkboxRadio_span"><input name="" type="radio" value=""> 只支持</span>
                                    <select name="">
                                        <option>江苏省</option>
                                    </select>
                                    <select name="">
                                        <option>苏州市</option>
                                    </select>
                                    <a href="#" class="pay_add_a">+</a>
                                    <div class="sa_area">
                                        <div class="saa_b"><span class="saab_t">省</span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span>
                                        </div>
                                        <div class="saa_b"><span class="saab_t">市</span>
                                            <span class="saab_c">上海市<a href="#" class="dele">删除</a></span></div>
                                    </div>
                                </div>
                                <div class="sa_list">
                                    <span class="checkboxRadio_span"><input name="" type="radio" value=""> 不支持</span>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li><span class="labelSpan">是否支持注销：</span>
                        <div class="text">
                            <span class="checkboxRadio_span"><input name="" type="radio" value=""> 是</span>
                            <span class="checkboxRadio_span"><input name="" type="radio" value=""> 否</span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <div class="attributes_box">
            <h2>交易费率</h2>
            <div class="item_list clear">
                <ul>
                    <li class="b"><span class="labelSpan">刷卡交易标准手续费：</span>
                        <div class="text">
                            <div class="rate_attributes">
                                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                    <colgroup>
                                        <col width="33%">
                                        <col width="33%">
                                        <col width="34%">
                                    </colgroup>
                                    <tbody><tr>
                                        <td>商户类型</td>
                                        <td>费率</td>
                                        <td>说明</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <select name="" class="select_xl">
                                                <option>qqq</option>
                                                <option>sww</option>
                                            </select>
                                        </td>
                                        <td><input name="" type="text" class="input_l"></td>
                                        <td><input name="" type="text" class="input_l"></td>
                                    </tr>
                                    </tbody></table>
                                <a href="#" class="pay_add_a">+</a>
                            </div>
                        </div>
                    </li>
                    <li class="b"><span class="labelSpan">资金服务费：</span>
                        <div class="text">
                            <div class="rate_attributes">
                                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                    <colgroup>
                                        <col width="33%">
                                        <col width="33%">
                                        <col width="34%">
                                    </colgroup>
                                    <tbody><tr>
                                        <td>结算周期</td>
                                        <td>费率</td>
                                        <td>说明</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <select name="" class="select_xl">
                                                <option>qqq</option>
                                                <option>sww</option>
                                            </select>
                                        </td>
                                        <td><input name="" type="text" class="input_l"></td>
                                        <td><input name="" type="text" class="input_l"></td>
                                    </tr>
                                    </tbody></table>
                                <a href="#" class="pay_add_a">+</a>
                            </div>
                        </div>
                    </li>
                    <li class="b o"><span class="labelSpan">其他交易类型：</span>
                        <div class="text">
                            <select name="">
                                <option>江苏省</option>
                            </select>
                            <a href="#" class="pay_add_a">+</a>
                            <div class="rate_attributes mtop">
                                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                    <colgroup>
                                        <col width="33%">
                                        <col width="33%">
                                        <col width="34%">
                                    </colgroup>
                                    <tbody><tr>
                                        <td>结算周期</td>
                                        <td>费率</td>
                                        <td>说明</td>
                                    </tr>
                                    <tr>
                                        <td><input name="" type="text" class="input_l"></td>
                                        <td><input name="" type="text" class="input_l"></td>
                                        <td><input name="" type="text" class="input_l"></td>
                                    </tr>
                                    </tbody></table>
                                <a href="#" class="pay_add_a">+</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <div class="attributes_box">
            <h2>开通认证</h2>
            <div class="item_list clear">
                <ul>
                    <li><span class="labelSpan">开通费用：</span>
                        <div class="text"><input name="" type="text"> 元<br>（保留小数点后两位）</div></li>
                    <li><span class="labelSpan">是否需要预审：</span>
                        <div class="text">
                            <span class="checkboxRadio_span"><input name="" type="radio" value=""> 是</span>
                            <span class="checkboxRadio_span"><input name="" type="radio" value=""> 否</span>
                        </div>
                    </li>
                    <li class="b"><span class="labelSpan">开通申请条件：</span>
                        <div class="text"><textarea name="" cols="" rows=""></textarea></div>
                    </li>
                    <li class="b"><span class="labelSpan">开通申请材料：</span>
                        <div class="text"><textarea name="" cols="" rows=""></textarea></div>
                    </li>
                    <li class="b"><span class="labelSpan">开通协议：</span>
                        <div class="text"><textarea name="" cols="" rows=""></textarea></div>
                    </li>
                    <li class="b"><span class="labelSpan">对公开通所需：</span>
                        <div class="text">
                            <div class="itl_area">
                                <div class="item_l2"><label>开通等级名称：</label><input name="" type="text" class="input_l"></div>
                                <div class="item_l2"><label>开通等级说明：</label><input name="" type="text" class="input_l"></div>
                                <div class="item_l2"><label>对公开通所需：</label><select name="">
                                    <option>江苏省</option>
                                </select>
                                    <a href="#" class="pay_add_a">+</a>
                                    <div class="ia_area">
                                        <span class="iaa_c">营业执照招聘<a href="#" class="dele">删除</a></span>
                                        <span class="iaa_c">营业执照招聘<a href="#" class="dele">删除</a></span>
                                        <span class="iaa_c">营业执照招聘<a href="#" class="dele">删除</a></span>
                                        <span class="iaa_c">营业执照招聘<a href="#" class="dele">删除</a></span>
                                    </div>
                                </div>
                                <div class="item_l2"><label>对公开通所需：</label><select name="">
                                    <option>江苏省</option>
                                </select>
                                    <a href="#" class="pay_add_a">+</a>
                                    <div class="ia_area">
                                        <span class="iaa_c">营业执照招聘<a href="#" class="dele">删除</a></span>
                                        <span class="iaa_c">营业执照招聘<a href="#" class="dele">删除</a></span>
                                        <span class="iaa_c">营业执照招聘<a href="#" class="dele">删除</a></span>
                                        <span class="iaa_c">营业执照招聘<a href="#" class="dele">删除</a></span>
                                    </div>
                                </div>
                            </div>
                            <a href="#" class="whiteBtn">添加开通等级</a>
                        </div>
                    </li>
                    <li class="b o"><span class="labelSpan">注销所需材料：</span>
                        <div class="text">

                            <div class="rate_attributes mtop">
                                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                    <colgroup>
                                        <col width="33%">
                                        <col width="33%">
                                        <col width="34%">
                                    </colgroup>
                                    <tbody><tr>
                                        <td>材料名称</td>
                                        <td>材料说明</td>
                                        <td>模版上传</td>
                                    </tr>
                                    <tr>
                                        <td><input name="" type="text" class="input_l"></td>
                                        <td><input name="" type="text" class="input_l"></td>
                                        <td>
                                            <a href="javascript:void(0);" class="informImg_a">
                                                <span>上传</span><input name="" multiple="" type="file">
                                            </a>
                                        </td>
                                    </tr>
                                    </tbody></table>
                                <a href="#" class="pay_add_a">+</a>
                            </div>
                        </div>
                    </li>
                    <li class="b o"><span class="labelSpan">更新资料所需材料：</span>
                        <div class="text">

                            <div class="rate_attributes mtop">
                                <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                    <colgroup>
                                        <col width="33%">
                                        <col width="33%">
                                        <col width="34%">
                                    </colgroup>
                                    <tbody><tr>
                                        <td>材料名称</td>
                                        <td>材料说明</td>
                                        <td>模版上传</td>
                                    </tr>
                                    <tr>
                                        <td><input name="" type="text" class="input_l"></td>
                                        <td><input name="" type="text" class="input_l"></td>
                                        <td>
                                            <a href="javascript:void(0);" class="informImg_a">
                                                <span>上传</span><input name="" multiple="" type="file">
                                            </a>
                                        </td>
                                    </tr>
                                    </tbody></table>
                                <a href="#" class="pay_add_a">+</a>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <div class="btnBottom"><button class="blueBtn">创建</button></div>
    </div>
</@c.html>