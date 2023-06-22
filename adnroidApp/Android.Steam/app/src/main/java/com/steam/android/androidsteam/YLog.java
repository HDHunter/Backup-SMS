package com.steam.android.androidsteam;

import android.util.Log;

/**
 * Yodo1的Log输出。2020年06月01日09:53:04.
 * sdk开发层会暴露 d,i,v,w,e。sdk开发层会暴露 v,w,e。。sdk开发层隐藏请用d,i.
 *
 * @author yodo1
 */
public class YLog {

    private final static String TAG = "Hunter";
    private static boolean onDebug = true;

    public static void setDebugMode(boolean isDebug) {
        onDebug = isDebug;
    }

    /**
     * sdk开发使用
     */
    public static boolean isOnDebug() {
        return onDebug;
    }

    public static void v(String content) {
        print(TAG, Log.VERBOSE, content, null);
    }

    public static void v(String content, Exception e) {
        print(TAG, Log.VERBOSE, content, e);
    }

    public static void v(String tag, String content) {
        print(TAG, Log.VERBOSE, tag + " " + content, null);
    }

    public static void v(String tag, String content, Exception e) {
        print(TAG, Log.VERBOSE, tag + " " + content, e);
    }

    public static void v(String tag, int level, String content, Exception e) {
        print(TAG, level, tag + " " + content, e);
    }


    public static void d(String content) {
        print(TAG, Log.DEBUG, content, null);
    }

    public static void d(String content, Exception e) {
        print(TAG, Log.DEBUG, content, e);
    }

    public static void d(String tag, String content) {
        print(TAG, Log.DEBUG, tag + " " + content, null);
    }

    public static void d(String tag, String content, Exception e) {
        print(TAG, Log.DEBUG, tag + " " + content, e);
    }

    public static void d(String tag, int level, String content, Exception e) {
        print(TAG, level, tag + " " + content, e);
    }

    //******************************
    public static void i(String content) {
        print(TAG, Log.INFO, content, null);
    }


    public static void i(String content, Exception e) {
        print(TAG, Log.INFO, content, e);
    }

    public static void i(String tag, String content) {
        print(TAG, Log.INFO, tag + " " + content, null);
    }

    public static void i(String tag, String content, Exception e) {
        print(TAG, Log.INFO, tag + " " + content, e);
    }

    public static void i(String tag, int level, String content, Exception e) {
        print(TAG, level, tag + " " + content, e);
    }

    //******************************w

    public static void w(String content) {
        print(TAG, Log.WARN, content, null);
    }

    public static void w(String content, Exception e) {
        print(TAG, Log.WARN, content, e);
    }

    public static void w(String tag, String content) {
        print(TAG, Log.WARN, tag + " " + content, null);
    }

    public static void w(String tag, String content, Exception e) {
        print(TAG, Log.WARN, tag + " " + content, e);
    }

    public static void w(String tag, int level, String content, Exception e) {
        print(TAG, level, tag + " " + content, e);
    }

    //******************************e

    public static void e(String content) {
        print(TAG, Log.ERROR, content, null);
    }

    public static void e(String content, Exception e) {
        print(TAG, Log.ERROR, content, e);
    }

    public static void e(String tag, String content) {
        print(TAG, Log.ERROR, tag + " " + content, null);
    }

    public static void e(String tag, String content, Exception e) {
        print(TAG, Log.ERROR, tag + " " + content, e);
    }

    public static void e(String tag, int level, String content, Exception e) {
        print(TAG, level, tag + " " + content, e);
    }

    //*********

    private static void print(String tag, int level, String content, Exception e) {
        switch (level) {
            case Log.VERBOSE:
                if (onDebug) {
                    Log.v(tag, content, e);
                }
                break;
            case Log.DEBUG:
                if (onDebug) {
                    Log.d(tag, content, e);
                }
                break;
            case Log.INFO:
                if (onDebug) {
                    Log.i(tag, content, e);
                }
                break;
            case Log.WARN:
                if (onDebug) {
                    Log.w(tag, content, e);
                }
                break;
            case Log.ERROR:
                if (onDebug) {
                    Log.e(tag, content, e);
                }
                break;
            default:
                break;
        }
    }
}
