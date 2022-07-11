package com.woniu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssStaffs;
import com.woniu.util.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IPssStaffsService {

    //多条件查询员工列表
    PageInfo<PssStaffs> staffList(Integer userid, Integer postid, String username,
                                 Integer pageNo, Integer pageSize);


    int staffAdd(PssStaffs pssStaffs);

    int staffUpdate(PssStaffs pssStaffs);

    int staffDelete(Integer id);

}
