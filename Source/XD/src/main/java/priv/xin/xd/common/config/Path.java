package priv.xin.xd.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import priv.xin.xd.common.properties.PathProperties;

import javax.annotation.Resource;

/**
 * 虚拟路径配置
 *
 * @author ：lu
 * @date ：2021/6/1 15:39
 */
@Configuration
public class Path extends WebMvcConfigurationSupport {

    @Resource
    private PathProperties pathProperties;

    /**
     * @Description: 对文件的路径进行配置, 创建一个虚拟路径/Path/**
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 访问前端HTML的虚拟路径
        registry.addResourceHandler(pathProperties.getHtmlVirtualPath())
                .addResourceLocations("file:" + pathProperties.getHtmlRealPath());
//        // 访问上传文件的虚拟路径
//        registry.addResourceHandler(pathProperties.getFileSaveVirtualPath())
//                .addResourceLocations("file:" + pathProperties.getFileSaveRealPath());
    }
}