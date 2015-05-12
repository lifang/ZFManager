<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li>系统</li>
            <li><a href="<@spring.url "/system/agent/list" />">代理商</a></li>
            <#if agent??>
                <li><a href="javascript:void(0)" onclick="reload()">编辑</a></li>
            <#else>
                <li><a href="javascript:void(0)" onclick="reload()">创建</a></li>
            </#if>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>${agent???string("编辑代理商","创建代理商")}</h1>
        </div>
        <div class="attributes_box">
            <div class="item_list clear">
                <ul>
                    <li class="block"><span class="labelSpan">类型：</span>
                        <div class="text">
                            <span class="checkboxRadio_span"><input name="a_types" type="radio" value="1"> 公司</span>
                            <span class="checkboxRadio_span"><input name="a_types" type="radio" value="2"> 个人</span>
                            <input type="hidden" value="${(agent.id)!""}" id="agent_id">
                        </div>
                    </li>
                    <li class="block"><span class="labelSpan">负责人姓名：</span>
                        <div class="text"><input name="a_name" type="text" value="${(agent.name)!""}"></div>
                    </li>
                    <li class="block"><span class="labelSpan">负责人身份证号：</span>
                        <div class="text"><input name="a_cardId" type="text"  onblur="numModify(this)"   onblur="numModify(this)"value="${(agent.cardId)!""}"></div>
                    </li>
                    <li class="block"><span class="labelSpan" title="负责人身份证照：">负责人身份证照：</span>
                        <div class="text">
                            <form id="fileForm1" action="<@spring.url "/system/agent/uploadImg" />" method="post" enctype="multipart/form-data">
                                <div class="item_photoBox">
                                    <img id="cardIdPhotoPath" src="<@spring.url "/resources/images/zp.jpg" />" class="cover"
                                        <#if (agent.cardIdPhotoPath)??>
                                         value="${FILE_PATH+(agent.cardIdPhotoPath)}" dbValue="${agent.cardIdPhotoPath}"
                                         <#else>
                                         value="" dbValue=""
                                        </#if>>
                                <span class="cover_span">
                                </span>
                                <a href="javascript:void(0);" class="informImg_a">
                                    <span>重新上传</span><input name="file" type="file" onChange="fileChange(this)" index="1">
                                </a>
                            </div>
                            </form>

                        </div>
                    </li>
                    <li class="block"><span class="labelSpan">公司全称：</span>
                        <div class="text"><input name="a_companyName" type="text" value="${(agent.companyName)!""}"></div>
                    </li>
                    <li class="block"><span class="labelSpan">公司营业执照登记号：</span>
                        <div class="text"><input name="a_businessLicense"  onblur="numModify(this)" type="text" value="${(agent.businessLicense)!""}"></div>
                    </li>
                    <li class="block"><span class="labelSpan" title="营业执照登记照：">营业执照登记照：</span>
                        <div class="text">
                            <form id="fileForm2" action="<@spring.url "/system/agent/uploadImg" />" method="post" enctype="multipart/form-data">
                            <div class="item_photoBox">
                                <span class="cover_span">
                                    <img id="licenseNoPicPath" src="<@spring.url "/resources/images/zp.jpg" />" class="cover"
                                        <#if (agent.licenseNoPicPath)??>
                                         value="${FILE_PATH+(agent.licenseNoPicPath)}" dbValue="${agent.licenseNoPicPath}"
                                        <#else>
                                         value="" dbValue=""
                                        </#if>>
                                </span>
                                <a href="javascript:void(0);" class="informImg_a">
                                    <span>重新上传</span><input name="file" value="" type="file" onChange="fileChange(this)"  index="2">
                                </a>
                            </div>
                            </form>
                        </div>
                    </li>
                    <li class="block"><span class="labelSpan">手机：</span>
                        <div class="text"><input name="a_phone" type="text" value="${(agent.phone)!""}"></div>
                    </li>
                    <li class="block"><span class="labelSpan">邮箱：</span>
                        <div class="text"><input name="a_email" type="text" value="${(agent.email)!""}"></div>
                    </li>
                    <li class="block select2"><span class="labelSpan">所在地：</span>
                        <div class="text">
                            <select id="provinceSelect">
                                <#list provinces as province>
                                    <option value="${province.id}">${province.name}</option>
                                </#list>
                            </select>
                            <select id="citySelect">
                            </select>
                            <input name="a_address" type="text"  value="${(agent.address)!""}">
                        </div>
                    </li>
                    <li class="block"><span class="labelSpan">登录ID：</span>
                        <div class="text"><input name="a_username" type="text" value="${(agent.customer.username)!""}"></div>
                    </li>
                    <li class="block"><span class="labelSpan">密码：</span>
                        <div class="text"><input name="a_password" type="password"></div>
                    </li>
                    <li class="block"><span class="labelSpan">确认密码：</span>
                        <div class="text"><input name="confirmPassword" type="password"></div>
                    </li>
                </ul>
                <div class="img_info" style="display: none; top: 0px; left: 0px;"><img src=""></div>
            </div>
        </div>
        <div class="btnBottom"><button class="blueBtn"  onClick="submitData()">${agent???string("确定","保存")}</button></div>
    </div>
