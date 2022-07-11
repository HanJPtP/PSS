package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.pojo.PssDoorArear;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.pojo.PssOrderElec;
import com.woniu.pojo.PssOrderTask;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssDoorArearMapper extends BaseMapper<PssDoorArear> {

    /**
     *  多条件查询
     * @param page
     * @param doorno
     * @return
     */
    IPage<PssDoorArear> listDoorArea(IPage<PssDoorArear> page, @Param("doorno") String doorno);


    /**
     *  新增
     * @param pssDoorArear
     * @return
     */
    default int addDoorArea(PssDoorArear pssDoorArear){
        return this.insert(pssDoorArear);
    }

    /**
     *  修改
     * @param pssDoorArear
     * @return
     */
    default int updateDoorArea(PssDoorArear pssDoorArear){
        return this.updateById(pssDoorArear);
    }

    /**
     *  删除
     * @param id
     * @return
     */
    default int deleteDoorArea(Integer id){
        return this.deleteById(id);
    }

}
