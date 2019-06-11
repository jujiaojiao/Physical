package com.physical.app.physical;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.physical.app.R;
import com.physical.app.adapter.RecommendAdapter;
import com.physical.app.adapter.SelectSeedlingAdapter;
import com.physical.app.bean.RecipeBean;
import com.physical.app.bean.RecommendBean;
import com.physical.app.bean.SeedlingBean;
import com.physical.app.callback.ISeedlingCallback;
import com.physical.app.common.base.BaseActivity;
import com.physical.app.common.widget.RecipeDialog;
import com.physical.app.presenter.SeedlingPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jjj
 * 版本：1.0
 * 创建日期：2019/5/26
 * 描述：选中幼苗
 */
public class SelectSeedlingActivity extends BaseActivity implements ISeedlingCallback {
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.rlTop)
    RelativeLayout rlTop;
    @Bind(R.id.gv_recommend)
    GridView gvRecommend;
    @Bind(R.id.gv_seedling)
    GridView gvSeedling;
    @Bind(R.id.iv_title)
    ImageView ivTitle;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    @Bind(R.id.ll_bottome)
    LinearLayout llBottome;
    private RecommendAdapter recommendAdapter;
    private ArrayList<RecommendBean> recommends;
    private ArrayList<SeedlingBean> seedlings;
    private SelectSeedlingAdapter selectSeedlingAdapter;
    private RecipeDialog recipeDialog;
    private SeedlingPresenter seedlingPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, SelectSeedlingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_seedling);
        ButterKnife.bind(this);
        initView();
        addListener();
    }

    private void initView() {
        ivTitle.setImageResource(R.mipmap.word_choose);
        recommends = new ArrayList<RecommendBean>();
        recommendAdapter = new RecommendAdapter(this, recommends);
        gvRecommend.setAdapter(recommendAdapter);

        seedlings = new ArrayList<SeedlingBean>();
        selectSeedlingAdapter = new SelectSeedlingAdapter(this, seedlings);
        gvSeedling.setAdapter(selectSeedlingAdapter);

        seedlingPresenter = new SeedlingPresenter(this, this);
        Log.i("jjj", "initView: "+getWifiMac());
        seedlingPresenter.seedling(getUserId());
        seedlingPresenter.queryRecipeList("",getUserId());

    }


    private void addListener() {
//        gvSeedling.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                SeedlingBean data = seedlings.get(position);
//                selectSeedlingAdapter.selOrRemoveItem(data);
//            }
//        });

        gvRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recommendAdapter.setcurrent(position);
            }
        });
    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm, R.id.ivBack})
    public void onClicK(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                showRecipeDialog();
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.ivBack:
                finish();
                break;
        }
    }


    private void showRecipeDialog() {
        List<RecipeBean> datas = new ArrayList<>();
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        datas.add(new RecipeBean());
        recipeDialog = new RecipeDialog(this, datas, new RecipeDialog.Callback() {
            @Override
            public void onConfirm() {
                recipeDialog.dismiss();
            }
        });
        recipeDialog.show();
    }



    @Override
    public void onSeedlingSuccess(List<SeedlingBean> beans) {
        seedlings.addAll(beans);
        selectSeedlingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecipeSuccess(List<RecommendBean> beans) {
        recommends.addAll(beans);
        selectSeedlingAdapter.notifyDataSetChanged();
    }
}
