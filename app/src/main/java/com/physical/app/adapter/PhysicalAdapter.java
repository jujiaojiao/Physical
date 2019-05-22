package com.physical.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.physical.app.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jjj
 * 时间:  2019/5/22
 * 邮箱: jujiaojiao@gemdale.com
 * 描述: 理疗
 */

public class PhysicalAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<String> list;
    private Context context;

    private int current;

    public PhysicalAdapter(Context context, List<String> list) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_physical, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == 8) {
            holder.viewFlag.setVisibility(View.GONE);
        }

        if (current > position) {
            holder.ivFlag.setImageResource(R.mipmap.icon_strip_have);
            holder.viewFlag.setBackgroundColor(context.getResources().getColor(R.color.color_24ff00));
        }
        if (current == position) {
            holder.ivFlag.setImageResource(R.mipmap.icon_strip_have);
            holder.viewFlag.setBackgroundColor(context.getResources().getColor(R.color.color_052c48));
        }
        if (current < position) {
            holder.ivFlag.setImageResource(R.mipmap.icon_strip_none);
            holder.viewFlag.setBackgroundColor(context.getResources().getColor(R.color.color_052c48));
        }


        holder.tvNum.setText(list.get(position));
        return convertView;
    }

    public void setcurrent(int current) {
        this.current = current;
        notifyDataSetChanged();
    }


    static class ViewHolder {
        @Bind(R.id.iv_flag)
        ImageView ivFlag;
        @Bind(R.id.view_flag)
        View viewFlag;
        @Bind(R.id.tv_num)
        TextView tvNum;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
