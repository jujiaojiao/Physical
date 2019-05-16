package com.physical.app.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liangzixun on 2018/4/14.
 */

public class RegularUtils {
    public static String IDCARD="(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
    public static String MOBLEPHONE="^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
    public static String PASSWORD="^[0-9A-Za-z]{6,20}$";

    public static boolean isMobilePhone(String data) {
        Pattern p = Pattern.compile(MOBLEPHONE);
        Matcher m = p.matcher(data);
        return m.matches();
    }

    public static boolean isPasswordOk(String data) {
        Pattern p = Pattern.compile(PASSWORD);
        Matcher m = p.matcher(data);
        return m.matches();
    }
    public static boolean isIdCard(String data) {
        Pattern p = Pattern.compile(IDCARD);
        Matcher m = p.matcher(data);
        return m.matches();
    }

}
