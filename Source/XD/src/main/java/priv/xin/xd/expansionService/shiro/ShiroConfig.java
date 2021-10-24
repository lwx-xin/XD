package priv.xin.xd.expansionService.shiro;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.xin.xd.common.entity.CommonConst;
import priv.xin.xd.common.properties.PathProperties;
import priv.xin.xd.expansionService.shiro.filter.FileAuthFilter;
import priv.xin.xd.expansionService.shiro.filter.LogoutFilter;
import priv.xin.xd.expansionService.shiro.filter.UrlAuthFilter;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类
 */
@Configuration
public class ShiroConfig {

    @Resource
    private PathProperties pathProperties;

    /**
     * 创建ShiroFilterFactoryBean
     * Filter工厂，设置对应的过滤条件和跳转条件
     */
    @Bean
    public ShiroFilterFactoryBean getFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 设置要使用的安全管理器
        filterFactoryBean.setSecurityManager(securityManager);

        //自定义拦截器
        Map<String, Filter> filter = new HashMap<>();
        // 验证用户是否有权限访问请求
        filter.put("urlAuth", new UrlAuthFilter());
        // 验证用户是否有权限访问文件
        filter.put("fileAuth", new FileAuthFilter());

        // 退出登录
        LogoutFilter logoutFilter = new LogoutFilter();
        // 设置退出登录后跳转的画面
        logoutFilter.setRedirectUrl(CommonConst.URL_LOGIN);
        filter.put("logout", logoutFilter);

        filterFactoryBean.setFilters(filter);

        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         *      常用的过滤器
         *          anon: 无需认证
         *          authc: 必须认证
         *          user: 如果使用rememberMe的功能可以直接访问
         *          perms: 必须得到资源权限才可以访问
         *          role: 必须得到角色权限才可以访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 静态资源无需权限
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/fonts/**", "anon");
        // 异常提示画面无需权限
        filterMap.put("/html/error/**", "anon");
        // 登录页面无需权限
        filterMap.put(CommonConst.URL_LOGIN, "anon");

        // 登录，登出请求无需权限
        filterMap.put("/sys/login", "anon");
        filterMap.put("/sys/logout", "logout");

//        filterMap.put("/sys/file/**", "fileAuth");
        filterMap.put("/sys/file/**", "anon");

        filterMap.put("/**", "urlAuth");

        filterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //调整登录页面
        filterFactoryBean.setLoginUrl(CommonConst.URL_LOGIN);
        return filterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     * 权限管理，配置主要是Realm的管理认证
     * 使用 @Qualifier("shiroRealm") 从spring容器中取出一个名为 shiroRealm 的对象, 也可以不使用, spring默认按参数名查找
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getSecurityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm,
                                                        @Qualifier("sessionManager") DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(getRealm());
        securityManager.setRealm(shiroRealm);
        // 注入session的管理
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager getSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 去掉shiro登录时url里的JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * 将自己的验证方式加入容器
     * 创建Realm,在spring中注入一个ShiroRealm对象
     */
    @Bean(name = "shiroRealm")
    public ShiroRealm getShiroRealm(@Qualifier("credentialsMatcher") CredentialsMatcher credentialsMatcher) {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(credentialsMatcher);
        return shiroRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * 所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     *
     * @return
     */
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new RetryLimitCredentialsMatcher();
    }

//    /**
//     * 凭证匹配器
//     *
//     * @return
//     */
//    @Bean(name = "credentialsMatcher")
//    public CredentialsMatcher getCredentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        // 加密方法
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        // 加密次数
//        credentialsMatcher.setHashIterations(1024);
//        return credentialsMatcher;
//    }
}