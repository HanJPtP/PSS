package com.woniu.service.impl;

import com.woniu.dao.mysql.PssWarehouseMapper;
import com.woniu.pojo.PssWarehouse;
import com.woniu.service.IPssWarehourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PssWarehourseService implements IPssWarehourseService {

    @Resource
    private PssWarehouseMapper warehouseMapper;

    /**
     * 四库类型列表
     * @param
     * @return
     */
    @Override
    public List<PssWarehouse> listWhtype() {
        return warehouseMapper.listWhtype();
    }


    /**
     *  根据id查询名字
     * @param id
     * @return
     */
    @Override
    public String getStyleName(Integer id) {
        return warehouseMapper.getStyleName(id);
    }


}
