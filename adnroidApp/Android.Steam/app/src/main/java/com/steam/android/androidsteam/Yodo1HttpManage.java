package com.steam.android.androidsteam;

import android.content.Context;
import android.webkit.WebSettings;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by yodo1 on 18/1/2.
 *
 * @author yodo1
 */
public class Yodo1HttpManage {

    private static final String TAG = "[Yodo1HttpManage]";
    private static Yodo1HttpManage instance;
    private Context mContext;
    private OkHttpClient client = null;
    private String userAgent;
    private final MediaType mediaType = MediaType.parse("text/plain;charset=UTF-8");
    private final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            YLog.d(TAG, message);
        }
    });

    public static Yodo1HttpManage getInstance() {
        if (instance == null) {
            instance = new Yodo1HttpManage();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context context context对象必须为ApplicationContext
     */
    public void initHttp(Context context) {
        mContext = context;
    }


    /**
     * 很多类还在直接轻引用Nohttp，get提供解耦和替换隔离。
     *
     * @param requestUrl 原始的url请求地址。
     * @param listener   回调
     */
    public void get(String requestUrl, Callback listener) {
        get(requestUrl, null, listener);
    }

    /**
     * 很多类还在直接轻引用Nohttp，get提供解耦和替换隔离。
     *
     * @param requestUrl    原始的url请求地址。
     * @param requestParams 请求参数 map集合
     * @param listener      回调
     */
    public void get(String requestUrl, Map<String, String> requestParams, Callback listener) {
        get(requestUrl, null, requestParams, listener);
    }

    /**
     * 带头文件的get请求
     *
     * @param requestUrl    原始的url请求地址
     * @param header        请求头
     * @param requestParams 请求参数map集合
     * @param listener      回调
     */
    public void get(String requestUrl, Map<String, String> header, Map<String, String> requestParams, Callback listener) {
        get(requestUrl, header, null, requestParams, listener);
    }


    /**
     * 带userAgent的get请求.header可能包含ua.
     *
     * @param requestUrl    原始的url请求地址
     * @param header        请求头
     * @param userAgent     请求头中的userAgent
     * @param requestParams 请求参数map集合
     * @param listener      回调
     */
    public void get(String requestUrl, Map<String, String> header, List<String> userAgent, Map<String, String> requestParams, Callback listener) {
        Request.Builder build = new Request.Builder();
        if (userAgent != null && userAgent.size() > 0) {
            String _userAgentStr = getUserAgent();
            for (String str : userAgent) {
                _userAgentStr = ";" + str;
            }
            build.header("User-Agent", _userAgentStr);
        } else {
            build.header("User-Agent", getUserAgent());
        }
        if (header != null) {
            for (String key : header.keySet()) {
                build.header(key, header.get(key));
            }
        }
        HttpUrl.Builder urlBuilder = null;
        if (requestParams != null && requestParams.size() > 0) {
            urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
            for (Map.Entry<String, String> entry : requestParams.entrySet()) {
                urlBuilder.addEncodedQueryParameter(entry.getKey(), entry.getValue());
            }
            build.url(urlBuilder.build());
        } else {
            build.url(requestUrl);
        }
        Request request = build.get().build();
        getClient().newCall(request).enqueue(listener);
    }


    public void getFile(String requestUrl, String filePath, HttpFileListener listener) {
        Request.Builder build = new Request.Builder().url(requestUrl);
        Request request = build.get().build();
        listener.setFilePath(filePath);
        getClient().newCall(request).enqueue(listener);
    }

    private synchronized OkHttpClient getClient() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            if (YLog.isOnDebug()) {
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addNetworkInterceptor(loggingInterceptor);
            }
            client = builder.build();
            YLog.d(TAG, "Fist Http Request.");
        }
        return client;
    }

    private String getUserAgent() {
        if (userAgent == null) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(mContext);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
                YLog.e(TAG, e);
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0, length = userAgent.length(); i < length; i++) {
                char c = userAgent.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    sb.append(String.format("\\u%04x", (int) c));
                } else {
                    sb.append(c);
                }
            }
            userAgent = sb.toString();
            return userAgent;
        } else {
            return userAgent;
        }
    }

    /**
     * 很多类还在直接轻引用Nohttp，post提供解耦和替换隔离。
     *
     * @param requestUrl  原始的url请求地址。
     * @param requestBody 请求体json
     * @param listener    回调。
     */
    public void post(String requestUrl, JSONObject requestBody, Callback listener) {
        post(requestUrl, requestBody.toString(), listener);
    }

    /**
     * 三参数post，指向四参
     *
     * @param requestUrl        请求地址
     * @param requestBodyString 请求参数
     * @param listener          回调
     */
    public void post(String requestUrl, String requestBodyString, Callback listener) {
        post(requestUrl, requestBodyString, null, listener);
    }

    /**
     * 四参数post，指向五参
     *
     * @param requestUrl        请求地址
     * @param requestBodyString 请求参数
     * @param contentType       内容类型
     * @param listener          回调
     */
    public void post(String requestUrl, String requestBodyString, String
            contentType, Callback listener) {
        post(requestUrl, null, requestBodyString, contentType, listener);
    }


    /**
     * 四参数post，指向五参
     *
     * @param requestUrl        请求地址
     * @param header            请求头
     * @param requestBodyString 请求参数
     * @param listener          回调
     */
    public void post(String requestUrl, Map<String, String> header, String
            requestBodyString, Callback listener) {
        post(requestUrl, header, requestBodyString, null, listener);
    }


    /**
     * 五参数post操作
     *
     * @param requestUrl        请求地址
     * @param header            请求头
     * @param requestBodyString 请求参数
     * @param contentType       内容类型
     * @param listener          回调
     */
    public void post(String requestUrl, Map<String, String> header, String
            requestBodyString, String contentType, Callback listener) {
        post(requestUrl, header, null, requestBodyString, contentType, listener);
    }

    public void postForm(String requestUrl, Map<String, String> header, List<String> userAgent, Map<String, String>
            formKV, Callback listener) {
        Request.Builder build = new Request.Builder();
        if (userAgent != null && userAgent.size() > 0) {
            String _userAgentStr = getUserAgent();
            for (String str : userAgent) {
                _userAgentStr = ";" + str;
            }
            build.header("User-Agent", _userAgentStr);
        } else {
            build.header("User-Agent", getUserAgent());
        }
        if (header != null) {
            for (String key : header.keySet()) {
                build.header(key, header.get(key));
            }
        }
        RequestBody body = null;
        if (formKV != null) {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            for (String key : formKV.keySet()) {
                builder.addFormDataPart(key, formKV.get(key));
            }
            body = builder.build();
        } else {
            body = RequestBody.create(mediaType, "");
        }
        Request request = build.url(requestUrl).post(body).build();
        getClient().newCall(request).enqueue(listener);
    }

    /**
     * 六参数post操作
     *
     * @param requestUrl        请求地址
     * @param header            请求头
     * @param userAgent         请求头内的User-Agent
     * @param requestBodyString 请求参数
     * @param contentType       内容类型
     * @param listener          回调
     */
    public void post(String requestUrl, Map<String, String> header, List<String> userAgent, String
            requestBodyString, String contentType, Callback listener) {
        Request.Builder build = new Request.Builder();
        if (userAgent != null && userAgent.size() > 0) {
            String _userAgentStr = getUserAgent();
            for (String str : userAgent) {
                _userAgentStr = ";" + str;
            }
            build.header("User-Agent", _userAgentStr);
        } else {
            build.header("User-Agent", getUserAgent());
        }
        if (header != null) {
            for (String key : header.keySet()) {
                build.header(key, header.get(key));
            }
        }
        RequestBody body = null;
        if (requestBodyString != null) {
            if (contentType == null) {
                body = RequestBody.create(mediaType, requestBodyString);
            } else {
                body = RequestBody.create(MediaType.parse(contentType), requestBodyString);
            }
        } else {
            body = RequestBody.create(mediaType, "");
        }

        Request request = build.url(requestUrl).post(body).build();
        getClient().newCall(request).enqueue(listener);
    }


    public void onDestroy() {
        mContext = null;
        instance = null;
    }
}
