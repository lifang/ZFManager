package com.comdosoft.financial.manage.service;

import java.util.ArrayList;
import java.util.List;

import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.comdosoft.financial.manage.domain.zhangfu.Factory;
import com.comdosoft.financial.manage.mapper.zhangfu.FactoryMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FactoryService {

    @Value("${page.factory.size}")
    private Integer pageSize;

	@Autowired
	private FactoryMapper factoryMapper;
	
	public List<Factory> findCheckedFactories(){
		return factoryMapper.selectFactoriesByStatus(Factory.STATUS_CHECKED);
	}

    public Page<Factory> findPages(int page, Byte status, String keys){
        if (keys != null) {
            keys = "%"+keys+"%";
        }
        long count = factoryMapper.countByKeys(status, keys);
        if (count == 0) {
            return new Page<Factory>(new PageRequest(1, pageSize), new ArrayList<Factory>(), count);
        }

        PageRequest request = new PageRequest(page, pageSize);
        List<Factory> result = factoryMapper.findPageFactoryByKeys(request, status, keys);
        Page<Factory> factories = new Page<>(request, result, count);
        if (factories.getCurrentPage() > factories.getTotalPage()) {
            request = new PageRequest(factories.getTotalPage(), pageSize);
            result = factoryMapper.findPageFactoryByKeys(request, status, keys);
            factories = new Page<>(request, result, count);
        }
        return factories;
    }

    /**
     * 初审不通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusFirstUnCheck(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_WAITING_FIRST_CHECK) {
            factory.setStatus(Factory.STATUS_FIRST_UN_CHECKED);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    /**
     * 初审通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusFirstCheck(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_WAITING_FIRST_CHECK
                || factory.getStatus() == Factory.STATUS_FIRST_UN_CHECKED) {
            factory.setStatus(Factory.STATUS_FIRST_CHECKED);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    /**
     * 审核不通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusUnCheck(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_WAITING_FIRST_CHECK
                || factory.getStatus() == Factory.STATUS_FIRST_CHECKED) {
            factory.setStatus(Factory.STATUS_UN_CHECKED);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    /**
     * 审核通过
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusCheck(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_WAITING_FIRST_CHECK
                || factory.getStatus() == Factory.STATUS_FIRST_UN_CHECKED
                || factory.getStatus() == Factory.STATUS_FIRST_CHECKED
                || factory.getStatus() == Factory.STATUS_UN_CHECKED) {
            factory.setStatus(Factory.STATUS_CHECKED);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    /**
     * 停止
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusStop(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_CHECKED) {
            factory.setStatus(Factory.STATUS_STOP);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    /**
     * 启用
     *
     * @param id
     * @return
     */
    @Transactional("transactionManager")
    public Factory statusWaitingFirstCheck(Integer id) {
        Factory factory = factoryMapper.selectByPrimaryKey(id);
        if (factory.getStatus() == Factory.STATUS_STOP) {
            factory.setStatus(Factory.STATUS_WAITING_FIRST_CHECK);
            factoryMapper.updateByPrimaryKey(factory);
        }
        return factory;
    }

    public Factory findFactoryInfo(Integer id){
        return factoryMapper.findFactoryInfo(id);
    }
}
