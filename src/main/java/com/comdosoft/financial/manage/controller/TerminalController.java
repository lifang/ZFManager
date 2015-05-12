package com.comdosoft.financial.manage.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.alibaba.fastjson.JSON;
import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.domain.zhangfu.TerminalMark;
import com.comdosoft.financial.manage.domain.zhangfu.TerminalOpeningInfo;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.TerminalService;
import com.comdosoft.financial.manage.utils.CommonServiceUtil;
import com.comdosoft.financial.manage.utils.HttpFile;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("terminal")
public class TerminalController {

    private Logger LOG = LoggerFactory.getLogger(TerminalController.class);
    @Value("${path.root}")
    private String rootPath;
    @Value("${path.prefix.export}")
    private String exportPath;
    @Value("${syncStatus}")
    private String syncStatus;
    @Value("${timingPath}")
    private String timingPath;
    @Value("${filePath}")
    private String filePath;
   
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private SessionService sessionService;


    @RequestMapping(value="list",method= RequestMethod.GET)
    public String list(Integer page, Byte status, String terminalNum, String orderNum, Model model){
    	findPage(page, status, terminalNum, orderNum, model);
        return "terminal/list";
    }

    @RequestMapping(value="page",method=RequestMethod.POST)
    public String page(Integer page, Byte status,
    		String terminalNum, String orderNum, Model model){
        findPage(page, status, terminalNum, orderNum, model);
        return "terminal/pageTerminal";
    }

    @RequestMapping(value="{id}/info",method=RequestMethod.GET)
    public String info(@PathVariable Integer id, Model model){
        Terminal terminal = terminalService.findTerminalInfo(id);
        String videoUrl = terminalService.videoFile(terminal.getId());
        model.addAttribute("terminal", terminal);
        model.addAttribute("videoUrl", videoUrl);
        return "terminal/info";
    }

    @RequestMapping(value="{id}/mark",method=RequestMethod.POST)
    public String mark(@PathVariable Integer id, String content, HttpServletRequest request, Model model){
        Customer customer = sessionService.getLoginInfo(request);
        TerminalMark terminalMark = terminalService.mark(id, customer.getId(), content);
        model.addAttribute("terminalMark", terminalMark);
        return "terminal/mark";
    }

    private void findPage(Integer page, Byte status, String terminalNum, String orderNum, Model model){
        if (page == null) {
            page = 1;
        }
        if (status != null && status == 0) {
            status = null;
        }
        Page<Terminal> terminals = terminalService.findPages(null, page, status, terminalNum,orderNum);
        model.addAttribute("terminals", terminals);
    }

    @RequestMapping(value="{id}/exportOpenInfo",method=RequestMethod.GET)
    @ResponseBody
    public Response exportOpenInfo(@PathVariable String id) throws Exception {
    	//获取该终端申请开通图片资料集合
    	List<Map<Object, Object>> list = terminalService.getTerminalOpen(Integer.valueOf(id),TerminalOpeningInfo.TYPE_FILE);
    	if(list == null){
			return Response.getError("下载失败！");
		}
		String[] str = new String[list.size()];
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null){
				if(list.get(i).get("value") !=null){
					str[i] = (String) list.get(i).get("value");
				}
			}
		}
		int count  = HttpFile.postWar(str,id);
		if(count == 0){
			return Response.getSuccess(filePath+"zip/terminal/"+id+".zip");
		}
			return Response.getError("下载失败！");
		/*
        String zipFileName = exportPath+ FileUtil.getPathFileName()+".zip";
        File parentFile = new File(rootPath + zipFileName.replace(".zip", ""));
        parentFile.mkdirs();
        Terminal terminal = terminalService.findTerminalInfo(id);

        File excelFile = new File(parentFile, "开通详情.xls");
        Workbook workbook = new HSSFWorkbook();
        Sheet hssfSheet = workbook.createSheet("第一页");
        Row firstRow = hssfSheet.createRow(0);
        //开通方向
        String openDirect = "";
        if(terminal.getOpeningApplie() != null){
            if (terminal.getOpeningApplie().getTypes() == OpeningApplie.TYPE_PUBLIC){
                openDirect = "对公";
            } else {
                openDirect = "对私";
            }
        }
        Cell cell = firstRow.createCell(0);
        cell.setCellValue("开通方向：" + openDirect);
        //绑定商户 商家电话 身份证
        String name = "";
        String phone = "";
        String legalPersonCardId = "";
        if(terminal.getMerchant() != null){
            name = terminal.getMerchant().getTitle();
            phone = terminal.getMerchant().getPhone();
            legalPersonCardId = terminal.getMerchant().getLegalPersonCardId();
        }
        Cell cell1 = firstRow.createCell(1);
        cell1.setCellValue("绑定商户：" + name);
        Cell cell2 = firstRow.createCell(2);
        cell2.setCellValue("商家电话：" + phone);
        Cell cell3 = firstRow.createCell(3);
        cell3.setCellValue("身份证：" + legalPersonCardId);

        if(terminal.getOpeningApplie() != null){
            int i = 1;
            for (TerminalOpeningInfo openingInfo : terminal.getOpeningApplie().getTerminalOpeningInfos()){
                if(openingInfo.getTypes() == TerminalOpeningInfo.TYPE_FILE){
                    File sourceFile = new File(rootPath + openingInfo.getValue());
                    if (sourceFile.exists()){
                        String suffix = sourceFile.getName().substring
                                (sourceFile.getName().lastIndexOf("."));
                        File targetFile = new File(parentFile, openingInfo.getKey()+suffix);
                        FileUtils.copyFile(sourceFile, targetFile);
                    }
                } else {
                    Row row = hssfSheet.createRow(i);
                    Cell c0 = row.createCell(0);
                    c0.setCellValue(openingInfo.getKey());
                    Cell c1 = row.createCell(1);
                    c1.setCellValue(openingInfo.getValue() == null ? "" : openingInfo.getValue());
                    i++;
                }
            }
        }
        String path = rootPath + exportPath+ FileUtil.getPath();
        FileOutputStream fileOutputStream = new FileOutputStream(excelFile);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        CompressedFileUtil.compressedFile(parentFile.getAbsolutePath(), rootPath + zipFileName);
        return Response.getSuccess(zipFileName);
    */}
    
    @RequestMapping(value="/syncStatus",method=RequestMethod.POST)
    @ResponseBody
    public String syncStatus(Integer terminalId){
    	String url = timingPath + syncStatus;
		String response = null;
		try {
			response = CommonServiceUtil.synchronizeStatus(url, terminalId);
		} catch (IOException e) {
			LOG.error("error..." + e);
            Response r = Response.getError("同步失败！");
            response = JSON.toJSON(r).toString();
		}
		return response;
    }

}
