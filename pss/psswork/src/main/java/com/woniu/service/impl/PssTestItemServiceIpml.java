package com.woniu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssTestBatchMapper;
import com.woniu.dao.mysql.PssTestItemMapper;
import com.woniu.dao.mysql.PssTestTypeMapper;
import com.woniu.fo.ItemCountsFo;
import com.woniu.fo.ItemCreateTestPaperFo;
import com.woniu.fo.ItemFo;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssTestBatch;
import com.woniu.pojo.PssTestItem;
import com.woniu.pojo.PssTestType;
import com.woniu.service.IPssTestItemService;
import com.woniu.fo.ItemUpdateFo;
import com.woniu.vo.ItemTestPaperVo;
import com.woniu.vo.ItemVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


@Service
@Transactional
public class PssTestItemServiceIpml implements IPssTestItemService {
    @Resource
    private PssTestItemMapper pssTestItemMapper;
    @Resource
    private PssTestTypeMapper pssTestTypeMapper;
    @Resource
    private PssTestBatchMapper pssTestBatchMapper;

    /**
     * 查询题目
     *
     * @param pageNo
     * @param pageSize
     * @param itemName
     * @param typeid
     * @return
     */
    @Override
    public PageInfo<PssTestItem> getPssTestItemByPageAndWrapper(Integer pageNo, Integer pageSize, String itemName, Integer typeid) {
        Page<PssTestItem> page = new Page<>(pageNo, pageSize);
        QueryWrapper<PssTestItem> queryWrapper = new QueryWrapper<>();
        if (itemName.trim().length() > 0) {
            queryWrapper.like("testname", itemName);
        }
        if (typeid != -1) {
            queryWrapper.eq("testtypeid", typeid);
        }
        queryWrapper.isNull("parentid");
        return new PageInfo<PssTestItem>(pssTestItemMapper.getPssTestItemByWrapper(page, queryWrapper));
    }

    /**
     * 新增题目
     *
     * @param itemfo
     * @return
     * @throws Exception
     */
    @Override
    public int insertItem(ItemFo itemfo) throws Exception {
        //获得题目
        PssTestItem pssTestItem = itemfo.getPssTestItem();
        pssTestItem.setStatus("y");
        //新增题目
        int i = pssTestItemMapper.insertItem(pssTestItem);
        //遍历子选项
        if (i >= 0 && itemfo.getPssTestItemList().size() > 0) {
            for (int t = 0; t < itemfo.getPssTestItemList().size(); t++) {
                //将子选项的父类关联起来
                PssTestItem pssTestItem1 = itemfo.getPssTestItemList().get(t);
                pssTestItem1.setParentid(pssTestItem.getId());
                pssTestItem1.setStatus("y");
                //存子选项
                i = pssTestItemMapper.insertItem(pssTestItem1);
            }
        }
        return i;
    }

    /**
     * 修改题目为判断题
     *
     * @param pssTestItem
     * @return
     * @throws Exception
     */
    @Override
    public int updateItemById(PssTestItem pssTestItem) throws Exception {
        pssTestItemMapper.delItemByParentId(pssTestItem.getId());
        return pssTestItemMapper.updateItemById(pssTestItem);
    }

    /**
     * 删除题目,把子选项也删除(修改这些题目的状态为禁用状态)
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public int delItemById(Integer id, String status) throws Exception {
        PssTestItem pssTestItem = new PssTestItem();
        pssTestItem.setId(id);
        pssTestItem.setStatus(status);
        //修改选项的状态
        pssTestItemMapper.updateItemByParent(id, status);
        //修改题目的状态
        return pssTestItemMapper.updateItemById(pssTestItem);
    }


    /**
     * 根据id查询题目(含子选项)
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ItemVo getItemVoById(Integer id) throws Exception {
        return new ItemVo(pssTestItemMapper.getPssTestItemById(id), pssTestItemMapper.getPssTestItemByParentId(id));
    }

    /**
     * 修改改变了题目类型的题目
     *
     * @param itemUpdateVo
     * @return
     * @throws Exception
     */
    @Override
    public int updateItemToChangeType(ItemUpdateFo itemUpdateVo) throws Exception {
        //查出关联表的里面的数据
        List<PssTestBatch> pssTestBatchByTestItemId = pssTestBatchMapper.getPssTestBatchByTestItemId(itemUpdateVo.getPssTestItem().getId());
        //删除子选项
        pssTestItemMapper.delItemByParentId(itemUpdateVo.getPssTestItem().getId());
        //删除自己
        pssTestItemMapper.delItemById(itemUpdateVo.getPssTestItem().getId());
        //新增自己
        itemUpdateVo.getPssTestItem().setId(null);
        pssTestItemMapper.insertItem(itemUpdateVo.getPssTestItem());
        //新增关联表数据
        if (pssTestBatchByTestItemId.size() > 0) {
            for (PssTestBatch ptb : pssTestBatchByTestItemId) {
                ptb.setTestitemid(itemUpdateVo.getPssTestItem().getId());
            }
            pssTestBatchMapper.updatePssTestBatchTestItemIdToAotherTestItemId(pssTestBatchByTestItemId);
        }
        //新增子选项
        int i = 0;
        for (PssTestItem pst : itemUpdateVo.getPssTestItems()) {
            pst.setId(null);
            pst.setParentid(itemUpdateVo.getPssTestItem().getId());
            i = pssTestItemMapper.insertItem(pst);
        }
        return i;
    }

