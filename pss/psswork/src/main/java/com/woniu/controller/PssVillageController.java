package com.woniu.controller;

import com.woniu.fo.CircuitFo;
import com.woniu.fo.ItemCircuitFo;
import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssVillage;
import com.woniu.service.impl.PssBmessageServiceImpl;
import com.woniu.service.impl.PssCircuitServiceImpl;
import com.woniu.service.impl.PssVillageServiceImpl;
import com.woniu.util.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PssVillageController {
    private final PssCircuitServiceImpl pssCircuitService;
    private final PssVillageServiceImpl pssVillageService;
    private final PssBmessageServiceImpl pssBmessageService;
    public PssVillageController(PssCircuitServiceImpl pssCircuitService, PssVillageServiceImpl pssVillageService, PssBmessageServiceImpl pssBmessageService) {
        this.pssCircuitService = pssCircuitService;
        this.pssVillageService = pssVillageService;
        this.pssBmessageService = pssBmessageService;
    }
    @GetMapping("/village/list")
    public ResponseResult<PageInfo> getVillageList(@RequestParam(name = "pageNo",required = false,defaultValue = "1") Integer pageNo,
                                               @RequestParam(name = "pageSize",required = false,defaultValue = "5")Integer pageSize,
                                               @RequestParam(name = "cname",required = false,defaultValue = "")String cname,
                                               @RequestParam(name = "circuit",required = false,defaultValue = "")String circuit,
                                               @RequestParam(name = "village",required = false,defaultValue = "")String village,
                                               @RequestParam(name = "username",required = false,defaultValue = "")String username,
                                               @RequestParam(name = "account",required = false,defaultValue = "")String account){
        PageInfo<PssVillage> pageInfo = pssVillageService.getVillageQuery(pageNo, pageSize, cname, circuit, village, username);
        Integer usersid = pssBmessageService.getAccount(account);
        ResponseResult<PageInfo> responseResult = null;
        if (pageInfo.getData().size()>0){
            responseResult = new ResponseResult<>(200, ""+usersid, pageInfo);
        }else {
            responseResult = new ResponseResult<>(2002, "????????????????????????,??????????????????!");
        }
        return responseResult;
    }

    /**
     * ?????????????????????
     */
    @PostMapping("/village/add")
    public ResponseResult<Void> saveVillage(@RequestBody ItemCircuitFo itemcircuit){

        System.out.println(itemcircuit);
        List<CircuitFo> circuitItem = itemcircuit.getCircuitItem();
        int i = pssVillageService.saveVillage(circuitItem);
        ResponseResult<Void> responseResult = null;
        if (i>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(2002, "????????????????????????,??????????????????!");
        }
        return responseResult;
    }
    /**
     * ????????????,??????,?????????
     */
    @PreAuthorize("hasAuthority('platform:delete')")
    @GetMapping("/village/del")
    public ResponseResult<Void> delCourts(Integer id){
        int i = pssVillageService.delVillage(id);
        ResponseResult<Void> responseResult = null;
        if (i>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(2002, "???????????????????????????,??????????????????!");
        }
        return responseResult;
    }
    /**
     * ????????????2,??????????????????
     */
    @PreAuthorize("hasAuthority('platform:delete')")
    @PostMapping("/circuit/del")
    public ResponseResult<Void> delCircuit(@RequestBody PssVillage village){
        int i = pssVillageService.delCircuit(village);
        ResponseResult<Void> responseResult = null;
        if (i>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(2002, "????????????????????????,??????????????????!");
        }
        return responseResult;
    }
    /**
     * ????????????3,???????????????,??????,?????????
     */
    @PreAuthorize("hasAuthority('platform:delete')")
    @PostMapping("/courts/del")
    public ResponseResult<Void> delCourts(@RequestBody PssVillage village){
        int i = pssVillageService.delCourts(village);
        ResponseResult<Void> responseResult = null;
        if (i>0){
            responseResult = new ResponseResult<>(200, "OK");
        }else {
            responseResult = new ResponseResult<>(2002, "??????????????????,??????????????????!");
        }
        return responseResult;
    }
}
