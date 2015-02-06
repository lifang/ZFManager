
$(function(){
	$("#errorBtn").click(function(){
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
	$("#errorP"),html("");
	$("#errorDiv").css('display','none');
	$(".mask").css('display','none');
}

