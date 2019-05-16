package com.physical.app.common.utils;

import java.util.Calendar;

/**
 * 作者：liangzixun
 * 时间：2018/8/13 09:53
 * 描述：
 */
public class TimeUtil {

    public static boolean isToday(long time) {
        Calendar pre = Calendar.getInstance();
        pre.setTimeInMillis(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isYesterday(long time) {
        Calendar pre = Calendar.getInstance();
        pre.setTimeInMillis(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (diffDay == -1) {
                return true;
            }
        }
        return false;
    }


}
