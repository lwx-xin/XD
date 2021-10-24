package priv.xin.xd.common.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ：lu
 * @date ：2021/10/24 12:24
 */
@PropertySource(value = {"classpath:crawler.properties"}, encoding = "utf-8")
@Configuration
public class CrawlerProperties {

    @Value("${Chrome.driver.path}")
    private String chromeDriverPath;

    @Value("${Chrome.driver.file}")
    private String chromeDriverFile;

    @Value("${crawler.root.path}")
    private String crawlerRootPath;

    public String getChromeDriverPath() {
        return chromeDriverPath;
    }

    public void setChromeDriverPath(String chromeDriverPath) {
        this.chromeDriverPath = chromeDriverPath;
    }

    public String getChromeDriverFile() {
        return chromeDriverFile;
    }

    public void setChromeDriverFile(String chromeDriverFile) {
        this.chromeDriverFile = chromeDriverFile;
    }

    public String getCrawlerRootPath() {
        return crawlerRootPath;
    }

    public void setCrawlerRootPath(String crawlerRootPath) {
        this.crawlerRootPath = crawlerRootPath;
    }
}
