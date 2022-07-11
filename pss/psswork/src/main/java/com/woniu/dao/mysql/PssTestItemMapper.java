package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssTestItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssTestItemMapper extends BaseMapper<PssTestItem> {

    /**
     * 查询所有的psstestitem值
     * @param page
     * @param queryWrapper
     * @return
     */
    Page<PssTestItem> getPssTestItemByWrapper(Page<PssTestItem> page, @Param(Constants.WRAPPER) QueryWrapper<PssTestItem> queryWrapper);

    /**
     * 新增题目
     * @param pssTestItem
     * @return
     */
    default  int insertItem(PssTestItem pssTestItem){
        return this.insert(pssTestItem);
    }

    /**
     * 修改题目
     * @param pssTestItem
     * @return
     */
    default int updateItemById(PssTestItem pssTestItem){
        return this.updateById(pssTestItem);
    }

    /**
     * 修改子选项的状态
     * @param parentid
     * @return
     */
    default int updateItemByParent(Integer parentid,String status){
        UpdateWrapper<PssTestItem> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("parentid",parentid);
        PssTestItem pssTestItem = new PssTestItem();
        pssTestItem.setStatus(status);
        return this.update(pssTestItem,updateWrapper);
    }
    /**
     * 删除题目
     * @param id
     * @return
     */
    default int delItemById(Integer id){
        QueryWrapper<PssTestItem> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return this.delete(queryWrapper);
    }

    /**
     * 删除子选项
     * @param parentid
     * @return
     */
    default int delItemByParentId(Integer parentid){
        QueryWrapper<PssTestItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentid", parentid);
        return this.delete(queryWrapper);
    }

    /**
     * 查询题目
     * @param id
     * @return
     */
    default PssTestItem getPssTestItemById(Integer id){
        return this.selectById(id);
    }

    /**
     * 根据parentid查询子选项
     * @param parentid
     * @return
     */
    default List<PssTestItem> getPssTestItemByParentId(Integer parentid){
        QueryWrapper<PssTestItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentid", parentid);
        return this.selectList(queryWrapper);
    }

    /**
     * 统计各种类型可用的题目的数量
     * @param typeid
     * @return
     */
    default Integer getPssTestItemsTypeCount(Integer typeid){
        QueryWrapper<PssTestItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("parentid").eq("testtypeid", typeid).eq("status", "y");
        return this.selectCount(queryWrapper);
    }

    /**
     * 查询所有正常状态下的题目
     * @return
     */
    default List<PssTestItem> listAllTestItemWhileStatusIsY(){
        QueryWrapper<PssTestItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", "y").isNull("parentid");
        return this.selectList(queryWrapper);
    }

    /**
     * 增加多条题目
     * @param pssTestItemList
     * @return
     */
    int insertListItemIntoMySql(@Param("pssTestItemList") List<PssTestItem> pssTestItemList);

}
