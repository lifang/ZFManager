<div id="record" class="user_record">
                    	<h2>追踪记录</h2>
                    	<#list refundRecord as record>
                    		<div class="ur_item">
                        		<div class="ur_item_text">${(record.content)!''}</div>
                            	<div class="ur_item_name">${(record.name)!''} <em>${(record.createdAt)!''}</em></div>
                        	</div>
                    	</#list>
                    </div>