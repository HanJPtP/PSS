package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniu.pojo.PssTestBatch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.pojo.PssTestItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssTestBatchMapper extends BaseMapper<PssTestBatch> {
    /**
     * 查询和题目关联的pssTestBatch对象
     * @param testItemId
     * @return
     */
    default List<PssTestBatch> getPssTestBatchByTestItemId(Integer testItemId){
        QueryWrapper<PssTestBatch> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("testitemid", testItemId);
        return this.selectList(queryWrapper);
    }

    /**
     * 新增题目相关的考试存储
     * @param pssTestBatchs
     * @return
     */
    int updatePssTestBatchTestItemIdToAotherTestItemId(@Param("pssTestBatchs") List<PssTestBatch> pssTestBatchs);

    /**
     * 查询试卷里面的最大试卷id
     * @return
     */
    @Select("select max(testid) from pss_test_batch")
    Integer getMaxTestid();

    /**
     * 查询所有试卷
     * @return
     */
    default List<PssTestBatch> listAllPssTestBatch(){
        return this.selectList(null);
    }

    /**
     * 修改试卷状态
     */
    default int updatePssTestBatch(Integer testId, String status){
        UpdateWrapper<PssTestBatch> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("testid", testId);
        PssTestBatch pssTestBatch = new PssTestBatch();
        pssTestBatch.setStatus(status);
        return this.update(pssTestBatch, updateWrapper);
    }

    /**
     * 查询所有考试试卷id
     * @return
     */
    @Select("select testid from pss_test_batch group by testid")
    List<Integer> showTestItemPaperId();



}
