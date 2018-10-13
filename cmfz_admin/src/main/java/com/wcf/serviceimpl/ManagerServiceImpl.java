package com.wcf.serviceimpl;

import com.wcf.dao.ManagerMapper;
import com.wcf.entity.Manager;
import com.wcf.service.ManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("managerService")
@Transactional
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private ManagerMapper managerMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Manager queryOne(String name) {
        Manager manager = managerMapper.findOne(name);
        if (manager!=null){
                return manager;
        }
        return null;
    }

    @Override
    public void modifyPassword(Manager manager) {
        managerMapper.update(manager);
    }

}
