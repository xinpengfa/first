package com.wcf.test;

import com.wcf.entity.Manager;
import com.wcf.service.ManagerService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ManagerTest {
    @Resource
    private ManagerService managerService;
    @Test
    public void login(){
        Manager xiaoxuanzi = managerService.queryOne("xiaoxuanzi");
        System.out.println(xiaoxuanzi);
    }

    @Test
    public void testMd5(){
        Md5Hash md5Hash = new Md5Hash("ABCD1"); // salt=ABCD 明文密码=123456
        System.out.println(md5Hash);
    }
}
