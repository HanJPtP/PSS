package com.woniu.dao.mysql;

import com.woniu.pojo.PssBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
@Component
public interface PssBusinessMapper extends BaseMapper<PssBusiness> {
    /**
     * 查询所有的新型业务类型
     * @return
     */
    default List<PssBusiness> queryAllBusiness(){
        return this.selectList(null);
    }

}
