package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.pojo.PssPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *  职位
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssPostMapper extends BaseMapper<PssPost> {

    //职位列表
    default List<PssPost> postList(Integer parentId){
        QueryWrapper<PssPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        return this.selectList(queryWrapper);
    }

}
