package com.comdosoft.financial.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by quqiang on 15/4/15.
 */
@Controller
@RequestMapping("video")
public class VideoController {
    @RequestMapping("info")
    public String info(){
        return "video/test";
    }
}
