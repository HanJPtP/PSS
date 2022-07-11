package com.woniu.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssBmessage;
import org.apache.ibatis.annotations.Param;

public interface IPssBmessageService {
    /**
     * 添加新型业务下载地址
     */
    int addFilecontent(PssBmessage pssBmessage);
    /**
     * 多条件分页显示新型业务列表信息
     */
    PageInfo<PssBmessage> getBmessageList(Integer pageNo, Integer pageSize
                                            ,String btitle,String busname, String username);
    /**
     * 添加新型业务列表信息
     */
    int saveBmessage(PssBmessage pssBmessage);
    /**
     * 修改新型业务列表信息
     */
    int updBmessage(PssBmessage pssBmessage);
    /**
     * 删除新型业务列表信息
     */
    int delBmessage(Integer id);

    //根据当前用户的账号查询当前用户的id
    Integer getAccount(String account);

}
