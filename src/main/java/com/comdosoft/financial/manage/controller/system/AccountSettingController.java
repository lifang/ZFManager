package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.CustomerService;
import com.comdosoft.financial.manage.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/system/account/setting")
public class AccountSettingController {
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/modify",method=RequestMethod.GET)
	public String modifyGet(){
		return "system/account_setting";
	}

	@RequestMapping(value="/modify",method=RequestMethod.POST)
    @ResponseBody
	public Response modifyPost(
			HttpServletRequest request,
			Model model, String oldPwd, String newPwd){
		Customer customer = sessionService.getLoginInfo(request);
		boolean result = customerService.modifyPwd(customer, oldPwd, newPwd);
		if(result){
            return Response.getSuccess(null);
        }
        return Response.getError("原密码不正确！");
	}

}
