package com.physical.app.common.utils;

import android.widget.Toast;

import com.physical.app.MyApplication;


/**
 * 作者: liangzixun
 * 时间: 2017/9/8 09:22
 * 邮箱: liangzixun@eims.com.cn
 */
public class ToastUtil {
    private static Toast mToast;

    private static void init(){
        mToast= Toast.makeText(MyApplication.context,"", Toast.LENGTH_SHORT);
    }

    public static void show(String tip){
        if (mToast==null){
            init();
        }
        mToast.setText(tip);
        mToast.show();
    }
}
