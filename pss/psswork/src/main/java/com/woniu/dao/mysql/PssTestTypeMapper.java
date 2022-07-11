package com.woniu.dao.mysql;

import com.woniu.pojo.PssTestType;
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
public interface PssTestTypeMapper extends BaseMapper<PssTestType> {
    /**
     * 查询所有的题目类型
     * @return
     */
    default List<PssTestType> listAllPssTestType(){
        return this.selectList(null);
    }

}
