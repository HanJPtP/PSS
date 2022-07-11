package com.woniu.dao.mysql;

import com.woniu.pojo.PssUnit;
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
public interface PssUnitMapper extends BaseMapper<PssUnit> {
    default List<PssUnit> listUnit(){
        return   this.selectList(null);
    }
}
