package com.physical.app.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by jjj
 * 时间:  2019/6/18
 * 邮箱: jujiaojiao@gemdale.com
 * 描述:
 */

public class GlideImageLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).load("http://120.79.18.122:8080/"+path).into(imageView);
    }
}
