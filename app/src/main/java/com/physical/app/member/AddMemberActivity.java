package com.physical.app.member;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
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
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * @author jjj
 *         版本：1.0
 *         创建日期：2019/5/25
 *         描述：新增会员信息
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
    private MemberVo data;
    private String id;

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
        data = ((MemberVo) getIntent().getSerializableExtra("data"));
        ivTitle.setImageResource(R.mipmap.word_add);
        datas = new ArrayList<>();
        adapter = new AddMemberAdapter(this, datas);
        gvData.setAdapter(adapter);

        addMemberPresenter = new AddMemberPresenter(this, this);
        addMemberPresenter.disease(getUserId());




    }

    private void addListener() {
        gvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicalHistory data = datas.get(position);
                adapter.selOrRemoveItem(data);
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
                showOutDay();
                break;
            case R.id.ll_birthday:
                showBirthDay();
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
        birthday = tvBirthday.getText().toString();
        out = tvOut.getText().toString();
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
        if (StringUtil.isEmpty(birthday)) {
            showToast("请输入出生日期");
            return;
        }
        if (StringUtil.isEmpty(out)) {
            showToast("请输入出诊日期");
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


        MemberVo memberVo = new MemberVo();
        memberVo.id = id;
        memberVo.name = name;
        memberVo.mobile = phone;
        memberVo.idCard = idCard;
        memberVo.sex = "" + sexId;//1男  2女
        long birth = StringUtil.stringToLong(birthday, "yyyy-MM-dd");
        long firstTime = StringUtil.stringToLong(out, "yyyy-MM-dd");
        memberVo.birthday = birth;
        memberVo.firstTime = firstTime;
        memberVo.height = high;
        memberVo.weight = weight;
        memberVo.totalMoney = totalmoney;
        memberVo.vipTimes = vipTimes;
        memberVo.medicalHistoryList =  adapter.getSelectList();
        Log.i("jjj", "confirm: " + toJson(memberVo));
        addMemberPresenter.save(toJson(memberVo), getUserId());

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
                if (null == data) {
                    totalmoney = money;
                    vipTimes = time;
                } else {
                    addMemberPresenter.charge(getWifiMac(), data.id, money, time, getUserId());
                }
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
    public void showBirthDay() {
        final DatePicker picker = new DatePicker(this);
        Calendar c = Calendar.getInstance();
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTextSize(26);
        picker.setCancelTextSize(26);
        picker.setSubmitTextSize(26);
        picker.setWidth(450);
        picker.setLabel("","","");
        picker.setTextPadding(ConvertUtils.toPx(this, 20));//加宽显示项
        picker.setGravity(Gravity.CENTER);//弹框居中
        picker.setTopPadding(ConvertUtils.toPx(this, 20));
        picker.setRangeEnd(2100, 1, 11);
        picker.setRangeStart(1900,1,1);
        picker.setSelectedItem(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                tvBirthday.setText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                tvBirthday.setText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                tvBirthday.setText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }
    /**
     * 出诊
     */
    public void showOutDay() {
        final DatePicker picker = new DatePicker(this);
        Calendar c = Calendar.getInstance();
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTextSize(26);
        picker.setCancelTextSize(26);
        picker.setSubmitTextSize(26);
        picker.setWidth(450);
        picker.setLabel("","","");
        picker.setTextPadding(ConvertUtils.toPx(this, 20));//加宽显示项
        picker.setGravity(Gravity.CENTER);//弹框居中
        picker.setTopPadding(ConvertUtils.toPx(this, 20));
        picker.setRangeEnd(2100, 1, 11);
        picker.setRangeStart(1900,1,1);
        picker.setSelectedItem(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                showToast(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                tvOut.setText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                tvOut.setText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                tvOut.setText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
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
        if (null != data) {
            addMemberPresenter.queryDetailById(data.id, getUserId());
        }
    }


    /**
     * 根据id查详情
     *
     * @param data
     */
    @Override
    public void onQueryDetailSuccess(MemberVo data) {
        id = data.id;
        etName.setText(data.name);
        etPhone.setText(data.mobile);
        etId.setText(data.idCard);
        if (data.sex.equals("1")) {
            selectSex(1);
        } else {
            selectSex(2);
        }
        tvBirthday.setText(StringUtil.longToSDate(data.birthday, "yyyy-MM-dd"));
        tvOut.setText(StringUtil.longToSDate(data.firstTime, "yyyy-MM-dd"));
        etHigh.setText("" + data.height);
        etHeight.setText("" + data.weight);
        for (MedicalHistory medicalHistory : datas) {//疾病史
            for (MedicalHistory history : data.medicalHistoryList) {//会员选中疾病史
                if (medicalHistory.name.equals(history.name)) {
                   adapter.selOrRemoveItem(medicalHistory);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onchargeSuccess() {
        showToast("充值成功");
    }


}
