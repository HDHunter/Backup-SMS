package com.testSSM.test.dao;

import com.testSSM.test.model.Sms;

import java.util.List;


public interface SmsMapper {
    int add(Sms sms);

    int add(List<Sms> sms);
}