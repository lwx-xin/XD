package priv.xin.xd.common;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import priv.xin.xd.expansionService.logger.UrlMdcFilter;

/**
 * @author ：lu
 * @date ：2021/11/21 0:09
 */
@Configuration
public class WebComponentConfig {

    @Bean
    public FilterRegistrationBean filterDemo3Registration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new UrlMdcFilter());
        registration.addUrlPatterns("/*");
        registration.setName("urlMdcFilter");
        registration.setOrder(-1);
        return registration;
    }

}
