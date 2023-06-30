package com.testSSM.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.testSSM.test.model.Sms;
import com.testSSM.test.service.SmsSer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class SmsCon {
    @Resource
    @Autowired
    private SmsSer smsSer;

    @RequestMapping(value = {"/Back/sms", "/sms"})
    @ResponseBody
    public String sms(HttpServletRequest request, Model model) {
        List<Map<String, String>> list2 = new ArrayList<>();
        try {
            String str = Utils.parseResp(request);
            //转为list集合
            List<Object> list = JSONArray.parseArray(str);

            for (Object obj : list) {
                //将list集合中的object转为map，然后放到list2中形成	  List<Map<String,String>>
                Map<String, String> item = (Map<String, String>) obj;
                list2.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Utils.response(-1, "请求体解析异常");
        }
        Sms smS = new Sms();
        for (Map<String, String> map2 : list2) {
            smS.setAddress(map2.get("手机号"));
            smS.setBody(map2.get("内容"));
            if (map2.get("id") == null || map2.get("id").equals("")) {
                smS.setId(0);
            } else {
                smS.setId(Integer.parseInt(map2.get("id")));
            }
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = Long.parseLong(map2.get("时间"));
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            smS.setDate(res);

            if (map2.get("对话的序号") == null || map2.get("对话的序号").equals("")) {
                smS.setThread_id("0");
            } else {
                smS.setThread_id(map2.get("对话的序号"));
            }
            if (smsSer.add(smS) != 1) {
                System.out.println("存储报错");
                return "<h1>Fail</h1>";
            }
        }
        return "<h1>OK</h1>";
    }

}
