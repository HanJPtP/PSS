package com.woniu.dao.mysql;

import com.woniu.pojo.PssPerms;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqi
 * @since 2022-05-05
 */
public interface PssPermsMapper extends BaseMapper<PssPerms> {

    //根据用户id查询用户权限code
    List<String> listPermsByUserid(Integer userid);

    //根据用户名查询用户的权限生成菜单
    @Select("select p.* from pss_perms p left join pss_user_perm up on p.id=up.permid left join pss_users u on u.id=up.userid " +
            "where account=#{account}")
    List<PssPerms> PermsMenu(@Param("account")String account);

    //权限列表（全）
    default List<PssPerms> listPerms(){
        return this.selectList(null);
    }

    //根据用户id查询用户权限id
    @Select("select permid from pss_user_perm where userid=#{userid}")
    List<Integer> UserPermByUserId(@Param("userid")Integer userid);

    //根据用户id删除所有权限
    @Delete("delete from pss_user_perm where userid=#{userid}")
    int delPermByUserId(@Param("userid")Integer userid);

    //根据用户id批量添加权限
    int addPermByUserId(@Param("userid")Integer userid,@Param("permids") String[] permids);


}
