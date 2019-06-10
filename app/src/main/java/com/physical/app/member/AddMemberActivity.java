package com.physical.app.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.physical.app.R;
import com.physical.app.adapter.AddMemberAdapter;
import com.physical.app.bean.MedicalHistory;
import com.physical.app.bean.MemberManageBean;
import com.physical.app.bean.MemberVo;
import com.physical.app.callback.IAddMemberCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.utils.StringUtil;
import com.physical.app.common.widget.ComDialog;
import com.physical.app.common.widget.RechargeDialog;
import com.physical.app.physical.SelectSeedlingActivity;
import com.physical.app.presenter.AddMemberPresenter;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/25
 * 描述：新增会员信息
 */
public class AddMemberActivity extends BaseActivity implements IAddMemberCallback {

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
    @Bind(R.id.iv_man)
    ImageView iv_man;
    @Bind(R.id.iv_woman)
    ImageView iv_woman;
    private AddMemberAdapter adapter;
    private List<MedicalHistory> datas;
    private RechargeDialog rechargeDialog;
    private ComDialog comDialog;
    private AddMemberPresenter addMemberPresenter;
    private String name;
    private String phone;
    private String idCard;
    private String high;
    private String weight;
    private String totalmoney;

    private int sexId = 1;
    private String vipTimes;
    private String birthday;//出生日期
    private String out;//出诊日期
    private MemberManageBean data;

    public static void start(Context context, MemberVo data) {
        Intent intent = new Intent(context, AddMemberActivity.class);
        intent.putExtra("data", data);
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
        data = ((MemberManageBean) getIntent().getSerializableExtra("data"));
        ivTitle.setImageResource(R.mipmap.word_add);
        datas = new ArrayList<>();
        adapter = new AddMemberAdapter(this, datas);
        gvData.setAdapter(adapter);

        if (null != data) {
            etName.setText(data.name);
            etPhone.setText(data.mobile);
            etId.setText(data.idCard);
            if (data.sex == 1) {
                selectSex(1);
            } else {
                selectSex(2);
            }
            tvBirthday.setText(StringUtil.longToSDate(data.birthday, "yyyy-MM-dd"));
            tvOut.setText(StringUtil.longToSDate(data.firstTime, "yyyy-MM-dd"));
            etHigh.setText(""+data.height);
            etHeight.setText(""+data.weight);
        } else {

        }

        addMemberPresenter = new AddMemberPresenter(this, this);
        addMemberPresenter.disease(getUserId());

    }

