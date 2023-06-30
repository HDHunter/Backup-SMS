package com.testSSM.test.service;

import com.testSSM.test.controller.Utils;
import com.testSSM.test.dao.CallsMapper;
import com.testSSM.test.model.Call;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CallsSer implements CallsMapper {
    @Resource
    @Autowired
    private CallsMapper callsMapper;


    @Override
    public List<Call> getAllCalls() {
        return null;
    }

    @Override
    public int add(Call call) {
        Utils.logD("CallsSer", "add " + call);
        return callsMapper.add(call);
    }

    @Override
    public int add(List<Call> calls) {
        Utils.logD("CallsSer", "add " + calls);
        return callsMapper.add(calls);
    }
}