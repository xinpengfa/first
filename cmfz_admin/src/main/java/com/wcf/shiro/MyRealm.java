package com.wcf.shiro;

import com.wcf.entity.Manager;
import com.wcf.service.ManagerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private ManagerService managerService;
    /**
     * 获取授权信息的方法
     *    角色信息 + 权限信息
     *
     * @param principals
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("--------------获取授权信息中！---------------");
        String username = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Manager manager = managerService.queryOne(username);
        if(manager != null){
            //完整的应该从数据库获取权限列表
            info.addRole("root");
            info.addRole("admin");
            info.addStringPermission("user:insert");
            info.addStringPermission("user:update");
            info.addStringPermission("user:find");
            info.addStringPermission("user:delete");
            return info;
        }
        return null;
    }

    /**
     * 获取认证信息的方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("--------------获取认证信息中！---------------");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        Manager manager = managerService.queryOne(username);
        // 加密的凭证信息
        if (manager != null){
            return new SimpleAuthenticationInfo(manager.getName(),
                    //加密后的密码
                    manager.getPassword(),
                    //经过处理的私盐
                    ByteSource.Util.bytes(manager.getSalt()),
                    //认证信息的唯一标识
                    UUID.randomUUID().toString());
        }
        return null;
    }
}
