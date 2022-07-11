package com.woniu.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.dao.mysql.PssBmessageMapper;
import com.woniu.dao.redis.UserRedisDao;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssBmessage;
import com.woniu.pojo.PssUsers;
import com.woniu.service.IPssBmessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class PssBmessageServiceImpl implements IPssBmessageService {

    private final PssBmessageMapper pssBmessageMapper;
    private final UserRedisDao userRedisDao;
    public PssBmessageServiceImpl(PssBmessageMapper pssBmessageMapper, UserRedisDao userRedisDao) {
        this.pssBmessageMapper = pssBmessageMapper;
        this.userRedisDao = userRedisDao;
    }

    /**
     * 添加新型业务下载地址
     * @param pssBmessage
     * @return
     */
    @Override
    public int addFilecontent(PssBmessage pssBmessage) {
        return pssBmessageMapper.addFilecontent(pssBmessage);
    }

    /**
     * 多条件分页查询新型业务列表信息
     * @param pageNo
     * @param pageSize
     * @param btitle
     * @param busname
     * @param username
     * @return
     */
    @Override
    public PageInfo<PssBmessage> getBmessageList(Integer pageNo, Integer pageSize, String btitle, String busname, String username) {
        Page<PssBmessage> page = new Page<>(pageNo, pageSize);
        IPage<PssBmessage> iPage = pssBmessageMapper.getBmessageList(page, btitle, busname, username);
        PageInfo<PssBmessage> pageInfo = new PageInfo<>(iPage);
        return pageInfo;
    }

    /**
     * 添加新型业务列表信息
     * @param pssBmessage
     * @return
     */
    @Override
    public int saveBmessage(PssBmessage pssBmessage) {
        return pssBmessageMapper.saveBmessage(pssBmessage);
    }

    /**
     * 修改新型业务列表信息
     * @param pssBmessage
     * @return
     */
    @Override
    public int updBmessage(PssBmessage pssBmessage) {
        return pssBmessageMapper.updBmessage(pssBmessage);
    }

    /**
     * 删除新型业务列表信息
     * @param id
     * @return
     */
    @Override
    public int delBmessage(Integer id) {
        return pssBmessageMapper.delBmessage(id);
    }
    /**
     * 根据当前账号查询当前用户的id
     */
    @Override
    public Integer getAccount(String account) {
        Optional<PssUsers> byId = userRedisDao.findById(account);
        return byId.get().getId();
    }
}
