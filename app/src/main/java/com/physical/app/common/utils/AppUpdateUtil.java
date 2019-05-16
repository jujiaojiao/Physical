package com.physical.app.common.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

/**
 * 作者：liangzixun
 * 时间：2018/7/19 10:31
 * 描述：
 */
public class AppUpdateUtil {

    public static long updateApp(Context context, String url, String fileName, String title, String desc) {
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        //设置WIFI下进行更新
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI| DownloadManager.Request.NETWORK_MOBILE);
        //设置是否允许漫游网络 建立请求 默认true
        request.setAllowedOverRoaming( true ) ;
        //下载中和下载完后都显示通知栏
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //使用系统默认的下载路径 此处为应用内 /android/data/packages ,所以兼容7.0
//        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fileName);
        //设置保存下载apk保存路径
        request.setDestinationInExternalPublicDir("lizeapp/", fileName);
        //通知栏标题
        request.setTitle(title);
        //通知栏描述信息
        request.setDescription(desc);
        //设置类型为.apk
        request.setMimeType("application/vnd.android.package-archive");
        request.setVisibleInDownloadsUi(true);
        //获取下载任务ID
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return manager.enqueue(request);
    }

    /**
     * 下载前先移除前一个任务，防止重复下载
     *
     * @param downloadId
     */
    public static void clearCurrentTask(Context context, long downloadId) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            manager.remove(downloadId);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

}
