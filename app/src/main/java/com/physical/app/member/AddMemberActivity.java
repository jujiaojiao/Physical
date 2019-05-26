package com.physical.app.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.AddMemberAdapter;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.widget.ComDialog;
import com.physical.app.common.widget.RechargeDialog;
import com.physical.app.physical.SelectSeedlingActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/25
 * 描述：新增会员信息
 */
public class AddMemberActivity extends BaseActivity {

    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.rlTop)
    RelativeLayout rlTop;
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_id)
    EditText etId;
    @Bind(R.id.ll_man)
    LinearLayout llMan;
    @Bind(R.id.ll_woman)
    LinearLayout llWoman;
    @Bind(R.id.iv_recharge)
    ImageView ivRecharge;
    @Bind(R.id.tv_birthday)
    TextView tvBirthday;
    @Bind(R.id.ll_birthday)
    LinearLayout llBirthday;
    @Bind(R.id.tv_out)
    TextView tvOut;
    @Bind(R.id.ll_out)
    LinearLayout llOut;
    @Bind(R.id.et_high)
    EditText etHigh;
    @Bind(R.id.et_height)
    EditText etHeight;
    @Bind(R.id.gv_data)
    GridView gvData;
    @Bind(R.id.iv_title)
    ImageView ivTitle;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.ll_bottome)
    LinearLayout llBottome;
    private AddMemberAdapter adapter;
    private ArrayList<String> datas;
    private RechargeDialog rechargeDialog;
    private ComDialog comDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, AddMemberActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        ButterKnife.bind(this);
        initView();
        addListener();
    }

    private void initView() {
        ivTitle.setImageResource(R.mipmap.word_add);
        datas = new ArrayList<>();
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        adapter = new AddMemberAdapter(this, datas);
        gvData.setAdapter(adapter);
    }

    private void addListener() {
        gvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setcurrent(position);
            }
        });
    }

    @OnClick({R.id.tv_confirm, R.id.tv_cancel, R.id.iv_recharge, R.id.ivBack})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                SelectSeedlingActivity.start(this);
                break;
            case R.id.tv_cancel:
                showComdialog();
                break;
            case R.id.iv_recharge:
                showRechargeDialog();
                break;
            case R.id.ivBack:
                finish();
                break;

        }
    }

    private void showComdialog() {
        comDialog = new ComDialog(this, "提示", "您的内容尚未保存，返回将丢失页面信息，确认返回吗？", new ComDialog.Callback() {
            @Override
            public void callback(int param) {

            }
        });
        comDialog.show();
    }

    private void showRechargeDialog() {
        rechargeDialog = new RechargeDialog(this, new RechargeDialog.Callback() {
            @Override
            public void onConfirm(String money, String time) {

            }

            @Override
            public void onCancel() {

            }
        });
        rechargeDialog.show();
    }
}
