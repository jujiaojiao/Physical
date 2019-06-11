package com.physical.app.physical;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.MemberManageAdapter;
import com.physical.app.adapter.PhysicalAdapter;
import com.physical.app.bean.MemberCaseVo;
import com.physical.app.bean.MemberVo;
import com.physical.app.bean.SeedlingBean;
import com.physical.app.callback.IPhysicalCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.utils.StringUtil;
import com.physical.app.member.MemberManageActivity;
import com.physical.app.presenter.PhysicalPresenter;

import java.util.ArrayList;

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

    @OnClick({R.id.ivBack,R.id.iv_on,R.id.iv_off,R.id.ivRight})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivRight:
                MemberManageActivity.start(this,"1");
                break;
            case R.id.iv_on:
                start();
                break;
            case R.id.iv_off:

                break;
        }
    }

    private void start(){
        String user = tvUser.getText().toString();
        if (StringUtil.isEmpty(user)){
            showToast("请选择会员");
            return;
        }
        if (seedling.size()==0){
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
        physicalPresenter.save(toJson(memberCaseVo),getUserId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101&&resultCode==RESULT_OK&&null!=data){
            seedling = (ArrayList<SeedlingBean>) data.getSerializableExtra("seedling");
            member = (MemberVo) data.getSerializableExtra("member");
            tvUser.setText(member.name);
        }
    }

    @Override
    public void onSaveSuccess() {

    }
}
