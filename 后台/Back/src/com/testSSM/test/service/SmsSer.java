package com.testSSM.test.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testSSM.test.dao.SmsMaper;
import com.testSSM.test.model.Sms;


@Service
public class SmsSer implements SmsMaper {
    @Resource
    @Autowired
    private SmsMaper smsMaper;

    @Override
    public int Sms(Sms sms) {
        System.out.println(sms.toString());
        return smsMaper.Sms(sms);
    }

}
