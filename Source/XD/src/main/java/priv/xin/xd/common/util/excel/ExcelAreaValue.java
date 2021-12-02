package priv.xin.xd.common.util.excel;

import java.lang.annotation.*;
import java.lang.reflect.Field;

@Documented
@Retention(RetentionPolicy.RUNTIME)
/**
 * 取值(ElementType)有：
 * 1.CONSTRUCTOR:用于描述构造器
 * 2.FIELD:用于描述域
 * 3.LOCAL_VARIABLE:用于描述局部变量
 * 4.METHOD:用于描述方法
 * 5.PACKAGE:用于描述包
 * 6.PARAMETER:用于描述参数
 * 7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
 */
@Target(ElementType.FIELD)
public @interface ExcelAreaValue {

    /**
     * sheet名字
     *
     * @return
     */
    String sheet() default "";

    /**
     * 导出excel时使用
     * 行<br>
     * 最小值：1<br>
     * 例：{5}或者5 第五行 —> 第五行,等同于{5,5}<br>
     * 例：{5,10} 第五行 —> 第十行<br>
     * 例：{5,-1} 第五行 —> 最后一行<br>
     * 例：{-1,-1} 第一行 —> 最后一行(默认值)
     *
     * @return
     */
    int[] rowArea() default {-1, -1};

    /**
     * 导出excel时使用
     * 列<br>
     * 最小值：1
     * 例：{5}或者5 第五列 —> 第五列,等同于{5,5}<br>
     * 例：{5,10} 第五列 —> 第十列<br>
     * 例：{5,-1} 第五列 —> 最后一列<br>
     * 例：{-1,-1} 第一列 —> 最后一列(默认值)
     *
     * @return
     */
    int[] columnArea() default {-1, -1};


}

