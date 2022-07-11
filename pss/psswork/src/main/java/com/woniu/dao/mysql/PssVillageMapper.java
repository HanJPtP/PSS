package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssBmessage;
import com.woniu.pojo.PssCircuit;
import com.woniu.pojo.PssVillage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-06
 */
@Component
public interface PssVillageMapper extends BaseMapper<PssVillage> {
    /**
     * 多条件关联查询台区信息并分页
     * @param page
     * @param cname
     * @param circuit
     * @param village
     * @param username
     * @return
     */
    IPage<PssVillage> getVillageQuery(Page<PssVillage> page,
                                      @Param("cname")String cname,
                                      @Param("circuit")String circuit,
                                      @Param("village")String village,
                                      @Param("username")String username);

    // 生成行政村的方法
    int saveVillage( List<PssVillage> villageList);

    // 删除台区行政村
    default int delVillage(Integer id){
        return this.deleteById(id);
    }

    // 删除台区路线
    default int delCircuit(Integer circuitid){
        QueryWrapper<PssVillage> wrapper = new QueryWrapper<>();
        wrapper.eq("circuitid", circuitid);
        return this.delete(wrapper);
    }
    // 修改台区的行政村
    default int updVillage(PssVillage pssVillage){
        return this.updateById(pssVillage);
    }
}
