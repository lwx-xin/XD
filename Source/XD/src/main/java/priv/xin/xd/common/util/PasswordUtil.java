package priv.xin.xd.common.util;

import priv.xin.xd.common.entity.CommonConst;

/**
 * @author ：lu
 * @date ：2021/6/1 14:25
 */
public class PasswordUtil {

    /**
     * AES 加密
     * @param password
     *         未加密的密码
     * @param userNumber
     *         盐值，默认使用账号就可
     * @return
     * @throws Exception
     */
    public static String encrypt(String password, String userNumber) throws Exception {
        // 获取加密的秘钥
        String md5Salt = MD5Util.MD5(userNumber + CommonConst.SHIRO_SALT);
        return AESUtil.encrypt(md5Salt, password);
    }

    /**
     * AES 解密
     * @param encryptPassword
     *         加密后的密码
     * @param userNumber
     *         盐值，默认使用账号就可
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptPassword, String userNumber) throws Exception {
        // 获取加密的秘钥
        String md5Salt = MD5Util.MD5(userNumber + CommonConst.SHIRO_SALT);
        return AESUtil.decrypt(md5Salt, encryptPassword);
    }

    public static void main(String[] args) throws Exception {
        String encrypt = PasswordUtil.encrypt("admin", "admin");
        System.err.println(encrypt);
    }
}
