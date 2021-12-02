package org.slf4j;

import org.slf4j.TtlMDCAdapter;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author ：lu
 * @date ：2021/11/20 16:18
 */
public class TtlMDCAdapterInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("111111111111111111111111111111111111");
        // 加载自定义的MDCAdapter
        TtlMDCAdapter.getInstance();
    }
}

