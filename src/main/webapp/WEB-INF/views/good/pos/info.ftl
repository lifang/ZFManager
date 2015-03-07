<#import "../../common.ftl" as c />
<@c.html>
	<div class="breadcrumb">
        <ul>
            <li><a href="#">商品</a></li>
            <li><a href="#">POS机管理</a></li>
            <li><a href="#">POS机详情</a></li>
        </ul>
    </div>
    <div class="content clear">
		<div class="user_title"><h1>POS机详情</h1>
 		      	<#include "infoStatus.ftl" />
        </div>
        <div class="attributes_box">
        	<h2>基础信息</h2>
            <div class="attributes_list clear">
                <ul>
                    <li><em>标题：</em><span>${good.title!}</span></li>
                    <li><em>副标题：</em><span>${good.secondTitle!}</span></li>
                    <li><em>关键字：</em><span>${good.keyWorlds!}</span></li>
                    <li><em>POS机分类：</em><span><#if good.posCategory??>${good.posCategory.name!}</#if></span></li>
                    <li><em>选择厂家：</em><span><#if good.factory??>${good.factory.name!}</#if></span></li>
                    <li><em>POS机品牌：</em><span><#if good.goodBrand??>${good.goodBrand.name!}</#if></span></li>
                    <li><em>POS机型号：</em><span>${good.modelNumber!}</span></li>
                    <li><em>加密卡方式：</em><span><#if good.encryptCardWay??>${good.encryptCardWay.encryptCardWay!}</#if></span></li>
                    <li><em>签购单打印方式：</em><span><#if good.signOrderWay??>${good.signOrderWay.signOrderWay!}</#if></span></li>
                    <li><em>支持银行卡：</em><span>
	                    <#if good.cardTypes??>
						  <#list good.cardTypes as cardType>
						  ${cardType.cardType}
						  </#list>
	                	</#if>
                    <li><em>电池信息：</em><span>${good.batteryInfo!}</span></li>
                    <li><em>外壳材质：</em><span>${good.shellMaterial!}</span></li>
                </ul>
            </div>  
        </div>
        
        <div class="attributes_box">
        	<h2>价格信息</h2>
            <div class="attributes_list clear">
                <ul>
                    <li><em>原价：</em><span>${(good.price/100)?string("0.00")}元</span></li>
                    <li><em>现价：</em><span>${(good.retailPrice/100)?string("0.00")}元</span></li>
                </ul>
            </div>  
        </div>
        
        <div class="attributes_box">
        	<h2>批购信息</h2>
            <div class="attributes_list clear">
                <ul>
                    <li><em>批购：</em><span>${(good.purchasePrice/100)?string("0.00")}元</span></li>
                    <li><em>最低限价：</em><span>${(good.floorPrice/100)?string("0.00")}元</span></li>
                    <li><em>最小批购量：</em><span>${good.floorPurchaseQuantity}个</span></li>
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
						  	<img src="<@spring.url "/resources/images/zp.jpg" />" class="cover" value="<@spring.url picture.urlPath />"> 
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
	
	<div class="tab approve_tab">
		<a href="javascript:void(0);" class="close">关闭</a>
		<div class="tabBody">
	    	<div class="approve_con">
	        	<h2>请确认产品可以通过审核</h2>
	            <p><input id="isThird" type="checkbox" /> 第三方库存</p>
	        </div>
		</div>
	    <div class="tabFoot"><button class="blueBtn" onClick="check(${good.id})">确认</button></div>
	</div>
	
	<script type="text/javascript">

	function firstUnCheck(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/firstUnCheck?source=info',
	            function (data) {
	                $('.userTopBtnBox').replaceWith(data);
	            });
	};
	
	function firstCheck(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/firstCheck?source=info',
	            function (data) {
	                $('.userTopBtnBox').replaceWith(data);
	            });	
	};
	
	function unCheck(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/unCheck?source=info',
	            function (data) {
	                $('.userTopBtnBox').replaceWith(data);
	            });	
	};
	
	function check(id){
		var isThird = $('#isThird').prop("checked");
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/check?source=info',
	            {
	            	"isThird": isThird
	            },
	            function (data) {
					$('.approve_tab').hide();
					$('.mask').hide();
	                $('.userTopBtnBox').replaceWith(data);
	                
	    });
	};
	
	function stop(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/stop?source=info',
	            function (data) {
	                $('.userTopBtnBox').replaceWith(data);
	            });
	};	
	function start(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/start?source=info',
	            function (data) {
	                $('.userTopBtnBox').replaceWith(data);
	            });
	};	
	
	
</script> 
</@c.html>