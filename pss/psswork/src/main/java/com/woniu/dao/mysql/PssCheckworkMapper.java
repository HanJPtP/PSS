package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.pojo.PssCheckwork;
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
public interface PssCheckworkMapper extends BaseMapper<PssCheckwork> {
    /**
     * 显示当前用户的考勤信息
     * @param userid
     * @return
     */
    default List<PssCheckwork> getCheckworkByUserid(Integer userid){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("userid", userid);
        return this.selectList(wrapper);
    }
}
