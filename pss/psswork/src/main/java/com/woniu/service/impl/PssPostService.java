package com.woniu.service.impl;

import com.woniu.dao.mysql.PssPostMapper;
import com.woniu.pojo.PssPost;
import com.woniu.service.IPssPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PssPostService implements IPssPostService {

    @Resource
    private PssPostMapper pssPostMapper;

    /**
     * 职位列表
     * @return
     */
    @Override
    public List<PssPost> postList(Integer parentId) {
        return pssPostMapper.postList(parentId);
    }
}
