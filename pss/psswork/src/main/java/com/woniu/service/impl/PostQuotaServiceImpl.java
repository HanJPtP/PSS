package com.woniu.service.impl;

import com.woniu.dao.mysql.PssPerfMapper;
import com.woniu.dao.mysql.PssPostQuotaMapper;
import com.woniu.dao.redis.UserRedisDao;
import com.woniu.pojo.PssPerf;
import com.woniu.pojo.PssQuota;
import com.woniu.pojo.PssStaffs;
import com.woniu.pojo.PssUsers;
import com.woniu.service.IPostQuotaService;
import com.woniu.util.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PostQuotaServiceImpl implements IPostQuotaService {
    private final PssPerfMapper pssPerfMapper;
    private final PssPostQuotaMapper pssPostQuotaMapper;
    private final UserRedisDao userRedisDao;
    private final PssStaffsService pssStaffsService;
    public PostQuotaServiceImpl(PssPerfMapper pssPerfMapper, PssPostQuotaMapper pssPostQuotaMapper, UserRedisDao userRedisDao, PssStaffsService pssStaffsService) {
        this.pssPerfMapper = pssPerfMapper;
        this.pssPostQuotaMapper = pssPostQuotaMapper;
        this.userRedisDao = userRedisDao;
        this.pssStaffsService = pssStaffsService;
    }

    @Override
    public PageInfo<PssStaffs> getStaffsIsName(String account, Integer pageNo, Integer pageSize) {
        Optional<PssUsers> byId = userRedisDao.findById(account);
        Integer pId = byId.get().getPId();
        PageInfo<PssStaffs> pssStaffsPageInfo =null;
        if (pId == 1){
            System.out.println("==="+account);
            pssStaffsPageInfo = pssStaffsService.staffList(-1, -1, "", pageNo, pageSize);

        }else {
            pssStaffsPageInfo = pssStaffsService.staffList(-1, pId, "", pageNo, pageSize);
        }
        return pssStaffsPageInfo;
    }

    /**
     * 根据当前登录人的postid查询当前员工要考核的选项
     * @param
     * @return
     */
    @Override
    public List<PssQuota> getQuotaListByPostid(String account) {
        Optional<PssUsers> byId = userRedisDao.findById(account);
        Integer pId = byId.get().getPId();
        return pssPostQuotaMapper.getQuotaListByPostid(pId);
    }

    /**
     * 添加考核评分
     * @param
     * @return
     */
    @Override
    public int savePerf(List<PssPerf> perfList) {
        return pssPerfMapper.savePerf(perfList);
    }

}
