package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.SysConfig;
import com.comdosoft.financial.manage.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created by quqiang on 15/3/19.
 */
@Controller
@RequestMapping("/system/setting")
public class SettingController {
    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping(value="",method= RequestMethod.GET)
    public String edit(Model model){
        List<SysConfig> sysConfigs = sysConfigService.findConfigs();
        model.addAttribute("", sysConfigs);
        return "system/setting";
    }

    @RequestMapping(value="edit",method= RequestMethod.POST)
    public Response edit(Map<String, Integer>configs){
        List<SysConfig> sysConfigs = sysConfigService.findConfigs();
        return Response.getSuccess(null);
    }
}
