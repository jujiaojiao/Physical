package com.physical.app.common.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者: liangzixun
 * 时间: 2017/9/8 11:38
 * 邮箱: liangzixun@eims.com.cn
 */
public class StringUtil {
    public static boolean isEmpty(String data) {
        if (data == null || "".equals(data)) {
            return true;
        }
        return false;
    }

    /**
     * 格式化钱的金额 保留2位小数
     *
     * @param data
     * @return
     */
    public static String formatMoney(String data) {
        if (data == null || "".equals(data)) {
            return "0.00";
        }
        float f = Float.parseFloat(data);
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String result = decimalFormat.format(f);//format 返回的是字符串
        return result;
    }


    public static String longToSDate(long currentTime, String formatType) {
        Date date = null; // long类型转成Date类型
        try {
            date = longToDate(currentTime, formatType);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }
}
