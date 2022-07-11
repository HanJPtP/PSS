package com.woniu.controller.whcontroller;


import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssToolsNums;
import com.woniu.service.impl.PsswhtoolnumService;
import com.woniu.service.mservice.impl.PssToolsNumsServiceImpl;
import com.woniu.util.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PssToolnumController {

    @Resource
    private PsswhtoolnumService psswhtoolnumService;

    @Resource
    private PssToolsNumsServiceImpl pssToolsNumsService;



    /**
     * 工单申请列表
     * @return
     */
    @GetMapping("psstoolnum/list")
    public ResponseResult<Object> queryWhdata(  @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize){
        PageInfo<PssToolsNums> pssToolsNumsPageInfo = psswhtoolnumService.ListWhtoolnum(pageNo, pageSize);
        ResponseResult<Object> responseResult = null;
        if(pssToolsNumsPageInfo.getData().size()>0){
            responseResult = new ResponseResult<>(200, "ok",pssToolsNumsPageInfo);
        }else {
            responseResult = new ResponseResult<>(5001, "没有数据");
        }
        return responseResult;
    }


    @GetMapping("psstoolnum/infor")
    public ResponseResult<Object> querypsstoolnum(@RequestParam(value = "orderno", required = false, defaultValue = "")String orderno){
        List<PssToolsNums> pssToolsNums = pssToolsNumsService.listToolsNums(orderno);
        ResponseResult<Object> responseResult = null;
        if(pssToolsNums.size()>0){
            responseResult = new ResponseResult<>(200, "ok",pssToolsNums);
        }else {
            responseResult = new ResponseResult<>(5001, "没有数据");
        }
        return responseResult;

    }


    @GetMapping("psstoolnum/detail")
    public ResponseResult<Object> psstoolnumdetail(@RequestParam(value = "orderno", required = false, defaultValue = "")String orderno){
        List<PssToolsNums> pssToolsNums = psswhtoolnumService.toolnumInfor(orderno);
        ResponseResult<Object> responseResult = null;
        if(pssToolsNums.size()>0){
            responseResult = new ResponseResult<>(200, "ok",pssToolsNums);
        }else {
            responseResult = new ResponseResult<>(5001, "没有数据");
        }
        return responseResult;
    }
}
