package com.testSSM.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.testSSM.test.model.Contacts;
import com.testSSM.test.model.HttpResponse;
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
import java.util.*;


@Controller
public class SmsCon {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    @Autowired
    private SmsSer smsSer;

    @RequestMapping(value = {"/Back/sms", "/sms"})
    @ResponseBody
    public HttpResponse sms(HttpServletRequest request, Model model) {
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
            Utils.logD("sms 上传条数:" + list2.size());
            Sms smS = new Sms();
            List<Sms> smsList = smsSer.get();
            List<Sms> success = new ArrayList<>();
            Map<String, Sms> maps = new HashMap<>();
            Utils.logD("Sms 已经保存条数:" + smsList.size());
            for (Sms c : smsList) {
                maps.put(c.getDate(), c);
            }
            for (Map<String, String> map2 : list2) {
                smS.setAddress(map2.get("address"));
                smS.setBody(map2.get("body"));
                if (map2.get("id") == null || map2.get("id").equals("")) {
                    smS.setId(0);
                } else {
                    smS.setId(Integer.parseInt(map2.get("id")));
                }
                String res;
                long lt = Long.parseLong(map2.get("date"));
                Date date = new Date(lt);
                res = simpleDateFormat.format(date);
                smS.setDate(res);
                if (maps.get(res) != null) {
                    continue;
                }
                smS.setStatus(map2.get("status"));
                smS.setError_code(map2.get("error_code"));
                if (map2.get("thread_id") == null || map2.get("thread_id").equals("")) {
                    smS.setThread_id("0");
                } else {
                    smS.setThread_id(map2.get("thread_id"));
                }
                try {
                    if (smsSer.add(smS) != 1) {
                        Utils.logE("存储报错");
                        return Utils.response(-1, "数据库插入失败");
                    }
                    success.add(smS);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Utils.response(-1, "数据库操作失败");
                }
            }
            Utils.logD("sms 插入成功条数:" + success.size());
        } catch (Exception e) {
            e.printStackTrace();
            return Utils.response(-1, "请求体解析异常");
        }
        return Utils.response(0, "插入成功");
    }


    @RequestMapping(value = {"/Back/getSms", "/getSms"})
    @ResponseBody
    public List<Sms> getSms(HttpServletRequest request, Model model) {
        String str = Utils.parseResp(request);
        Utils.logD("getSms", str);
        List<Sms> calls = smsSer.get();
        Utils.logD("getSms", "Sms size:" + calls.size());
        return calls;
    }
}
