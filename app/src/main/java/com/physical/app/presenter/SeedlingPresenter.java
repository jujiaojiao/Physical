package com.physical.app.presenter;

import android.content.Context;

import com.physical.app.bean.RecommendBean;
import com.physical.app.bean.SeedlingBean;
import com.physical.app.callback.ISeedlingCallback;
import com.physical.app.common.api.ProgressSubscriber;
import com.physical.app.common.base.BasePresenter;
import com.physical.app.common.utils.ToastUtil;

import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/5/29
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class SeedlingPresenter extends BasePresenter {

    private ISeedlingCallback callback;

    public SeedlingPresenter(Context context,ISeedlingCallback callback) {
        super(context);
        this.callback = callback;
    }

    /**
     * 幼苗
     *
     * @return
     */
    public void seedling(String sessionId) {
        mRequestClient.seedling(sessionId).subscribe(new ProgressSubscriber<List<SeedlingBean>>(mContext) {
            @Override
            public void onNext(List<SeedlingBean> bean) {
                callback.onSeedlingSuccess(bean);
            }

        });
    }

    /**
     * 查询处方
     *
     * @param hisIds
     * @return
     */
    public void queryRecipeList(String hisIds, String sessionId) {
        mRequestClient.queryRecipeList(hisIds, sessionId).subscribe(new ProgressSubscriber<List<RecommendBean>>(mContext) {
            @Override
            public void onNext(List<RecommendBean> beans) {
                callback.onRecipeSuccess(beans);
            }
        });
    }


}
