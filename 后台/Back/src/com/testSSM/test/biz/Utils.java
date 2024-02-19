package com.testSSM.test.biz;

import com.alibaba.fastjson.JSONObject;
import com.testSSM.test.model.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Utils {

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0 || "null".equals(s);
    }

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

    public static String get(String url) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer result = new StringBuffer();
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setConnectTimeout(15000);
            //设置读取超时时间
            connection.setReadTimeout(15000);
            //开始连接
            connection.connect();
            //获取响应数据
            if (connection.getResponseCode() == 200) {
                //获取返回的数据
                is = connection.getInputStream();
                if (is != null) {
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String temp = null;
                    while ((temp = br.readLine()) != null) {
                        result.append(temp);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();// 关闭远程连接
            }
        }
        return result.toString();
    }

    public static CallJspResponse getCalls(String url, String telNumber, String keyword, String startDate, String endDate, String pageNum, String numPerPage) {
        logD("getCalls:url:" + url + " temNumber:" + telNumber + " keyword:" + keyword + " startDate:" + startDate + " endDate:" + endDate + " pageNum:" + pageNum + " numPerPage:" + numPerPage);
        String s = get(url);
        List<Call> callAll = JSONObject.parseArray(s, Call.class);
        List<Call> calls1 = new ArrayList<>();
        List<Call> callResult = new ArrayList<>();
        if (callAll.size() > 0 && !isEmpty(startDate) && !isEmpty(endDate)) {
            for (Call c : callAll) {
                if (isRightInTime(c.getDate(), startDate, endDate)) {
                    calls1.add(c);
                }
            }
        } else {
            calls1.addAll(callAll);
        }
        if (calls1.size() > 0 && !isEmpty(telNumber)) {
            for (Call c : calls1) {
                String number = c.getNumber();
                if (number.contains(telNumber)) {
                    callResult.add(c);
                }
            }
        } else {
            callResult.addAll(calls1);
        }
        int size = callResult.size();
        int num = Integer.parseInt(isEmpty(pageNum) ? "1" : pageNum);
        int numPer = Integer.parseInt(isEmpty(numPerPage) ? "50" : numPerPage);
        int begin = (num - 1) * numPer;
        int end = num * numPer;
        if (begin >= 0 && end <= size) {
            callResult = callResult.subList(begin, end);
        } else if (begin < size && end > size) {
            callResult = callResult.subList(begin, size);
        } else {
            callResult = null;
        }
        CallJspResponse callJspResponse = new CallJspResponse();
        callJspResponse.setCalls(callResult);
        callJspResponse.setSize(size);
        return callJspResponse;
    }

    public static ContactsJspResponse getContact(String url, String keyword, String pageNum, String numPerPage) {
        logD("getContact:url:" + url + " keyword:" + keyword + " pageNum:" + pageNum + " numPerPage:" + numPerPage);
        String s = get(url);
        List<Contacts> contactss = JSONObject.parseArray(s, Contacts.class);
        List<Contacts> contacts = new ArrayList<>();
        if (contactss.size() > 0 && !isEmpty(keyword)) {
            for (Contacts c : contactss) {
                String displayName = c.getDisplayName();
                if (!isEmpty(displayName) && displayName.contains(keyword)) {
                    contacts.add(c);
                }
            }
        } else {
            contacts.addAll(contactss);
        }
        int size = contacts.size();
        int num = Integer.parseInt(isEmpty(pageNum) ? "1" : pageNum);
        int numPer = Integer.parseInt(isEmpty(numPerPage) ? "50" : numPerPage);
        int begin = (num - 1) * numPer;
        int end = num * numPer;
        if (begin >= 0 && end <= size) {
            contacts = contacts.subList(begin, end);
        } else if (begin < size && end > size) {
            contacts = contacts.subList(begin, size);
        } else {
            contacts = null;
        }
        ContactsJspResponse contactsJspResponse = new ContactsJspResponse();
        contactsJspResponse.setContacts(contacts);
        contactsJspResponse.setSize(size);
        return contactsJspResponse;
    }

    public static SmsJspResponse getSms(String url, String telNumber, String keyword, String startDate, String endDate, String pageNum, String numPerPage) {
        logD("getSms:url:" + url + " temNumber:" + telNumber + " keyword:" + keyword + " startDate:" + startDate + " endDate:" + endDate + " pageNum:" + pageNum + " numPerPage:" + numPerPage);
        String s = get(url);
        List<Sms> smsAll = JSONObject.parseArray(s, Sms.class);
        List<Sms> sms1 = new ArrayList<>();
        List<Sms> sms2 = new ArrayList<>();
        List<Sms> smsResult = new ArrayList<>();
        if (smsAll.size() > 0 && !isEmpty(telNumber)) {
            for (Sms c : smsAll) {
                String address = c.getAddress();
                if (!isEmpty(address) && address.contains(telNumber)) {
                    sms1.add(c);
                }
            }
        } else {
            sms1.addAll(smsAll);
        }
        if (sms1.size() > 0 && !isEmpty(startDate) && !isEmpty(endDate)) {
            for (Sms c : sms1) {
                if (isRightInTime(c.getDate(), startDate, endDate)) {
                    sms2.add(c);
                }
            }
        } else {
            sms2.addAll(sms1);
        }
        if (sms2.size() > 0 && !isEmpty(keyword)) {
            for (Sms c : sms2) {
                String body = c.getBody();
                if (!isEmpty(body) && body.contains(keyword)) {
                    smsResult.add(c);
                }
            }
        } else {
            smsResult.addAll(sms2);
        }
        int size = smsResult.size();
        int num = Integer.parseInt(isEmpty(pageNum) ? "1" : pageNum);
        int numPer = Integer.parseInt(isEmpty(numPerPage) ? "50" : numPerPage);
        int begin = (num - 1) * numPer;
        int end = num * numPer;
        if (begin >= 0 && end <= size) {
            smsResult = smsResult.subList(begin, end);
        } else if (begin < size && end > size) {
            smsResult = smsResult.subList(begin, size);
        } else {
            smsResult = null;
        }
        SmsJspResponse smsJspResponse = new SmsJspResponse();
        smsJspResponse.setSms(smsResult);
        smsJspResponse.setSize(size);
        return smsJspResponse;
    }

    private static boolean isRightInTime(String date, String startDate, String endDate) {
        if (date.length() < 10 || startDate.length() < 10 || endDate.length() < 10) {
            return false;
        }
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);
        String year1 = startDate.substring(0, 4);
        String month1 = startDate.substring(5, 7);
        String day1 = startDate.substring(8, 10);
        String year2 = endDate.substring(0, 4);
        String month2 = endDate.substring(5, 7);
        String day2 = endDate.substring(8, 10);
        try {
            if (year1.hashCode() < year.hashCode()) {
                if (year2.hashCode() > year.hashCode()) {
                    return true;
                } else if (year2.hashCode() == year.hashCode()){
                    if (month2.hashCode() > month.hashCode()) {
                        return true;
                    } else if (month2.hashCode() == month.hashCode()){
                        if (day2.hashCode() >= day.hashCode()) {
                            return true;
                        }
                    }
                }
            } else if (year1.hashCode() == year.hashCode()) {
                if (month1.hashCode() < month.hashCode()) {
                    if (year2.hashCode() > year.hashCode()) {
                        return true;
                    } else if (year2.hashCode() == year.hashCode()){
                        if (month2.hashCode() > month.hashCode()) {
                            return true;
                        } else if (month2.hashCode() == month.hashCode()){
                            if (day2.hashCode() >= day.hashCode()) {
                                return true;
                            }
                        }
                    }
                } else if (month1.hashCode() == month.hashCode()) {
                    if (day1.hashCode() <= day.hashCode()) {
                        if (year2.hashCode() > year.hashCode()) {
                            return true;
                        } else if (year2.hashCode() == year.hashCode()){
                            if (month2.hashCode() > month.hashCode()) {
                                return true;
                            } else if (month2.hashCode() == month.hashCode()){
                                if (day2.hashCode() >= day.hashCode()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
