package com.woniu.dao.redis;

import com.woniu.util.UserPerms;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermsRedisDao extends CrudRepository<UserPerms,Integer> {
}
