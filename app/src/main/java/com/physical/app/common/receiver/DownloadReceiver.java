package com.physical.app.common.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;


import com.physical.app.MyApplication;
import com.physical.app.common.constains.Constains;
import com.physical.app.common.utils.Preferences;

import java.io.File;

/**
 * 作者：liangzixun
 * 时间：2018/7/20 11:14
 * 描述：
 */
public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
//            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            installApk(context);
        } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
            //处理 如果还未完成下载，用户点击Notification ，跳转到下载中心
            Intent viewDownloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(viewDownloadIntent);
        }
    }

    private static void installApk(Context context) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        String filePath = MyApplication.BASEPATH + Preferences.getString(Constains.APK_FILE_NAME);
        File file=new File(filePath);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if ((Build.VERSION.SDK_INT >= 24)) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(context, "com.ttyyedu.app.provider", file);//在AndroidManifest中的android:authorities值
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else {
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(install);
    }
}

