package com.physical.app.common.sign;


import org.apaches.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;


/**
 * 提供的MD5加密码签名类
 *
 * @author admin
 */
public class MD5Util {
    public final static String DEFAULT_CHARSET = "UTF-8";

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param appSecret     密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String appSecret, String input_charset) {
        text = text + appSecret;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }

    /**
     * 签名字符串
     *
     * @param text          需要签名的字符串
     * @param sign          签名结果
     * @param appSecret     密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String appSecret, String input_charset) {
        text = text + appSecret;
        String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
        if (mysign.equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 默认返回验签值，字符集编码为：UTF-8
     *
     * @param text
     * @param sign
     * @param appSecret
     * @return
     */
    public static boolean verify(String text, String sign, String appSecret) {
        return verify(text, sign, appSecret, DEFAULT_CHARSET);
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

}