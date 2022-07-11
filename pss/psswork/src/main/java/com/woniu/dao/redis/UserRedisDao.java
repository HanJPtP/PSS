package com.woniu.dao.redis;

import com.woniu.pojo.PssUsers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRedisDao extends CrudRepository<PssUsers,String> {
}
