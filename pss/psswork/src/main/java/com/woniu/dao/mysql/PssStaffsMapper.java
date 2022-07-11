package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssStaffs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工表
 */
public interface PssStaffsMapper extends BaseMapper<PssStaffs> {

    //员工列表
    IPage<PssStaffs> staffList(@Param("userid")Integer userid,
                              @Param("postid")Integer postid,
                              @Param("username") String username,
                              Page<PssStaffs> UsersPage);

    //添加员工
    default int staffAdd(PssStaffs pssStaffs){
        return this.insert(pssStaffs);
    }

    //修改员工
    default int staffUpdate(PssStaffs pssStaffs){
        return this.updateById(pssStaffs);
    }

    //删除员工
    default int staffDelete(Integer id){
        return this.deleteById(id);
    }


}
