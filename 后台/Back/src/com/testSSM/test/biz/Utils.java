package com.testSSM.test.biz;

import com.testSSM.test.model.HttpResponse;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Utils {


    public static String parseResp(HttpServletRequest request) {
        String str = null;
        try {
            request.setCharacterEncoding("utf-8");
            //获取流
            InputStream is = request.getInputStream();
            //接收流缓冲
            StringBuilder sb = new StringBuilder();
            //读取流
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            //读入流，转换成字符串
            String sss;
            while ((sss = br.readLine()) != null) {
                sb.append(sss).append("\n");
            }
            str = sb.toString();
            //close stream
            br.close();
            is.close();

            StringBuffer requestURL = request.getRequestURL();
            String contextPath = request.getContextPath();
            String method = request.getMethod();
            Enumeration<String> headerNames = request.getHeaderNames();
            request.getCookies();
            Utils.logD("parseResp", "requestURL:  " + requestURL);
            Utils.logD("parseResp", "contextPath:  " + contextPath);
            Utils.logD("parseResp", "method:  " + method);
            List<String> hs = new ArrayList<>();
            while (headerNames.hasMoreElements()) {
                String s = headerNames.nextElement();
                String v = request.getHeader(s);
                hs.add(s + ": " + v);
            }
            for (String ssss : hs) {
                Utils.logD("parseResp", ssss);
            }
            Utils.logD("parseResp", "body: " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static HttpResponse response(int code, String msg) {
        return new HttpResponse(code, msg);
    }

    public static String filterEmoji(String source) {
        String slipStr = "";
        if (StringUtils.isNotBlank(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        } else {
            return source;
        }
    }

    public static void logE(String tag, Object ob) {
        System.err.println(tag + "    " + ob);
    }

    public static void logD(String tag, Object ob) {
        System.out.println(tag + "    " + ob);
    }

    public static void logE(Object ob) {
        System.err.println(ob);
    }

    public static void logD(Object ob) {
        System.out.println(ob);
    }
}
