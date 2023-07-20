package com.steam.android.androidsteam;


import android.content.Context;
import android.widget.Toast;

/**
 * Created by beicky on 18/4/13.
 */
public class Http {

    private static final String baseUrl = "http://192.168.2.139:8080/Back";
    private Context context;

    public Http(Context context) {
        this.context = context;
    }

    public void sms(String json1) {
        String uri = baseUrl + "/sms";
        Yodo1HttpManage.getInstance().post(uri, json1, new HttpStringListener() {
            @Override
            public void onSuccess(int code, String responseString) {
                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                YLog.d("Http", "onSuccess code:" + code + "  msg:" + responseString);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                Toast.makeText(context, "失败:code:" + errorCode + " msg:" + msg, Toast.LENGTH_SHORT).show();
                YLog.d("Http", "onFailure errorCode:" + errorCode + "  msg:" + msg);
            }
        });
    }


    public void phone(String json1) {
        String uri = baseUrl + "/contact";
        Yodo1HttpManage.getInstance().post(uri, json1, new HttpStringListener() {
            @Override
            public void onSuccess(int code, String responseString) {
                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                YLog.d("Http", "onSuccess code:" + code + "  msg:" + responseString);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                Toast.makeText(context, "失败:code:" + errorCode + " msg:" + msg, Toast.LENGTH_SHORT).show();
                YLog.d("Http", "onFailure errorCode:" + errorCode + "  msg:" + msg);
            }
        });
    }

    public void callLog(String json1) {
        String uri = baseUrl + "/calls";
        Yodo1HttpManage.getInstance().post(uri, json1, new HttpStringListener() {
            @Override
            public void onSuccess(int code, String responseString) {
                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                YLog.d("Http", "onSuccess code:" + code + "  msg:" + responseString);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                Toast.makeText(context, "失败:code:" + errorCode + " msg:" + msg, Toast.LENGTH_SHORT).show();
                YLog.d("Http", "onFailure errorCode:" + errorCode + "  msg:" + msg);
            }
        });
    }

    public void imageUpload(String filePath) {
        String uri = baseUrl + "/img";
        YLog.d(filePath);
        Yodo1HttpManage.getInstance().get(uri, new HttpStringListener() {
            @Override
            public void onSuccess(int code, String responseString) {
                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                YLog.d("Http", "onSuccess code:" + code + "  msg:" + responseString);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                Toast.makeText(context, "失败:code:" + errorCode + " msg:" + msg, Toast.LENGTH_SHORT).show();
                YLog.d("Http", "onFailure errorCode:" + errorCode + "  msg:" + msg);
            }
        });
    }
}
