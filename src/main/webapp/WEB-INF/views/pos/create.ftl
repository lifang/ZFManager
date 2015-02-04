<#import "../common.ftl" as c />
<@c.html>

	<div class="breadcrumb">
        <ul>
            <li><a href="#">商品</a></li>
            <li><a href="#">POS机管理</a></li>
            <#if good??>
            <li><a href="#">编辑POS机</a></li>
            <#else>
            <li><a href="#">创建POS机</a></li>
            </#if>
        </ul>
    </div>
    <div class="content clear">
		<div class="user_title">
		<h1>
			<#if good??>
			编辑POS机
            <#else>创建POS机
            </#if>
		</h1>
        </div>
        <div class="attributes_box">
        	<h2>基础信息</h2>
            <div class="item_list clear">
                <ul>
                    <li class="b"><span class="labelSpan">标题：</span>
                    	<div class="text"><input name="g_title" type="text" 
                    	<#if (good.title)??>value="${good.title}"</#if>
                    	></div></li>
                    <li class="b"><span class="labelSpan">副标题：</span>
                    	<div class="text"><input name="g_secondTitle" type="text"
                    	<#if (good.secondTitle)??>value="${good.secondTitle}"</#if>
                    	></div></li>
                    <li class="b"><span class="labelSpan">关键字：</span>
                    	<div class="text"><input name="g_keyWorlds" type="text"
                    	<#if (good.keyWorlds)??>value="${good.keyWorlds}"</#if>
                    	></div></li>
                    <li class="o"><span class="labelSpan">选择POS机分类：</span>
                    	<div class="text">
                        <select name="g_posCategory">
                        <#if posCategories??>
                          <#list posCategories as posCategory>
                    	  <option value="${posCategory.id}" 
                    	  		<#if ((good.posCategoryId)?? && good.posCategoryId=posCategory.id)
                    	  				||(!((good.posCategoryId)??) && posCategory_index=0)> selected="true"</#if>>
                    	  <#if posCategory.parentId??>&nbsp;&nbsp;L</#if>${posCategory.name}</option>
                    	  </#list>
                    	</#if>
                    	</select>
                        </div>
                    </li>
                    <li class="o"><span class="labelSpan">选择厂家：</span>
                    	<div class="text">
                        <select name="g_factory">
   						 <#if factories??>
                          <#list factories as factory>
                    	  <option value="${factory.id}"
                    	  	<#if ((good.factoryId)?? && good.factoryId=factory.id)
                    	  	     ||(!((good.factoryId)??) && factory_index=0) > selected="true"</#if>
                    	  >${factory.name}</option>
                    	  </#list>
                    	</#if>
                    	</select>
                        </div>
                    </li>
                    <li><span class="labelSpan">POS机品牌：</span>
                    	<div class="text"><input name="g_goodBrand" type="text"
                    	<#if (good.goodBrand)??>value="${good.goodBrand.name}"</#if>
                    	></div></li>
                    <li><span class="labelSpan">POS机型号：</span>
                    	<div class="text"><input name="g_modelNumber" type="text"
                    	<#if (good.modelNumber)??>value="${good.modelNumber}"</#if>
                    	></div></li>
                    <li class="o"><span class="labelSpan">加密卡方式：</span>
                    	<div class="text">
                        <select name="g_encryptCardWay">
                        <#if encryptCardWays??>
                          <#list encryptCardWays as encryptCardWay>
                    	  <option value="${encryptCardWay.id}" 
                    	  <#if ((good.encryptCardWayId)?? && good.encryptCardWayId=encryptCardWay.id)
                    	  	     ||(!((good.encryptCardWayId)??) && encryptCardWay_index=0) > selected="true"</#if>
                    	  >${encryptCardWay.encryptCardWay}</option>
                    	  </#list>
                    	</#if>
                    	</select>
                        </div>
                    </li>
                    <li class="o"><span class="labelSpan">签购单打印方式：</span>
                    	<div class="text">
                        <select name="g_signOrderWay">
                         <#if signOrderWays??>
                          <#list signOrderWays as signOrderWay>
                    	  <option value="${signOrderWay.id}" 
                    	  <#if ((good.signOrderWayId)?? && good.signOrderWayId=signOrderWay.id)
                    	  	     ||(!((good.signOrderWayId)??) && signOrderWay_index=0)> selected="true"</#if>
                    	  >${signOrderWay.signOrderWay}</option>
                    	  </#list>
                    	</#if>
                    	</select>
                        </div>
                    </li>
                    <li class="b"><span class="labelSpan">支持银行卡：</span>
                    	<div class="text">
                    	<#if cardTypes??>
                          <#list cardTypes as cardType>
                        	<span class="checkboxRadio_span"><input name="g_cardType" type="checkbox" value="${cardType.id}" 
                        	<#if (good.cardTypes)??>
	                          <#list good.cardTypes as gCard>
                        		<#if cardType.id=gCard.id> checked="checked"</#if>
	                    	  </#list>
                    		</#if>
                        	> ${cardType.cardType}</span>
                    	  </#list>
                    	</#if>
                        </div>
                    </li>
                    <li><span class="labelSpan">电池信息：</span>
                    	<div class="text"><input name="g_batteryInfo" type="text" 
                    	<#if (good.batteryInfo)??>value="${good.batteryInfo}"</#if>
                    	></div></li>
                    <li><span class="labelSpan">外壳材质：</span>
                    	<div class="text"><input name="g_shellMaterial" type="text"
                    	<#if (good.shellMaterial)??>value="${good.shellMaterial}"</#if>
                    	></div></li>
                </ul>
            </div> 
        </div>
        <div class="attributes_box">
        	<h2>价格信息</h2>
            <div class="item_list clear">
                <ul>
                    <li class=""><span class="labelSpan">原价：</span>
                    	<div class="text"><input name="g_price" type="text"
                    	<#if (good.price)??>value="${(good.price/100)?string("0.00")}"</#if>
                    	> 元<br>（保留小数点后两位）</div></li>
                    <li><span class="labelSpan">现价：</span>
                    	<div class="text"><input name="g_retailPrice" type="text"
                    	<#if (good.retailPrice)??>value="${(good.retailPrice/100)?string("0.00")}"</#if>
                    	> 元<br>（保留小数点后两位）</div></li>
                </ul>
            </div> 
        </div>
        
        <div class="attributes_box">
        	<h2>批购信息</h2>
            <div class="item_list clear">
                <ul>
                    <li class=""><span class="labelSpan">批购：</span>
                    	<div class="text"><input name="g_purchasePrice" type="text"
                    	<#if (good.purchasePrice)??>value="${(good.purchasePrice/100)?string("0.00")}"</#if>
                    	> 元<br>（保留小数点后两位）</div></li>
                    <li><span class="labelSpan">最低限价：</span>
                    	<div class="text"><input name="g_floorPrice" type="text"
                    	<#if (good.floorPrice)??>value="${(good.floorPrice/100)?string("0.00")}"</#if> > 元<br>（保留小数点后两位）</div></li>
                    <li><span class="labelSpan">最小批购量：</span>
                    	<div class="text"><input name="g_floorPurchaseQuantity" type="text"
                    	<#if (good.floorPurchaseQuantity)??>value="${good.floorPurchaseQuantity}"</#if>
                    	onkeyup="value=this.value.replace(/\D+/g,'')"> 个</div></li>
                </ul>
            </div> 
        </div>
        
        <div class="attributes_box">
        	<h2>租赁设置</h2>
            <div class="item_list clear">
                <ul>
                    <li class=""><span class="labelSpan">租赁押金：</span>
                    	<div class="text"><input name="g_leaseDeposit" type="text"
                    	<#if (good.leaseDeposit)??>value="${(((good.leaseDeposit)!0)/100)?string("0.00")}"</#if>
                    	> 元<br>（保留小数点后两位）</div></li>
                    <li><span class="labelSpan">最低租赁时间：</span>
                    	<div class="text"><input name="g_leaseTime" type="text"
                    	<#if (good.leaseTime)??>value="${good.leaseTime}"</#if> 
                    	onkeyup="value=this.value.replace(/\D+/g,'')"> 月</div></li>
                    <li class="clear"><span class="labelSpan">最长租赁时间：</span>
                    	<div class="text"><input name="g_returnTime" type="text"
                    	<#if (good.returnTime)??>value="${good.returnTime}"</#if> 
                    	onkeyup="value=this.value.replace(/\D+/g,'')"> 月</div></li>
                    <li class="b"><span class="labelSpan">租赁说明：</span>
                    	<div class="text">
                    	<textarea name="g_leaseDescription" cols="" rows=""><#if (good.leaseDescription)??>${good.leaseDescription}</#if></textarea></div></li>
                    <li class="b"><span class="labelSpan">租赁协议：</span>
                    	<div class="text"><textarea name="g_leaseAgreement" cols="" rows=""><#if (good.leaseAgreement)??>${good.leaseAgreement}</#if></textarea></div></li>
                </ul>
            </div> 
        </div>
        
        <div class="attributes_box">
        	<h2>支付通道</h2>
            <div class="item_list clear">
                <ul>
					<li class="overflow"><span class="labelSpan">添加支付通道：</span>
                    	<div class="text" id="channel_search">
                    	<input name="" type="text" id="input_channel">
                    	 <#if (good.channels)??>
                          <#list good.channels as channel>
                          <div class="item_relevance_pro" value="${channel.id}">
                        	<span>${channel.name}</span> <a class="a_btn" onClick="del(this)">删除</a>
                         </div>
                    	  </#list>
                    	</#if>
                    	</div>
                        <div class="suggest" id="channel_result_id">
                        </div>
                    </li>
                </ul>
            </div> 
        </div>
        
        <div class="attributes_box">
        	<h2>其他</h2>
            <div class="item_list clear">
                <ul>
                    <li class="b"><span class="labelSpan">详细说明：</span>
                    	<div class="text"><textarea name="g_description" cols="" rows=""><#if (good.description)??>${good.description}</#if></textarea></div></li>
                    <li><span class="labelSpan">POS机图片：</span>
                    	<div class="text" id="photos">
							<#list 0..2 as i>
                            <div class="item_photoBox">
                            	<#if (good.pictures[i])??>
                                <img src="<@spring.url "/resources/images/zp.jpg" />" class="cover" value="<@spring.url good.pictures[i].urlPath />" dbValue="${good.pictures[i].urlPath}">
                                <a href="javascript:void(0);" class="informImg_a">
                                    <span>重新上传</span><input name="file" multiple="" type="file" id="imageFile${i}" onChange="fileChange(this)"/>
                                </a>
                                <#else>
                                <a href="javascript:void(0);" class="informImg_a">
                                    <span>上传照片</span><input name="file" multiple="" type="file" id="imageFile${i}" onChange="fileChange(this)"/>
                                </a>
                            	</#if>
                            </div>
						  </#list>
                            </div>
                        </div>
                    </li>
                    
                    <li class="b overflow">
                    <span class="labelSpan">关联商品：</span>
                    	<div class="text" id="rgood_search">
                    	<input name="" type="text" id="input_rgood">
                        	<div class="item_relevance_pro" >
	                    	 <#if (good.relativeGoods)??>
	                          <#list good.relativeGoods as relativeGood>
	                          <div class="item_relevance_pro" value="${relativeGood.id}">
	                        	<span>${relativeGood.title}</span> <a class="a_btn" onClick="del(this)">删除</a>
	                         </div>
	                    	  </#list>
	                    	</#if>
                            </div>
                        <div class="suggest" id="rgood_result_id">
                        </div>
                        </div></li>
                </ul>
                <div class="img_info"><img src=""></div>
            </div> 
        </div>
        
        <div class="btnBottom"><button class="blueBtn" onClick="submitData()">
			<#if good??>
			确定
            <#else>创建
            </#if>
		</button></div>
    </div>
