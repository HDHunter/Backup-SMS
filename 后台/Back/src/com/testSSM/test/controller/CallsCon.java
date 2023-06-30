package com.testSSM.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.testSSM.test.model.Call;
import com.testSSM.test.service.CallsSer;
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
public class CallsCon {
    @Resource
    @Autowired
    private CallsSer callsSer;

    @RequestMapping(value = {"/Back/calls", "/calls"})
    @ResponseBody
    public String call(HttpServletRequest request, Model model) {
        List<Map<String, String>> list2 = new ArrayList<>();
        try {
            String str = Utils.parseResp(request);
            //转为list集合
            List<Object> list = JSONArray.parseArray(str, Object.class);

            for (Object obj : list) {
                //将list集合中的object转为map，然后放到list2中形成	  List<Map<String,String>>
                Map<String, String> item = (Map<String, String>) obj;
                list2.add(item);
            }
        } catch (Exception e) {
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

}
