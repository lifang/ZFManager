package com.comdosoft.financial.manage.service.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgentMark;
import com.comdosoft.financial.manage.mapper.zhangfu.CsAgentMarkMapper;

@Service
public class CsAgentMarkService {

	@Autowired
	private CsAgentMarkMapper csAgentMarkMapper;

	public List<CsAgentMark> findByAgentId(Integer csAgentId) {
		return csAgentMarkMapper.selectByAgentId(csAgentId);
	}
}
