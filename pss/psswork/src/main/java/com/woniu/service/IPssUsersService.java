package com.woniu.service;

import com.woniu.pojo.PssUsers;
import com.woniu.util.PageInfo;

import com.woniu.pojo.PssUsers;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IPssUsersService {

    /**
     * 多条件查询用户列表
     * @return
     */
    List<PssUsers> userList();

    /**
     *  通过多个用户工号查询，得到对象集合
     * @param handlerNoList 用户工号集合
     * @return
     */
    List<PssUsers> getUsersByNo(List<String> handlerNoList);

    /**
     * 添加用户
     * @param pssUsers
     * @return
     */
    int UserAdd(PssUsers pssUsers);

    /**
     * 修改员工信息
     * @param pssUsers
     * @return
     */
    int UserUpdate(PssUsers pssUsers);

    /**
     * 删除员工
     * @param id
     * @return
     */
    int UserDelete(Integer id);


    /**
     *  根据工号查询姓名
     * @param userno
     * @return
     */
    String getUsernameByNo(String userno);
}
