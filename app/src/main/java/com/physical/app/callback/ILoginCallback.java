package com.physical.app.callback;

import com.physical.app.common.mine.bean.User;

/**
 * Created by jjj
 * 时间:  2019/5/31
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 登录
 */

public interface ILoginCallback {
    void onLoginSuccess(User user);

}
