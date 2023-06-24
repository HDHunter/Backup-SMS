package com.testSSM.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.testSSM.test.model.Contacts;
import com.testSSM.test.service.ContactsSer;
import jakarta.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class ContactsCon {
    @Resource
    @Autowired
    private ContactsSer contactsSer;

    @RequestMapping(value = {"/Back/phone", "/phone"})
    @ResponseBody
    public String phone(HttpServletRequest request, Model model) {
        List<Map<String, String>> list2 = new ArrayList<>();
        try {
            request.setCharacterEncoding("utf-8");
            try {
                //获取流
                InputStream requestInputStream = request.getInputStream();
                //接收流缓冲
                StringBuilder stringBuffer = new StringBuilder();
                //读取流
                BufferedReader reader = new BufferedReader(new InputStreamReader(requestInputStream, "utf-8"));
                //读入流，转换成字符串
                String readRequestInputStream;
                while ((readRequestInputStream = reader.readLine()) != null) {
                    stringBuffer.append(readRequestInputStream).append("\n");
                }
                String str = stringBuffer.toString();
                str = filterEmoji(str);
                List<Object> list = JSONArray.parseArray(str, Object.class);

                for (Object obj : list) {
                    Map<String, String> item = (Map<String, String>) obj;
                    list2.add(item);
                }
                //关闭资源
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Contacts contacts = new Contacts();
        for (Map<String, String> map2 : list2) {
            if (map2.get("number") == null || map2.get("number").equals("") || map2.get("number").equals("null")) {
                contacts.setPhonenum("0");
            } else {
                contacts.setPhonenum(map2.get("number"));
            }
            if (map2.get("name") == null || map2.get("name").equals("null") || map2.get("name").equals("")) {
                contacts.setPhonename("");
            } else {
                contacts.setPhonename(map2.get("name"));
            }
            int aa = contactsSer.phone(contacts);
            if (aa != 1 || contactsSer.phone(contacts) != -1) {
                if (aa != -1) {
                    System.out.println("存储报错" + contactsSer.phone(contacts));
                    return "<h1>Fail</h1>";
                }
            }
        }
        return "<h1>OK</h1>";
    }

    public static String filterEmoji(String source) {
        String slipStr = "";
        if (StringUtils.isNotBlank(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        } else {
            return source;
        }
    }


}
