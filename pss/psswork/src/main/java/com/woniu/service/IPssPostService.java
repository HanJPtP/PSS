package com.woniu.service;

import com.woniu.pojo.PssPost;

import java.util.List;

public interface IPssPostService {

    //部门列表
    List<PssPost> postList(Integer parentId);
}
