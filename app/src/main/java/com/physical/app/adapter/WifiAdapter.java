package com.physical.app.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
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
 * @author jjj
 *         版本：1.0
 *         创建日期：2019/5/21
 *         描述：wifi
 */
public class WifiAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int current;
    private List<ScanResult> datas;

    public WifiAdapter(Context context, List<ScanResult> datas) {
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
            convertView = inflater.inflate(R.layout.item_wifi, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ScanResult data = datas.get(position);
        holder.tvName.setText(data.SSID);
        if (null != data.capabilities) {
            if (data.capabilities.contains("WEP")||data.capabilities.contains("PSK")||data.capabilities.contains("EAP")){
                holder.ivSwitch.setImageResource(R.mipmap.icon_wifi_lock);
            }else{
                holder.ivSwitch.setImageResource(R.mipmap.icon_wifi_open);
            }
        }

        holder.tvName.setText(datas.get(position).SSID);
        return convertView;
    }

    public void setcurrent(int current) {
        this.current = current;
    }

    public void setScanResultList(List<ScanResult> scanResultList) {
        this.datas = scanResultList;
        notifyDataSetChanged();
    }


    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.iv_switch)
        ImageView ivSwitch;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
