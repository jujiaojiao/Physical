package com.physical.app.common.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.common.model.AreaInfo;
import com.physical.app.common.model.CityInfo;
import com.physical.app.common.model.ProvinceInfo;
import com.physical.app.common.utils.FileUtil;
import com.physical.app.common.utils.ToastUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：liangzixun
 * 时间：2018/9/28 15:10
 * 描述：地址选择器(省市区)
 */
public class AreaDialog extends AlertDialog {

    private Context mContext;
    private LayoutInflater inflater;
    private List<ProvinceInfo> provinceInfoList;
    private List<CityInfo> cityInfoList;
    private List<AreaInfo> areaInfoList;

    private ArrayList<String> proviceStrings;
    private ArrayList<String> cityStrings;
    private ArrayList<String> areaStrings;

    private Callback callback;

    private TextView tvCancel;
    private TextView tvConfirm;
    private EasyPickerView epvProvince;
    private EasyPickerView epvCity;
    private EasyPickerView epvArea;

    private ProvinceInfo currentProvince;
    private CityInfo currentCity;
    private AreaInfo currentArea;


    public AreaDialog(Context context, Callback callback) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        addListener();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.dialog_area_picker, null);
        tvCancel = view.findViewById(R.id.tvCancel);
        tvConfirm = view.findViewById(R.id.tvConfirm);
        epvProvince = view.findViewById(R.id.epvProvince);
        epvCity = view.findViewById(R.id.epvCity);
        epvArea = view.findViewById(R.id.epvArea);
        setContentView(view);
        setCanceledOnTouchOutside(false);
    }

    private void initData() {
        provinceInfoList = getAreasFromFile();
        if (provinceInfoList == null || provinceInfoList.size() == 0) {
            ToastUtil.show("系统错误");
            return;
        }
        cityInfoList = provinceInfoList.get(0).data;
        areaInfoList = cityInfoList.get(0).data;
        proviceStrings = getProviceList(provinceInfoList);
        cityStrings = getCityList(cityInfoList);
        areaStrings = getAreaList(areaInfoList);

        epvProvince.setDataList(proviceStrings);
        epvCity.setDataList(cityStrings);
        epvArea.setDataList(areaStrings);

        currentProvince = provinceInfoList.get(0);
        currentCity = cityInfoList.get(0);
        currentArea = areaInfoList.get(0);
    }


    private void addListener() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                String param01 = "";
                String param02 = "";
                if (currentProvince != null && currentCity != null && currentArea != null) {
                    param01 = currentProvince.id + "," + currentCity.id + "," + currentArea.id;
                    param02 = currentProvince.name + "," + currentCity.name + "," + currentArea.name;
                }
                callback.callback(param01, param02);
            }
        });

        epvProvince.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int curIndex) {

            }

            @Override
            public void onScrollFinished(int curIndex) {
                currentProvince = provinceInfoList.get(curIndex);
                cityInfoList = currentProvince.data;
                cityStrings = getCityList(cityInfoList);
                epvCity.setDataList(cityStrings);

                currentCity = cityInfoList.get(0);
                areaInfoList = currentCity.data;
                areaStrings = getAreaList(areaInfoList);
                epvArea.setDataList(areaStrings);
                currentArea = areaInfoList.get(0);
            }
        });

        epvCity.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int curIndex) {

            }

            @Override
            public void onScrollFinished(int curIndex) {
                currentCity = cityInfoList.get(curIndex);
                areaInfoList = currentCity.data;
                areaStrings = getAreaList(areaInfoList);
                epvArea.setDataList(areaStrings);
                currentArea = areaInfoList.get(0);
            }
        });

        epvArea.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int curIndex) {

            }

            @Override
            public void onScrollFinished(int curIndex) {
                currentArea = areaInfoList.get(curIndex);
            }
        });

    }

    private ArrayList<String> getAreaList(List<AreaInfo> areaInfoList) {
        ArrayList<String> temps = new ArrayList<>();
        for (AreaInfo temp : areaInfoList) {
            temps.add(temp.name);
        }
        return temps;
    }

    private ArrayList<String> getCityList(List<CityInfo> cityInfoList) {
        ArrayList<String> temps = new ArrayList<>();
        for (CityInfo temp : cityInfoList) {
            temps.add(temp.name);
        }
        return temps;
    }

    private ArrayList<String> getProviceList(List<ProvinceInfo> provinceInfoList) {
        ArrayList<String> temps = new ArrayList<>();
        for (ProvinceInfo temp : provinceInfoList) {
            temps.add(temp.name);
        }
        return temps;
    }

    private List<ProvinceInfo> getAreasFromFile() {
        String tempString = FileUtil.getFromAssets(mContext, "location.json");
        Gson gson = new Gson();
        ProvinceInfo[] temps = gson.fromJson(tempString, ProvinceInfo[].class);
        List<ProvinceInfo> list = Arrays.asList(temps);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public interface Callback {
        public void callback(String param01, String param02);
    }

}
