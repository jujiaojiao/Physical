package com.physical.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.bean.MemberManageBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/19
 * 描述：会员信息管理
 */
public class MemberManageAdapter extends BaseAdapter {
    private Activity context;
    private List<MemberManageBean> datas;
    private LayoutInflater inflater;

    public MemberManageAdapter(Activity context, List<MemberManageBean> datas) {
        this.context = context;
        this.datas = datas;
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_member_manage, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }


    class ViewHolder {
        @Bind(R.id.tv_num)
        TextView tvNum;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_sex)
        TextView tvSex;
        @Bind(R.id.tv_phone)
        TextView tvPhone;
        @Bind(R.id.tv_certi)
        TextView tvCerti;
        @Bind(R.id.tv_times)
        TextView tvTimes;
        @Bind(R.id.tv_charge)
        TextView tvCharge;
        @Bind(R.id.tv_change)
        TextView tvChange;
        @Bind(R.id.tv_watch)
        TextView tvWatch;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