<script src="<@spring.url "/resources/js/ajaxfileupload.js"/>"></script>
<script type="text/javascript"">

$(function(){
   <#--通道搜索-->
   var channelClose = true ;
   $("#channel_result_id").hide();
   $("#input_channel").keyup(function(){
    var name = $.trim($(this).val()) ;
    if("" == name || null == name){
     $("#channel_result_id").hide();
    }
    else {
	$.get('<@spring.url "/pos/searchChannel" />',
		{"name": name},
        function (data) {
        	$("#channel_result_id").show();
     		$("#channel_result_id").html(data);
     		$("#channel_result_id a").click(function(){
	      		var name = $(this).html();
	      		var id = $(this).attr("value");
	      		$("#input_channel").val(name);
	      		$("#channel_result_id").hide();
	      		var newDiv = '<div class="item_relevance_pro" value="'+id+'"><span>'+name+'</span> <a class="a_btn" onClick="del(this)">删除</a></div>';
	      		$("#rgood_search").append(newDiv);
     		}).hover(function () {channelClose = false ;}
     				,function () {channelClose = true ;});
        });
    }
   }).blur(function(){
	    if(channelClose) {
	   		$("#channel_result_id").hide();
	   	}
   }).focus(function(){
  		$("#channel_result_id").show();
   });	
   
   <#--商品搜索-->
   var rgoodClose = true ;
   $("#rgood_result_id").hide();
   $("#input_rgood").keyup(function(){
    var name = $.trim($(this).val()) ;
    if("" == name || null == name){
     $("#rgood_result_id").hide();
    }
    else {
	$.get('<@spring.url "/pos/searchGood" />',
		{"name": name},
        function (data) {
        	$("#rgood_result_id").show();
     		$("#rgood_result_id").html(data);
     		$("#rgood_result_id a").click(function(){
	      		var name = $(this).html();
	      		var id = $(this).attr("value");
	      		$("#input_rgood").val(name);
	      		$("#rgood_result_id").hide();
	      		var newDiv = '<div class="item_relevance_pro" value="'+id+'"><span>'+name+'</span> <a class="a_btn" onClick="del(this)">删除</a></div>';
	      		$("#rgood_search").append(newDiv);
     		}).hover(function () {rgoodClose = false ;}
     				,function () {rgoodClose = true ;});
        });
    }
   }).blur(function(){
	    if(rgoodClose) {
	   		$("#rgood_result_id").hide();
	   	}
   }).focus(function(){
  		$("#rgood_result_id").show();
   });
   
});

