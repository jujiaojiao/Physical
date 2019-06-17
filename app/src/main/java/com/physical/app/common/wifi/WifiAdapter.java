package com.physical.app.common.wifi;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.physical.app.R;

import java.util.List;

/**
 * Created by Acer on 2018/9/4.
 */

public class WifiAdapter extends BaseAdapter {

    private static final String TAG = WifiAdapter.class.getSimpleName();

    private LayoutInflater mInflater;
    private List<ScanResult> mWifiResults;
    private Activity mContext;

    public WifiAdapter(Activity context, List<ScanResult> resultList) {
        this.mInflater= LayoutInflater.from(context);
        this.mContext = context;
        this.mWifiResults = resultList;
    }

    @Override
    public int getCount() {
        return mWifiResults.size() != 0 ? mWifiResults.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mWifiResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * =========================ViewHolder=========================
     */
    class ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvSignal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder mHodler;
        if (convertView == null) {
            mHodler = new ViewHolder();
            view = mInflater.inflate(R.layout.wifi_item, null);
            //初始化控件
            mHodler.ivIcon = view.findViewById(R.id.iv_icon);
            mHodler.tvName = view.findViewById(R.id.tv_name);
            mHodler.tvSignal = view.findViewById(R.id.tv_signal);
            view.setTag(mHodler);
        } else {
            view = convertView;
            mHodler = (ViewHolder) view.getTag();
        }
        ScanResult scanResult = mWifiResults.get(position);
        setWifiSignalIcon(mHodler.ivIcon,scanResult.level);
        mHodler.tvName.setText(scanResult.SSID.toString());
//        mHodler.tvSignal.setText();

        return view;
    }


    /**
     *  设置WIFI信号图标
     *
     * @param imageView
     * @param level
     */
    private static void setWifiSignalIcon(ImageView imageView, int level) {
        switch (level){
            case 0:
                Log.i(TAG,"wifi当前等级为 = " + level);
//                imageView.setImageResource(R.drawable.wifi_0);
                break;
            case 1:
                Log.i(TAG,"wifi当前等级为 = " + level);
//                imageView.setImageResource(R.drawable.wifi_1);
                break;
            case 2:
                Log.i(TAG,"wifi当前等级为 = " + level);
//                imageView.setImageResource(R.drawable.wifi_2);
                break;
            case 3:
                Log.i(TAG,"wifi当前等级为 = " + level);
//                imageView.setImageResource(R.drawable.wifi_3);
                break;
        }
    }
}
