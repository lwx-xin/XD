package priv.xin.xd.common.util.excel;

import java.lang.annotation.*;

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
@Target(value = ElementType.FIELD)
public @interface CellStyleCode {

    String typeCode() default "default";

}
