package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.pojo.PssCircuit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface PssCircuitMapper extends BaseMapper<PssCircuit> {
    // 生成路线的方法
    int saveCircuit( List<PssCircuit> circuitList);
    // 根据路线名称查询路线的id
    default Integer getCircuitName(String circuit){
        QueryWrapper<PssCircuit> wrapper = new QueryWrapper<>();
        wrapper.eq("circuit", circuit);
        PssCircuit pssCircuit = this.selectOne(wrapper);
        Integer id =pssCircuit.getId();
        return id;
    }

    // 删除台区路线
    default int delCircuitName(String circuit){
        QueryWrapper<PssCircuit> wrapper = new QueryWrapper<>();
        wrapper.eq("circuit", circuit);
        return this.delete(wrapper);
    }
    // 删除台区路线(根据台区courtsid删除)
    default int delCircuitByCourtsid(Integer courtsid){
        QueryWrapper<PssCircuit> wrapper = new QueryWrapper<>();
        wrapper.eq("courtsid", courtsid);
        return this.delete(wrapper);
    }
    // 根据路线名称查询路线的id
    default Integer getCircuitByName(String circuit,Integer courtsid){
        QueryWrapper<PssCircuit> wrapper = new QueryWrapper<>();
        wrapper.eq("circuit", circuit).eq("courtsid", courtsid);
        PssCircuit pssCircuit = this.selectOne(wrapper);
        Integer id =pssCircuit.getId();
        return id;
    }
    // 修改台区路线名称
    default int updCircuit(PssCircuit pssCircuit){
        return this.updateById(pssCircuit);
    }
}
