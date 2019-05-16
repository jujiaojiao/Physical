package com.physical.app.common.utils;

import java.text.DecimalFormat;

/**
 * 作者: liangzixun
 * 时间: 2017/9/8 11:38
 * 邮箱: liangzixun@eims.com.cn
 */
public class StringUtil {
    public static boolean isEmpty(String data){
        if (data==null||"".equals(data)){
            return true;
        }
        return false;
    }

    /**
     * 格式化钱的金额 保留2位小数
     * @param data
     * @return
     */
    public static String formatMoney(String data){
        if (data==null||"".equals(data)){
            return "0.00";
        }
        float f= Float.parseFloat(data);
        DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String result=decimalFormat.format(f);//format 返回的是字符串
        return result;
    }
}