<script type="text/javascript" >
    $(function(){
        closeMask();
    <#if ((agent.types)!1) == 1>
        $("input[name='a_types'][value='1']").attr("checked", true);
    <#else>
        $("input[name='a_types'][value='2']").attr("checked", true);
    </#if>
        $('#provinceSelect').change(function(){
            var provinceId = $(this).children('option:selected').val();
            $.post('<@spring.url "/common/cities" />',
                    {'id':provinceId},
                    function (data) {
                        $("#citySelect").empty();
                        $("#citySelect").append(data);
                        $("#citySelect").find("option[value='${(agent.customer.cityId)!""}']").attr("selected",true);
                    });
        });
        $("#provinceSelect").find("option[value='${(agent.customer.city.parentId)!""}']").attr("selected",true);
        $("#provinceSelect").trigger("change");
    });

    function submitData() {
        var types=$("input[name='a_types']:checked").val();
        var name=$("input[name='a_name']").val();
        if(isNull(name, "负责人姓名不能为空!")){return false;}
        var cardId=$("input[name='a_cardId']").val();
        if(isNull(cardId, "负责人身份证号不能为空!")){return false;}
        var cardIdPhotoPath = $("#cardIdPhotoPath").attr("dbValue");
        if(isNull(cardIdPhotoPath, "负责人身份证照不能为空!")){return false;}
        var licenseNoPicPath = $("#licenseNoPicPath").attr("dbValue");
        if(isNull(licenseNoPicPath, "营业执照登记照不能为空!")){return false;}
        var companyName=$("input[name='a_companyName']").val();
        if(isNull(companyName, "公司全称不能为空!")){return false;}
        var businessLicense=$("input[name='a_businessLicense']").val();
        if(isNull(businessLicense, "公司营业执照登记号不能为空!")){return false;}
        var phone=$("input[name='a_phone']").val();
        if(isNull(phone, "手机不能为空!")){return false;}
        if(!checkMobile(phone)){
            showErrorTip("手机号格式不正确！");
            return false;
        }
        var email=$("input[name='a_email']").val();
        if(isNull(email, "邮箱不能为空!")){return false;}
        if(!checkEmail(email)){
            showErrorTip("邮箱格式不正确！");
            return false;
        }
        var cityId=$("#citySelect").find("option:selected").val();
        var address=$("input[name='a_address']").val();
        if(isNull(address, "所在地详细地址不能为空!")){return false;}
        var username=$("input[name='a_username']").val();
        if(isNull(username, "登录ID不能为空!")){return false;}
        var accountType;
        <#-- 
        if(checkMobile(username)){
            accountType=1;
        } else if(checkEmail(username)){
            accountType=2;
        } else{
            showErrorTip("登录ID必须为手机号或邮箱");
            return false;
        }
    	-->
		var a_id = $("#agent_id").val();
        var u_url="<@spring.url "/system/agent/findCustomerByName" />";
        $.post(u_url, {  
						 id: a_id,
	                    username: username
				 },
		   function(data){
		  		 if(data.code==1){
                         var password=$("input[name='a_password']").val();
				        var confirmPassword=$("input[name='confirmPassword']").val();
				        <#if !(agent??)>
				            if(isNull(password, "密码不能为空!")){return false;}
				        </#if>
				        if(password!=confirmPassword){
				            showErrorTip("密码和确认密码必须相同");
				            return false;
				        }
				
				        if(!checkPassword(password)){
				            return false;
				        }
				
				        <#if agent??>
				            var url="<@spring.url "/system/agent/${agent.id}/edit" />";
				        <#else>
				            var url="<@spring.url "/system/agent/create" />";
				        </#if>
				        $.post(url,
				                { name: name,
				                    types: types,
				                    name: name,
				                    cardId: cardId,
				                    companyName: companyName,
				                    businessLicense: businessLicense,
				                    phone: phone,
				                    email: email,
				                    cityId: cityId,
				                    address: address,
				                    username: username,
				                    password: password,
				                    accountType: accountType,
                                    cardIdPhotoPath: cardIdPhotoPath,
                                    licenseNoPicPath: licenseNoPicPath
				                },
				                function(data){
				                    if(data.code==1){
				                        window.location.href="<@spring.url "/system/agent/list" />"
				                    } else {
				                        showErrorTip(data.message);
				                    }
				                }
				        );
	        } else {
	            showErrorTip(data.message);
				return false;
	        }
		  
		   }, "json");
         
    } 
    
    function numModify(obj){
	  var reg = new RegExp("^[0-9]*\.[0-9]{0,1}$");
	  if(!reg.test(obj.value)){
		 obj.value="";
	  }
    }
    function fileChange(obj){
        showMask();
        var index = $(obj).attr("index");
        var options = {
            success: function(data){
                closeMask();
                if(data.code==1){
                    var img = $('#fileForm'+index).find(".item_photoBox img");
                    if(img.length > 0){
                        img.attr("value", "${FILE_PATH}"+data.result);
                        img.attr("dbValue", data.result);
                    }
                    alert("上传成功!");
                }else{
                    showErrorTip(data.message);
                }
            },
            resetForm: true,
            dataType: 'json'
        };
        $('#fileForm'+index).ajaxSubmit(options);
        return false;
    }

    function showMask(){
        var doc_height = $(document).height();
        $(".mask").css({
            display : 'block',
            height : doc_height
        }).show();
        $(".upImgLoading").show();
    }
    function closeMask(){
        $(".mask").hide();
        $(".upImgLoading").hide();
    }

</script>
</@c.html>
