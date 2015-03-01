package com.comdosoft.financial.manage.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/system/operate")
public class OperateController {
	
	@RequestMapping(value="/accounts",method=RequestMethod.GET)
	public String accountsList(){
		return "system/accounts_list";
	}

    @RequestMapping(value="/account/create",method=RequestMethod.GET)
    public String accountCreateGet(){
        return "system/account_create";
    }

	@RequestMapping(value="/roles",method=RequestMethod.GET)
	public String rolesList(){
		return "system/roles_list";
	}

    @RequestMapping(value="/role/create",method=RequestMethod.GET)
    public String roleCreateGet(){
        return "system/role_create";
    }
}
