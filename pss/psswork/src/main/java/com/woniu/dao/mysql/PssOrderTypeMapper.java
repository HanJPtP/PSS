package com.woniu.dao.mysql;

import com.woniu.pojo.PssOrderTask;
import com.woniu.pojo.PssOrderType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssOrderTypeMapper extends BaseMapper<PssOrderType> {

    /**
     *  查询所有订单类型
     * @return
     */
    default List<PssOrderType> listPssOrderType(){
        return this.selectList(null);
    }

    /**
     *  根据id查询
     * @param ordertypeid
     * @return
     */
    default PssOrderType getByordertypeid(Integer ordertypeid){
        return this.selectById(ordertypeid);
    }

}
