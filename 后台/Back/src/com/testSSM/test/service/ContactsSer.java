package com.testSSM.test.service;

import com.testSSM.test.dao.ContactsMaper;
import com.testSSM.test.model.Contacts;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ContactsSer implements ContactsMaper {
    @Resource
    @Autowired
    private ContactsMaper contactsMaper;

    @Override
    public int phone(Contacts con) {
        System.out.println(con.toString());
        return contactsMaper.phone(con);
    }
}
