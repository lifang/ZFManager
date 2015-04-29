// AnyChat for Web SDK

/********************************************
 *				业务逻辑控制				*
 *******************************************/

var mDefaultServerAddr = "121.40.84.2";		// 默认服务器地址 
//var mDefaultServerAddr = "121.40.64.120";		// 默认服务器地址  发布地址
var mDefaultServerPort = 8906;					// 默认服务器端口号
var mSelfUserId = -1; 							// 本地用户ID
var mTargetUserId = -1;							// 目标用户ID（请求了对方的音视频）
var mRefreshVolumeTimer = -1; 					// 实时音量大小定时器
var mRefreshPluginTimer = -1;					// 检查插件是否安装完成定时器
var mRoomId = 0;
var mUsername = "-1";

// 日志记录类型，在日志信息栏内显示不同的颜色
var LOG_TYPE_NORMAL = 0;
var LOG_TYPE_API = 1;
var LOG_TYPE_EVENT = 2;
var LOG_TYPE_ERROR = 3;

// 通知类型，在文字消息栏内显示不同的颜色
var NOTIFY_TYPE_NORMAL = 0;
var NOTIFY_TYPE_SYSTEM = 1;

var recording = false;
var dwFlags = ANYCHAT_RECORD_FLAGS_VIDEO + ANYCHAT_RECORD_FLAGS_AUDIO
    + BRAC_RECORD_FLAGS_SERVER + ANYCHAT_RECORD_FLAGS_LOCALCB;

//登陆初始化，填写 登陆人id，和终端id
function LogicInit(username, roomId) {
    mRoomId = roomId;
    mUsername = username;
    //GetID("LOG_DIV_BODY").style.display = "none";;
    setTimeout(function () {
        if (navigator.plugins && navigator.plugins.length) {
            window.navigator.plugins.refresh(false);
        }
        //检查是否安装了插件	
        var NEED_ANYCHAT_APILEVEL = "0"; 						// 定义业务层需要的AnyChat API Level
        var errorcode = BRAC_InitSDK(NEED_ANYCHAT_APILEVEL); 	// 初始化插件
        AddLog("BRAC_InitSDK(" + NEED_ANYCHAT_APILEVEL + ")=" + errorcode, LOG_TYPE_API);
        if (errorcode == GV_ERR_SUCCESS) {
            if(mRefreshPluginTimer != -1)
                clearInterval(mRefreshPluginTimer); 			// 清除插件安装检测定时器
            AddLog("AnyChat Plugin Version:" + BRAC_GetVersion(0), LOG_TYPE_NORMAL);
            AddLog("AnyChat SDK Version:" + BRAC_GetVersion(1), LOG_TYPE_NORMAL);
            AddLog("Build Time:" + BRAC_GetSDKOptionString(BRAC_SO_CORESDK_BUILDTIME), LOG_TYPE_NORMAL);

            GetID("prompt_div").style.display = "none";
            // 初始化界面元素
            InitInterfaceUI();
        } else { 						// 没有安装插件，或是插件版本太旧，显示插件下载界面
            if (errorcode == GV_ERR_PLUGINNOINSTALL)
                GetID("prompt_div_line1").innerHTML = "首次进入需要安装插件，请点击下载按钮进行安装！";
            else if (errorcode == GV_ERR_PLUGINOLDVERSION)
                GetID("prompt_div_line1").innerHTML = "检测到当前插件的版本过低，请下载安装最新版本！";
            if(mRefreshPluginTimer == -1) {
                mRefreshPluginTimer = setInterval(function(){ LogicInit(); }, 1000);
            }
        }
    }, 500);
}

//设置AnyChat参数，需要在收到登录成功回调之后调用
function ConfigAnyChatParameter(){

}

