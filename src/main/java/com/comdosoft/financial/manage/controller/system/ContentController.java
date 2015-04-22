package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.SysActivity;
import com.comdosoft.financial.manage.domain.zhangfu.SysShufflingFigure;
import com.comdosoft.financial.manage.domain.zhangfu.WebMessage;
import com.comdosoft.financial.manage.service.SysActivityService;
import com.comdosoft.financial.manage.service.SysShufflingFigureService;
import com.comdosoft.financial.manage.service.WebMessageService;
import com.comdosoft.financial.manage.utils.CompressedFileUtil;
import com.comdosoft.financial.manage.utils.FileUtil;
import com.comdosoft.financial.manage.utils.HttpFile;
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
    @Autowired
    private SysActivityService sysActivityService;
    @Value("${filePath}")
   	private String filePath;
    @Value("${sysShufflingfigurePath}")
    private String sysShufflingfigurePath;
    @Value("${path.prefix.activity}")
    private String activityPath;
    
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
        	String joinpath="";
        	 joinpath = HttpFile.upload(file, sysShufflingfigurePath);
             if("上传失败".equals(joinpath) || "同步上传失败".equals(joinpath)){
              return Response.getError(joinpath);
             }else{
              joinpath=filePath+joinpath;
              return Response.getSuccess(joinpath);
             }
        } catch (Exception e) {
            LOG.error("", e);
            return Response.getError("上传失败！");
        }
    }
    
    @RequestMapping(value="carousel/{id}/edit",method=RequestMethod.POST)
    @ResponseBody
    public Response editCarousel(@PathVariable Integer id, String pictureUrl, String webSiteUrl){
        sysShufflingFigureService.edit(id, pictureUrl, webSiteUrl);
        return Response.getSuccess("");
    }

    @RequestMapping(value = "activity", method = RequestMethod.GET)
    public String activityList(Integer page, Model model){
        findActivityPage(page, model);
        return "system/activity_list";
    }

    @RequestMapping(value="activity/page",method=RequestMethod.GET)
    public String activityPage(Integer page, Model model){
        findActivityPage(page, model);
        return "system/activity_list_page";
    }


    @RequestMapping(value="activity/{id}/edit",method=RequestMethod.GET)
    public String editActivity(@PathVariable Integer id, Model model){
        SysActivity sysActivity = sysActivityService.findInfo(id);
        model.addAttribute("activity", sysActivity);
        return "system/activity_create";
    }

    @RequestMapping(value="activity/{id}/edit",method=RequestMethod.POST)
    @ResponseBody
    public Response editActivity(@PathVariable Integer id, String title, String url, Model model){
        sysActivityService.edit(id, title, url);
        return Response.getSuccess(null);
    }

    @RequestMapping(value="activity/create",method=RequestMethod.POST)
    @ResponseBody
    public Response createActivity(String title, String url, Model model){
        sysActivityService.create(title, url);
        return Response.getSuccess(null);
    }

    @RequestMapping(value="activity/create",method=RequestMethod.GET)
    public String createActivity(Model model){
        return "system/activity_create";
    }

    @RequestMapping(value="activity/{id}/delete",method=RequestMethod.GET)
    @ResponseBody
    public Response deleteActivity(@PathVariable Integer id, Model model){
        sysActivityService.delete(id);
        return Response.getSuccess(null);
    }

    @RequestMapping(value="activity/uploadZip",method=RequestMethod.POST)
    @ResponseBody
    public Response uploadActivityZip(MultipartFile file){
        String fileName = activityPath + FileUtil.getPathFileName()+".zip";
        try {
            File osFile = new File(rootPath + fileName);
            if (!osFile.getParentFile().exists()) {
                osFile.getParentFile().mkdirs();
            }
            file.transferTo(osFile);
            CompressedFileUtil.unCompressedFile(osFile.getAbsolutePath(), osFile.getAbsolutePath().replace(".zip",""));
        } catch (Exception e) {
            LOG.error("", e);
            return Response.getError("上传失败！");
        }
        return Response.getSuccess(fileName.replace(".zip", "/index.html"));
    }

    private void findActivityPage(Integer page, Model model){
        if (page == null) {
            page = 1;
        }
        Page<SysActivity> messages = sysActivityService.findPages(page);
        model.addAttribute("page", messages);
        model.addAttribute("pageNum", page);
    }

}
