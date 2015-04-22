package com.comdosoft.financial.manage.service.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.task.CertifiedOpen;
import com.comdosoft.financial.manage.domain.zhangfu.task.Mark;
import com.comdosoft.financial.manage.domain.zhangfu.task.Opendetailsinfo;
import com.comdosoft.financial.manage.domain.zhangfu.task.Showinfo;
import com.comdosoft.financial.manage.mapper.zhangfu.CertifiedOpenMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class CertifiedOpenService {

    @Value("${page.size}")
    private Integer pageSize;

    @Value("${filePath}")
    private String filePath ;
    
	@Autowired
	private CertifiedOpenMapper certifiedOpenMapper;
	
	public Page<CertifiedOpen> findPages(int id,int page, Byte status, String keys){
		long count = certifiedOpenMapper.countByKeys(status, keys);
		if (count == 0) {
			return new Page<CertifiedOpen>(new PageRequest(1, pageSize), new ArrayList<CertifiedOpen>(), count);
		}
		
		PageRequest request = new PageRequest(page, pageSize);
		List<CertifiedOpen> result = certifiedOpenMapper.findPageByKeys(request, status, keys,id);
		for (CertifiedOpen certifiedOpen : result) {
		    int isNeedOpen=certifiedOpenMapper.isNeedOpen(certifiedOpen.getPcid());
		    if(isNeedOpen>0){
	            try {
	                isNeedOpen = certifiedOpenMapper.videoStatus(certifiedOpen.getId());
	            } catch (Exception e) {
	                isNeedOpen=1;
	            }
		    }
		    certifiedOpen.setVideo_status(isNeedOpen);
        }
		Page<CertifiedOpen> goods = new Page<>(request, result, count);
		if (goods.getCurrentPage() > goods.getTotalPage()) {
			request = new PageRequest(goods.getTotalPage(), pageSize);
			result = certifiedOpenMapper.findPageByKeys(request, status, keys,id);
			for (CertifiedOpen certifiedOpen : result) {
			    int isNeedOpen=certifiedOpenMapper.isNeedOpen(certifiedOpen.getPcid());
	            if(isNeedOpen>0){
	                try {
	                    isNeedOpen = certifiedOpenMapper.videoStatus(certifiedOpen.getId());
	                } catch (Exception e) {
	                    isNeedOpen=0;
	                }
	            }
	            certifiedOpen.setVideo_status(isNeedOpen);
	        }
			goods = new Page<>(request, result, count);
		}
		return goods;
	}
	
	


	public Showinfo findInfo(Integer id) {
		return certifiedOpenMapper.findInfo(id);
	}
	
	public List<Opendetailsinfo> opendetailsinfo(Integer id) {
	    List<Opendetailsinfo> list=certifiedOpenMapper.getOpeningDetails(id);
	    for (Opendetailsinfo opendetailsinfo : list) {
	        if(opendetailsinfo.getTypes()==2){
	            opendetailsinfo.setValue(filePath+opendetailsinfo.getValue());
	        }
        }
        return list;
    }




    public List<Mark> getMark(Integer id) {
        return certifiedOpenMapper.getMark(id);
    }




    public void addMark(Mark mark) {
        certifiedOpenMapper.addMark(mark);
        
    }


    public int upVstatus(Integer id, Integer status) {
        int a=certifiedOpenMapper.upVstatus(id,status);
        if(a==0){
            a=certifiedOpenMapper.inVstatus(id,status);
        }
        return  a;
        
    }

    public int upStatus(Integer id, Integer status) {
        return  certifiedOpenMapper.upStatus(id,status);
    }




    public int upMstatus(Integer id, Integer status) {
        return  certifiedOpenMapper.upMstatus(id,status);
    }




    public void dispatch(String ids, Integer customerId, String customerName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids.split(","));
        params.put("customerId", customerId);
        params.put("customerName", customerName);
        certifiedOpenMapper.dispatch(params);
        
    }
	
}
