package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.SysShufflingFigure;
import com.comdosoft.financial.manage.domain.zhangfu.WebMessage;
import com.comdosoft.financial.manage.service.SysShufflingFigureService;
import com.comdosoft.financial.manage.service.WebMessageService;
import com.comdosoft.financial.manage.utils.FileUtil;
import com.comdosoft.financial.manage.utils.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by quqiang on 15/3/20.
 */
@Controller
@RequestMapping("/system/content")
public class ContentController {
    private Logger LOG = LoggerFactory.getLogger(ContentController.class);

    @Value("${path.root}")
    private String rootPath;
    @Value("${path.prefix.carousel}")
    private String carouselPath;
    @Autowired
    private WebMessageService webMessageService;
    @Autowired
    private SysShufflingFigureService sysShufflingFigureService;

    @RequestMapping(value = "webmessage", method = RequestMethod.GET)
    public String messageList(Integer page, Model model){
        findMessagePage(page, model);
        return "system/web_message_list";
    }

    @RequestMapping(value="webmessage/page",method=RequestMethod.GET)
    public String messagePage(Integer page, Model model){
        findMessagePage(page, model);
        return "system/web_message_list_page";
    }

    @RequestMapping(value="webmessage/{id}/top",method=RequestMethod.GET)
    @ResponseBody
    public Response topMessage(@PathVariable Integer id, Model model){
        webMessageService.top(id);
        return Response.getSuccess(null);
    }

    @RequestMapping(value="webmessage/{id}/canceltop",method=RequestMethod.GET)
    @ResponseBody
    public Response cancelTopMessage(@PathVariable Integer id, Model model){
        webMessageService.cancelTop(id);
        return Response.getSuccess(null);
    }

    @RequestMapping(value="webmessage/{id}/delete",method=RequestMethod.GET)
    @ResponseBody
    public Response deleteMessage(@PathVariable Integer id, Model model){
        webMessageService.delete(id);
        return Response.getSuccess(null);
    }

    @RequestMapping(value="webmessage/{id}/edit",method=RequestMethod.GET)
    public String editMessage(@PathVariable Integer id, Model model){
        model.addAttribute("message", webMessageService.findInfo(id));
        return "system/web_message_create";
    }

    @RequestMapping(value="webmessage/{id}/info",method=RequestMethod.GET)
    public String infoMessage(@PathVariable Integer id, Model model){
        model.addAttribute("message", webMessageService.findInfo(id));
        return "system/web_message_info";
    }

    @RequestMapping(value="webmessage/create",method=RequestMethod.GET)
    public String createMessage(Model model){
        return "system/web_message_create";
    }

    @RequestMapping(value="webmessage/{id}/edit",method=RequestMethod.POST)
    @ResponseBody
    public Response editMessage(@PathVariable Integer id, String title, String content, Model model){
        webMessageService.edit(id, title, content);
        return Response.getSuccess(null);
    }

    @RequestMapping(value="webmessage/create",method=RequestMethod.POST)
    @ResponseBody
    public Response createMessage(String title, String content, Model model){
        webMessageService.create(title, content);
        return Response.getSuccess(null);
    }

    private void findMessagePage(Integer page, Model model){
        if (page == null) {
            page = 1;
        }
        Page<WebMessage> messages = webMessageService.findPages(page);
        model.addAttribute("page", messages);
        model.addAttribute("pageNum", page);
    }


    @RequestMapping(value = "carousel", method = RequestMethod.GET)
    public String carouselList(Model model){
        List<SysShufflingFigure> sysShufflingFigures = sysShufflingFigureService.findSysShufflingFigures();
        model.addAttribute("sysShufflingFigures", sysShufflingFigures);
        return "system/carousel_list";
    }

    @RequestMapping(value="carousel/uploadImg",method=RequestMethod.POST)
    @ResponseBody
    public Response uploadImg(MultipartFile file){
        String fileName = carouselPath+ FileUtil.getPathFileName()+".jpg";
        try {
            File osFile = new File(rootPath + fileName);
            if (!osFile.getParentFile().exists()) {
                osFile.getParentFile().mkdirs();
            }
            file.transferTo(osFile);
        } catch (Exception e) {
            LOG.error("", e);
            return Response.getError("上传失败！");
        }
        return Response.getSuccess(fileName);
    }

    @RequestMapping(value="carousel/{id}/edit",method=RequestMethod.POST)
    @ResponseBody
    public Response editCarousel(@PathVariable Integer id, String pictureUrl, String webSiteUrl){
        sysShufflingFigureService.edit(id, pictureUrl, webSiteUrl);
        return Response.getSuccess("");
    }
}
