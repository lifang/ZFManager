<#import "commonOrder.ftl" as c />
<@c.html>
<div id="page_fresh">
	<#include "goodListFresh.ftl" />
</div>
<script type="text/javascript">
	function goodPageChange(page) {
			var goodBrandsId = $("#hidden_good_brand_id").val();
			//var status = $("#hidden_status").val();
			//var factoryId=$("#hidden_factory_id").val();
		    $.get('<@spring.url "/good/user/page" />',
		            {"page": page,
		             "goodBrandsId": goodBrandsId
		             //"status": status,
		             //"factoryId":factoryId
		            },
		            function (data) {
		                $('#page_fresh').html(data);
		                //popupPage();
		            });
		}
		
	function goodBrandSelect(id){
		//var goodBrandsIdLast = $("#hidden_good_brand_id").val();
		$("#hidden_good_brand_id").val(id);
		//$("#good_brand_"+id).addClass("hover");
		//$("#good_brand_"+goodBrandsIdLast).removeClass("hover");
		//add("good_brand",id,goodBrandsIdLast,brandName);
		goodPageChange(1);
	}
	
	function goodBrandRemove(){
		$("#hidden_good_brand_id").val("");
		goodPageChange(1);
	}
	
	function add(txt,id,idLast,brandName) {
		var ul=$("#select_ul");
		var str="<li class='crumbs_nav_drop' id='"+txt+id+"'><a href='#' class='hover'><span class='cnd_p'>POS机品牌："+brandName+"</span><span class='cnd_x'></span></a><i></i></li>";
		var li=$("#"+txt+idLast);
		li.remove();
		$(str).appendTo(ul);
	}
</script>
</@c.html>