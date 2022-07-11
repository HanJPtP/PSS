package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssToolsNums;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-06
 */
public interface PssToolsNumsMapper extends BaseMapper<PssToolsNums> {

    /**
     *  根据工单编号查询 对象集合
     * @param orderno
     * @return
     */
    default List<PssToolsNums> listToolsNums(String orderno){
        QueryWrapper<PssToolsNums> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("orderno", orderno);
        return this.selectList(queryWrapper);
    }

    /**
     *工单申请列表
     * @param page
     * @return
     */
    @Select("SELECT ptn.*,typename,dataname,standard FROM (pss_tools_nums ptn LEFT JOIN pss_order_type pot on " +
            "ptn.ordertypeid = pot.id LEFT JOIN pss_whdata pda on ptn.tools=pda.whnameno")
    IPage<PssToolsNums> ListWhtoolnum(Page<PssToolsNums> page);


    /**
     *工单申详情
     * @param
     * @return
     */
    @Select("SELECT ptn.*,typename,dataname,standard,whhouseid FROM pss_tools_nums ptn LEFT JOIN pss_order_type pot on " +
            " ptn.ordertypeid = pot.id LEFT JOIN pss_whdata pda on ptn.tools=pda.whnameno  where orderno=#{orderno}")
    List<PssToolsNums> toolnumInfor(String orderno);



    /**
     *  新增对象数据
     * @param pssToolsNums
     * @return
     */
    default int addToolsNums(PssToolsNums pssToolsNums){
        return this.insert(pssToolsNums);
    }

    /**
     *  修改状态为已归还
     * @param orderno
     * @return
     */
    @Update("update pss_tools_nums set status=2 where orderno=#{orderno}")
    int updateStatus(String orderno);

    /**
     *  查看归还状态
     * @param orderno
     * @return
     */
    @Select("select `status` from pss_tools_nums where orderno=#{orderno} limit 0,1")
    int getStatus(String orderno);

}
