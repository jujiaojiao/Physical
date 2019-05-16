package com.physical.app.common.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.physical.app.R;


/**
 * 作者: liangzixun
 * 时间: 2017/9/8 10:08
 * 邮箱: liangzixun@eims.com.cn
 */
public class ProgressDialog extends AlertDialog {
    private Context context;
    private LayoutInflater inflater;

    private TextView tvDes;

    public ProgressDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.progress_dialog, null);
        setContentView(view);
        setCanceledOnTouchOutside(false);
    }

    public void dismiss(){
        if(isShowing()){
            super.dismiss();
        }
    }
}
