package com.physical.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.physical.app.common.base.BaseActivity;

public class RegisterActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}