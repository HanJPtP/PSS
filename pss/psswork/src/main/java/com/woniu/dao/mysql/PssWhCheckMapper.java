package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssWhCheck;
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
public interface PssWhCheckMapper extends BaseMapper<PssWhCheck> {

    /**
     * 检验表单列表
     * @param page
     * @param account
     * @return
     */
    IPage<PssWhCheck> listCheck(Page<PssWhCheck> page, @Param("account") String account);


    /**
     *工单申详情
     * @param
     * @return
     */
    @Select("SELECT pwc.*,ptn.username,ptn.nums,ptn.tools,ptn.borrowtime,pw.dataname,pw.standard from  pss_wh_check pwc LEFT JOIN " +
            "pss_tools_nums ptn on pwc.orderno = ptn.orderno LEFT JOIN pss_whdata pw on pw.whnameno = ptn.tools  " +
            "where pwc.orderno like concat('%',#{orderno},'%')")
    List<PssWhCheck> psscheckInfor(String orderno);


    /**
     * 删除校验数据
     * @param id
     * @return
     */
    default int checkDel(Integer id){
        return this.deleteById(id);
    }




    /**
     * 添加四库
     * @param pssWhCheck
     * @return
     */
    default int addWhcheck(PssWhCheck pssWhCheck){
        return this.insert(pssWhCheck);
    }

    /**
     * 根据id修改四库数据
     * @param pssWhCheck
     * @return
     */
    default int updateWhcheck(PssWhCheck pssWhCheck){
        return updateById(pssWhCheck);
    }



}
