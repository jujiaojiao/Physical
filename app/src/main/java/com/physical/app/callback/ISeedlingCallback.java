package com.physical.app.callback;

import com.physical.app.bean.RecommendBean;
import com.physical.app.bean.SeedlingBean;

import java.util.List;

/**
 * Created by jjj
 * 时间:  2019/5/29
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public interface ISeedlingCallback {
    void onSeedlingSuccess(List<SeedlingBean> beans);

    void onRecipeSuccess(List<RecommendBean> beans);
}
