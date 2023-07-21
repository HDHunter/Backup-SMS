package com.steam.android.androidsteam;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 2020年06月04日21:36:09。zjq
 *
 * @author yodo1
 */
public abstract class HttpStringListener implements Callback {

    protected String requestUrl;
    private static final String TAG = "[HttpStringListener]";
    protected static Handler mHandler = new Handler(Looper.getMainLooper());


    public HttpStringListener() {
    }


    public abstract void onSuccess(int code, String responseString);

    public abstract void onFailure(int errorCode, String msg);

    @Override
    public void onFailure(Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(-1, e.getMessage());
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response res) {
        String data = null;
        ResponseBody body = null;
        if (res != null && (body = res.body()) != null) {
            try {
                data = body.string();
            } catch (Exception e) {
                YLog.e(TAG, e);
            }
        }
        final String finalData = data;
        if (res.code() > 220) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onFailure(res.code(), finalData);
                }
            });
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(res.code(), finalData);
                }
            });
        }
    }

}
