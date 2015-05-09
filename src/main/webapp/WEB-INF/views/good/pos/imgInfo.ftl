<#import "../../common.ftl" as c />
<@c.html>
	<div class="breadcrumb">
        <ul>
            <li>商品</li>
            <li><a href="<@spring.url "/good/pos/list"/>">POS机管理</a></li>
            <li><a href="<@spring.url "/good/pos/create" />">创建POS机</a></li>
            <li><a href="">编辑图片详情</a></li>
        </ul>
    </div>
    <div class="content clear">
		<div class="user_title"><h1>编辑图片详情</h1></div>
	        <div class="upLoadImg_con">
	        	<ul>
	        		<#if goodImgs??>
					  <#list goodImgs as goodImg>
			       		<li>
	            			<img src="${goodImg.urlPath!""}" />
	                		<a href="javascript:void(0)" id="a_${goodImg.id}" onClick="deleteImg(${goodImg.id},'a_${goodImg.id}')" class="close" title="删除">Close</a>
	                	</li>
					  </#list>
	            	</#if>
	            </ul>
	        </div>
	        <div class="btnBottom">
	        	<form id="fileForm" action="<@spring.url "/good/pos/uploadPosImg" />" method="post" enctype="multipart/form-data">
		        	<a href="javascript:void(0);" class="informImg_a">
		            	<span>上传图片</span><input name="file" onChange="fileChange()" multiple="" type="file">
		            </a>
		            <a href="javascript:window.opener=null;window.open('','_self');window.close();" class="informImg_a">
		            	<span>关闭</span>
		        	</a>
		            <input type="hidden" name="goodId" value="${goodId}"/>
	            </form>
	        </div>
	    </div>
	   </div>
	
<script type="text/javascript">
	function fileChange(){
        var options = {
            success: function(data){
                if(data.code==1){
                    var htmlstr = "<li><img src='"+data.result[0]+"' /><a href='javascript:void(0)' id='a_"+data.result[1]
                    +"' onClick='deleteImg(" + data.result[1] + ",&quot;a_"+data.result[1]+"&quot;)' class='close' title='删除'>Close</a></li>";
                    $(".upLoadImg_con>ul").append(htmlstr);
                    alert("上传成功!");
                }else{
                	alert(data.message);
                }
            },
            resetForm: true,
            dataType: 'json'
        };
        $("#fileForm").ajaxSubmit(options);
        return false;
    }
    function deleteImg(id,eleId){				
   	 	var url = "<@spring.url "/good/pos/delete" />";	
    	$.post(url,{id:id},			
    		function(data){		
    			if(data.code==-1){	
    				alert("删除图片失败！");
    			}else{
    				$("#"+eleId).parent().remove();
    			}	
    	});			
    }				
    
</script> 
</@c.html>