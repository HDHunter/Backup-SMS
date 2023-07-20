package com.testSSM.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.testSSM.test.model.Contacts;
import com.testSSM.test.model.HttpResponse;
import com.testSSM.test.service.ContactsSer;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * number作为排除标识
 */
@Controller
public class ContactsCon {


    @Resource
    @Autowired
    private ContactsSer contactsSer;

    @RequestMapping(value = {"/Back/contact", "/contact"})
    @ResponseBody
    public HttpResponse contact(HttpServletRequest request, Model model) {
        List<Map<String, String>> list2 = new ArrayList<>();
        try {
            String str = Utils.parseResp(request);
            JSONObject list = JSON.parseObject(str);

            for (String obj : list.keySet()) {
                Map<String, String> item = (Map<String, String>) list.get(obj);
                list2.add(item);
            }
            Utils.logD("contact 上传条数:" + list2.size());
            Contacts contacts = new Contacts();
            List<Contacts> contactsList = contactsSer.get();
            List<Contacts> success = new ArrayList<>();
            Map<String, Contacts> maps = new HashMap<>();
            Utils.logD("Contacts 已经保存条数:" + contactsList.size());
            for (Contacts c : contactsList) {
                maps.put(c.getNumber(), c);
            }
            int index = contactsList.size();
            for (Map<String, String> map2 : list2) {
                contacts.setNumber(map2.get("number"));
                if (contacts.getNumber() == null || maps.get(contacts.getNumber()) != null) {
                    Utils.logE("扔掉一条:" + map2);
                    continue;
                }
                contacts.setNumber1(map2.get("number1"));
                contacts.setNumber2(map2.get("number2"));
                String contactId = map2.get("contactId");
                if (contactId == null || contactId.equals("")) {
                    contacts.setContactId(index);
                } else {
                    contacts.setContactId(Integer.parseInt(contactId));
                }
                contacts.setCompany(map2.get("company"));
                contacts.setDepartment(map2.get("department"));
                contacts.setCustomProtocol(map2.get("customProtocol"));
                contacts.setJob(map2.get("job"));
                contacts.setDisplayName(map2.get("displayName"));
                contacts.setIdentity(map2.get("identity"));
                contacts.setEmailAddress(map2.get("emailAddress"));
                contacts.setLabel(map2.get("label"));
                contacts.setEmailAddressDisplayName(map2.get("emailAddressDisplayName"));
                contacts.setGroupId(map2.get("groupId"));
                contacts.setNote(map2.get("note"));
                contacts.setNamespace(map2.get("namespace"));
                contacts.setProtocol(map2.get("protocol"));
                contacts.setLastName(map2.get("lastName"));
                contacts.setFirstName(map2.get("firstName"));
                contacts.setNickName(map2.get("nickName"));
                contacts.setJobDescription(map2.get("jobDescription"));
                contacts.setNumberType(map2.get("numberType"));
                contacts.setPhotoUri(map2.get("photoUri"));
                contacts.setWebUrl(map2.get("webUrl"));
                contacts.setRelationName(map2.get("relationName"));
                try {
                    if (contactsSer.add(contacts) != 1) {
                        Utils.logE("存储报错");
                        return Utils.response(-1, "数据库插入失败");
                    }
                    index += 1;
                    success.add(contacts);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Utils.response(-1, "数据库操作失败");
                }
            }
            Utils.logD("contacts 插入成功条数:" + success.size());
        } catch (Exception e) {
            e.printStackTrace();
            return Utils.response(-1, "请求体解析异常");
        }
        return Utils.response(0, "插入成功");
    }


    @RequestMapping(value = {"/Back/getContact", "/getContact"})
    @ResponseBody
    public List<Contacts> getContact(HttpServletRequest request, Model model) {
        String str = Utils.parseResp(request);
        Utils.logD("getContact", str);
        List<Contacts> calls = contactsSer.get();
        Utils.logD("getContact", "contact size:" + calls.size());
        return calls;
    }
}
