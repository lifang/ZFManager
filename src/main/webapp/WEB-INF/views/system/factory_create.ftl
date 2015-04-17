<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">系统</a></li>
            <li><a href="#">第三方机构</a></li>
            <li><a href="#">${factory???string("编辑","创建")}</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>${factory???string("编辑","创建")}第三方机构</h1>
        </div>
        <div class="item_list clear">
            <ul>
                <li><span class="labelSpan">机构类型：</span>
                    <div class="text">
                        <span class="checkboxRadio_span"><input name="f_types" type="radio" value="1"> 收单机构</span>
                        <span class="checkboxRadio_span"><input name="f_types" type="radio" value="2"> 生产厂商</span>
                    
                    </div>
                </li>
                <li><span class="labelSpan">机构名称：</span>
                    <div class="text"><input name="f_name" type="text" value="${(factory.name)!""}"></div>
                         <input type="hidden" value="${(factory.id)!""}" id="agent_id">
                </li>
                <li><span class="labelSpan">登录ID：</span>
                    <div class="text"><input name="f_username" type="text" value="${(factory.customer.username)!""}"></div>
                </li>
                <li><span class="labelSpan">登录密码：</span>
                    <div class="text"><input name="f_password" type="password"></div>
                </li>
                <li><span class="labelSpan">确认密码：</span>
                    <div class="text"><input name="confirmPassword" type="password"></div>
                </li>
                <li><span class="labelSpan">机构LOGO：</span>
                    <form id="fileForm" action="<@spring.url "/system/factory/uploadImg" />" method="post" enctype="multipart/form-data">
                    <div class="text">
                        <img src="<@spring.url "/resources/images/zp.jpg"/>" class="cover"  id="img_path">
                        <a href="javascript:void(0);" class="informImg_a">
                            <span>上传</span><input name="file"  onChange="fileChange()"  multiple="" type="file" value="${(factory.logoFilePath)!""}">
                        </a>
                    </div>
                    </form>
                </li>
                <li><span class="labelSpan">机构URL：</span>
                    <div class="text"><input name="f_websiteUrl" value="${(factory.websiteUrl)!""}" type="text"></div>
                </li>
                <li><span class="labelSpan">简介：</span>
                    <div class="text"><textarea name="f_description" cols="" rows="">${(factory.description)!""}</textarea></div>
                </li>

                <li><span class="labelSpan">售后收货地址：</span>
                    <div class="text">
                        <select id="provinceSelect" class="select_l">
                            <#list provinces as province>
                                <option value="${province.id}">${province.name}</option>
                            </#list>
                        </select>
                        <select id="citySelect" class="select_l">
                        </select>
                        <input name="f_address" value="${(address.address)!""}" type="text" placeholder="详细地址">
                        <input name="f_cellphone" value="${(address.moblephone)!""}" type="text" placeholder="电话">
                    </div>
                </li>
            </ul>
            <div class="img_info" style="display: none; top: 0px; left: 0px;"><img src="<@spring.url "/resources/images/mt_big.jpg" />"></div>
        </div>
        <div class="btnBottom"><button class="blueBtn" onclick="submitData()">${factory???string("确定","创建")}</button></div>
    </div>
<script>
    $(function(){
        $("input[name='f_types'][value='${(factory.types)!1}']").attr("checked", true);
        $('#provinceSelect').change(function(){
            var provinceId = $(this).children('option:selected').val();
            $.post('<@spring.url "/common/cities" />',
                    {'id':provinceId},
                    function (data) {
                        $("#citySelect").empty();
                        $("#citySelect").append(data);
                        $("#citySelect").find("option[value='${(address.city.id)!""}']").attr("selected",true);
                    });
        });
        $("#provinceSelect").find("option[value='${(address.parentCity.id)!""}']").attr("selected",true);
        $("#provinceSelect").trigger("change");
    })

    function submitData() {
        var types=$("input[name='f_types']:checked").val();
        var name=$("input[name='f_name']").val();
        if(isNull(name, "机构名称不能为空!")){return false;}
        var username=$("input[name='f_username']").val();
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
						 factory_id: a_id,
	                    username: username
				 },
		   function(data){
		  		 if(data.code==1){
					var password=$("input[name='f_password']").val();
        var confirmPassword=$("input[name='confirmPassword']").val();
        <#if !(factory??)>
            if(isNull(password, "密码不能为空!")){return false;}
        </#if>
        if(password!=confirmPassword){
            showErrorTip("密码和确认密码必须相同");
            return false;
        }
        if(!checkPassword(password)){
            return false;
        }

        var logoFilePath=$("input[name='file']").attr("value");
        if(isNull(logoFilePath, "机构LOGO不能为空!")){return false;}
        var websiteUrl=$("input[name='f_websiteUrl']").val();
        if(isNull(websiteUrl, "机构URL不能为空!")){return false;}
        var description=$("textarea[name='f_description']").val();
        if(isNull(description, "简介不能为空!")){return false;}
        var cityId=$("#citySelect").find("option:selected").val();
        var address=$("input[name='f_address']").val();
        if(isNull(address, "详细地址不能为空!")){return false;}
        var cellphone=$("input[name='f_cellphone']").val();
        if(isNull(cellphone, "电话不能为空!")){return false;}


        <#if factory??>
            var url="<@spring.url "/system/factory/${factory.id}/edit" />";
        <#else>
            var url="<@spring.url "/system/factory/create" />";
        </#if>

		
        $.post(url,
                {types: types,
                    name: name,
                    username: username,
                    password: password,
                    accountType: accountType,
                    logoFilePath: logoFilePath,
                    websiteUrl: websiteUrl,
                    description: description,
                    cityId: cityId,
                    address: address,
                    cellphone: cellphone
                },
                function(data){
                    if(data.code==1){
                        window.location.href="<@spring.url "/system/factory/list" />"
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

    function fileChange(){
        var options = {
            success: function(data){
                if(data.code==1){
               		 $("#img_path").attr("src", data.result);
                    var $file = $("#fileForm").find(":file");
                    if($file.length > 0){
                        $file.attr("value", data.result);
                    }
                    alert("上传成功!");
                }
            },
            resetForm: true,
            dataType: 'json'
        };
        $("#fileForm").ajaxSubmit(options);
        return false;
    }

    function checkMobile(str) {
        var re = /^1\d{10}$/
        if (re.test(str)) {
            return true;
        } else {
            return false;
        }
    }
    function checkEmail(str){
        var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/
        if(re.test(str)){
            return true;
        }else{
            return false;
        }
    }
</script>
</@c.html>