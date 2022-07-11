package com.woniu.service;

import com.woniu.pojo.PssPerms;
import java.util.List;

public interface IPssPermsService {

    //根据用户名查询权限生成菜单
    List<PssPerms> PermsMenu(String account);

    //权限列表（全）
    List<PssPerms> listPerms();

    //根据用户id查询用户权限id
    List<Integer> UserPermByUserId(Integer userid);

    //根据用户id批量添加权限
    int addPermByUserId(Integer userid, String[] permids);

}
