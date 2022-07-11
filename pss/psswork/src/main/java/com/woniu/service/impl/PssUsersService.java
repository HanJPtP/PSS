package com.woniu.service.impl;

import com.woniu.dao.mysql.PssUsersMapper;
import com.woniu.pojo.PssUsers;
import com.woniu.service.IPssUsersService;
import com.woniu.util.PageInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class PssUsersService implements IPssUsersService {

    @Resource
    private PssUsersMapper pssUsersMapper;

    /**
     * 多条件查询用户列表
     * @return
     */
    @Override
    public List<PssUsers> userList() {
        return pssUsersMapper.userList();
    }

    /**
     *  根据工号查询姓名
     * @param userno
     * @return
     */
    @Override
    public String getUsernameByNo(String userno) {
        return pssUsersMapper.getUsernameByNo(userno);
    }


    /**
     *  通过多个用户工号查询，得到对象集合
     * @param handlerNoList 用户工号集合
     * @return
     */
    @Override
    public List<PssUsers> getUsersByNo(List<String> handlerNoList) {
        return pssUsersMapper.getUsersByNo(handlerNoList);
    }

    /**
     * 添加员工
     * @param pssUsers
     * @return
     */
    @Override
    public int UserAdd(PssUsers pssUsers) {
        return pssUsersMapper.UserAdd(pssUsers);
    }


    /**
     * 修改员工
     * @param pssUsers
     * @return
     */
    @Override
    public int UserUpdate(PssUsers pssUsers) {
        return pssUsersMapper.UserUpdate(pssUsers);
    }

    /**
     * 删除员工
     * @param id
     * @return
     */
    @Override
    public int UserDelete(Integer id) {
        return pssUsersMapper.UserDelete(id);
    }


}
