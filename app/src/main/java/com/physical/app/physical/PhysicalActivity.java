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
import com.physical.app.adapter.PhysicalAdapter;
import com.physical.app.common.base.BaseActivity;

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

public class PhysicalActivity extends BaseActivity {
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
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
        addListener();
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
        list4.add("1");
        list4.add("2");
        list4.add("3");
        list4.add("4");
        list4.add("5");
        list4.add("6");
        list4.add("7");
        list4.add("8");
        list4.add("9");
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
                PhysicalAdapter1.setcurrent(position);
                PhysicalDetailActivity.start(PhysicalActivity.this);
            }
        });
        gvData2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhysicalAdapter2.setcurrent(position);
            }
        });
        gvData3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhysicalAdapter3.setcurrent(position);
            }
        });
        gvData4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhysicalAdapter4.setcurrent(position);
            }
        });
        gvData5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhysicalAdapter5.setcurrent(position);
            }
        });
        gvData6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhysicalAdapter6.setcurrent(position);
            }
        });
        gvData7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhysicalAdapter7.setcurrent(position);
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
