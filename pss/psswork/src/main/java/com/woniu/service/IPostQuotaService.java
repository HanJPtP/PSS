package com.woniu.service;

import com.woniu.pojo.PssPerf;
import com.woniu.pojo.PssQuota;
import com.woniu.pojo.PssStaffs;
import com.woniu.util.PageInfo;

import java.util.List;

public interface IPostQuotaService {
    // 显示需要绩效考核的员工
    PageInfo<PssStaffs> getStaffsIsName(String account,Integer pageNo, Integer pageSize);

    // 根据当前登录人的postid查询当前员工要考核的选项
    List<PssQuota> getQuotaListByPostid(String account);

    // 添加考核评分
    int savePerf(List<PssPerf> perfList);
}
