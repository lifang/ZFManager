<#import "../common.ftl" as c />
<@c.html>
<div class="right">
    <div class="breadcrumb">
        <ul>
            <li><a href="<@spring.url "/user/list"/>">用户</a></li>
            <li><a href="<@spring.url "/user/${merchant.customerId}/info"/>">用户详情</a></li>
            <li><a href="<@spring.url "/user/merchant/${merchant.id}/info"/>">商户详情</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>商户资料</h1>
            <div class="userTopBtnBox">
                <a href="javascript:history.go(-1);" class="ghostBtn">返回</a>
            </div>
        </div>
        <div class="item_list item_list_pl150 clear">
            <ul>
                <li><span class="labelSpan">商户法人姓名：</span>
                    <div class="text">${(merchant.legalPersonName)!""}</div>
                </li>
                <li><span class="labelSpan">商户法人身份证号码：</span>
                    <div class="text">${(merchant.legalPersonCardId)!""}</div>
                </li>
                <li><span class="labelSpan">商户法人身份证照片正面：</span>
                    <div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>"
                                           <#if (merchant.cardIdFrontPhotoPath)??>value="${FILE_PATH+(merchant.cardIdFrontPhotoPath)}"<#else>value=""</#if>
                                           class="cover"></div>
                </li>
                <li><span class="labelSpan">商户法人身份证照片背面：</span>
                    <div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>"
                                           <#if (merchant.cardIdBackPhotoPath)??>value="${FILE_PATH+(merchant.cardIdBackPhotoPath)}"<#else>value="" </#if>
                                               class="cover"></div>
                </li>
                <li><span class="labelSpan">商户法人上半身照片：</span>
                    <div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>"
                                           <#if (merchant.bodyPhotoPath)??>value="${FILE_PATH+(merchant.bodyPhotoPath)}"<#else> value=""  </#if>
                                           class="cover"></div>
                </li>
                <li><span class="labelSpan">商户名：</span>
                    <div class="text">${(merchant.title)!""}</div>
                </li>
                <li><span class="labelSpan">营业执照登记号：</span>
                    <div class="text">${(merchant.businessLicenseNo)!""}</div>
                </li>
                <li><span class="labelSpan">税务登记号：</span>
                    <div class="text">${(merchant.taxRegisteredNo)!""}</div>
                </li>
                <li><span class="labelSpan">组织机构代码证号：</span>
                    <div class="text">${(merchant.organizationCodeNo)!""}</div>
                </li>
                <li><span class="labelSpan">银行开户许可证号：</span>
                    <div class="text">${(merchant.bankOpenAccount)!""}</div>
                </li>
                <li><span class="labelSpan">营业执照照片：</span>
                    <div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>"
                                           <#if (merchant.licenseNoPicPath)??>value="${FILE_PATH+(merchant.licenseNoPicPath)}"<#else> value=""  </#if>
                                           class="cover"></div>
                </li>
                <li><span class="labelSpan">税务登记证照片：</span>
                    <div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>"
                                           <#if (merchant.taxNoPicPath)??>value="${FILE_PATH+(merchant.taxNoPicPath)}"<#else> value=""  </#if>
                                           class="cover"></div>
                </li>
                <li><span class="labelSpan">组织机构代码证照片：</span>
                    <div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>"
                                           <#if (merchant.orgCodeNoPicPath)??>value="${FILE_PATH+(merchant.orgCodeNoPicPath)}"<#else> value=""  </#if>
                                           class="cover"></div>
                </li>
                <li><span class="labelSpan">银行开户许可证照片：</span>
                    <div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>"
                                           <#if (merchant.accountPicPath)??>value="${FILE_PATH+(merchant.accountPicPath)}"<#else> value="" </#if>
                                           class="cover"></div>
                </li>
                <li><span class="labelSpan">开户行名称：</span>
                    <div class="text">${(merchant.accountBankName)!""}</div>
                </li>
                <li><span class="labelSpan">开户行地址：</span>
                    <div class="text">
                    <#if city??>
                    ${(city.parentCity.name)!""}${city.name}
                    </#if>
                    ${(merchant.accountBankAddress)!""}</div>
                </li>
            </ul>
            <div class="img_info" style="display: none; top: 0px; left: 0px;"><img src=""></div>
        </div>

    </div>
</div>
</@c.html>