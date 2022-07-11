package com.woniu.service;

import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssApproval;

import java.util.List;

public interface IPssApprovalService {
    int addApproval(PssApproval approval);

   PageInfo<PssApproval> getApprovalList(Integer approvalid,Integer pageNo,Integer pageSize);

    int editApproval(PssApproval approval);
}
