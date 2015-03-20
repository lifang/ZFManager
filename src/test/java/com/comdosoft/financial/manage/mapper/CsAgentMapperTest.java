package com.comdosoft.financial.manage.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.comdosoft.financial.manage.domain.zhangfu.CsAgent;
import com.comdosoft.financial.manage.mapper.zhangfu.CsAgentMapper;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mvc.xml")
@ActiveProfiles("local")
@Transactional
public class CsAgentMapperTest {

	@Autowired
	CsAgentMapper csAgentMapper;

	@Before
	public void setUp() {
		CsAgent record = new CsAgent();
		record.setApplyNum("19900712");
		record.setStatus((byte) 1);
		int result = csAgentMapper.insert(record);
		Assert.assertEquals(1, result);
	}

	@Test
	public void testCountSelective() {
		long count = csAgentMapper.countSelective(null, null);
		Assert.assertTrue(count > 0);
		count = csAgentMapper.countSelective((byte) 1, "9007");
		Assert.assertEquals(1, count);
	}

	@Test
	public void testFindPageSelective() {
		PageRequest pageRequest = new PageRequest(1, 10);
		List<CsAgent> csAgents = csAgentMapper.findPageSelective(pageRequest, (byte) 1, "9007");
		Assert.assertEquals("19900712", csAgents.get(0).getApplyNum());
	}

}
