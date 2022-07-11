package com.woniu.service.impl;

import com.woniu.dao.mysql.PssPermsMapper;
import com.woniu.pojo.PssPerms;
import com.woniu.service.IPssPermsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PssPermsService implements IPssPermsService {

    @Resource
    private PssPermsMapper pssPermsMapper;

    /**
     * 根据用户名查询权限生成菜单
     * @param account
     * @return
     */
    @Override
    public List<PssPerms> PermsMenu(String account) {
        //原始权限
        List<PssPerms> permsMenu = pssPermsMapper.PermsMenu(account);

        //一级菜单
        List<PssPerms> rootMenu = new ArrayList<>();

        //储存一级菜单
        for (PssPerms rm:permsMenu){
            if (rm.getParentid()==null){
                rootMenu.add(rm);
            }
        }

        //循环一级菜单
        for (PssPerms parent:rootMenu){
            //二级菜单
            List<PssPerms> childMenu = new ArrayList<>();
            //保存二级菜单
            for (PssPerms child:permsMenu){
                if (parent.getId()==child.getParentid()){
                    childMenu.add(child);
                }
            }
            //将二级菜单保存到一级菜单里
            parent.setChildList(childMenu);
        }
        return rootMenu;
    }

    /**
     * 权限列表（全）
     * @return
     */
    @Override
    public List<PssPerms> listPerms() {
        List<PssPerms> permsList = pssPermsMapper.listPerms();

        //保存所有的一级权限
        List<PssPerms> preList=new ArrayList<>();

        //找到所有的一级权限
        for(PssPerms rp:permsList){
            if(rp.getParentid()==null){
                preList.add(rp);
            }
        }

        //找到第二级，看第二级里面有没有第三级
        //有第三级，将第二级变为第三级的父亲
        for(PssPerms root:preList){
            //调用查询下一级的方法
            root.setChildList(getChildPerms(root.getId(), permsList));
        }
        return preList;
    }
    /**
     *  查找子级方法，递归算法
     * @param pid
     * @param permsList
     * @return
     */
    public List<PssPerms> getChildPerms(Integer pid,List<PssPerms> permsList){

        //存放子级集合
        List<PssPerms> childList = new ArrayList<>();

        //查询所有权限，根据父级id，查询该父级下的所有子级
        for (PssPerms ch: permsList){
            if(ch.getParentid()==pid){
                childList.add(ch);
            }
        }
        //获得子元素的子对象
        for(PssPerms p : childList){
            p.setChildList(getChildPerms(p.getId(), permsList));
        }

        //终止的时机，当查询到的子级中没有数据了，就结束
        if(childList.size() <= 0){
            return null;
        }
        return childList;
    }

    /**
     * 根据用户id查询用户权限id
     * @param userid
     * @return
     */
    @Override
    public List<Integer> UserPermByUserId(Integer userid) {
        return pssPermsMapper.UserPermByUserId(userid);
    }

    /**
     * 根据用户id批量添加权限
     * @param userid
     * @param permids
     * @return
     */
    @Override
    public int addPermByUserId(Integer userid, String[] permids) {
        pssPermsMapper.delPermByUserId(userid);
        return pssPermsMapper.addPermByUserId(userid, permids);
    }


}
