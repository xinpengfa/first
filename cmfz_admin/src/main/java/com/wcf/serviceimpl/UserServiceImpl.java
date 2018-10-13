package com.wcf.serviceimpl;

import com.wcf.dao.UserMapper;
import com.wcf.entity.Statistic;
import com.wcf.entity.User;
import com.wcf.service.UserService;
import com.wcf.utils.RandomSaltUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> queryByPage(Integer page, Integer size) {
        List<User> users = userMapper.findByPage((page-1)*size, size);
        return users;
    }

    @Override
    public Long findCounts() {
        Long counts = userMapper.findCounts();
        return counts;
    }

    @Override
    public List<User> queryAll() {
        List<User> users = userMapper.findAll();
        return users;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> queryByColumns(String columns) {
        List<User> users = userMapper.findByColumns(columns);
        return users;
    }

    @Override
    public void add(User user) {
        user.setId(UUID.randomUUID().toString().replace("-",""));
        String saltCode = RandomSaltUtil.generetRandomSaltCode();
        user.setSalt(saltCode);
        user.setPassword(saltCode+user.getPassword());
        userMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Statistic> queryNumbers(String sex) {
        return userMapper.findNumbers(sex);
    }

    @Override
    public Map<String, Object> queryDate() {
        List<Long> days = userMapper.findDays();
        Map<String,Object> map=new HashMap<String, Object>();
        int[] count=new int[7];
        List<String> list1=new ArrayList<String>();
        list1.add("7天");
        list1.add("15天");
        list1.add("30天");
        list1.add("90天");
        list1.add("半年");
        list1.add("一年");
        list1.add("一年以上");
        List<Integer> list2=new ArrayList<Integer>();

        for (Long day : days) {
            if(day<=7)
                count[0]+=1;
            else if(day>7&&day<=15)
                count[1]+=1;
            else if(day>15&&day<=30)
                count[2]+=1;
            else if(day>30&&day<=90)
                count[3]+=1;
            else if(day>90&&day<=180)
                count[4]+=1;
            else if(day>180&&day<=365)
                count[5]+=1;
            else count[6]+=1;
        }
        list2.add(count[0]);
        list2.add(count[1]);
        list2.add(count[2]);
        list2.add(count[3]);
        list2.add(count[4]);
        list2.add(count[5]);
        list2.add(count[6]);

        map.put("x",list1);
        map.put("y",list2);
        return map;
    }

}
