package com.woniu.controller.mcontroller;

import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssOrderElec;
import com.woniu.pojo.PssUsers;
import com.woniu.service.mservice.impl.PssCourtsService2Impl;
import com.woniu.service.mservice.impl.PssOrderElecServiceImpl;
import com.woniu.service.mservice.impl.PssOrderProcessServiceImpl;
import com.woniu.util.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/elec")
@Slf4j
@RequiredArgsConstructor
public class OrderElecController {

    private final PssOrderElecServiceImpl pssOrderElecService;

    private final PssOrderProcessServiceImpl pssOrderProcessService;

    private final PssCourtsService2Impl pssCourtsService;

    /**
     *  多条件查询
     * @param pageNo
     * @param pageSize
     * @param userno
     * @param areano
     * @param electricno
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('elec:list')")
    public ResponseResult<Object> listPssOrderElec(@RequestParam(value = "pageNo",required = false,defaultValue = "1") Integer pageNo,
                                                   @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "userno",required = false,defaultValue = "") String userno,
                                                   @RequestParam(value = "areano",required = false,defaultValue = "") String areano,
                                                   @RequestParam(value = "electricno",required = false,defaultValue = "") String electricno) {
        PageInfo<PssOrderElec> pageInfo = pssOrderElecService.listOrderElec(pageNo, pageSize, userno, areano, electricno);

        //将数据存入map
        Map map = new HashMap<String,Object>();

        //得到所有普通用户
        List<PssUsers> usersList = pssOrderProcessService.getOrderUsers();

        //得到所有台区名称
        List<String> listAllCname = pssCourtsService.listAllCname();

        map.put("usersList",usersList);
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
     * @param pssOrderElec
     * @return
     */
    @PostMapping("add")
    public ResponseResult<Void> addOrderElec(@RequestBody PssOrderElec pssOrderElec){
        int rs = pssOrderElecService.addOrderElec(pssOrderElec);
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
     * @param pssOrderElec
     * @return
     */
    @PostMapping("update")
    public ResponseResult<Void> updateOrderElec(@RequestBody PssOrderElec pssOrderElec){
        int rs = pssOrderElecService.updateOrderElec(pssOrderElec);
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
        int rs = pssOrderElecService.deleteOrderElec(id);
        ResponseResult<Void> responseResult = null;

        if(rs > 0){
            responseResult = new ResponseResult<>(200,"删除成功");
        }else{
            responseResult = new ResponseResult<>(2001,"删除失败");
        }
        return responseResult;
    }
}
