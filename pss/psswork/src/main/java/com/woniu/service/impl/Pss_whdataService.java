package com.woniu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssWhdataMapper;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssWhdata;
import com.woniu.service.IPss_whdataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class Pss_whdataService implements IPss_whdataService {
    @Resource
    private PssWhdataMapper whdataMapper;

    /**
     * 根据四库工具名查询并分页
     * @param pageNo
     * @param pageSize
     * @param dataname
     * @return
     */
    @Override
    public PageInfo<PssWhdata> ListWhdata(Integer pageNo, Integer pageSize, String dataname) {
        Page<PssWhdata> page = new Page<>(pageNo,pageSize);
        IPage<PssWhdata> psswhdataList = whdataMapper.ListWhdata(page,dataname);
        PageInfo<PssWhdata> pageInfo = new PageInfo<>(psswhdataList);
        return pageInfo;
    }

    /**
     * 添加四库
     * @param pssWhdata
     * @return
     */
    @Override
    public int addWhdata(PssWhdata pssWhdata) {
        return whdataMapper.addWhdata(pssWhdata);
    }

    /**
     * 修改四库数据
     * @param pssWhdata
     * @return
     */
    @Override
    public int updateWhdata(PssWhdata pssWhdata) {
        return whdataMapper.updateWhdata(pssWhdata);
    }

    /**
     * 删除四库数据
     * @param id
     * @return
     */
    @Override
    public int deleteWhdata(Integer id) {
        return whdataMapper.deleteWhdata(id);
    }

    /**
     *  根据工具编号查询对象
     * @param no
     * @return
     */
    @Override
    public PssWhdata getByNo(String no) {
        return whdataMapper.getByNo(no);
    }

    /**
     * 根据id查四库信息
     * @param id
     * @return
     */
    @Override
    public PssWhdata queryData(Integer id) {
        return whdataMapper.queryData(id);
    }


    /**
     * 修改库存 增加
     * @param tools
     * @param nums
     * @return
     */
    @Override
    public int UpdateNums(String tools, Integer nums) {
        return whdataMapper.UpdateNums(tools, nums);
    }


    /**
     * 修改库存 减少
     * @param tools
     * @param nums
     * @return
     */
    @Override
    public int delNums(String tools, Integer nums) {
        return whdataMapper.delNums(tools, nums);
    }
}
