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
<script src="<@spring.url "/resources/js/common.js"/>"></script>
<!-- 加载AnyChat for Web SDK库  -->
<script language="javascript" type="text/javascript" src="<@spring.url "/resources/js/video/anychatsdk.js"/>" charset="GB2312"></script>
<script language="javascript" type="text/javascript" src="<@spring.url "/resources/js/video/anychatevent.js"/>" charset="GB2312"></script>
<!-- 加载业务逻辑控制脚本  -->
<script language="javascript" type="text/javascript" src="<@spring.url "/resources/js/video/logicfunc.js"/>"></script>
</head>
<body onload="LogicInit('111', 222)">
	<div class="videoAuthentication">
    	<div class="va_title">
            <h1>视频认证</h1>
            <div class="btn"><a href="#" class="ghostBtn">关闭</a><a href="#" class="ghostBtn remark_a">备注</a></div>
        </div>
        <div class="va_box" id="viewBox">
        	<div class="va_left">
            	<div class="va_video">
					<div id="div_videoarea" style="width:100%; height:100%;">
					</div>
				</div>
                <div class="val_bottom">
                	<a onClick="record(this)" class="greenBtn">开始录制</a>
                    <a href="#" class="blueBtn">视频认证通过</a>
                    <a href="#" class="ghostBtn danger_a">添加风险标签</a>
                </div>
            </div>
            <div class="va_right">
            	<div class="attributes_box">
                    	<h2>终端信息</h2>
                        <div class="attributes_list_s clear">
                            <ul>
                                <li>终端号：<span class="orangeText">af154694513218461</span></li>
                                <li>处理人：张丹</li>
                                <li>终端状态：已开通</li>
                                <li>开通申请状态：已完成</li>
                                <li>视频认证状态：认证通过</li>
                                <li>品牌型号：掌富 ZF300</li>
                                <li>支付通道：收账宝</li>
                                <li>到账日期：T+0</li>
                                <li>用户名：掌富 ZF300</li>
                                <li>所属代理商：收账宝</li>
                                <li>手机：T+0</li>
                                <li>E-mail：-</li>
                            </ul>
                        </div> 
                    </div>
                <div class="attributes_box">
                    	<h2>开通详情</h2>
                        <div class="attributes_list_s clear">
                            <ul>
                                <li>开通方向：对公</li>
                                <li>绑定商户：三峡格格</li>
                                <li>商家电话：0512-86523699</li>
                                <li>身份证：32198745633215563</li>
                            </ul>
                        </div>
                        <div class="item_list clear">
                            <ul>
                                <li><span class="labelSpan">营业执照：</span>
                                	<div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>" class="cover" /></div></li>
                                <li><span class="labelSpan">组织机构照片：</span>
                                	<div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>" class="cover" /></div></li>
                                <li><span class="labelSpan">税务登记照片：</span>
                                	<div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>" class="cover" /></div></li>
                                <li><span class="labelSpan">对私银行卡照片：</span>
                                	<div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>" class="cover" /></div></li>
                                <li><span class="labelSpan">法人身份证正面照片：</span>
                                	<div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>" class="cover" /></div></li>
                                <li><span class="labelSpan">法人身份证反面照片：</span>
                                	<div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>" class="cover" /></div></li>
                                <li><span class="labelSpan">个人上半身照片：</span>
                                	<div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>" class="cover" /></div></li>
                                <li><span class="labelSpan">个人签名照片：</span>
                                	<div class="text"><img src="<@spring.url "/resources/images/zp.jpg"/>" class="cover" /></div></li>
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
        	<textarea name="" cols="" rows=""></textarea>
        </div>
        <div class="tabFoot"><button class="blueBtn">确定</button></div>
    </div>
    
    <div class="tab danger_tab">
    	<a href="#" class="close">关闭</a>
        <div class="tabHead">添加商户风险标签</div>
        <div class="tabBody">
        	<div class="item_list clear">
            	<ul>
                	<li><span class="labelSpan">添加风险标签</span>
                    	<div class="text">
                        <select name="">
                          <option>风险标签</option>
                        </select>
            			</div>
                    </li>
                </ul>
            </div>
        	
        </div>
        <div class="tabFoot"><button class="blueBtn">确定</button></div>
    </div>
    
    <!--安装插件提示层 -->
    <div id="prompt_div">
        <div class="close_div">
            <div>插件安装提示</div>
            <div>刷新</div>
        </div>
        <div>
            <div id="prompt_div_line1"></div>
            <div>控件安装完成后，请重启浏览器</div>
            <div onclick="window.open('http://anychat.oss.aliyuncs.com/AnyChatWebSetup.exe')">下载安装</div>
        </div>
        <div>
            <div>
                <a href="http://www.anychat.cn/">AnyChat</a>&nbsp|&nbsp<a href="http://www.bairuitech.com/">佰锐科技</a>
            </div>
        </div>
    </div> 
	<!--系统日志信息层-->
	<div id="LOG_DIV_BODY">
		<div id="LOG_DIV_TITLE">系统日志</div>
		<div id="LOG_DIV_CONTENT"></div>
	</div>
</body>
</html>
