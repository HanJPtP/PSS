package com.woniu.service.mservice;

import com.woniu.pojo.PssWhdata;

import java.util.List;

public interface IToolsService {
    /**
     *  通过工具编号集合得到对象集合
     * @param toolsNoList   工具编号集合
     * @return
     */
    List<PssWhdata> getToolsByNo(List<String> toolsNoList);
}
