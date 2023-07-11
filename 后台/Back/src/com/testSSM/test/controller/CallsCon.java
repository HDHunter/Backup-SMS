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


    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

            Call call = new Call();
            for (Map<String, String> map2 : list2) {
                call.setNumber(map2.get("number"));
                if (map2.get("id") == null || map2.get("id").equals("")) {
                    call.setId(0);
                } else {
                    call.setId(Integer.parseInt(map2.get("id")));
                }
                String res;
                long lt = Long.parseLong(map2.get("date"));
                Date date = new Date(lt);
                res = simpleDateFormat.format(date);
                call.setDate(res);
                call.setDuration(Integer.parseInt(map2.get("duration")));
                call.setNumber(map2.get("number"));
                try {
                    if (callsSer.add(call) != 1) {
                        Utils.logE("存储报错");
                        return Utils.response(-1, "数据库插入失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return Utils.response(-1, "数据库操作失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Utils.response(-1, "请求体解析异常");
        }
        return Utils.response(0, "插入成功");
    }

}
