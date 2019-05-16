package com.physical.app.common.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.physical.app.MyApplication;
import com.physical.app.R;

/**
 * 图片获取统一管理类
 */
public class ImageManager {

    /**
     * 默认图片
     */
    public static int IMAGE_DEFAULT = R.mipmap.ic_launcher;


    /**
     * 加载图片
     *
     * @param imgUrl
     * @param imageView
     */
    public static void Load(String imgUrl, ImageView imageView) {
        Glide.with(MyApplication.context).load(imgUrl).asBitmap().error(IMAGE_DEFAULT).into(imageView);
    }
    /**
     * 加载图片
     *
     * @param imgUrl
     * @param imageView
     */
    public static void Load(String imgUrl, ImageView imageView, int imgError) {
        Glide.with(MyApplication.context).load(imgUrl).asBitmap().error(imgError).into(imageView);
    }

}
