package com.woniu.service.mservice.impl;

import com.woniu.dao.mysql.PssOrderTypeMapper;
import com.woniu.pojo.PssOrderType;
import com.woniu.service.mservice.IPssOrderType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PssOrderTypeServiceImpl implements IPssOrderType {
    private final PssOrderTypeMapper pssOrderTypeMapper;

    /**
     *  查询所有订单类型
     * @return
     */
    @Override
    public List<PssOrderType> listPssOrderType() {
        return pssOrderTypeMapper.listPssOrderType();
    }

    /**
     *  根据id查询
     * @param ordertypeid
     * @return
     */
    @Override
    public PssOrderType getByordertypeid(Integer ordertypeid) {
        return pssOrderTypeMapper.getByordertypeid(ordertypeid);
    }
}
