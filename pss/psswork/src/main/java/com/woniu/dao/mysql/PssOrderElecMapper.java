package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.pojo.PssOrderElec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface PssOrderElecMapper extends BaseMapper<PssOrderElec> {

    /**
     *  多条件查询
     * @param page
     * @param userno
     * @param areano
     * @param electricno
     * @return
     */
    IPage<PssOrderElec> listOrderElec(IPage<PssOrderTask> page, @Param("userno") String userno,
                                      @Param("areano") String areano,
                                      @Param("electricno") String electricno);

    /**
     *  新增
     * @param pssOrderElec
     * @return
     */
    default int addOrderElec(PssOrderElec pssOrderElec){
        return this.insert(pssOrderElec);
    }

    /**
     *  修改
     */
    default int updateOrderElec(PssOrderElec pssOrderElec){
        return this.updateById(pssOrderElec);
    }

    /**
     *  删除
     */
    default int deleteOrderElec(Integer id){
        return this.deleteById(id);
    }

}