function del(obj){
	$(obj).parent().remove();
};

function submitData(){
	var title=$("input[name='g_title']").val();
	var secondTitle=$("input[name='g_secondTitle']").val();
	var keyWorlds=$("input[name='g_keyWorlds']").val();
	var title=$("input[name='g_title']").val();
	var title=$("input[name='g_title']").val();
	var posCategory=$("select[name='g_posCategory']").find("option:selected").val();
	var factory=$("select[name='g_factory']").find("option:selected").val();
	var goodBrand=$("input[name='g_goodBrand']").val();
	var encryptCardWay=$("select[name='g_encryptCardWay']").find("option:selected").val();
	var signOrderWay=$("select[name='g_signOrderWay']").find("option:selected").val();
	var cardTypes = new Array();
	var i = 0;
	$("input[name='g_cardType']").each(function() {
            if (this.checked) {
            	cardTypes[i]=$(this).attr("value");
            }
            i++;
    });
    
    var batteryInfo=$("input[name='g_batteryInfo']").val();
    var shellMaterial=$("input[name='g_shellMaterial']").val();
    var price=$("input[name='g_price']").val();
    var retailPrice=$("input[name='g_retailPrice']").val();
	var purchasePrice=$("input[name='g_purchasePrice']").val();
 	var floorPrice=$("input[name='g_floorPrice']").val();
 	var floorPurchaseQuantity=$("input[name='g_floorPurchaseQuantity']").val();
 	var leaseDeposit=$("input[name='g_leaseDeposit']").val();
 	var leaseTime=$("input[name='g_leaseTime']").val();
 	var returnTime=$("input[name='g_returnTime']").val();
 	var leaseDescription=$("textarea[name='g_leaseDescription']").val();
 	var leaseAgreement=$("textarea[name='g_leaseAgreement']").val();
   	var re=/^\d+\.\d{2}$/;//2位小数
   	
   	var channels = new Array();
	i = 0;
   	$("#channel_search .item_relevance_pro").each(function() {
            channels[i]=$(this).attr("value");
            i++;
    });
    
    var description=$("textarea[name='g_description']").val();
    
    var photos = new Array();
	i = 0;
   	$("#photos .item_photoBox img").each(function() {
            photos[i]=$(this).attr("dbValue");
            i++;
    });
    
    var goods = new Array();
	i = 0;
   	$("#good_search .item_relevance_pro").each(function() {
            goods[i]=$(this).attr("value");
            i++;
    });
    
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    isNull(title, '标题不能为空');
    
    
}

function isNull(value, error){
	 if(value.length==0){
	 	alert(error);
	 }
}

function fileChange(obj){
	fileUpload($(obj).attr("id"));
}


 function fileUpload(id) {
    $.ajaxFileUpload(
        {
            url: '<@spring.url "/pos/uploadImg" />', 
            secureuri: false, 
            fileElementId: id,
            dataType: 'json',
            success: function (data, status)  
            {
            	alert(data);
            },
            error: function (data, status, e)
            {
                alert(e);
            }
        }
    )
}

</script>
</@c.html>