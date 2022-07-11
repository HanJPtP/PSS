package com.woniu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssStaffsMapper;
import com.woniu.pojo.PssStaffs;
import com.woniu.service.IPssStaffsService;
import com.woniu.util.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
@Transactional
public class PssStaffsService implements IPssStaffsService {

    @Resource
    private PssStaffsMapper pssStaffsMapper;


    /**
     * 多条件查询员工列表（分页）
     * @param userid
     * @param postid
     * @param username
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<PssStaffs> staffList(Integer userid, Integer postid, String username,
                                        Integer pageNo, Integer pageSize) {

        Page<PssStaffs> page=new Page<>(pageNo,pageSize);
        IPage<PssStaffs> iPage = pssStaffsMapper.staffList(userid, postid, username, page);
        PageInfo<PssStaffs> pageInfo = new PageInfo<>(iPage);
        return pageInfo;
    }

    @Override
    public int staffAdd(PssStaffs pssStaffs) {
        return pssStaffsMapper.staffAdd(pssStaffs);
    }

    @Override
    public int staffUpdate(PssStaffs pssStaffs) {
        return pssStaffsMapper.staffUpdate(pssStaffs);
    }

    @Override
    public int staffDelete(Integer id) {
        return pssStaffsMapper.staffDelete(id);
    }
}
