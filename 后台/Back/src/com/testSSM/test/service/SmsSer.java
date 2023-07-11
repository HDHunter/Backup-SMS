package com.testSSM.test.service;

import com.testSSM.test.controller.Utils;
import com.testSSM.test.dao.SmsMapper;
import com.testSSM.test.model.Sms;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SmsSer implements SmsMapper {
    @Resource
    @Autowired
    private SmsMapper smsMapper;


    @Override
    public int add(Sms sms) {
        Utils.logD("SmsSer", "add " + sms);
        return smsMapper.add(sms);
    }

    @Override
    public List<Sms> get() {
        Utils.logD("SmsSer", "get ");
        return smsMapper.get();
    }

}