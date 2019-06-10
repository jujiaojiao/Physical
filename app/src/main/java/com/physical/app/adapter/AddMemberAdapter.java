package com.physical.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.bean.MedicalHistory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/25
 * 描述：
 */
public class AddMemberAdapter extends BaseAdapter{
    private Context context;
    private List<MedicalHistory> datas;
    private LayoutInflater inflater;
    private int current;
    private ArrayList<MedicalHistory> selectList;
    private Callback callback;

    public AddMemberAdapter(Context context, List<MedicalHistory> datas) {
        this.context = context;
        this.datas = datas;
        selectList = new ArrayList<>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
//        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_add_member, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
        MedicalHistory data = datas.get(position);

        if (selectList.contains(data)){
            holder.ivCheck.setImageResource(R.mipmap.choose_have);
        }else{
            holder.ivCheck.setImageResource(R.mipmap.choose_none);
        }
//        if (current==position){
//            if (data.select){
//                data.select = false;
//            }else{
//                data.select = true;
//            }
//        }
        holder.tvContent.setText(data.name);
        return convertView;
    }

    public void setcurrent(int current) {
        this.current = current;
        notifyDataSetChanged();
    }


    class ViewHolder {
        @Bind(R.id.iv_check)
        ImageView ivCheck;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    //单选添加或移除
    public void selOrRemoveItem(MedicalHistory data) {
        if (selectList.contains(data)) {
            selectList.remove(data);
        } else {
            selectList.add(data);
        }
//        if (selectList.size() == datas.size()) {
//            callback.onCallback(1);
//        } else {
//            callback.onCallback(0);
//        }
        notifyDataSetChanged();
    }


    //获取选中的数据集合
    public ArrayList<MedicalHistory> getSelectList() {
        return selectList;
    }



    //是否选中
    public boolean isCheckAll() {
        if (selectList.size() == datas.size()) {
            return true;
        } else {
            return false;
        }
    }
    //接口回调 当选中数据的集合与请求到的数据集合长度一致时,表示全部选中,回调给主页面的全选按钮,改变其状态
    public interface Callback {
        void onCallback(int param);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
