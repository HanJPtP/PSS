package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.pojo.PssWarehouse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssWarehouseMapper extends BaseMapper<PssWarehouse> {

    /**
     * 显示四库种类
     * @param
     * @return
     */
    default List<PssWarehouse> listWhtype(){
        return this.selectList(null);
    }

    /**
     *  根据id查询名字
     * @param id
     * @return
     */
    @Select("select whname from pss_warehouse where id=#{id}")
    String getStyleName(Integer id);

}
