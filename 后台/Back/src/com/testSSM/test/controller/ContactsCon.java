package com.testSSM.test.controller;

import com.alibaba.fastjson.JSONArray;
import com.testSSM.test.model.Contacts;
import com.testSSM.test.service.ContactsSer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class ContactsCon {
    @Resource
    @Autowired
    private ContactsSer contactsSer;

    @RequestMapping(value = {"/Back/contact", "/contact"})
    @ResponseBody
    public String contact(HttpServletRequest request, Model model) {
        List<Map<String, String>> list2 = new ArrayList<>();
        try {
            String str = Utils.parseResp(request);
            List<Object> list = JSONArray.parseArray(str, Object.class);

            for (Object obj : list) {
                Map<String, String> item = (Map<String, String>) obj;
                list2.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Contacts contacts = new Contacts();
        for (Map<String, String> map2 : list2) {
            if (map2.get("number") == null || map2.get("number").equals("") || map2.get("number").equals("null")) {
                contacts.setNumber("0");
            } else {
                contacts.setNumber1(map2.get("number"));
            }
            if (map2.get("name") == null || map2.get("name").equals("null") || map2.get("name").equals("")) {
                contacts.setDisplayName("");
            } else {
                contacts.setDisplayName(map2.get("name"));
            }
            int aa = contactsSer.add(contacts);
            if (aa != 1 || contactsSer.add(contacts) != -1) {
                if (aa != -1) {
                    System.out.println("存储报错" + contactsSer.add(contacts));
                    return "<h1>Fail</h1>";
                }
            }
        }
        return "<h1>OK</h1>";
    }


}
