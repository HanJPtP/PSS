package com.woniu.service;

import com.woniu.pojo.PssCircuit;

import java.util.List;

public interface IPssCircuitService {
    // 生成路线的方法
    int saveCircuit( List<PssCircuit> circuitList);
    // 根据路线名称查询路线的id
    Integer getCircuitName(String circuit,Integer courtsid);
}
