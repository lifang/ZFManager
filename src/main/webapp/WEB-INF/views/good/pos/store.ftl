<#import "../../common.ftl" as c />
<@c.html>
<div class="right">
    <div class="breadcrumb">
         <ul>
            <li>商品</li>
            <li><a href="<@spring.url "/good/pos/list"/>">POS机管理</a></li>
            <li><a href="">库存管理</a></li>
         </ul>
    </div>
    <div class="content clear">
		<div class="user_title"><h1>库存管理</h1></div>
            <div class="pos_inventory">
                <h2>POS机名称：${good.title!"POS机"}</h2>
                <input type="hidden" value="${good.id}"/>
                <div class="pos_inventory_con">
                <h3>入库</h3>
                <div class="pos_inventory_box">
                     <textarea id="terminals" name="" cols="" rows=""></textarea>
                     <div class="pit_btn">
                          <button class="blueBtn" onClick="submitTerminal()">入库</button>
                          <p><span class="red">*</span>请输入要入库的终端编号列表，以逗号（,）、空格或回车分隔。</p>
                     </div>
                </div>
             </div>
             <div class="pos_inventory_con">
                 <h3>库存列表</h3>
                 <div class="pos_inventory_box">
	                 <ul>
	                    <#if (terminalList)??>
		  					<#list terminalList as terminal>
       							<li><span>${terminal.serialNum}</span><a id="a_${terminal.serialNum}" onClick="deleteTer('${terminal.serialNum}',${good.id},'a_${terminal.serialNum}')" class="x" title="删除">删除</a></li>
		  					</#list>
						</#if>
	                </ul>               
                </div>
             </div>
          </div>
      </div>
</div>
<div class="mask"></div> 
<div class="tab inventory_tab">
	<a href="#" class="close">关闭</a>
    <div class="tabBody">
    	<p>是否要删除<span></span>这台终端？</p>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="confirm">确定</button><button class="grayBtn" onClick="cancel()">取消</button></div>
</div>
<script type="text/javascript">
	function submitTerminal(){
        var terminalList = $("#terminals").val().replace(/[ ]|\,/g,"\r");
        if(isNull(terminalList,"终端数据不能为空！")){
            return false;
        }
        var id = $("input[type='hidden']").val();
        $.post('<@spring.url "" />'+'/good/pos/'+id+'/importTerminal',
                {data:terminalList},
                function (data) { 
                   if(data.code==-1){
                       showErrorTip(data.message);
                   	   return;
                   }
                   var terminalArray = terminalList.split("\n|\r\n|\r");
                   var htmlStr = new Array();
                   for(var i=0;i<terminalArray.length;i++){
                       htmlStr.push("<li><span>"+terminalArray[i]+"</span><a id='a_"+terminalArray[i]+
                       	"' onClick=&quto;&quto;deleteTer('"+terminalArray[i]+"',${good.id},'a_"+terminalArray[i]+"')&quto;&quto; class='x' title='删除'>删除</a></li>");
                   }
                   $(".pos_inventory_box>ul").prepend(htmlStr.join(""));
                   window.location.reload();
                });
    }
    
    function deleteTer(terminalNum,goodId,id){
    	$(".inventory_tab>div>p>span").html(terminalNum);
    	popup(".inventory_tab","a.x");//pos机管理-库存管理
    	$("#confirm").unbind().bind("click",function(){
    		$.post('<@spring.url "" />'+'/good/pos/deleteTerminal',
                {terminalNum:terminalNum,goodId:goodId},
                function (data) {
                	if(data.code==-1){
                		showErrorTip(data.message);
                		return;
                	}
                	cancel();
                	$(".pos_inventory_box>ul>li:first").remove();
                });
    	});
    	
    }
    function cancel(){
    	$(".mask").hide();
    	$(".inventory_tab").hide();
    }
	
</script>    
</@c.html>