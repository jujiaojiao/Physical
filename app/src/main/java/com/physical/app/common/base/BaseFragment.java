package com.physical.app.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.physical.app.AppData;
import com.physical.app.common.utils.ToastUtil;
import com.physical.app.common.mine.bean.User;


/**
 * 作者: liangzixun
 * 时间: 2017/9/5 16:30
 * 邮箱: liangzixun@eims.com.cn
 */
public class BaseFragment extends Fragment {
    public Context mContext;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext=getActivity();
    }

    public void showToast(String msg){
        ToastUtil.show(msg);
//        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }


    public boolean userIsLogin(boolean startToLogin) {
        User user = AppData.getInstance().getUser();
        if(null == user){
            if(startToLogin){
//                startTo(LoginActivity.class);
            }
            return false;
        }
        return true;
    }
    public String getUserId() {
        return AppData.getInstance().getUserId();
    }
    public User getUser() {
        return AppData.getInstance().getUser();
    }


}
