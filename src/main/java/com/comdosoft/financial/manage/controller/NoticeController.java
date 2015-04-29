package com.comdosoft.financial.manage.controller;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.OpeningApplie;
import com.comdosoft.financial.manage.service.NoticeService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by quqiang on 15/4/28.
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private TerminalService terminalService;
    @RequestMapping(value = "video", method= RequestMethod.GET)
    @ResponseBody
    public Response video(Integer terminalId){
        noticeService.applyVideo(terminalId);
        return Response.getSuccess(null);
    }

    @RequestMapping(value = "getVideo", method= RequestMethod.GET)
    @ResponseBody
    public Response getVideo(HttpServletRequest request){
        Customer customer = sessionService.getLoginInfo(request);
        Integer id = noticeService.getVideoApply(customer.getId());
        int applyId = 0;

        if (id != null){
            OpeningApplie openingApplie = terminalService.getOpeningApplie(id);
            if(openingApplie != null){
                applyId = openingApplie.getId();
            }
        }
        return Response.getSuccess(applyId);
    }

}
