package com.testSSM.test.controller;

import com.alibaba.fastjson.JSONArray;
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
public class ImgCon {
    @Resource
    @Autowired
    private ContactsSer contactsSer;

    @RequestMapping(value = {"/Back/img", "/img"})
    @ResponseBody
    public String img(HttpServletRequest request, Model model) {
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
        return "<h1>OK</h1>";
    }

}
