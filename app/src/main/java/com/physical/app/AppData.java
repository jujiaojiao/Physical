package com.physical.app;

import android.text.TextUtils;

import com.physical.app.common.constains.Constains;
import com.physical.app.common.utils.Base64Helper;
import com.physical.app.common.utils.Preferences;
import com.physical.app.common.mine.bean.User;


public class AppData {

    private static volatile AppData appData;

    private AppData() {
    }
    public static AppData getInstance() {
        if (null == appData) {
            synchronized (AppData.class) {
                if (null == appData) {
                    appData = new AppData();
                }
            }
        }
        return appData;
    }


    /**
     * 登录成功保存的用户信息
     **/
    private User user;


    public void setUser(User user) {

        if(null != user) {
            //将user保存到本地
            String userBase64 = Base64Helper.encode(user);
            Preferences.putString(Constains.USER, userBase64);
            this.user = user;
        }
    }


    /**
     * 退出登录
     */
    public void logoutClearData() {
        //清空缓存
        user = null;
        //清空用户名，密码
        Preferences.removeKey(Constains.USER);

    }


    /**
     * 获取用户信息
     *
     * @return
     */
    public User getUser() {
        if (null != user) {
            return user;
        }
        //判断本地用户json数据是否存在(这一步判断也是又必要的，user在缓存有可能被free掉，所以)
        String userBase64 = Preferences.getString(Constains.USER);
        if (!TextUtils.isEmpty(userBase64)) {
            Object object = Base64Helper.decode(userBase64);
            User temp = (null != object ? (User)object : null);
            if (null != temp && !TextUtils.isEmpty(temp.id)) {

                //登录成功
                user = temp;
            }
        }
        return user;
    }

    public boolean isLogin() {
        //判断缓存用户是否存在
        if (null != user) {
            return true;
        }
        return false;
    }


    /**
     * 返回用户id
     *
     * @return
     */
    public String getUserId() {
        User user = getUser();
        if (null != user) {
            return user.id;
        }
        return "";
    }

}
