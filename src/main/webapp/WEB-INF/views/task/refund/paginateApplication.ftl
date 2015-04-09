<div class="pageTurn">
<#if (refund.getTotalPage() > 0) >
	<div id="wPaginate">
    	<script type="text/javascript">
        	var totalPage = ${refund.getTotalPage()};
        	var indexPage=${refund.getCurrentPage()}-1;
        	$('#wPaginate').wPaginate({
        		index: indexPage,
                total: totalPage,
                spread : 5,
                url: function(i) {
                    getCurrentPage(i+1)
                },
                ajax: true
            });	
        </script>
    </div>
</#if>
<script type="text/javascript">
	function getCurrentPage(totalPage) {
			var url = "/task/refund/" + totalPage + "/info";
			$.ajax({
					url : "<@spring.url ''/>" + url,
					type : 'get',
					success : function(data) {
						$(".right").html(data);	
					}
			});
	    }
	    
</script>
</div>
