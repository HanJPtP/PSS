package com.woniu.controller;

import com.woniu.pojo.PssCheckwork;
import com.woniu.service.impl.PssCheckworkServiceImpl;
import com.woniu.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class CheckworkController {
    @Resource
    private PssCheckworkServiceImpl pssCheckworkService;
    @GetMapping("/checkwork/list")
    public ResponseResult<Object> showCheckwork(Integer userid){
        List<PssCheckwork> checkworkList =pssCheckworkService.getCheckworkByUserid(userid);
        ResponseResult<Object> responseResult = null;
        if(checkworkList.size()>0){
            responseResult = new ResponseResult<>(200,"ok",checkworkList);
        }else {
            responseResult = new ResponseResult<>(2002,"没有数据");
        }
        return responseResult;
    }
}
