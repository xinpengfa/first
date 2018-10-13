package com.wcf.serviceimpl;

import com.wcf.dao.LogDAO;
import com.wcf.entity.Log;
import com.wcf.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

@Service("logService")
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    private LogDAO logDAO;

    @Override
    public void save(Log log) {
        log.setId(UUID.randomUUID().toString().replace("-", ""));
        logDAO.insert(log);
    }
}
