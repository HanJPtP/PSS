package com.woniu.controller.mcontroller;

import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssDoorArear;
import com.woniu.service.mservice.impl.PssCourtsService2Impl;
import com.woniu.service.mservice.impl.PssDoorAreaServiceImpl;
import com.woniu.util.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/doorarea")
public class DoorAreaController {
    private final PssDoorAreaServiceImpl pssDoorAreaService;

    private final PssCourtsService2Impl pssCourtsService;


    /**
     *  多条件查询
     * @param pageNo
     * @param pageSize
     * @param doorno
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('doorarea:list')")
    public ResponseResult<Object> listPssOrderElec(@RequestParam(value = "pageNo",required = false,defaultValue = "1") Integer pageNo,
                                                   @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "doorno",required = false,defaultValue = "") String doorno) {
        PageInfo<PssDoorArear> pageInfo = pssDoorAreaService.listDoorArea(pageNo, pageSize, doorno);

        //将数据存入map
        Map map = new HashMap<String,Object>();

        //得到所有台区名称
        List<String> listAllCname = pssCourtsService.listAllCname();

        map.put("listAllCname",listAllCname);

        ResponseResult<Object> responseResult = null;
        if(pageInfo.getData().size() > 0){
            map.put("pageInfo",pageInfo);
            responseResult = new ResponseResult<>(200,map);
        }else{
            responseResult = new ResponseResult<>(2001,"查无数据",map);
        }

        return responseResult;
    }

    /**
     *  新增
     * @param pssDoorArear
     * @return
     */
    @PostMapping("add")
    public ResponseResult<Void> addOrderElec(@RequestBody PssDoorArear pssDoorArear){
        int rs = pssDoorAreaService.addDoorArea(pssDoorArear);
        ResponseResult<Void> responseResult = null;

        if(rs > 0){
            responseResult = new ResponseResult<>(200,"新增成功");
        }else{
            responseResult = new ResponseResult<>(2001,"新增失败");
        }

        return responseResult;
    }

    /**
     *  修改
     * @param pssDoorArear
     * @return
     */
    @PostMapping("update")
    public ResponseResult<Void> updateOrderElec(@RequestBody PssDoorArear pssDoorArear){
        int rs = pssDoorAreaService.updateDoorArea(pssDoorArear);
        ResponseResult<Void> responseResult = null;

        if(rs > 0){
            responseResult = new ResponseResult<>(200,"修改成功");
        }else{
            responseResult = new ResponseResult<>(2001,"修改失败");
        }

        return responseResult;
    }

    /**
     *  删除
     * @param id
     * @return
     */
    @GetMapping("delete")
    public ResponseResult<Void> deleteOrderElec(@RequestParam(value = "id") Integer id){
        int rs = pssDoorAreaService.deleteDoorArea(id);
        ResponseResult<Void> responseResult = null;

        if(rs > 0){
            responseResult = new ResponseResult<>(200,"删除成功");
        }else{
            responseResult = new ResponseResult<>(2001,"删除失败");
        }
        return responseResult;
    }
}
