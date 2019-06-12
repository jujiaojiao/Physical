package com.physical.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.bean.MemberCardVo;
import com.physical.app.bean.MemberDetailRecordBean;
import com.physical.app.common.utils.StringUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/25
 * 描述：会员详情-诊疗记录
 */
public class MemberDetailRecordAdapter extends BaseAdapter {
    private Context context;
    private List<MemberCardVo> datas;
    private LayoutInflater inflater;
    private int current;

    public MemberDetailRecordAdapter(Context context, List<MemberCardVo> datas) {
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
            convertView = inflater.inflate(R.layout.item_member_detail_record, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MemberCardVo data = datas.get(position);


        holder.tvNum.setText(""+(position+1));
        holder.tvTime.setText(StringUtil.longToSDate(data.createTime,"yyyy-MM-dd HH:mm"));
        holder.tvArea.setVisibility(View.GONE);
        StringBuffer choose = new StringBuffer();
        for (int i = 0; i < data.medicineInfoList.size(); i++) {
            if (data.medicineInfoList.size()-1 ==i){
                choose.append(data.medicineInfoList.get(i).name);
            }else{
                choose.append(data.medicineInfoList.get(i).name+",");
            }
        }
        holder.tvChoose.setText(choose.toString());
        if (current == position) {
            holder.tvDetail.setVisibility(View.VISIBLE);
            holder.tvDetail.setText(choose);
            holder.ivChoose.setImageResource(R.mipmap.icon_ins);
        }else{
            holder.tvDetail.setVisibility(View.GONE);
            holder.ivChoose.setImageResource(R.mipmap.icon_plus);
        }
        return convertView;
    }

    public void setCurrent(int current) {
        this.current = current;
        notifyDataSetChanged();
    }

    class ViewHolder {
        @Bind(R.id.iv_choose)
        ImageView ivChoose;
        @Bind(R.id.tv_num)
        TextView tvNum;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_area)
        TextView tvArea;
        @Bind(R.id.tv_choose)
        TextView tvChoose;
        @Bind(R.id.tv_detail)
        TextView tvDetail;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
