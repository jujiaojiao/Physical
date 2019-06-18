
package com.physical.app.common.utils;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5密码加密算法
 */
public class Md5Util {

    private static final int LO_BYTE = 0x0f;
    private static final int MOVE_BIT = 4;
    private static final int HI_BYTE = 0xf0;
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 防止被构建.
     */
    private Md5Util() {
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */

    private static String byteArrayToHexString(byte[] b) {
        StringBuilder buf = new StringBuilder();
        for (byte aB : b) {
            buf.append(byteToHexString(aB));
        }
        return buf.toString();
    }

    /**
     * 字节转成字符.
     *
     * @param b 原始字节.
     * @return 转换后的字符.
     */
    private static String byteToHexString(byte b) {
        return HEX_DIGITS[(b & HI_BYTE) >> MOVE_BIT] + HEX_DIGITS[b & LO_BYTE];
    }

    /**
     * 进行加密.
     *
     * @param origin 原始字符串.
     * @return 加密后的结果.
     */
    public static String encode(String origin) {
        if (origin == null) {
            throw new IllegalArgumentException("加密传入参数不正确!");
        }
        String resultString = origin;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        } catch (NoSuchAlgorithmException e) {
//            throw new BizException("没有MD5算法", e);
        }

        return resultString;
    }

}
