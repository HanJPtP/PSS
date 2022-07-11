package com.woniu.service;
import com.woniu.fo.CircuitFo;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssVillage;

import java.util.List;

public interface IPssVillageService {
    /**
     * 多条件关联查询台区信息并分页
     * @param pageNo
     * @param pageSize
     * @param cname
     * @param circuit
     * @param village
     * @param username
     * @return
     */
    PageInfo<PssVillage> getVillageQuery(Integer pageNo, Integer pageSize,
                                         String cname, String circuit,
                                         String village, String username);
    // 生成行政村的方法
    int saveVillage(List<CircuitFo> circuitItem);
    // 删除台区行政村
    int delVillage(Integer id);
    // 删除台区路线及行政村
    int delCircuit(PssVillage pssVillage);
    // 删除台区,路线,及行政村
    int delCourts(PssVillage pssVillage);
}
