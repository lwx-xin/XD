package priv.xin.xd.expansionService.crawler.taobao;

import org.openqa.selenium.Cookie;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 爬取淘宝商品
 *
 * @author ：lu
 * @date ：2021/10/16 15:22
 */
@Component
public class TaobaoProcessor implements PageProcessor {

    @Resource
    private JedisPool jedisPool;

    private Site site = Site.me()
            //设置编码
//            .setCharset("utf-8")
            //设置UserAgent
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
            //设置超时时间，单位是毫秒
            .setTimeOut(5000)
            //设置重试次数
            .setRetryTimes(3)
            //设置重试间隔时间，单位是毫秒
            .setRetrySleepTime(1000)
            //设置域名，需设置域名后，addCookie才可生效
            .setDomain("https://www.taobao.com")
            //添加一条cookie
            .addCookie("", "");

    @Override
    public void process(Page page) {
        String url = page.getUrl().toString();
        //支持css选择器，xpath，正则表达式
//        page.putField("taobao", page.getHtml().css("ul.service-bd li a").all());
        // 将抓取到的link添加到任务中
//        page.addTargetRequests(page.getHtml().css("ul.service-bd li").links().all());

//        page.putField("taobao", page.getHtml().css("ul.service-bd li a").all());
        System.err.println(page.getHtml());
    }

    @Override
    public Site getSite() {
        Set<Cookie> cookieSet = new TaoBaoLogin().Login();
        for (Cookie cookie : cookieSet) {
            site.addCookie(cookie.getName().toString(), cookie.getValue().toString());
        }
        return site;
    }

    public void startProcessor() {
        Spider spider = Spider.create(new TaobaoProcessor());
        spider.addUrl("https://s.taobao.com/search?spm=a21bo.jianhua.201867-main.10.392611d9qG3wsa&q=%E7%94%B7%E8%A3%85");
        spider.setScheduler(new RedisScheduler(jedisPool));
        spider.addPipeline(new TaobaoPipeline());
        spider.thread(5);
        spider.run();
//        spider.start();
    }

    public static void main(String[] args) {
        new TaobaoProcessor().startProcessor();
    }

}