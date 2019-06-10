package com.physical.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.bean.MemberManageBean;
import com.physical.app.bean.MemberVo;
import com.physical.app.common.utils.StringUtil;

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
    private List<MemberVo> datas;
    private LayoutInflater inflater;
    private String type;

    private Callback callback;

    public MemberManageAdapter(Activity context, List<MemberVo> datas, String type, Callback callback) {
        this.context = context;
        this.datas = datas;
        this.type = type;
        this.callback = callback;
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

        if (StringUtil.isEmpty(type)) {
            holder.ivChoose.setVisibility(View.GONE);
            holder.tvCharge.setVisibility(View.VISIBLE);
            holder.tvChange.setVisibility(View.VISIBLE);
        } else {
            holder.ivChoose.setVisibility(View.VISIBLE);
            holder.tvCharge.setVisibility(View.GONE);
            holder.tvChange.setVisibility(View.GONE);
        }
        MemberVo data = datas.get(position);
        holder.tvNum.setText("" + (position + 1));
        holder.tvName.setText(data.name);
        if (data.sex .equals("1")) {
            holder.tvSex.setText("男");
        } else {
            holder.tvSex.setText("女");
        }
        holder.tvPhone.setText(data.mobile);
        holder.tvCerti.setText(data.idCard);
        holder.tvTimes.setText("" + data.usedTime);
        holder.tvCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.recharge(position);
            }
        });
        holder.tvWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onwatch(position);

            }
        });
        holder.tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onchange(position);

            }
        });
        return convertView;
    }




    class ViewHolder {
        @Bind(R.id.iv_choose)
        ImageView ivChoose;
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


    public interface  Callback{
        void recharge(int position);
        void onchange(int position);
        void onwatch(int position);
    }
}
