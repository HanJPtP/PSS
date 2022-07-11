package com.woniu.service;


import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssWhdata;


public interface IPss_whdataService {
    /**
     * 四库列表
     * @param pageNo
     * @param pageSize
     * @param dataname
     * @return
     */
    PageInfo<PssWhdata> ListWhdata(Integer pageNo,Integer pageSize,String dataname);

    /**
     * 添加四库
     * @param pssWhdata
     * @return
     */
    int addWhdata(PssWhdata pssWhdata);


    /**
     * 根据id修改四库数据
     * @param pssWhdata
     * @return
     */
     int updateWhdata(PssWhdata pssWhdata);


    /**
     * 根据id删除四库数据
     * @param id
     * @return
     */
     int deleteWhdata(Integer id);

    /**
     *  根据工具编号查询对象
     * @param no
     * @return
     */
    PssWhdata getByNo(String no);


    /**
     * 根据id查四库信息
     * @param id
     * @return
     */
    PssWhdata queryData(Integer id);

    /**
     * 根据物品单号和数量增加库存
     * @param tools
     * @param nums
     * @return
     */
    int UpdateNums(String tools,Integer nums);


    /**
     * 根据物品单号和数量减少库存
     * @param tools
     * @param nums
     * @return
     */
    int delNums(String tools,Integer nums);
}
