package com.woniu.service;

import com.woniu.pojo.PssBusiness;

import java.util.List;

public interface IPssBusinessService {
    // 新型业务类型列表
    List<PssBusiness> queryAllBusiness();
}
