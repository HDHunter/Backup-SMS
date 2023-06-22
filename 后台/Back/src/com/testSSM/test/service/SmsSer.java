package com.testSSM.test.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.testSSM.test.dao.SmsMaper;
import com.testSSM.test.model.Sms;


@Service
public class SmsSer {
    @Resource
    private SmsMaper smsmaper;

    public int sms(Sms sms) {
        System.out.println(sms.toString());
        return smsmaper.Sms(sms);
    }
}
