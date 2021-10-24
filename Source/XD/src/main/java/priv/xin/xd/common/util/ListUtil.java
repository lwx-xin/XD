package priv.xin.xd.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListUtil {

    /**
     * 判断list是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(Collection<?> list) {
        boolean returnValue = false;
        if (list == null || list.size() == 0) {
            returnValue = true;
        } else {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * 判断list是否为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(Collection<?> list) {
        return !isEmpty(list);
    }

    /**
     * 判断list是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        boolean returnValue = false;
        if (array == null || array.length == 0) {
            returnValue = true;
        } else {
            returnValue = false;
        }
        return returnValue;
    }

    /**
     * 判断list是否为空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 交集
     *
     * @param a [A,G,L,C]
     * @param b [A,B,C]
     * @return [A, C]
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List intersection(List a, List b) {
        if (ListUtil.isEmpty(a) || ListUtil.isEmpty(b)) {
            return new ArrayList<>();
        } else {
            List copyA = new ArrayList<>();
            copyA.addAll(a);

            copyA.retainAll(b);
            return copyA;
        }
    }

    /**
     * 差集
     *
     * @param a [A,G,L,C]
     * @param b [A,B,C]
     * @return [G, L]
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static List except(List a, List b) {
        if (ListUtil.isEmpty(a) && ListUtil.isEmpty(b)) {
            return new ArrayList<>();
        } else if (ListUtil.isEmpty(a)) {
            return b;
        } else if (ListUtil.isEmpty(b)) {
            return a;
        } else {
            List copyA = new ArrayList<>();
            copyA.addAll(a);

            copyA.removeAll(b);
            return copyA;
        }
    }


}
