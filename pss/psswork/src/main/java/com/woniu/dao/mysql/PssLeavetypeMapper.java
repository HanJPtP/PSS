package com.woniu.dao.mysql;

import com.woniu.pojo.PssLeavetype;
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
public interface PssLeavetypeMapper extends BaseMapper<PssLeavetype> {
    /**
     * 显示请假事由
     * @return
     */
    default List<PssLeavetype> leavetypeList(){
        return this.selectList(null);
    }

}
