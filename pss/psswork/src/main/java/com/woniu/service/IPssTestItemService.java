package com.woniu.service;

import com.woniu.fo.ItemCountsFo;
import com.woniu.fo.ItemCreateTestPaperFo;
import com.woniu.fo.ItemFo;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssTestItem;
import com.woniu.fo.ItemUpdateFo;
import com.woniu.vo.ItemTestPaperVo;
import com.woniu.vo.ItemVo;

import java.util.HashMap;
import java.util.List;

public interface IPssTestItemService {
    /**
     * 查询所有的psstestitem值
     * @param pageNo
     * @param pageSize
     * @param itemName
     * @param typeid
     * @return
     * @throws Exception
     */
    PageInfo<PssTestItem> getPssTestItemByPageAndWrapper(Integer pageNo,Integer pageSize,
                                                         String itemName,Integer typeid)throws Exception;
    /**
     * 新增题目
     * @param itemfo
     * @return
     */
    int insertItem(ItemFo itemfo)throws Exception;
    /**
     * 修改题目
     * @param pssTestItem
     * @return
     */
    int updateItemById(PssTestItem pssTestItem)throws Exception;
    /**
     * 删除题目为不可用状态
     * @param id
     * @return
     */
    int delItemById(Integer id,String status)throws Exception;
    /**
     * 根据id查询题目
     * @param id
     * @return
     * @throws Exception
     */
    ItemVo getItemVoById(Integer id)throws Exception;
    /**
     * 修改的时候改变了题目的类型
     */
    int updateItemToChangeType(ItemUpdateFo itemUpdateVo)throws Exception;

    /**
     * 得到不同种类题目的数量
     * @return
     * @throws Exception
     */
    HashMap<Integer, ItemCountsFo> getPssTestItemCountsByTypeId()throws Exception;

    /**
     * 生成试卷的show
     * @param itemTypeChooseCounts
     * @return
     * @throws Exception
     */

    List<ItemTestPaperVo> showTheTestItemPaperVo(HashMap<Integer,ItemCountsFo> itemTypeChooseCounts)throws Exception;


    /**
     * 新增试卷和题目,同个上传的excel表格
     */
    int insertTestPaperItem(List<ItemCreateTestPaperFo> itemCreateTestPaperFoList)throws Exception;



}
