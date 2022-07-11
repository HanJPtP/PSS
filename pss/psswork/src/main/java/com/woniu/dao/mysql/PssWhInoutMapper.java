package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssToolsNums;

import com.woniu.pojo.PssWhInout;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssWhInoutMapper extends BaseMapper<PssWhInout> {


    /**
     * 出入库分页
     * @param page
     * @param intime
     * @return
     */
    IPage<PssWhInout> ListWhinout(Page<PssWhInout> page, @Param("intime") Date intime, @Param("outtime")Date outtime);

    /**
     * 添加出入库信息
     * @param pssWhInout
     * @return
     */
    default int addInout(PssWhInout pssWhInout){
        return this.insert(pssWhInout);
    }

    /**
     * 根据id修改出入库数据
     * @param pssWhInout
     * @return
     */
    default int updateInout(PssWhInout pssWhInout){
        return updateById(pssWhInout);
    }
    /**
     * 删除出入库信息
     * @param id
     * @return
     */
    default int delInout(Integer id){
        return this.deleteById(id);
    }


    /**
     *工单申详情
     * @param
     * @return
     */
    @Select("SELECT pit.*,ptn.username,ptn.nums,ptn.tools,ptn.returntime,ptn.borrowtime,pw.dataname,pw.standard,pot.typename " +
            "from pss_wh_inout pit LEFT JOIN pss_tools_nums ptn on pit.orderno = ptn.orderno " +
            "LEFT JOIN pss_whdata pw on pw.whnameno = ptn.tools LEFT JOIN pss_order_type pot on pot.id = ptn.ordertypeid " +
            "where pit.orderno like concat('%',#{orderno},'%')")
    List<PssWhInout> pssinoutInfor(String orderno);



}
