package net.scat.lp.springboot.config;

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public LPRealm lpRealm() {
        return new LPRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        return new DefaultWebSecurityManager(lpRealm());
    }



    @Bean
    // 默认会用loginUrl的post提交作为登录请求, 参见org.apache.shiro.web.filter.authc.FormAuthenticationFilter.onAccessDenied
    // 这里配置的loginUrl是toLogin
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        ShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        Map<String, String> filterChainMap = definition.getFilterChainMap();
//        filterChainMap.put("/toLogin", "authc");
        filterChainMap.put("/webjars/**", "anon");
        filterChainMap.put("/css/**", "anon");
        filterChainMap.put("/assets/**", "anon");
        filterChainMap.put("/img/**", "anon");
        filterChainMap.put("/js/**", "anon");
        filterChainMap.put("/**", "authc");
        return definition;
    }
}
