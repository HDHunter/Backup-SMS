package com.testSSM.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.testSSM.test.model.Call;
import com.testSSM.test.service.CallsSer;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class CallsCon {
    @Resource
    @Autowired
    private CallsSer callsSer;

    @RequestMapping(value = {"/Back/calls", "/calls"}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String call(HttpServletRequest request, Model model) {
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
                //去掉emoji
                str = filterEmoji(str);
                //转为list集合
                List<Object> list = JSONArray.parseArray(str, Object.class);

                for (Object obj : list) {
                    //将list集合中的object转为map，然后放到list2中形成	  List<Map<String,String>>
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
        Call call = new Call();
        for (Map<String, String> map2 : list2) {
            call.setNumber(map2.get("手机号"));
            call.setDate(map2.get("内容"));
            if (map2.get("id") == null || map2.get("id").equals("")) {
                call.setId(0);
            } else {
                call.setId(Integer.parseInt(map2.get("id")));
            }
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = Long.parseLong(map2.get("时间"));
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            call.setDate(res);

            if (map2.get("对话的序号") == null || map2.get("对话的序号").equals("")) {
                call.setId(0);
            } else {
                call.setId(34);
            }
            if (callsSer.add(call) != 1) {
                System.out.println("存储报错");
                return "<h1>Fail</h1>";
            }
        }
        return "<h1>OK</h1>";
    }

    //去掉短信中的Emoji
    public static String filterEmoji(String source) {
        String slipStr = "";
        if (StringUtils.isNotBlank(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        } else {
            return source;
        }
    }
}
