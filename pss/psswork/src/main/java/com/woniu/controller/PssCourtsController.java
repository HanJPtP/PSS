package com.woniu.controller;

import com.woniu.pojo.PssCourts;
import com.woniu.pojo.PssVillage;
import com.woniu.service.impl.PssBusinessServiceImpl;
import com.woniu.service.impl.PssCourtsServiceImpl;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PssCourtsController {
    private final PssCourtsServiceImpl pssCourtsService;

    public PssCourtsController(PssCourtsServiceImpl pssCourtsService) {
        this.pssCourtsService = pssCourtsService;
    }

    @PreAuthorize("hasAuthority('platform:add')")
    @PostMapping("/courts/add")
    public ResponseResult<Object> saveCourts(@RequestBody PssCourts courts){
        Integer courtsid = pssCourtsService.saveCourts(courts);
        ResponseResult<Object> responseResult = null;
        if (courtsid != null){
            responseResult = new ResponseResult<>(200, "OK", courtsid);
        }else {
            responseResult = new ResponseResult<>(2002, "添加台区业务失败,请联系管理员!!!");
        }
        return responseResult;
    }
    @PreAuthorize("hasAuthority('platform:update')")
    @PostMapping("/courts/upd")
    public ResponseResult<Void> updCourts(@RequestBody PssVillage pssvillage){
        int i = pssCourtsService.updCourts(pssvillage);
        ResponseResult<Void> responseResult = null;
        if (i > 0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(2002, "修改台区失败,请联系管理员!!!");
        }
        return responseResult;
    }

    /**
     * 根据台区的名称查询id的方法
     */
    @GetMapping ("/courts/getbyId")
    public ResponseResult<Object> getCourtsById(String cname){
        System.out.println("台区的名称是:"+cname);
        Integer courtsid = pssCourtsService.getCourtsName(cname);
        ResponseResult<Object> responseResult = null;
        if (courtsid != null){
            responseResult = new ResponseResult<>(200, "OK", courtsid);
        }else {
            responseResult = new ResponseResult<>(2002, "查询台区id失败,请联系管理员!!!");
        }
        return responseResult;
    }
    /**
     * 判断是否有添加台区权限
     */
    @PreAuthorize("hasAuthority('platform:add')")
    @PostMapping("/courts/isAdd")
    public ResponseResult<Void> IsCourtsAdd(){
        return new ResponseResult<>(200, "OK");
    }

    /**
     * 判断是否有添加台区权限
     */
    @PreAuthorize("hasAuthority('platform:update')")
    @PostMapping("/courts/isUpd")
    public ResponseResult<Void> IsCourtsUpd(){
        return new ResponseResult<>(200, "OK");
    }
}
