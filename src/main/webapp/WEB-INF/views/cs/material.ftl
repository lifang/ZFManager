<#macro material title materials> 
	<div class="attributes_box">
		<h2>${title}</h2>
		<div class="attributes_list_s clear">
			<#list materials as material>
				<div class="af_con">
					<div class="af_con_n">
						${material_index + 1}.${material.title} 
						<a href="<@spring.url '${material.templetFilePath}'/>" class="a_btn">下载模版</a>
					</div>
				</div>
			</#list>
		</div>
	</div>
</#macro>