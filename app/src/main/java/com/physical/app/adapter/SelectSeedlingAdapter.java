package com.physical.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.bean.SelectSeedlingBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/26
 * 描述： 选用幼苗
 */
public class SelectSeedlingAdapter extends BaseAdapter {
    private Context context;
    private List<SelectSeedlingBean> datas;
    private LayoutInflater inflater;
    private int current;

    public SelectSeedlingAdapter(Context context, List<SelectSeedlingBean> datas) {
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
            convertView = inflater.inflate(R.layout.item_select_seedling, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (current == position) {
            holder.ivCheck.setImageResource(R.mipmap.icon_block_have);
        }
//        else{
//            holder.ivCheck.setImageResource(R.mipmap.icon_circle_none);
//        }
//        holder.tvContent.setText(datas.get(position));
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
        @Bind(R.id.et_num)
        EditText etNum;
        @Bind(R.id.ll_root)
        LinearLayout llRoot;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
