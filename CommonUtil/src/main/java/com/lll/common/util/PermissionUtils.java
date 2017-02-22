package com.lll.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

/**
 * Version 1.0
 * Created by lll on 16/12/27.
 * Description 6.0版本后权限检查工具
 * copyright generalray4239@gmail.com
 */
public class PermissionUtils {


    /**
     * 权限是否具有检查
     *
     * @param context
     * @param permission
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean hasePermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 申请权限
     * @param activity
     * @param shouldPermission
     * @param requestPermissions
     * @param requestCode
     */
    public static void requestPermission(Activity activity, String shouldPermission, String[] requestPermissions, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, shouldPermission)) {
            ActivityCompat.requestPermissions(activity, requestPermissions, requestCode);
        }
    }


}
