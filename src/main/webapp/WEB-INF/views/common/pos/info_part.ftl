<div class="content clear">
    <div class="user_title"><h1>POS机详情</h1>
        <#if isFactory?? && isFactory>
        <div class="userTopBtnBox">
        <#if good.status=1>

            <a href="<@spring.url "/factory/pos/${good.id}/edit" />" class="ghostBtn">编辑</a>

        <#elseif good.status=2>
            <a href="<@spring.url "/factory/pos/${good.id}/edit" />" class="ghostBtn">编辑</a>

        <#elseif good.status=3>
            <a href="<@spring.url "/factory/pos/${good.id}/edit" />" class="ghostBtn">编辑</a>

        <#elseif good.status=4>
            <a href="<@spring.url "/factory/pos/${good.id}/edit" />" class="ghostBtn">编辑</a>

        <#elseif good.status=5>
            <#if (good.isPublished)?? && !(good.isPublished)>
                <a href="<@spring.url "/factory/pos/${good.id}/edit" />" class="ghostBtn">编辑</a>
            </#if>
        <#elseif good.status=6>
        </#if>
        </div>
        <#else>
            <#include "../../good/pos/infoStatus.ftl" />
        </#if>
    </div>
    <div class="attributes_box">
        <h2>基础信息</h2>
        <div class="attributes_list clear">
            <ul>
                <li><em>标题：</em><span>${good.title!""}</span></li>
                <li><em>副标题：</em><span>${good.secondTitle!""}</span></li>
                <li><em>关键字：</em><span>${good.keyWorlds!""}</span></li>
                <li><em>POS机分类：</em><span><#if good.posCategory??>${good.posCategory.name!}</#if></span></li>
                <li><em>选择厂家：</em><span><#if good.factory??>${good.factory.name!}</#if></span></li>
                <li><em>POS机品牌：</em><span><#if good.goodBrand??>${good.goodBrand.name!}</#if></span></li>
                <li><em>POS机型号：</em><span>${good.modelNumber!""}</span></li>
                <li><em>加密卡方式：</em><span><#if good.encryptCardWay??>${good.encryptCardWay.encryptCardWay!}</#if></span></li>
                <li><em>签购单打印方式：</em><span><#if good.signOrderWay??>${good.signOrderWay.signOrderWay!}</#if></span></li>
                <li><em>支持银行卡：</em><span>
	                    <#if good.cardTypes??>
                            <#list good.cardTypes as cardType>
                            ${cardType.cardType}
                            </#list>
                        </#if>
                            <li><em>电池信息：</em><span>${good.batteryInfo!""}</span></li>
                    <li><em>外壳材质：</em><span>${good.shellMaterial!""}</span></li>
            </ul>
        </div>
    </div>

    <div class="attributes_box">
        <h2>价格信息</h2>
        <div class="attributes_list clear">
            <ul>
                <li><em>原价：</em><span><#if good.price??>${(good.price/100)?string("0.00")}元</#if></span></li>
                <li><em>现价：</em><span><#if good.retailPrice??>${(good.retailPrice/100)?string("0.00")}元</#if></span></li>
            </ul>
        </div>
    </div>

    <div class="attributes_box">
        <h2>批购信息</h2>
        <div class="attributes_list clear">
            <ul>
                <li><em>批购：</em><span><#if good.purchasePrice??>${(good.purchasePrice/100)?string("0.00")}元</#if></span></li>
                <li><em>最低限价：</em><span><#if good.floorPrice??>${(good.floorPrice/100)?string("0.00")}元</#if></span></li>
                <li><em>最小批购量：</em><span><#if good.floorPurchaseQuantity??>${good.floorPurchaseQuantity}个</#if></span></li>
            </ul>
        </div>
    </div>

<#if (good.hasLease)?? && good.hasLease>
    <div class="attributes_box">
        <h2>租赁设置</h2>
        <div class="attributes_list clear">
            <ul>
                <li><em>租赁押金：</em><span><#if good.leaseDeposit??>${(good.leaseDeposit/100)?string("0.00")}元</#if></span></li>
                <li><em>月租金：</em><span><#if good.leasePrice??>${(good.leasePrice/100)?string("0.00")}元</#if></span></li>
                <li><em>最低租赁时间：</em><span><#if good.leaseTime??>${good.leaseTime}个月</#if></span></li>
                <li><em>最长租赁时间：</em><span><#if good.returnTime??>${good.returnTime}个月</#if></span></li>
                <li><em>租赁说明：</em><span><a href="#" class="a_btn leaseExplain_a">点击查看</a></span></li>
                <li><em>租赁协议：</em><span><a href="#" class="a_btn leaseAgreement_a">点击查看</a></span></li>
            </ul>
        </div>
    </div>
</#if>
    <div class="attributes_box">
        <h2>支付通道</h2>
        <div class="attributes_list clear">
            <ul>
                <li><em>支付通道：</em>
               		<span><#if (good.channels)??>
					  <#list good.channels as channel>
                    ${channel.name!}
                    </#list>
                	</#if></span>
                </li>
            </ul>
        </div>
    </div>

    <div class="attributes_box">
        <h2>其他</h2>
        <div class="attributes_list clear">
            <ul>
                <li><em>详细说明：</em><span><a href="#" class="a_btn description_a">点击查看</a></span></li>
                <li><em>POS机图片：</em><span>
                <#if good.pictures??>
                    <#list good.pictures as picture>
                        <img src="<@spring.url "/resources/images/zp.jpg" />" class="cover" value="${picture.urlPath}">
                    </#list>
                </#if>
                </span></li>
                <li><em>关联商品：</em>
                    	<span><#if good.relativeGoods??>
						  <#list good.relativeGoods as relativeGood>
                        ${relativeGood.title!}
                        </#list>
	                	</#if></span>
                </li>
            </ul>
        </div>
        <div class="img_info" style="display: none; top: 0px; left: 0px;"><img src=""></div>
    </div>
</div>

<div class="mask"></div>

<div class="tab leaseExplain_tab">
    <a href="javascript:void(0);" class="close">关闭</a>
    <div class="tabHead">租赁说明</div>
    <div class="tabBody">
        <div class="lease_con">
        ${good.leaseDescription!}
        </div>
    </div>
</div>

<div class="tab leaseAgreement_tab">
    <a href="javascript:void(0);" class="close">关闭</a>
    <div class="tabHead">租赁协议</div>
    <div class="tabBody">
        <div class="lease_con">
        ${good.leaseAgreement!}
        </div>
    </div>
</div>

<div class="tab description_tab">
    <a href="javascript:void(0);" class="close">关闭</a>
    <div class="tabHead">详细说明</div>
    <div class="tabBody">
        <div class="lease_con">
        ${good.description!}
        </div>
    </div>
</div>