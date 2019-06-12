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
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.CommentAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private Callback callback;
    private List<String> datas;
    private TextView tv_cancel;
    private TextView tv_confirm;
    private String data;

    public CommentDialog(@NonNull Context context,Callback callback) {
        super(context, R.style.dialog);
        this.context = context;
        this.callback = callback;

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
        tv_cancel = ((TextView) findViewById(R.id.tv_cancel));
        tv_confirm = ((TextView) findViewById(R.id.tv_confirm));

        datas = new ArrayList<>();
        datas.add("非常满意");
        datas.add("满意");
        datas.add("一般");
        datas.add("不满意");
        commentAdapter = new CommentAdapter(context, datas);
        gvData.setAdapter(commentAdapter);
        commentAdapter.setcurrent(0);
        data = datas.get(0);

        gvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                commentAdapter.setcurrent(position);
                data = datas.get(position);
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCallback(data);
            }
        });
    }


    public interface Callback{
        void onCallback(String param);
    }

}
