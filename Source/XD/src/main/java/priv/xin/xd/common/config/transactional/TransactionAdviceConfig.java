package priv.xin.xd.common.config.transactional;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;
import priv.xin.xd.common.util.JsonUtil;
import priv.xin.xd.common.util.ListUtil;
import priv.xin.xd.common.util.LoggerUtil;
import priv.xin.xd.expansionService.redis.service.ServiceLogger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 全局事物配置
 *
 * @author ：lu
 * @date ：2021/7/5 14:28
 * SUPPORTS ：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
 * MANDATORY ：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
 * REQUIRES_NEW ：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
 * NOT_SUPPORTED ：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
 * NEVER ：以非事务方式运行，如果当前存在事务，则抛出异常。
 * NESTED ：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于 REQUIRED 。
 * 指定方法：通过使用 propagation 属性设置，例如：@Transactional(propagation = Propagation.REQUIRED)
 * 参考文章；https://blog.csdn.net/schcilin/article/details/93306826
 */
@Aspect
@Configuration
public class TransactionAdviceConfig {

    //配置方法过期时间，默认-1,永不超时(单位：秒)
    private final static int TX_METHOD_TIME_OUT = -1;

    //配置切入点表达式,这里解释一下表达式的含义
    /**
     * 1.execution(): 表达式主体
     * 2.第一个*号:表示返回类型，*号表示所有的类型
     * 3.priv.xin.xd.core.service表示切入点的包名
     * 4.第二个*号:表示实现包
     * 5.*(..)*号表示所有方法名,..表示所有类型的参数
     * 6.多个切面使用'||'连接
     */
//    private static final String POINTCUT_EXPRESSION = "execution(* priv.xin.xd.core.service.*.*(..))"+
//            " || execution(* priv.xin.xd.common.config.transactional.*.*(..))";
    private static final String POINTCUT_EXPRESSION = "execution(* priv.xin.xd.core.service.*.*(..))";

    @Resource
    private PlatformTransactionManager platformTransactionManager;
    @Resource
    private ServiceLogger serviceLogger;

    @Bean
    public TransactionInterceptor txadvice() {
        // 事务管理规则
        // 只读事物、不做更新删除等
        RuleBasedTransactionAttribute readOnlyRule = new RuleBasedTransactionAttribute();
        // 设置当前事务是否为只读事务，true为只读
        readOnlyRule.setReadOnly(true);
        // transactiondefinition.PROPAGATION_REQUIRED 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中
        readOnlyRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        RuleBasedTransactionAttribute requireRule = new RuleBasedTransactionAttribute();
        /*抛出异常后执行切点回滚*/
        requireRule.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        /*PROPAGATION_REQUIRED:事务隔离性为1，若当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。 */
        requireRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /*设置事务失效时间，超过10秒,可根据hytrix，则回滚事务*/
        requireRule.setTimeout(TX_METHOD_TIME_OUT);

//        RuleBasedTransactionAttribute requireNewRule = new RuleBasedTransactionAttribute();
//        /*抛出异常后执行切点回滚*/
//        requireNewRule.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
//        /*PROPAGATION_REQUIRES_NEW:会新开启事务，外层事务不会影响内部事务的提交/回滚。 */
//        requireNewRule.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        /*设置事务失效时间，超过10秒,可根据hytrix，则回滚事务*/
//        requireNewRule.setTimeout(TX_METHOD_TIME_OUT);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        // 根据阿里巴巴Java开发手册上建议HashMap初始化时设置已知的大小，如果不超过16个，那么设置成默认大小16：
        Map<String, TransactionAttribute> nameMap = new HashMap<>(16);
        nameMap.put("add*", requireRule);
        nameMap.put("save*", requireRule);
        nameMap.put("insert*", requireRule);
        nameMap.put("update*", requireRule);
        nameMap.put("delete*", requireRule);
        nameMap.put("remove*", requireRule);
        nameMap.put("set*", requireRule);
        /*进行批量操作时*/
        nameMap.put("batch*", requireRule);
        nameMap.put("get*", readOnlyRule);
        nameMap.put("query*", readOnlyRule);
        nameMap.put("find*", readOnlyRule);
        nameMap.put("select*", readOnlyRule);
        nameMap.put("count*", readOnlyRule);

//        nameMap.put("saveFile", requireNewRule);
        source.setNameMap(nameMap);
        return new TransactionInterceptor(platformTransactionManager, source);
    }

    /**
     * 设置切面=切点pointcut+通知TxAdvice
     *
     * @return
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txadvice());
    }

    @Pointcut(POINTCUT_EXPRESSION)
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        String signature = point.getSignature().toString();
        // 输入参数
        Object[] input = point.getArgs();
        // 调用方法并接收返回结果
        Object output = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        String inputStr = "";
        if (ListUtil.isNotEmpty(input)) {
            inputStr = JsonUtil.toJson(input);
        }

        String outputStr = "";
        if (output != null) {
            outputStr = JsonUtil.toJson(output);
        }

        //保存日志
        String log = "执行方法:" + signature +
                "\n输入参数:" + inputStr +
                "\n返回结果:" + outputStr +
                "\n执行时长(毫秒):" + time;
        LoggerUtil.info(log, this.getClass());

        String userId = "";
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject.isAuthenticated()) {
                userId = (String) subject.getPrincipal();
            }
        } catch (Exception e) {
            LoggerUtil.error(e, this.getClass());
        }
        serviceLogger.addLog(userId, log);

        return output;
    }
}
