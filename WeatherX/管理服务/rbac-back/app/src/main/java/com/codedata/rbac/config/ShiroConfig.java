package com.codedata.rbac.config;


import com.codedata.rbac.security.auth.OAuth2AuthenticationFilter;
import com.codedata.rbac.security.auth.OAuth2AuthorizingRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //shiro接管servlet session
    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    //注册权限管理器
    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2AuthorizingRealm authRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(authRealm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    //Shiro过滤器
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new OAuth2AuthenticationFilter());
        shiroFilter.setFilters(filters);
        Map<String, String> filterMap = new LinkedHashMap<>();
        //访问白名单
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/druid/**", "anon");
        //接口文档
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs/**", "anon");//Springfox-Swagger提供的分组实例详情接口
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/doc.html", "anon");//Knife4j提供的文档访问地址
        filterMap.put("/swagger-ui.html", "anon");//Springfox-Swagger提供的文档访问地址
        filterMap.put("/swagger-resources/configuration/ui", "anon");//Springfox-Swagger提供
        filterMap.put("/swagger-resources/configuration/security", "anon");//Springfox-Swagger提供
        //安全认证
        filterMap.put("/security/login", "anon");//用户登录
        filterMap.put("/security/captcha", "anon");//验证码
        filterMap.put("/security/getfile", "anon");//下载文件
        //静态资源
        filterMap.put("/", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/fonts/**", "anon");
        filterMap.put("/index.html", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/**", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
