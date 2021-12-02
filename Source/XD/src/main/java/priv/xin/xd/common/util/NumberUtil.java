package priv.xin.xd.common.util;

import java.math.BigDecimal;
import java.util.Random;

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

    /**
     * 获取随机数（包含最大最小值）
     *
     * @param min
     * @param max
     * @return
     */
    public static int random(int min, int max) {
        if (max < min) {
            return 0;
        }
        Random random = new Random(1);
        return random.nextInt(max - min + 1) + min;
    }

}
