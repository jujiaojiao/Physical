package com.physical.app.setting.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.common.base.BaseFragment;
import com.physical.app.common.utils.StringUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jjj
 * 时间:  2019/5/20
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 设备报修
 */

public class RepairFragment extends BaseFragment {
    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    private String title;
    private String content;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repair, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {

    }

    @OnClick({R.id.tv_cancel,R.id.tv_confirm})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_cancel:

                break;
            case R.id.tv_confirm:
                title = etTitle.getText().toString().trim();
                content = etContent.getText().toString().trim();
                if (StringUtil.isEmpty(title)){
                    showToast("请输入标题");
                    return;
                }
                if (StringUtil.isEmpty(content)){
                    showToast("请输入内容");
                    return;
                }

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
