package com.physical.app.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.LocalMusicAdapter;
import com.physical.app.bean.MusicBean;
import com.physical.app.common.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/19
 * 描述：本地音乐
 */
public class LocalMucicActivity extends BaseActivity {
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv_music)
    ImageView ivMusic;//音乐配图
    @Bind(R.id.tv_music_name)
    TextView tvMusicName;//音乐名称
    @Bind(R.id.tv_name)
    TextView tvName;//歌手
    @Bind(R.id.pv_music)
    ProgressBar pvMusic;//音乐进度条
    @Bind(R.id.iv_prev)
    ImageView ivPrev;//上一首
    @Bind(R.id.iv_pause)
    ImageView ivPause;//暂停
    @Bind(R.id.iv_next)
    ImageView ivNext;//下一首
    @Bind(R.id.pb_voice)
    ProgressBar pbVoice;//音量
    @Bind(R.id.iv_single)
    ImageView ivSingle;//单曲循环
    @Bind(R.id.iv_loop)
    ImageView ivLoop;//循环列表
    @Bind(R.id.iv_random)
    ImageView ivRandom;//随机播放
    @Bind(R.id.lv_data)
    ListView lvData;
    @Bind(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    private LocalMusicAdapter adapter;
    private ArrayList<MusicBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_mucic);
        ButterKnife.bind(this);
        initView();
        addListener();
    }

    private void initView(){

        list = new ArrayList<>();
        list.add(new MusicBean());
        list.add(new MusicBean());
        list.add(new MusicBean());
        list.add(new MusicBean());
        list.add(new MusicBean());
        adapter = new LocalMusicAdapter(this, list);
        lvData.setAdapter(adapter);
    }

    private void addListener(){
        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @OnClick({R.id.ivBack})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

}
