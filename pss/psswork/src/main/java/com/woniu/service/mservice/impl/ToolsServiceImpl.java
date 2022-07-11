package com.woniu.service.mservice.impl;

import com.woniu.dao.mysql.PssWhdataMapper;
import com.woniu.pojo.PssWhdata;
import com.woniu.service.mservice.IToolsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ToolsServiceImpl implements IToolsService {
    private final PssWhdataMapper pssWhdataMapper;

    /**
     *  通过工具编号集合得到对象集合
     * @param toolsNoList   工具编号集合
     * @return
     */
    @Override
    public List<PssWhdata> getToolsByNo(List<String> toolsNoList) {
        return pssWhdataMapper.getToolsByNo(toolsNoList);
    }
}
