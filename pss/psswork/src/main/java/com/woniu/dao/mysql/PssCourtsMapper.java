package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniu.pojo.PssCourts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Select;

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
public interface PssCourtsMapper extends BaseMapper<PssCourts> {
    //添加台区的方法
    default int saveCourts(PssCourts pssCourts){
        int i = this.insert(pssCourts);
        Integer courtsId = null;
        if (i>0){
            courtsId = pssCourts.getId();
        }
        System.out.println("===="+courtsId);
        return courtsId;
    }
    // 根据台区名称查询台区的id
    default Integer getCourtsName(String cname){
        QueryWrapper<PssCourts> wrapper = new QueryWrapper<>();
        wrapper.eq("cname", cname);
        PssCourts pssCourts = this.selectOne(wrapper);
        Integer id = pssCourts.getId();
        return id;
    }
    // 根据台区名称删除台区
    default int delCourtsName(String cname){
        QueryWrapper<PssCourts> wrapper = new QueryWrapper<>();
        wrapper.eq("cname", cname);
        return this.delete(wrapper);
    }
    // 修改台区的方法
    default int updCourts(PssCourts pssCourts){
        return this.updateById(pssCourts);
    }

    /**
     *  查询所有的台区名称
     * @return
     */
    @Select("select cname from pss_courts")
    List<String> listAllCname();

}
