package com.wcf.shiro.config;

import com.wcf.shiro.MyRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * shiro的配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * shiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean createShiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        // 定制访问规则的过滤器链
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("/login.jsp","anon");//匿名即可
        map.put("/img/**","anon");//匿名即可
        map.put("/js/**","anon");//匿名即可
        map.put("/themes/**","anon");//匿名即可
        map.put("/index.jsp","anon");//匿名即可
        map.put("/user/**","anon");//匿名即可
        map.put("/manager/**","anon");//匿名即可
        map.put("/main/main.jsp","user");//经过表单验证或者记住我才能访问
        map.put("/**","authc");//其他,要表单验证过滤器

        factoryBean.setFilterChainDefinitionMap(map);
        factoryBean.setLoginUrl("/login.jsp");  // 未认证所跳转的页面/login.jsp
        factoryBean.setUnauthorizedUrl("/unauthorized.jsp");//设置未授权所跳转的页面
        return factoryBean;
    }

    @Bean
    public SecurityManager createSecurityManager(Realm realm,RememberMeManager rememberMeManager,CacheManager cacheManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    /**
     * 缓存管理器
     *      Ehcache 缓存框架 构建本地缓存
     * @return
     */
    @Bean
    public CacheManager createCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml"); // ehcache框架的配置文件
        return cacheManager;
    }

    /**
     * 注解式授权相关
     * 默认通知自动代理创建器
     * 与spring boot整合必须要有这个bean,代理才能生效
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator createDefaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor createAuthorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 记住我相关
     * @param cookie
     * @return
     */
    @Bean
    public RememberMeManager createRememberMeManager(Cookie cookie){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        rememberMeManager.setCookie(cookie);
        return rememberMeManager;
    }

    @Bean
    public Cookie createCookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setMaxAge(60*60*7*24);
        cookie.setPath("/");
        cookie.setName("rm");
        return cookie;
    }

    /**
     * 声明自定义的数据源类
     * @param credentialsMatcher
     * @return
     */
    @Bean
    public Realm createRealm(CredentialsMatcher credentialsMatcher){
        MyRealm realm = new MyRealm();
        //注入凭证匹配器
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    /**
     * 声明复杂的凭证匹配器
     * @return
     */
    @Bean
    public CredentialsMatcher createCredentialsMatcher(){
        //告诉shiro,要使用叫做hash的凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //并且初始化md5的算法(使用md5算法)
        credentialsMatcher.setHashAlgorithmName("md5");
        return credentialsMatcher;
    }
}
