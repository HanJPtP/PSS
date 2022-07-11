package com.woniu.dao.mysql;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.ApplicationApp;
import com.woniu.pojo.PssVillage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = {ApplicationApp.class})
class PssVillageMapperTest {
    @Resource
    private PssVillageMapper pssVillageMapper;

    @Test
    public void test01(){
        Page<PssVillage> page = new Page<>(1, 2);
        IPage<PssVillage> iPage = pssVillageMapper.getVillageQuery(page, "金融港-a", "", "", "");
        System.out.println(iPage.toString());
    }
}