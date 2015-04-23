
$(function(){
	$("#errorBtn").click(function(){
		hiddenErrorTip();
	});
	
	$(".errorClose").click(function(){
		hiddenErrorTip();
	});
})

function showErrorTip(message){
	$("#errorP").html(message);
	
	var doc_height = $(document).height();
	var doc_width = $(document).width();
	var win_height = $(window).height();
	var win_width = $(window).width();
	
	var layer_height = $("#errorDiv").height();
	var layer_width = $("#errorDiv").width();
	var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;

	$(".mask").css({display:'block',height:doc_height});
	$("#errorDiv").css('top',(win_height-layer_height)/2);
	$("#errorDiv").css('left',(win_width-layer_width)/2);
	$("#errorDiv").css('display','block');
		
}

function hiddenErrorTip(){
	$("#errorP").html("");
	$("#errorDiv").css('display','none');
	$(".mask").css('display','none');
}


function isNull(value, error){
    if(!isNotNull(value)){
        showErrorTip(error);
        return true;
    }
    return false;
}

function isNotNull(value){
    return value != "" && value != null && value != undefined;
}

function checkPassword(value){
    if(!isNotNull(value)){
        return true;
    }
    var l = value.length;
    if(l<6 || l>20){
        showErrorTip("密码必须为6-20位！");
        return false;
    }
    return true;
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
