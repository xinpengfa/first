package com.wcf.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;

import java.util.Arrays;

/**
 * shiro的第一个演示代码
 */
public class ShiroFirstDemo {

    /**
     * org.apache.shiro.authc.UnknownAccountException  用户名错误
     * org.apache.shiro.authc.IncorrectCredentialsException  密码错误
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            //1. 初始化Shiro的核心组件 安全管理器
            IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-realm-md5.ini");
            SecurityManager securityManager = iniSecurityManagerFactory.getInstance();
            SecurityUtils.setSecurityManager(securityManager);

            //2. 获取主体对象
            Subject subject = SecurityUtils.getSubject();

            //3. 调用认证方法 完成权限管理的认证流程
            AuthenticationToken token = new UsernamePasswordToken("zs","123456"); // 用户输入的信息

            subject.login(token);
            boolean[] booleans = subject.hasRoles(Arrays.asList("root", "admin"));

            System.out.println(subject.isAuthenticated() ?"认证成功":"认证失败");
        } catch (UnknownAccountException e) {
            System.out.println("用户不存在！");
        } catch (IncorrectCredentialsException e){
            System.out.println("密码不匹配！");
        } catch (AuthenticationException e){
            System.out.println("认证失败！");
        }
    }
}
