package com.woniu.service;

import com.woniu.pojo.PssCheckwork;

import java.util.List;

public interface IPssCheckworkService {
    List<PssCheckwork> getCheckworkByUserid(Integer userid);
}
