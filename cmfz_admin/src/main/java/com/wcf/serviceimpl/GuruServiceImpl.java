package com.wcf.serviceimpl;

import com.wcf.dao.GuruMapper;
import com.wcf.entity.Guru;
import com.wcf.service.GuruService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("guruService")
public class GuruServiceImpl implements GuruService {
    @Resource
    private GuruMapper guruMapper;
    @Override
    public List<Guru> queryAll() {
        List<Guru> gurus = guruMapper.findAll();
        return gurus;
    }
}
