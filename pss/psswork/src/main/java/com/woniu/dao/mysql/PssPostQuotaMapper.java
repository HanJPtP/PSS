package com.woniu.dao.mysql;

import com.woniu.pojo.PssPostQuota;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.pojo.PssQuota;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-14
 */
@Component
public interface PssPostQuotaMapper extends BaseMapper<PssPostQuota> {
    // 根据当前登录人的postid查询当前员工要考核的选项
    List<PssQuota> getQuotaListByPostid(Integer postid);
}
