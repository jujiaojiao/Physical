package com.physical.app.physical;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.PhysicalAdapter;
import com.physical.app.bean.AddPhysicalBean;
import com.physical.app.bean.MemberCaseVo;
import com.physical.app.bean.MemberVo;
import com.physical.app.bean.SeedlingBean;
import com.physical.app.callback.IPhysicalCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.utils.NetworkUtils;
import com.physical.app.common.utils.StringUtil;
import com.physical.app.common.widget.CommentDialog;
import com.physical.app.member.MemberManageActivity;
import com.physical.app.presenter.PhysicalPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj
 * 时间:  2019/5/22
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 理疗
 */

public class PhysicalActivity extends BaseActivity implements IPhysicalCallback {
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.ivRight)
    ImageView ivRight;
    @Bind(R.id.rlTop)
    RelativeLayout rlTop;
    @Bind(R.id.tv_user)
    TextView tvUser;
    @Bind(R.id.gv_data1)
    GridView gvData1;
    @Bind(R.id.gv_data2)
    GridView gvData2;
    @Bind(R.id.gv_data3)
    GridView gvData3;
    @Bind(R.id.gv_data4)
    GridView gvData4;
    @Bind(R.id.gv_data5)
    GridView gvData5;
    @Bind(R.id.gv_data6)
    GridView gvData6;
    @Bind(R.id.gv_data7)
    GridView gvData7;
    @Bind(R.id.iv_title)
    ImageView ivTitle;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.iv_off)
    ImageView ivOff;
    @Bind(R.id.iv_on)
    ImageView ivOn;
    @Bind(R.id.tv_count)
    TextView tvCount;

    private PhysicalAdapter PhysicalAdapter1;
    private ArrayList<String> list1;
    private PhysicalAdapter PhysicalAdapter2;
    private ArrayList<String> list2;
    private PhysicalAdapter PhysicalAdapter3;
    private ArrayList<String> list3;
    private PhysicalAdapter PhysicalAdapter4;
    private ArrayList<String> list4;
    private PhysicalAdapter PhysicalAdapter5;
    private ArrayList<String> list5;
    private PhysicalAdapter PhysicalAdapter6;
    private ArrayList<String> list6;
    private PhysicalAdapter PhysicalAdapter7;
    private ArrayList<String> list7;
    private ArrayList<SeedlingBean> seedling;
    private MemberVo member;
    private String lampLight = "1";
    private String seedlingLight = "1";
    private String waveGuide = "1";
    private String ultrashortWave = "1";
    private String superaudible = "1";
    private String totalTime = "10";
    private String magneticIntensity;
    private PhysicalPresenter physicalPresenter;
    private String endTime;
    private String beginTime;
    private Thread thread;
    private Integer integer;
    private int id;
    private CommentDialog commentDialog;

    private int commentType;


    public static void start(Context context) {
        Intent intent = new Intent(context, PhysicalActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);
        ButterKnife.bind(this);
        initView();
        intiData();
        addListener();
    }

    private void intiData() {
        physicalPresenter = new PhysicalPresenter(this, this);

    }

    public void initView() {
        ivTitle.setImageResource(R.mipmap.word_phy);
        list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");
        list1.add("6");
        list1.add("7");
        list1.add("8");
        list1.add("9");
        PhysicalAdapter1 = new PhysicalAdapter(this, list1);
        gvData1.setAdapter(PhysicalAdapter1);

        list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");
        list2.add("4");
        list2.add("5");
        list2.add("6");
        list2.add("7");
        list2.add("8");
        list2.add("9");
        PhysicalAdapter2 = new PhysicalAdapter(this, list2);
        gvData2.setAdapter(PhysicalAdapter2);


        list3 = new ArrayList<>();
        list3.add("1");
        list3.add("2");
        list3.add("3");
        list3.add("4");
        list3.add("5");
        list3.add("6");
        list3.add("7");
        list3.add("8");
        list3.add("9");
        PhysicalAdapter3 = new PhysicalAdapter(this, list3);
        gvData3.setAdapter(PhysicalAdapter3);


        list4 = new ArrayList<>();
        list4.add("10");
        list4.add("20");
        list4.add("30");
        list4.add("40");
        list4.add("50");
        list4.add("60");
        list4.add("70");
        list4.add("80");
        list4.add("90");
        PhysicalAdapter4 = new PhysicalAdapter(this, list4);
        gvData4.setAdapter(PhysicalAdapter4);


        list5 = new ArrayList<>();
        list5.add("1");
        list5.add("2");
        list5.add("3");
        list5.add("4");
        list5.add("5");
        list5.add("6");
        list5.add("7");
        list5.add("8");
        list5.add("9");
        PhysicalAdapter5 = new PhysicalAdapter(this, list5);
        gvData5.setAdapter(PhysicalAdapter5);

        list6 = new ArrayList<>();
        list6.add("1");
        list6.add("2");
        list6.add("3");
        list6.add("4");
        list6.add("5");
        list6.add("6");
        list6.add("7");
        list6.add("8");
        list6.add("9");
        PhysicalAdapter6 = new PhysicalAdapter(this, list6);
        gvData6.setAdapter(PhysicalAdapter6);

        list7 = new ArrayList<>();
        list7.add("1");
        list7.add("2");
        list7.add("3");
        list7.add("4");
        list7.add("5");
        list7.add("6");
        list7.add("7");
        list7.add("8");
        list7.add("9");
        PhysicalAdapter7 = new PhysicalAdapter(this, list7);
        gvData7.setAdapter(PhysicalAdapter7);

    }

    private void addListener() {
        gvData1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lampLight = list1.get(position);
                PhysicalAdapter1.setcurrent(position);
//                PhysicalDetailActivity.start(PhysicalActivity.this);
            }
        });
        gvData2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seedlingLight = list2.get(position);
                PhysicalAdapter2.setcurrent(position);
            }
        });
        gvData3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                magneticIntensity = list3.get(position);
                PhysicalAdapter3.setcurrent(position);
            }
        });
        gvData4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                totalTime = list4.get(position);
                PhysicalAdapter4.setcurrent(position);
            }
        });
        gvData5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                superaudible = list5.get(position);

                PhysicalAdapter5.setcurrent(position);
            }
        });
        gvData6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ultrashortWave = list6.get(position);

                PhysicalAdapter6.setcurrent(position);
            }
        });
        gvData7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                waveGuide = list4.get(position);

                PhysicalAdapter7.setcurrent(position);
            }
        });
    }

    @OnClick({R.id.ivBack, R.id.iv_on, R.id.iv_off, R.id.ivRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivRight:
                MemberManageActivity.start(this, "1");
                break;
            case R.id.iv_on:
                start();
                break;
            case R.id.iv_off:
                finishPysical();
//                showCommentDialog();
                break;
        }
    }

    private void start() {
        String user = tvUser.getText().toString();
        if (StringUtil.isEmpty(user)) {
            showToast("请选择会员");
            return;
        }
        if (seedling.size() == 0) {
            showToast("请选择幼苗");
            return;
        }


        MemberCaseVo memberCaseVo = new MemberCaseVo();
        memberCaseVo.lampLight = lampLight;
        memberCaseVo.seedlingLight = seedlingLight;
        memberCaseVo.magneticIntensity = magneticIntensity;
        memberCaseVo.totalTime = totalTime;
        memberCaseVo.superaudible = superaudible;
        memberCaseVo.ultrashortWave = ultrashortWave;
        memberCaseVo.waveGuide = waveGuide;
        memberCaseVo.memberId = member.id;
        memberCaseVo.memberName = member.name;
        memberCaseVo.medicineInfoList = seedling;
        memberCaseVo.machineCode = getWifiMac();
        Log.i("jjj", "start: " + toJson(memberCaseVo));
        if (NetworkUtils.isNetworkAvailable(this)) {
            physicalPresenter.save(toJson(memberCaseVo), getUserId());
        } else {
            integer = Integer.valueOf(totalTime) * 60 * 1000;
            Message message = handler.obtainMessage(1);     // Message
            handler.sendMessageDelayed(message, 1000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && null != data) {
            seedling = (ArrayList<SeedlingBean>) data.getSerializableExtra("seedling");
            member = (MemberVo) data.getSerializableExtra("member");
            if (null != member) {
                tvUser.setText(member.name);
            }
        }
    }


    private String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// HH:mm:ss
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 新增
     */
    @Override
    public void onSaveSuccess(AddPhysicalBean bean) {
        id = bean.id;
        beginTime = getCurrentTime();
        physicalPresenter.start("" + id, beginTime, getUserId(), getWifiMac());
    }

    /**
     * 结束
     */
    @Override
    public void onFinishSuccess() {
        showToast("结束理疗");
        showCommentDialog();
    }


    private void startPysical() {
        showToast("开始理疗");
        integer = Integer.valueOf(totalTime) * 60 * 1000;
        Message message = handler.obtainMessage(1);     // Message
        handler.sendMessageDelayed(message, 1000);
    }

    private void finishPysical() {
        integer = 1000;
        Message message1 = handler.obtainMessage(1);     // Message
        handler.sendMessage(message1);
    }

    /**
     * 开始
     */
    @Override
    public void onStartSuccess() {
        //TODO 开始指令
        timer.start();
//        startPysical();
    }


    /**
     * 评价
     */
    @Override
    public void onCommentSuccess() {
        showToast("评价成功");
        this.finish();
    }

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    integer = integer - 1000;
                    tv_time.setText("" + formatTime(integer));
                    if (integer > 0) {
                        Message message = handler.obtainMessage(1);
                        handler.sendMessageDelayed(message, 1000);
                    } else {
                        tv_time.setText("00:00");
                        endTime = getCurrentTime();
                        if (NetworkUtils.isNetworkAvailable(PhysicalActivity.this)) {
                            physicalPresenter.finish("" + id, endTime, getUserId(), getWifiMac());
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 将毫秒转化为 分钟：秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public String formatTime(long millisecond) {
        int minute;//分钟
        int second;//秒数
        minute = (int) ((millisecond / 1000) / 60);
        second = (int) ((millisecond / 1000) % 60);
        if (minute < 10) {
            if (second < 10) {
                return "0" + minute + ":" + "0" + second;
            } else {
                return "0" + minute + ":" + second;
            }
        } else {
            if (second < 10) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }
        }
    }


    private void showCommentDialog() {
        commentDialog = new CommentDialog(this, new CommentDialog.Callback() {
            @Override
            public void onCallback(String param) {
                switch (param) {
                    case "非常满意":
                        commentType = 0;
                        break;
                    case "满意":
                        commentType = 1;

                        break;
                    case "一般":
                        commentType = 2;

                        break;
                    case "不满意":
                        commentType = 3;

                        break;
                }
                physicalPresenter.comment("" + id, "" + commentType, param, getUserId());
                commentDialog.dismiss();
            }
        });
        commentDialog.show();
    }


    CountDownTimer timer = new CountDownTimer(3000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            int time = (int) (millisUntilFinished / 1000);
            tvCount.setText(time + "");
            tvCount.setEnabled(false);
//            tvGetmsg.setBackgroundResource(R.drawable.);
        }

        @Override
        public void onFinish() {
            tvCount.setVisibility(View.GONE);
            startPysical();
        }
    };


    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

}

