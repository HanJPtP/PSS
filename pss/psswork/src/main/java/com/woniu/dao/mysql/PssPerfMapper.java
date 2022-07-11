package com.woniu.dao.mysql;

import com.woniu.pojo.PssPerf;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface PssPerfMapper extends BaseMapper<PssPerf> {
    // 根据传递的数据生成考核数据
    int savePerf(List<PssPerf> perfList);
}
