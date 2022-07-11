package com.woniu.controller;

import com.woniu.pojo.PssPunch;
import com.woniu.service.impl.PssPunchServiceImpl;
import com.woniu.util.OrderUtil;
import com.woniu.util.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
public class PunchController {
    @Resource
    private PssPunchServiceImpl pssPunchService;

    /**
     * 上班打卡
     * @param pssPunch
     * @return
     */
    @ResponseBody
    public ResponseResult<Void> punchUp(@RequestBody PssPunch pssPunch){
        pssPunch.setPunchno(OrderUtil.getPunchNo());
        pssPunch.setStarttime(LocalDateTime.now());
        int n = pssPunchService.changePunch(pssPunch);
        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200,"上班成功");
        }else {
            responseResult = new ResponseResult<>(2003,"上班失败");
        }
        return responseResult;
    }

    /**
     * 上班打卡
     * @param pssPunch
     * @return
     */
    @ResponseBody
    public ResponseResult<Void> punchDown(@RequestBody PssPunch pssPunch){
        pssPunch.setPunchno(OrderUtil.getPunchNo());
        pssPunch.setEndtime(LocalDateTime.now());
        int n = pssPunchService.changePunch(pssPunch);
        ResponseResult<Void> responseResult = null;
        if(n>0){
            responseResult = new ResponseResult<>(200,"下班成功");
        }else {
            responseResult = new ResponseResult<>(2003,"下班失败");
        }
        return responseResult;
    }
}
