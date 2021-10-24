package priv.xin.xd.common.util;

import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.core.entity.Code;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class CodeUtil {

    /**
     * 获取当前分组下的code列表
     *
     * @param group
     * @return
     */
    public static List<Code> getByGroup(String group) {
        List<Code> codeList = new ArrayList<Code>();

        EnumSet<CodeEnum> codes = EnumSet.allOf(CodeEnum.class);
        if (StrUtil.isEmpty(group) || ListUtil.isEmpty(codes)) {
            return codeList;
        }

        for (CodeEnum code : codes) {
            if (group.equals(code.getGroup())) {
                Code element = new Code();
                element.setCodeGroup(code.getGroup());
                element.setCodeName(code.getName());
                element.setCodeValue(code.getValue());
                codeList.add(element);
            }
        }

        return codeList;
    }

    /**
     * 获取code的值
     *
     * @param group
     * @param value
     * @return
     */
    public static String getCodeName(String group, String value) {

        if (group == null || "".equals(group) || value == null || "".equals(value)) {
            return "";
        }

        List<Code> codes = getByGroup(group);
        if (codes != null && codes.size() > 0) {
            for (Code element : codes) {
                String codeName = element.getCodeName();
                String codeValue = element.getCodeValue();
                if (codeValue.equals(value)) {
                    return codeName;
                }
            }
        }
        return "";
    }

    /**
     * 获取code的名称
     *
     * @param group
     * @param name
     * @return
     */
    public static String getCodeValue(String group, String name) {

        if (group == null || "".equals(group) || name == null || "".equals(name)) {
            return "";
        }

        List<Code> codes = getByGroup(group);
        if (codes != null && codes.size() > 0) {
            for (Code element : codes) {
                String codeName = element.getCodeName();
                String codeValue = element.getCodeValue();
                if (codeName.equals(name)) {
                    return codeValue;
                }
            }
        }
        return "";
    }

    /**
     * 获取code
     *
     * @param group
     * @param value
     * @return
     */
    public static CodeEnum getCode(String group, String value) {

        EnumSet<CodeEnum> codes = EnumSet.allOf(CodeEnum.class);
        if (StrUtil.isEmpty(group) || StrUtil.isEmpty(value) || ListUtil.isEmpty(codes)) {
            return null;
        }

        for (CodeEnum code : codes) {
            if (group.equals(code.getGroup()) && value.equals(code.getValue())) {
                return code;
            }
        }
        return null;
    }

}
