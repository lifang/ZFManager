<div class="tab deliver_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabHead">添加第三方库存发货信息</div>
    <div class="tabBody">
    	<div id="pos_info">
	    	<p>POS机名称：汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装 点餐机奶茶店 </p>
	        <p>POS机数量：10</p>
	    </div>
    	<textarea name="" cols="" rows="" id="terminal_serial_num" placeholder="输入终端号"></textarea>
    	<textarea name="" cols="" rows="" id="reserver2" placeholder="中汇终端激活码（非中汇终端无需填写）"></textarea>
        <input name="" type="text" placeholder="物流公司" id="logistics_name" />
        <input name="" type="text" placeholder="物流单号" id="logistics_number"/>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="deliverSure">确定</button></div>
</div>
<script type="text/javascript">
	function deliverBtn(id,size){
    	var htmlStr='';
    	for(var i=0;i<size;i++){
    		var hidden_good_title = $('#hidden_good_title_'+i).val();
    		var hidden_quantity = $('#hidden_quantity_'+i).val();
    		htmlStr+="<p>POS机名称："+hidden_good_title+"</p>"+
	        "<p>POS机数量："+hidden_quantity+"</p>";
    	}
		$("#pos_info").html(htmlStr);
		$("#deliverSure").unbind().bind('click',function(){deliverSure(id)});
    }
</script>