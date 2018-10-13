package com.wcf.serviceimpl;

import com.wcf.dao.MenuMapper;
import com.wcf.entity.Menu;
import com.wcf.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service("menuService")
@Transactional
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Menu> queryAll() {
        List<Menu> menus = menuMapper.findAll();
        return menus;
    }
}
