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

    public CallsSer(CallsMapper callsMapper) {
        this.callsMapper = callsMapper;
    }


    @Override
    public List<Call> get() {
        Utils.logD("CallsSer", "get ");
        return callsMapper.get();
    }

    @Override
    public int add(Call call) {
        Utils.logD("CallsSer", "add " + call);
        return callsMapper.add(call);
    }

}