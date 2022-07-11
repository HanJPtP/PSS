package com.woniu.dao.mysql;

import com.woniu.pojo.PssApprovaltype;
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
public interface PssApprovaltypeMapper extends BaseMapper<PssApprovaltype> {
    /**
     * 显示审批类型表
     * @return
     */
    default List<PssApprovaltype> getApprovalType(){
        return this.selectList(null);
    }
}
