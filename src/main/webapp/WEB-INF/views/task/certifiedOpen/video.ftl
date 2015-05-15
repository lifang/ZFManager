<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>运营中心</title>
<link href="<@spring.url "/resources/style/style.css"/>" rel="stylesheet" type="text/css" />

<script src="<@spring.url "/resources/js/jquery-1.11.2.min.js"/>"></script>
<script src="<@spring.url "/resources/js/main.js"/>"></script>
<!-- 加载AnyChat for Web SDK库  -->
<script language="javascript" type="text/javascript" src="<@spring.url "/resources/js/video/anychatsdk.js"/>" charset="GB2312"></script>
<script language="javascript" type="text/javascript" src="<@spring.url "/resources/js/video/anychatevent.js"/>" charset="GB2312"></script>
<!-- 加载业务逻辑控制脚本  -->
<script language="javascript" type="text/javascript" src="<@spring.url "/resources/js/video/logicfunc.js"/>"></script>
</head>
<body onload="LogicInit('${customer.id}', ${tinfo.tid})">
	<div class="videoAuthentication">
    	<div class="va_title">
            <h1>视频认证</h1>
            <div class="btn"><a href="#" class="ghostBtn">关闭</a><a href="#" class="ghostBtn remark_a">备注</a></div>
        </div>
        <div class="va_box">
        	<div class="va_left">
            	<div class="va_video">
                    <!--安装插件提示层 -->
                    <div id="prompt_div">
                        <div class="plug">
                            <h2>插件安装提示</h2>
                            <div id="prompt_div_line1"></div>
                            <i></i>
                            <div>控件安装完成后，请重启浏览器</div>
                            <div class="plug_btn"><a href="javascript:void(0);" onclick="window.open('http://anychat.oss.aliyuncs.com/AnyChatWebSetup.exe')">下载安装</a></div>
                            <div>
                                <a href="http://www.anychat.cn/">AnyChat</a>&nbsp;|&nbsp; <a href="http://www.bairuitech.com/">佰锐科技</a>
                            </div>
                        </div>
                    </div>
                    <div id="div_videoarea" style="width:100%; height:100%;">

                    </div>
                </div>
                <div class="val_bottom">
                    <a onClick="record(this)" class="greenBtn">开始录制</a>
                    <a id="vvv1" onClick="upvds(${tinfo.id!},2)" class="blueBtn">视频认证通过</a>
                    <a id="vvv2" class="greenBtn">视频已认证</a>
                    <a href="#" class="ghostBtn danger_a">添加风险标签</a>
                </div>
            </div>
            <div class="va_right">
            	<div class="attributes_box">
                    	<h2>终端信息</h2>
                        <div class="attributes_list_s clear">
                            <ul>
                                <li>终端号：<span class="orangeText">${tinfo.serial_num!}</span></li>
								<li>处理人：${tinfo.serial_num!}</li>
								<li>终端状态：
								   <#if tinfo.tstatus=1>已开通
							       <#elseif tinfo.tstatus=2>部分开通
							       <#elseif tinfo.tstatus=3>未开通 
							       <#elseif tinfo.tstatus=4>已注销
							       <#elseif tinfo.tstatus=5>已停用</#if></li>
								<li>开通申请状态：
								   <#if tinfo.status=1>待初次预审
							       <#elseif tinfo.status=2>初次预审不通过
							       <#elseif tinfo.status=3>二次预审中  
							       <#elseif tinfo.status=4>二次预审不通过
							       <#elseif tinfo.status=5>待审核
							       <#elseif tinfo.status=6>审核中
							       <#elseif tinfo.status=7>审核失败
							       <#elseif tinfo.status=8>审核成功
							       <#elseif tinfo.status=9>已取消
						       	   </#if></li>
								<li>视频认证状态：
								   <#if tinfo.video_status=1>无需认证
							       <#elseif tinfo.video_status=2>待认证
							       <#elseif tinfo.video_status=3>认证通过
							       <#elseif tinfo.video_status=4>认证失败
						       	   </#if></li>
								<li>品牌型号：${tinfo.good_brand!} ${tinfo.model_number!}</li>
								<li>支付通道：${tinfo.pcname!}</li>
								<li>到账日期：${tinfo.dbcname!}</li>
								<li>用户名：${tinfo.cname!}</li>
								<li>所属代理商：${tinfo.aname!}</li>
								<li>手机：${tinfo.phone!}</li>
								<li>E-mail：${tinfo.email!}</li>
                            </ul>
                        </div> 
                    </div>
                <div class="attributes_box">
                    	<h2>开通详情</h2>
                        <div class="attributes_list_s clear">
                            <ul>
								<li>开通方向：${tinfo.types!}</li>
								<li>绑定商户：${tinfo.merchant_name!}</li>
								<li>商家电话：${tinfo.mphone!}</li>
								<li>身份证：${tinfo.card_id!}</li>
							</ul>
                        </div>
                        <div class="item_list clear">
                            <ul>
							    <#if (opendetailsinfos)??> 
							    <#list opendetailsinfos as one> 
							    	<#if (one.types)??&&one.types != 2> 
							    		<li>
				                    		 <span class="labelSpan">${one.key!}：</span>
				                     		 <div class="text">${one.value!}</div>
				               			</li>
							    	</#if>
							    </#list> 
							    </#if>
							    
							    <#if (opendetailsinfos)??> 
							    <#list opendetailsinfos as one> 
							    	<#if (one.types)??&&one.types == 2> 
							    		<li>
						                     <span class="labelSpan" >${one.key!}：</span>
                                                 <div class="text">
                                                     <img src="<@spring.url "/resources/images/zp.jpg"/>" value="${one.value!}" class="cover" />
                                                </div>
				               			 </li>
							    	</#if>
							    </#list> 
							    </#if>
							</ul>
                            <div class="img_info"><img src="" /></div>
                        </div> 
                    </div>
            </div>
        </div>
	</div>
    <div class="mask"></div>
    
    <div class="tab remark_tab">
    	<a href="#" class="close">关闭</a>
        <div class="tabHead">备注</div>
        <div class="tabBody">
        	<textarea id="content" cols="" rows=""></textarea>
        </div>
        <div class="tabFoot"><button class="blueBtn" onclick="add()">确定</button></div>
    </div>
    
    <div class="tab danger_tab">
    	<a href="#" class="close">关闭</a>
        <div class="tabHead">添加商户风险标签</div>
        <div class="tabBody">
        	<div class="item_list clear">
            	<ul>
                	<li><span class="labelSpan">添加风险标签</span>
                    	<div class="text">
                        <select id="status">
                        	<#if (creditTypes)??> 
							    <#list creditTypes as one> 
							   		 <option value="${one.id!}">${one.name!}</option>
							    </#list> 
						    </#if>
                        </select>
            			</div>
                    </li>
                </ul>
            </div>
        	
        </div>
        <div class="tabFoot"><button class="blueBtn" onclick="upvs()">确定</button></div>
    </div>
    <!--系统日志信息层-->
    <div id="LOG_DIV_BODY" style="display:none;">
        <div id="LOG_DIV_TITLE">系统日志</div>
        <div id="LOG_DIV_CONTENT"></div>
    </div>
  <script type="text/javascript">
  $('#vvv2').hide();
	var add=function(){
		var id=${tinfo.id};
		var content=$('#content').val();
		if(""==content.trim()){
			return;
		}
		$.post('<@spring.url "/task/certifiedopen/addmark" />',
				{"applyid": id,"content":content},
		        function (data) {
					 $('#content').val('');
					 $('#mark_fresh').html(data);
					 $('.tab').hide();
					 $('.mask').hide();
		        });
	}
	var upvs=function(){
		var id=${tinfo.merchant_id!};
		var status=$('#status').val();
		$.post('<@spring.url "/task/certifiedopen/upmstatus" />',
				{"id": id,"status":status},
		        function (data) {
					if(data==1){
						$('.tab').hide();
						$('.mask').hide();
					}else{
						alert("操作失败!");
					}
		        });
	}
	var upvds=function(id,status){
		$.post('<@spring.url "/task/certifiedopen/upvstatus" />',
				{"id": id,"status":status},
		        function (data) {
					if(data>0){
						$('#vvv1').hide();
						$('#vvv2').show();
					}else{
						alert("操作失败!");
					}
		        });
	}
    function endRecord(url){
        $.post('<@spring.url "/task/certifiedopen/endRecord" />',
                {"id": ${tinfo.id},"url":url},
                function (data) {
                    if(data.code==1){
                        alert("保存成功!");
                    }else{
                        showErrorTip("保存失败!");
                    }
                });
    }
</script>  
</body>
</html>
