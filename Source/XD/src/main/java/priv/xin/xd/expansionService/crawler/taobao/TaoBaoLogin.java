package priv.xin.xd.expansionService.crawler.taobao;


import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import priv.xin.xd.common.properties.CrawlerProperties;
import priv.xin.xd.common.util.SpringUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 模拟淘宝登录
 * @author ：lu
 * @date ：2021/10/24 11:30
 */
public class TaoBaoLogin {

    /**
     * 序号 说明   是否必填  允许填写的值			    允许的通配符
     * 1    秒     是        0-59    			, - * /
     * 2    分     是  	    0-59    			, - * /
     * 3    小时   是        0-23    			, - * /
     * 4    日     是        1-31    			, - * ? / L W
     * 5    月     是        1-12 or JAN-DEC   	, - * /
     * 6    周     是        1-7 or SUN-SAT   	, - * ? / L #
     * 7    年     否        empty 或 1970-2099	, - * /
     */
    public static final String cron = "0 40 12 * * ? *"; // 每天3点触发

    private CrawlerProperties crawlerProperties;

    private String TAOBAO_LOGIN_HTML = "https://login.taobao.com/member/login.jhtml";
    private String TAOBAO_USER_NUMBER_INPUT = "#login-form #fm-login-id";
    private String TAOBAO_USER_PASSWORD_INPUT = "#login-form #fm-login-password";
    private String TAOBAO_LOGIN_BUTTON = "#login-form button[type='submit']";

    private static String username = "18571637287"; //账号
    private static String password = "1314520lu.."; //密码

    public Set<Cookie> Login() {
        Set<Cookie> cookies = new HashSet<>();
        if (crawlerProperties == null) {
            crawlerProperties = SpringUtil.getBean(CrawlerProperties.class);
        }
        /**
         * 下载chrome驱动包，将驱动包地址添加到环境变量(path)中
         * http://chromedriver.storage.googleapis.com/index.html
         */
        String chromeDriver = crawlerProperties.getChromeDriverPath() + "/" + crawlerProperties.getChromeDriverFile();

        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver();
        driver.get(TAOBAO_LOGIN_HTML);

        // 防止页面未能及时加载出来而设置一段时间延迟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector(TAOBAO_USER_NUMBER_INPUT)).sendKeys(username);
        driver.findElement(By.cssSelector(TAOBAO_USER_PASSWORD_INPUT)).sendKeys(password);
        driver.findElement(By.cssSelector(TAOBAO_LOGIN_BUTTON)).click();
        try {
            // 防止页面未能及时加载出来而设置一段时间延迟
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cookies = driver.manage().getCookies();
            driver.close();
        }
        return cookies;
    }

}
