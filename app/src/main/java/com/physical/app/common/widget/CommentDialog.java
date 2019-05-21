package com.physical.app.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.physical.app.R;
import com.physical.app.adapter.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/5/21
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 评价
 */

public class CommentDialog extends Dialog {
    private Context context;
    private GridView gvData;
    private CommentAdapter commentAdapter;

    private List<String> datas;
    public CommentDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        this.context = context;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comment);
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        gvData = ((GridView) findViewById(R.id.gv_data));

        datas = new ArrayList<>();
        commentAdapter = new CommentAdapter(context, datas);
        gvData.setAdapter(commentAdapter);

        gvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                commentAdapter.setcurrent(position);
            }
        });
    }
}
