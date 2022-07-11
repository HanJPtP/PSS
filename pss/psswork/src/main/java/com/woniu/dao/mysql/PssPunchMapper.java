package com.woniu.dao.mysql;

import com.woniu.pojo.PssPunch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssPunchMapper extends BaseMapper<PssPunch> {

    default int changePunch(PssPunch pssPunch){
        return this.updateById(pssPunch);
    }
}
