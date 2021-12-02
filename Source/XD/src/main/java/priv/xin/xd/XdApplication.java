package priv.xin.xd;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.TtlMDCAdapterInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 使用 @ServletComponentScan注解后，Servlet、Filter、Listener
 * 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码
 */
@ServletComponentScan
@SpringBootApplication
@MapperScan(value = {"priv.xin.xd.core.dao", "priv.xin.xd.expansionService.*.dao"})
@EnableCaching
public class XdApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder){
        return springApplicationBuilder.sources(XdApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(XdApplication.class, args);
    }

}
