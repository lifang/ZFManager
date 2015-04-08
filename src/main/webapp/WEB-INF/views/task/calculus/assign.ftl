<#macro assign name page suspend> 

<div class="tab assign_tab">
	<a class="close">关闭</a>
	<div class="tabHead">任务分派</div>
	<div id="dispatch_select" class="tabBody">
	</div>
	<div id="dispatch_submit" class="tabFoot">
		<button class="blueBtn close" onClick="onDispatch();">确定</button>
	</div>
</div>

<script type="text/javascript">

	$(function() {
		$("#btn_dispatch").unbind("click");
		$("input[name='cb_row']").unbind("click");
		$("input[name='cb_all']").unbind("click");
		
		popup(".assign_tab",".assign_a");//分派
	
		$("#btn_dispatch").bind("click", function() {
			$('#dispatch_select').empty();
		
			var dispatchIds = [];
			var dispatchedNums = [];
			$("input[name='cb_row']").each(function () {
				var id = $(this).attr("cs_id");
				var status = $(this).attr("cs_status");
				var num = $(this).attr("cs_num");
           		if($(this).prop("checked") ) {
           			if (status==${suspend}) {
           				dispatchIds.push(id);
           			} else {
           				dispatchedNums.push(num);
           			}
           		}
            });
            
            $.get('<@spring.url "/task/calculus/dispatch" />',
	            {}, function (data) {
	            	$('#dispatch_select').append('<p class="assign_tab_p">将选中的 <span class="orangeText">'+dispatchIds.length+'</span> 条<span class="orangeText">待处理</span>任务分派给</p>')
	                $('#dispatch_select').append(data);
	                if (dispatchedNums.length>0) {
            			$('#dispatch_select').append('<br/><br/><em>编号<span class="orangeText">'+dispatchedNums+'</span>的申请已经在处理，不能再分派<em>')
            		}
	            });
	    });
	    
	    $("input[name='cb_row']").bind("click", function () {
			var total=0;
			var checked=0;
			$("input[name='cb_row']").each(function () {
           		total++;
           		if ($(this).prop("checked")) {
           			checked++;
           		}
            });
            if(checked<total) {
            	$("input[name='cb_all']").prop("checked",false);
            } else if(total==checked) {
            	$("input[name='cb_all']").prop("checked",true);
            }
        });
        
        $("input[name='cb_all']").bind("click", function () {
			var checked = this.checked;
			$("input[name='cb_row']").each(function () {
           		$(this).prop("checked", checked);
            });
        });
	});
	
	function onDispatch() {
		var ids = [];
		$("input[name='cb_row']").each(function () {
				var id = $(this).attr("cs_id");
				var status = $(this).attr("cs_status");
           		if($(this).prop("checked") && status==${suspend}) {
           			ids.push(id);
           		}
            });
		var customerId = $("#customer_select  option:selected").attr("customer_id");
		var customerName = $("#customer_select  option:selected").text();
		$.post('<@spring.url "" />'+'/task/${name}/dispatch',
	            {"ids": ids.join(','),
	            "customerId":customerId,
	            "customerName":customerName}, function (data) {
	            	pageChange(${page});
	            });
	}

</script>
</#macro> 
