package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.pojo.PssBmessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *  新型业务信息
 * @author liuqi
 * @since 2022-05-05
 */
@Component
public interface PssBmessageMapper extends BaseMapper<PssBmessage> {
    /**
     * 添加新型业务下载地址
     */
    default int addFilecontent(PssBmessage pssBmessage){
        return this.insert(pssBmessage);
    }
    /**
     * 多条件分页显示新型业务列表信息
     */
    IPage<PssBmessage> getBmessageList(Page<PssBmessage> page,
                                       @Param("btitle")String btitle,
                                       @Param("busname")String busname,
                                       @Param("username")String username);
    /**
     * 添加新型业务列表信息
     */
    default int saveBmessage(PssBmessage pssBmessage){
        return this.insert(pssBmessage);
    }
    /**
     * 修改新型业务列表信息
     */
    default int updBmessage(PssBmessage pssBmessage){
        return this.updateById(pssBmessage);
    }
    /**
     * 删除新型业务列表信息
     */
    default int delBmessage(Integer id){
        return this.deleteById(id);
    }
}
