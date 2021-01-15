package com.onlineeducationsyestem;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Objects;

public class ApplicationSelectorReceiver extends BroadcastReceiver {
    @SuppressLint("LongLogTag")

    @Override
    public void onReceive(Context context, Intent intent) {
        for (String key : Objects.requireNonNull(intent.getExtras()).keySet()) {
            try {
                ComponentName componentInfo = (ComponentName) intent.getExtras().get(key);
                PackageManager packageManager = context.getPackageManager();
                assert componentInfo != null;
                String appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(componentInfo.getPackageName(), PackageManager.GET_META_DATA));
                Log.i("Selected Application Name", appName);
                CourseDetailActivity courseDetailActivity=new CourseDetailActivity();
                courseDetailActivity.callling();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}