package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssWhdata;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssWhdataMapper extends BaseMapper<PssWhdata> {

    /**
     *  通过工具编号集合得到对象集合
     * @param toolsNoList   工具编号集合
     * @return
     */
    default List<PssWhdata> getToolsByNo(List<String> toolsNoList){
        QueryWrapper<PssWhdata> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("whnameno", toolsNoList);
        return this.selectList(queryWrapper);
    }

    /**
     *  根据工具编号查询对象
     * @param no
     * @return
     */
    default PssWhdata getByNo(String no){
        QueryWrapper<PssWhdata> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("whnameno", no);
        return this.selectOne(queryWrapper);
    }

    /**
     * 根据四库物品名查询并分页
     * @param page
     * @param dataname
     * @return
     */
    IPage<PssWhdata> ListWhdata(Page<PssWhdata> page, @Param("dataname")String dataname);

    /**
     * 添加四库
     * @param pssWhdata
     * @return
     */
    default int addWhdata(PssWhdata pssWhdata){
        return this.insert(pssWhdata);
    }

    /**
     * 根据id修改四库数据
     * @param pssWhdata
     * @return
     */
    default int updateWhdata(PssWhdata pssWhdata){
        return updateById(pssWhdata);
    }


    /**
     * 根据id删除四库数据
     * @param id
     * @return
     */
    default int deleteWhdata(Integer id){
        return deleteById(id);
    }



    /**
     * 根据id查四库信息
     * @param id
     * @return
     */
    default PssWhdata queryData(Integer id){
        return this.selectById(id);
    }


    @Update("update pss_whdata set numbers=numbers+#{nums} where whnameno = #{tools}")
    Integer UpdateNums(@Param("tools")String tools,@Param("nums")Integer nums);

    @Update("update pss_whdata set numbers=numbers-#{nums} where whnameno = #{tools}")
    Integer delNums(@Param("tools")String tools,@Param("nums")Integer nums);

}
