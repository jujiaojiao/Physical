package com.physical.app.common.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 作者：liangzixun
 * 时间：2018/6/16 11:42
 * 描述：
 */
public class KeyBoardUtil {
    /**
     * 打开软键盘
     * @param mContext  上下文
     */
    public static void openKeybord(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 关闭软键盘
     * @param mContext  上下文
     */
    public static void closeKeybord(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
