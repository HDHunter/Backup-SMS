package com.steam.android.androidsteam;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MiuiUtils {

    private MiuiUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final int REQUEST_CODE_SERVICE_SMS = 100;

    public static boolean checkMIUI() {
        String versionCode = "";
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        Log.i("MIUI", "manufacturer:" + manufacturer + " model:" + model);
        return !TextUtils.isEmpty(manufacturer) && manufacturer.equals("Xiaomi");
    }

    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.i("MIUI", "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.i("MIUI", "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }


    public static void goPermissionSettings(Activity context) {
        Intent intent;
        try {//MIUI8/9
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter",
                    "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivityForResult(intent, REQUEST_CODE_SERVICE_SMS);
        } catch (ActivityNotFoundException e) {
            try {//MIUI5/6
                intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter",
                        "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivityForResult(intent, REQUEST_CODE_SERVICE_SMS);
            } catch (ActivityNotFoundException e1) {
                //应用信息界面
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(),
                        null);
                intent.setData(uri);
                context.startActivityForResult(intent, REQUEST_CODE_SERVICE_SMS);
            }
        }
    }
}