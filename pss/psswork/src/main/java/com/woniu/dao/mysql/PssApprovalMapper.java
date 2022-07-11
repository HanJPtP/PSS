package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssApproval;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssApprovalMapper extends BaseMapper<PssApproval> {
    /**
     * 添加审批信息
     * @param approval
     * @return
     */
    default int addApproval(PssApproval approval){
        return this.insert(approval);
    }

    /**
     * 显示所有的审批请求
     * @param approvalid
     * @return
     */
    IPage<PssApproval> getApprovalList(Page<PssApproval> page,@Param("approvalid") Integer approvalid);

    /**
     * 修改审批信息
     * @param approval
     * @return
     */
    default int editApproval(PssApproval approval){
        return this.updateById(approval);
    }
}
