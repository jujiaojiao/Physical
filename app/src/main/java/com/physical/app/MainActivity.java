package com.physical.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.physical.app.common.widget.SystemSetDialog;
import com.physical.app.member.MemberManageActivity;
import com.physical.app.music.LocalMucicActivity;
import com.physical.app.setting.SettingActivity;

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
    private SystemSetDialog systemSetDialog;


    private int door = 0;//0 关  1开

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.i("jjj", "onCreate: ");
    }


    @OnClick({R.id.ll_local_music, R.id.ll_phsical, R.id.ll_vip, R.id.ll_set, R.id.ll_qq_music, R.id.iv_menu, R.id.iv_switch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_local_music:
                LocalMucicActivity.start(this);
                break;
            case R.id.ll_phsical:

                break;
            case R.id.ll_vip:
                MemberManageActivity.start(this);
                break;
            case R.id.ll_set:
                SettingActivity.start(this);
                break;
            case R.id.ll_qq_music:

                break;
            case R.id.iv_menu://菜单
                showSystemDilaog();
                break;
            case R.id.iv_switch://门磁开关
                if (door==0){
//                    ivSwitch.setImageResource();//开的图标
                    door=1;
                }else{
//                    ivSwitch.setImageResource();//关的图标
                    door=0;
                }
                break;
        }
    }

    private void showSystemDilaog() {
        systemSetDialog = new SystemSetDialog(this, new SystemSetDialog.Callback() {
            @Override
            public void onConfirm() {

            }

            @Override
            public void onCancel() {
                systemSetDialog.dismiss();
            }
        });
        systemSetDialog.show();
    }
}
