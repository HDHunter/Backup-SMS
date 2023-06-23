package com.testSSM.test.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.testSSM.test.dao.ContactsMaper;
import com.testSSM.test.model.Contacts;


@Service
public class ContactsSer {
    @Resource
    private ContactsMaper contactscaper;

    public int phone(Contacts con) {
        System.out.println(con.toString());
        return contactscaper.phone(con);
    }
}