    private void addListener() {
        gvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setcurrent(position);
            }
        });
        etName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etName.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
        etPhone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etPhone.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
        etId.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etId.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
        etHigh.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etHigh.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
        etHeight.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 监听到回车键，会执行2次该方法。按下与松开
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        //松开事件
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(etHeight.getWindowToken(), 0); //强制隐藏键盘
                    }
                }
                return false;

            }
        });
    }


    @OnClick({R.id.tv_confirm, R.id.tv_cancel, R.id.iv_recharge, R.id.ivBack, R.id.ll_man, R.id.ll_woman,
            R.id.ll_out, R.id.ll_birthday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
//                SelectSeedlingActivity.start(this);
                confirm();
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
            case R.id.ll_man:
                selectSex(1);
                break;
            case R.id.ll_woman:
                selectSex(2);
                break;
            case R.id.ll_out:
                showDataOutPicker();
                break;
            case R.id.ll_birthday:
                showDatabirthPicker();
                break;

        }
    }

    private void selectSex(int sex) {
        if (sex == 1) {
            sexId = 1;
            iv_man.setImageResource(R.mipmap.icon_circle_have);
            iv_woman.setImageResource(R.mipmap.icon_circle_none);
        } else {
            sexId = 2;
            iv_woman.setImageResource(R.mipmap.icon_circle_have);
            iv_man.setImageResource(R.mipmap.icon_circle_none);
        }
    }

    private void confirm() {
        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        idCard = etId.getText().toString();
        high = etHigh.getText().toString();
        weight = etHeight.getText().toString();
        if (StringUtil.isEmpty(name)) {
            showToast("请输入会员姓名");
            return;
        }
        if (StringUtil.isEmpty(phone)) {
            showToast("请输入手机号码");
            return;
        }
        if (StringUtil.isEmpty(idCard)) {
            showToast("请输入身份证号码");
            return;
        }
        if (StringUtil.isEmpty(high)) {
            showToast("请输入会员身高");
            return;
        }
        if (StringUtil.isEmpty(weight)) {
            showToast("请输入会员体重");
            return;
        }
//        if (StringUtil.isEmpty(name)){
//            showToast("请输入会员姓名");
//            return;
//        }

        MemberVo memberVo = new MemberVo();
        memberVo.name = name;
        memberVo.mobile = phone;
        memberVo.idCard = idCard;
        memberVo.sex = "" + sexId;//1男  2女
        memberVo.birthday = birthday;
        memberVo.firstTime = out;
        memberVo.height = high;
        memberVo.weight = weight;
        memberVo.totalMoney = totalmoney;
        memberVo.vipTimes = vipTimes;
        List<MedicalHistory> selects = new ArrayList<>();
        for (MedicalHistory data : datas) {
            if (data.select) {
                selects.add(data);
            }
        }
        Log.i("jjj", "confirm: "+toJson(memberVo));
        memberVo.medicalHistoryList = selects;
//        addMemberPresenter.save(toJson(memberVo), getUserId());

    }


    private void showComdialog() {
        comDialog = new ComDialog(this, "提示", "您的内容尚未保存，返回将丢失页面信息，确认返回吗？", new ComDialog.Callback() {
            @Override
            public void callback(int param) {
                comDialog.dismiss();
                finish();
            }
        });
        comDialog.show();
    }

    private void showRechargeDialog() {
        rechargeDialog = new RechargeDialog(this, new RechargeDialog.Callback() {

            @Override
            public void onConfirm(String money, String time) {
                totalmoney = money;
                vipTimes = time;
                String mobile = etPhone.getText().toString();
                if (StringUtil.isEmpty(mobile)) {
                    showToast("请输入手机号码");
                    return;
                }
                addMemberPresenter.charge(getWifiMac(), mobile, money, time, getUserId());
                rechargeDialog.dismiss();
            }

            @Override
            public void onCancel() {
                rechargeDialog.dismiss();
            }
        });
        rechargeDialog.show();
    }


    /**
     * 生日
     */
    private void showDatabirthPicker() {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //当开始时间已选中时，结束时间不能早于开始时间
        if (StringUtil.isEmpty(tvBirthday.getText().toString())) {
//            startDate.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar
//                    .DAY_OF_MONTH));
            startDate.set(1970, 01, 01);
        } else {
            //设置开始年份
            String[] times = tvBirthday.getText().toString().split("-");
            Integer startYear = Integer.valueOf(times[0]);
            Integer startMonth = Integer.valueOf(times[1]) - 1;
            Integer startDay = Integer.valueOf(times[2]);
            startDate.set(startYear, startMonth, startDay);
        }
        Calendar endDate = Calendar.getInstance();
        //设置结束年份
        endDate.set(2030, 0, 1);
        //设置结束年份
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(AddMemberActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                birthday = format.format(date);
                tvBirthday.setText(format.format(date));
            }
        }).setRangDate(startDate, endDate).build();
        pvTime.show();

    }

    /**
     * 出诊
     */
    private void showDataOutPicker() {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //当开始时间已选中时，结束时间不能早于开始时间
        if (StringUtil.isEmpty(tvOut.getText().toString())) {
            startDate.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar
                    .DAY_OF_MONTH));
//            startDate.set(1970, 01, 01);
        } else {
            //设置开始年份
            String[] times = tvOut.getText().toString().split("-");
            Integer startYear = Integer.valueOf(times[0]);
            Integer startMonth = Integer.valueOf(times[1]) - 1;
            Integer startDay = Integer.valueOf(times[2]);
            startDate.set(startYear, startMonth, startDay);
        }
        Calendar endDate = Calendar.getInstance();
        //设置结束年份
        endDate.set(2030, 0, 1);
        //设置结束年份
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(AddMemberActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                out = format.format(date);
                tvOut.setText(format.format(date));
            }
        }).setRangDate(startDate, endDate).build();
        pvTime.show();

    }


    /**
     * 新增会员
     */
    @Override
    public void onSaveSuccess() {
        showToast("新增成功");
        finish();
    }

    /**
     * 疾病史
     */
    @Override
    public void onDiseaseSuccess(List<MedicalHistory> bean) {
        if (null != bean && bean.size() > 0) {
            datas.addAll(bean);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onchargeSuccess() {
        showToast("充值成功");
    }
}
