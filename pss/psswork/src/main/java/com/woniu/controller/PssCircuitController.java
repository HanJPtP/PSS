package com.woniu.controller;

import com.woniu.pojo.PssCircuit;
import com.woniu.pojo.PssCourts;
import com.woniu.service.impl.PssCircuitServiceImpl;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PssCircuitController {
    private final PssCircuitServiceImpl pssCircuitService;
    public PssCircuitController(PssCircuitServiceImpl pssCircuitService) {
        this.pssCircuitService = pssCircuitService;
    }

    @GetMapping("/circuit/add")
    public ResponseResult<Object> saveCircuitList(@RequestParam(name = "courtsid",required = false,defaultValue = "") Integer courtsid,
                                                @RequestParam(name = "circuitStr",required = false,defaultValue = "") String circuitStr){
        String[] circuitItem = circuitStr.split(",");
        List<PssCircuit> circuitList = new ArrayList<>();
        for (int i = 0; i < circuitItem.length; i++) {
            PssCircuit pssCircuit = new PssCircuit();
            pssCircuit.setCourtsid(courtsid);
            pssCircuit.setCircuit(circuitItem[i]);
            circuitList.add(pssCircuit);
        }
        int i = pssCircuitService.saveCircuit(circuitList);
        ResponseResult<Object> responseResult = null;
        if (i>0){
            responseResult = new ResponseResult<>(200, "OK",circuitList);
        }else{
            responseResult = new ResponseResult<>(2002, "添加台区路线失败,请联系管理员!!!");
        }
        return responseResult;
    }
    /**
     * 根据台区的名称查询id的方法
     */
    @GetMapping ("/circuit/byId")
    public ResponseResult<Object> getCircuitById(@RequestParam(name = "circuit",defaultValue = "",required = false) String circuit,
                                                 @RequestParam(name = "courtsid",required = false,defaultValue = "")Integer courtsid){
        System.out.println("台区路线的名称是:"+circuit+",台区的id为:"+courtsid);
        Integer circuitid = pssCircuitService.getCircuitName(circuit,courtsid);
        ResponseResult<Object> responseResult = null;
        if (circuitid !=null){
            responseResult = new ResponseResult<>(200, "OK", circuitid);
        }else {
            responseResult = new ResponseResult<>(2002, "查询台区路线id失败,请联系管理员!!!");
        }
        return responseResult;
    }
}
