package com.woniu.service.impl;


import com.woniu.dao.mysql.PssPermsMapper;
import com.woniu.dao.mysql.PssUsersMapper;
import com.woniu.dao.redis.PermsRedisDao;
import com.woniu.dao.redis.UserRedisDao;
import com.woniu.pojo.PssUsers;
import com.woniu.util.UserPerms;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Resource
    private PermsRedisDao permsRedisDao;

    @Resource
    private UserRedisDao userRedisDao;

    @Resource
    private PssUsersMapper userMapper;

    @Resource
    private PssPermsMapper permsMapper;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);

        PssUsers pssUser = null;
        try {
            //先在redis里找该账户
            pssUser=userRedisDao.findById(username).orElseThrow(()->new UsernameNotFoundException("找不到该用户"));
        }catch (Exception e){
            // 使用用户输入的账号到数据库中查询，如果有，则账号存在；如果没有，则账号不存在
            pssUser = userMapper.getUserByAccount(username);
            System.out.println(pssUser);
            //存储用户
            userRedisDao.save(pssUser);
        }

        boolean bol=false;
        if(pssUser.getStatus().equals("y") || pssUser.getStatus().equals("s")){
            bol=true;
        }

        UserPerms userPerms=new UserPerms();
        try {
            //到redis中查询权限
            userPerms=permsRedisDao.findById(pssUser.getId()).orElseThrow(()->new NullPointerException());
        }catch (Exception e){
            // 根据用户id查询用户所有的权限
            List<String> authorityItem = permsMapper.listPermsByUserid(pssUser.getId());
            //将权限存储到userPerms中
            userPerms.setId(pssUser.getId());
            userPerms.setPermsList(authorityItem);
            //存储到redis
            permsRedisDao.save(userPerms);
        }

        //集合转字符串
        String authorityStr=String.join(",", userPerms.getPermsList());
        System.out.println(authorityStr);

        User user= new User(pssUser.getAccount(),pssUser.getPassword(), true, true, true,
                bol, AuthorityUtils.commaSeparatedStringToAuthorityList(authorityStr));

        return user;
    }
}
