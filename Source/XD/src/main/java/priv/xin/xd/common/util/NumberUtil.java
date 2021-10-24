package priv.xin.xd.common.util;

import java.math.BigDecimal;

/**
 * @author ：lu
 * @date ：2021/9/26 14:19
 */
public class NumberUtil {

    public static BigDecimal add(Object a, Object b) {
        BigDecimal aBigDecimal = new BigDecimal(a.toString());
        BigDecimal bBigDecimal = new BigDecimal(b.toString());
        return aBigDecimal.add(bBigDecimal);
    }

    public static BigDecimal sub(Object a, Object b) {
        BigDecimal aBigDecimal = new BigDecimal(a.toString());
        BigDecimal bBigDecimal = new BigDecimal(b.toString());
        return aBigDecimal.subtract(bBigDecimal);
    }

}
