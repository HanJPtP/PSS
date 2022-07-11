package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssUsers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssUsersMapper extends BaseMapper<PssUsers> {

    /**
     * 根据用户名查询用户信息
     * @param account
     * @return
     */
    default PssUsers getUserByAccount(String account){
        QueryWrapper<PssUsers> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account", account);
        return this.selectOne(queryWrapper);
    }

    /**
     * 管理员列表
     * @return
     */
    List<PssUsers> userList();


    /**
     *  通过多个用户工号查询，得到对象集合
     * @param handlerNoList 用户工号集合
     * @return
     */
    default List<PssUsers> getUsersByNo(List<String> handlerNoList){
        QueryWrapper<PssUsers> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("jobno", handlerNoList);
        return this.selectList(queryWrapper);
    }

    /**
     * 添加员工
     * @param pssUsers
     * @return
     */
    default int UserAdd(PssUsers pssUsers){
        return this.insert(pssUsers);
    }

    /**
     * 修改员工信息
     * @param pssUsers
     * @return
     */
    default int UserUpdate(PssUsers pssUsers){
        return this.updateById(pssUsers);
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    default int UserDelete(Integer id){
        return this.deleteById(id);
    }

    /**
     *  得到所有状态为'y'的普通用户
     * @return
     */
    @Select("select * from pss_users where status='y'")
    List<PssUsers> getOrderUsers();

    /**
     *  根据工号查询姓名
     * @param userno
     * @return
     */
    @Select("select username from pss_users where jobno=#{userno}")
    String getUsernameByNo(String userno);
}
