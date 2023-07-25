package com.testSSM.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.testSSM.test.biz.Utils;
import com.testSSM.test.model.Call;
import com.testSSM.test.model.HttpResponse;
import com.testSSM.test.service.CallsSer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * date日期时间作为排除标识
 */
@Controller
public class CallsCon {


    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    @Autowired
    private CallsSer callsSer;

    @RequestMapping(value = {"/Back/calls", "/calls"})
    @ResponseBody
    public HttpResponse call(HttpServletRequest request, Model model) {
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
            Utils.logD("call 上传条数:" + list2.size());
            Call call = new Call();
            List<Call> calls = callsSer.get();
            List<Call> success = new ArrayList<>();
            Map<String, Call> maps = new HashMap<>();
            Utils.logD("call 已经保存条数:" + calls.size());
            for (Call c : calls) {
                maps.put(c.getDate(), c);
            }
            int index = calls.size();
            for (Map<String, String> map2 : list2) {
                call.setNumber(map2.get("number"));
                if (map2.get("id") == null || map2.get("id").equals("")) {
                    call.setId(index);
                } else {
                    call.setId(Integer.parseInt(map2.get("id")));
                }
                if (map2.get("type") == null || map2.get("type").equals("")) {
                    call.setType(0);
                } else {
                    call.setType(Integer.parseInt(map2.get("type")));
                }
                String res;
                try {
                    long lt = Long.parseLong(map2.get("date"));
                    Date date = new Date(lt);
                    res = simpleDateFormat.format(date);
                    call.setDate(res);
                } catch (Exception e) {
                    call.setDate(map2.get("date"));
                }
                if (call.getDate() == null || maps.get(call.getDate()) != null) {
                    Utils.logE("扔掉一条:" + map2);
                    continue;
                }
                call.setDuration(Integer.parseInt(map2.get("duration")));
                call.setNumber(map2.get("number"));
                try {
                    if (callsSer.add(call) != 1) {
                        Utils.logE("存储报错");
                        return Utils.response(-1, "数据库插入失败");
                    }
                    index += 1;
                    success.add(call);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Utils.response(-1, "数据库操作失败");
                }
            }
            Utils.logD("call 插入成功条数:" + success.size());
        } catch (Exception e) {
            e.printStackTrace();
            return Utils.response(-1, "请求体解析异常");
        }
        return Utils.response(0, "插入成功");
    }

    @RequestMapping(value = {"/Back/getCalls", "/getCalls"})
    @ResponseBody
    public List<Call> getCalls(HttpServletRequest request, Model model) {
        String str = Utils.parseResp(request);
        Utils.logD("getCalls", str);
        List<Call> calls = callsSer.get();
        Utils.logD("getCalls", "calls size:" + calls.size());
        return calls;
    }

}
