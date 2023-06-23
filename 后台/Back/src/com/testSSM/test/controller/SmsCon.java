package com.testSSM.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.testSSM.test.model.Sms;
import com.testSSM.test.service.SmsSer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/sms")
public class SmsCon {
    @Resource
    private SmsSer smss;

    @RequestMapping(value = "sms", produces = "application/json;charset=utf-8")
    @ResponseBody
    public int sms(HttpServletRequest request, Model model) {
        List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
        try {
            request.setCharacterEncoding("utf-8");
            try {
                //获取流
                InputStream requestInputStream = request.getInputStream();

                //接收流缓冲
                StringBuffer stringBuffer = new StringBuffer();

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
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Sms smS = new Sms();
        for (int i = 0; i < list2.size(); i++) {
            Map<String, String> map2 = new HashMap<String, String>();
            map2 = list2.get(i);
            smS.setPhonenum(map2.get("手机号"));
            smS.setSms(map2.get("内容"));
            if (map2.get("id") == null || map2.get("id").equals("")) {
                smS.setSms_id(0);
            } else {
                smS.setSms_id(Integer.parseInt(map2.get("id")));
            }

            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(Long.parseLong(map2.get("时间")));
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            smS.setSmsdate(res);

            if (map2.get("对话的序号") == null || map2.get("对话的序号").equals("")) {
                smS.setSms_huihua(0);
            } else {
                smS.setSms_huihua(Integer.parseInt(map2.get("对话的序号")));
            }
            if (smss.Sms(smS) != 1) {
                System.out.println("存储报错");
                return 0;
            }
        }
        return 1;
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
