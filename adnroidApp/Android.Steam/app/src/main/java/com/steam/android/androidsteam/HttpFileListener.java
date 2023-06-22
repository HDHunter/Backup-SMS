package com.steam.android.androidsteam;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 2022年04月08日20:29:58。zjq
 *
 * @author yodo1
 */
public abstract class HttpFileListener implements Callback {

    private static final String TAG = "[HttpFileListener]";
    protected static Handler mHandler = new Handler(Looper.getMainLooper());
    private String filePath;

    public HttpFileListener() {
    }

    public abstract void onSuccess(int code, File downloadFile);

    public abstract void onFailure(int errorCode);

    @Override
    public void onFailure(Call call, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onFailure(-1);
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) {
        ResponseBody body = response.body();
        File file = null;
        InputStream is = null;
        FileOutputStream fos = null;
        if (body != null) {
            try {
                is = body.byteStream();
                file = new File(filePath);
                fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buf)) != -1) {
                    fos.write(buf, 0, bytesRead);
                }
            } catch (Exception e) {
                YLog.d(TAG, e);
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    YLog.d(TAG, e);
                }
            }
        }
        final File finalFile = file;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (finalFile != null) {
                    onSuccess(1, finalFile);
                } else {
                    onFailure(-1);
                }
            }
        });
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}