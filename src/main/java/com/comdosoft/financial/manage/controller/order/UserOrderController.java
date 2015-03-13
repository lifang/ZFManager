package com.comdosoft.financial.manage.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/order/user")
public class UserOrderController {
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(){
		return "order/user/list";
	}

    @RequestMapping(value="create",method = RequestMethod.GET)
    public String createGet(){
    	
    	
    	
        return "order/user/create";
    }
}
