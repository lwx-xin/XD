package priv.xin.xd.expansionService.crawler.taobao;


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import priv.xin.xd.common.properties.CrawlerProperties;
import priv.xin.xd.common.util.NumberUtil;
import priv.xin.xd.common.util.SpringUtil;

import java.util.*;

/**
 * 模拟淘宝登录
 *
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

        ChromeOptions options = new ChromeOptions();
        // 此步骤很重要，设置为开发者模式，防止被各大网站识别出来使用了Selenium
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        // 不加载图片,加快访问速度
        options.setExperimentalOption("prefs", Collections.singletonMap("Collections.singletonMap()", 2));

//        options.addArguments("--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");
//        options.setExperimentalOption("detach", true);
//        options.setHeadless(false);
//        options.addArguments("--disable-blink-features");
//        options.addArguments("--disable-blink-features=AutomationControlled");

        ChromeDriver driver = new ChromeDriver();
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("source", "Object.defineProperty(navigator,'webdriver',{get: ()=> false,});");
//        ((JavascriptExecutor)driver).executeScript("Page.addScriptToEvaluateOnNewDocument", params);
        ((JavascriptExecutor)driver).executeScript("Object.defineProperty(navigator,'webdriver',{get: ()=> false,});");
        driver.get(TAOBAO_LOGIN_HTML);

        // 防止页面未能及时加载出来而设置一段时间延迟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.cssSelector(TAOBAO_USER_NUMBER_INPUT)).sendKeys(username);
        driver.findElement(By.cssSelector(TAOBAO_USER_PASSWORD_INPUT)).sendKeys(password);

        // 防止页面未能及时加载出来而设置一段时间延迟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 滑动验证
        WebDriver frame = driver.switchTo().frame("baxia-dialog-content");
        WebElement area = frame.findElement(By.id("nc_1_n1t"));// 滑动区域
        WebElement block = frame.findElement(By.id("nc_1_n1z"));// 滑动按钮
        if (area != null && block != null) {
            int x = area.getSize().getWidth();
//            int y = area.getSize().getHeight();
            Actions action = new Actions(driver);
            int min = 10;
            int max = 50;
            while (x > 0) {
                int random = NumberUtil.random(min, max);
                action.clickAndHold(block).moveByOffset(random, 0).perform();
                x = max - random;
                try {
                    Thread.sleep(NumberUtil.random(100, 300));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //拖动完释放鼠标
            action.moveToElement(block).release();
            Action actions = action.build();
            actions.perform();
        }

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