    /**
     * 获得每个种类类型题目的数据
     *
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<Integer, ItemCountsFo> getPssTestItemCountsByTypeId() throws Exception {
        HashMap<Integer, ItemCountsFo> countByTypeid = new HashMap<>();
        List<PssTestType> pssTestTypes = pssTestTypeMapper.listAllPssTestType();
        for (PssTestType ptt : pssTestTypes) {
            ItemCountsFo itemCountsFo = new ItemCountsFo();
            itemCountsFo.setPssTestType(ptt);
            itemCountsFo.setCounts(pssTestItemMapper.getPssTestItemsTypeCount(ptt.getId()));
            countByTypeid.put(ptt.getId(), itemCountsFo);
        }

        return countByTypeid;
    }

    /**
     * 生成考试题目查看的
     *
     * @param itemTypeChooseCounts
     * @return
     * @throws Exception
     */
    @Override
    public List<ItemTestPaperVo> showTheTestItemPaperVo(HashMap<Integer, ItemCountsFo> itemTypeChooseCounts) throws Exception {
        Set<Integer> integers = itemTypeChooseCounts.keySet();
        //创建ItemTestPaperVo集合
        List<ItemTestPaperVo> itemTestPaperVoList = new ArrayList<>();
        //获得所有题目
        List<PssTestItem> pssTestItems = pssTestItemMapper.listAllTestItemWhileStatusIsY();
        //用来存储下标
        List<Integer> tmep = new ArrayList<>();
        //遍历set集合
        for (Integer i : integers) {
            List<ItemTestPaperVo> paperVoList = new ArrayList<>();
            //获得当前键下的值
            ItemCountsFo itemCountsFo = itemTypeChooseCounts.get(i);
            //传递过来的数量
            Integer counts = itemCountsFo.getCounts();
            //随机数
            Random random = new Random();
            if (counts != null) {
                while (paperVoList.size() < counts) {
                    //随机是第几个下标
                    int i1 = random.nextInt(pssTestItems.size());
                    //获得该随机下标的list集合中的对象
                    PssTestItem pssTestItem = pssTestItems.get(i1);
                    //判断题型是否相同
                    if (pssTestItem.getTesttypeid() == itemCountsFo.getPssTestType().getId()) {
                        //判断里面是否元素重复
                        if (!tmep.contains(i1)) {
                            ItemTestPaperVo itemTestPaperVo = new ItemTestPaperVo();
                            //存储题目
                            itemTestPaperVo.setPssTestItem(pssTestItem);
                            //查选项
                            List<PssTestItem> pssTestItemByParentId = pssTestItemMapper.getPssTestItemByParentId(pssTestItem.getId());
                            //存选项
                            itemTestPaperVo.setPssTestItems(pssTestItemByParentId);
                            //添加到集合
                            paperVoList.add(itemTestPaperVo);
                            //存下标(作为判断条件)
                            tmep.add(i1);
                        }
                    }
                }
                itemTestPaperVoList.addAll(paperVoList);
            }
        }

        return itemTestPaperVoList;
    }

    @Override
    public int insertTestPaperItem(List<ItemCreateTestPaperFo> itemCreateTestPaperFoList) throws Exception {
        /**
         * 遍历对象
         */
        int reslut = -1;
        for (int i = 0; i < itemCreateTestPaperFoList.size(); i++) {
            ItemCreateTestPaperFo itemCreateTestPaperFo = itemCreateTestPaperFoList.get(i);
            PssTestItem pssTestItem = itemCreateTestPaperFo.getPssTestItem();
            //将题目初始化成要求
            pssTestItem.setId(null);
            pssTestItem.setParentid(null);
            if (itemCreateTestPaperFo.getPssTestItems()!=null) {
                //计数器,用来判断式单选还是多选
                int counts = 0;
                //该题目不是判断题
                for (int j = 0; j < itemCreateTestPaperFo.getPssTestItems().size(); j++) {
                    if (itemCreateTestPaperFo.getPssTestItems().get(j).getTestyn().equals("y")) {
                        counts++;
                    }
                }
                if (counts == 1) {
                    //单选题
                    pssTestItem.setTesttypeid(1);
                } else if (counts >= 2) {
                    //多选题
                    pssTestItem.setTesttypeid(2);
                }
            } else {
                //该题目是判断题
                pssTestItem.setTesttypeid(3);
            }
            reslut = pssTestItemMapper.insertItem(pssTestItem);
            if (reslut > 0) {
                //遍历子选项
                List<PssTestItem> pssTestItems = itemCreateTestPaperFo.getPssTestItems();
                if (pssTestItems != null && pssTestItems.size() > 0) {
                    for (int j = 0; j < pssTestItems.size(); j++) {
                        PssTestItem pssTestItem1 = pssTestItems.get(j);
                        pssTestItem1.setParentid(pssTestItem.getId());
                        pssTestItem1.setTesttypeid(pssTestItem.getTesttypeid());
                    }
                    reslut=pssTestItemMapper.insertListItemIntoMySql(pssTestItems);
                }
            }
        }
        return reslut;
    }

}
