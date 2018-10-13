package com.wcf.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;

import java.util.UUID;

/**
 * 自定义的数据源类  继承认证数据源类 实现从数据库中获取用户信息 覆盖shiro的默认行为
 */
public class MyRealm extends AuthenticatingRealm {
    /**
     * 获取认证信息的方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println("-----------------------------------");

        UsernamePasswordToken upToken = (UsernamePasswordToken)authenticationToken;
        String username = upToken.getUsername();
        // 根据令牌中查询到的用户信息对象
//        User user = userService.queryUserByUsername(username);
//        if(user != null){
//            /**
//             * 参数1：代表从数据库中获取的用户名
//             * 参数2：代表从数据库中获取的密码
//             * 参数3：代表从Realm名称 唯一 不重复即可
//             */
//            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), UUID.randomUUID().toString());
//        }

        if("zs".equals(username)){
            return new SimpleAuthenticationInfo("zs","123456",UUID.randomUUID().toString());
        }
        return null;
    }
}