// 初始化界面元素
function InitInterfaceUI() {
    setTimeout( function(){
        var errorcode = BRAC_Connect(mDefaultServerAddr, mDefaultServerPort); //连接服务器
        AddLog("BRAC_Connect(" + mDefaultServerAddr + "," + mDefaultServerPort + ")=" + errorcode, LOG_TYPE_API);
        errorcode = BRAC_Login(mUsername, "x", 0);//登陆
        AddLog("BRAC_Login(" + mUsername + ")=" + errorcode, LOG_TYPE_API);
        EnterRoomRequest(mRoomId);
    } , 500);

    // 鼠标移到日志层上面
    GetID("LOG_DIV_BODY").onmousemove = function () {
        GetID("LOG_DIV_BODY").style.zIndex = 100;
        GetID("LOG_DIV_CONTENT").style.backgroundColor = "#FAFADD";
        GetID("LOG_DIV_CONTENT").style.border = "1px solid black";
    }
    // 鼠标从日志层上面移开
    GetID("LOG_DIV_BODY").onmouseout = function () {
        GetID("LOG_DIV_BODY").style.zIndex = -1;
        GetID("LOG_DIV_CONTENT").style.backgroundColor = "#C4CEDD";
        GetID("LOG_DIV_CONTENT").style.border = "";
    }
}

// 当鼠标离开时候改变文本框背景颜色
function myblur(obj,color){
    obj.style.background=color;
}

// 请求进入指定的房间
function EnterRoomRequest(roomid) {
    var errorcode = BRAC_EnterRoom(roomid, "", 0); //进入房间
    AddLog("BRAC_EnterRoom(" + roomid + ")=" + errorcode, LOG_TYPE_API);
}

function GetID(id) {
    if (document.getElementById) {
        return document.getElementById(id);
    } else if (window[id]) {
        return window[id];
    }
    return null;
}

//div按钮鼠标划入划出效果
function Mouseover(id) {
    GetID(id).style.backgroundColor = "#FFFFCC";
}
//div按钮鼠标划入划出效果
function Mouseout(id) {
    GetID(id).style.backgroundColor = "#E6E6E6";
}
//获取当前时间  (00:00:00)
function GetTheTime() {
    var TheTime = new Date();
    return TheTime.toLocaleTimeString();
}

// 添加日志并显示，根据不同的类型显示不同的颜色
function AddLog(message, type) {
    if (type == LOG_TYPE_API) {			// API调用日志，绿色
        message = message.fontcolor("Green");
    } else if(type == LOG_TYPE_EVENT) {	// 回调事件日志，黄色
        message = message.fontcolor("#CC6600");
    } else if(type == LOG_TYPE_ERROR) {	// 出错日志，红色
        message = message.fontcolor("#FF0000");
    } else {							// 普通日志，灰色
        message = message.fontcolor("#333333");
    }
    var divContent = GetID("LOG_DIV_CONTENT");
    if(divContent){
        GetID("LOG_DIV_CONTENT").innerHTML += message + "&nbsp" + GetTheTime().fontcolor("#333333") + "<br />";
        DisplayScroll("LOG_DIV_CONTENT");
    } else{
        alert(1111);
    }

}

//系统信息框滚动条显隐
function DisplayScroll(id) {
    var offset = GetID(id); //需要检测的div
    if (offset.offsetHeight < offset.scrollHeight) {//div可见高度小于div滚动条高度
        GetID(id).style.overflowY = "scroll";//显示滚动条
        GetID(id).scrollTop = GetID(id).scrollHeight;//滚动条自动滚动到底部
    }
    else
        GetID(id).style.overflowY = "hidden";//隐藏滚动条
}

function RequestVideoByUserId(dwUserId)
{
    BRAC_SetVideoPos(dwUserId, GetID("div_videoarea"), "ANYCHAT_VIDEO_REMOTE");// 设置视频显示位置
    var errorcode = BRAC_UserCameraControl(dwUserId, 1); // 打开对方视频
    BRAC_UserSpeakControl(dwUserId, 1); // 打开对方音频
    if(errorcode == 0){
        mTargetUserId = dwUserId;
    }
}

function record(obj)
{
    if(mTargetUserId!=-1){
        var errorcode = 0;
        if(recording){
            errorcode = BRAC_StreamRecordCtrl(mTargetUserId , 0, dwFlags , 0);
        } else{
            errorcode = BRAC_StreamRecordCtrl(mTargetUserId , 1, dwFlags , 0);
        }
        if(errorcode != 0){
            alert("录制失败！");
        } else {
            if(recording){
                AddLog("结束录制", LOG_TYPE_EVENT);
                obj.innerHTML="开始录制";
            } else{
                AddLog("开始录制", LOG_TYPE_EVENT);
                obj.innerHTML="结束录制";
            }
            recording = !recording;
        }
    }
}