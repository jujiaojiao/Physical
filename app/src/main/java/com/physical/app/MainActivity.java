package com.physical.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.iv_head)
    ImageView ivHead;//头像
    @Bind(R.id.tv_username)
    TextView tvUsername;//用户名
    @Bind(R.id.ll_local_music)
    LinearLayout llLocalMusic;//本地音乐
    @Bind(R.id.ll_phsical)
    LinearLayout llPhsical;//理疗
    @Bind(R.id.ll_vip)
    LinearLayout llVip;//会员
    @Bind(R.id.ll_set)
    LinearLayout llSet;//设置
    @Bind(R.id.ll_qq_music)
    LinearLayout llQqMusic;//qq音乐
    @Bind(R.id.iv_menu)
    ImageView ivMenu;//菜单
    @Bind(R.id.iv_switch)
    ImageView ivSwitch;//门磁开关

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.i("jjj", "onCreate: ");
    }


    @OnClick({R.id.ll_local_music,R.id.ll_phsical,R.id.ll_vip,R.id.ll_set,R.id.ll_qq_music,R.id.iv_menu,R.id.iv_switch})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.ll_local_music:

                break;
            case R.id.ll_phsical:

                break;
            case R.id.ll_vip:

                break;
            case R.id.ll_set:

                break;
            case R.id.ll_qq_music:

                break;
            case R.id.iv_menu://菜单

                break;
            case R.id.iv_switch://门磁开关

            break;
        }
    }
}
