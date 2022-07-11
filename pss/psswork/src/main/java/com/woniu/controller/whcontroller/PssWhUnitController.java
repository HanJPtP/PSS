package com.woniu.controller.whcontroller;

import com.woniu.pojo.PssUnit;
import com.woniu.service.impl.WhUnitService;
import com.woniu.util.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
public class PssWhUnitController {
    @Resource
    private WhUnitService whUnitService;

    @GetMapping("unit/list")
    public ResponseResult<Object> listUnit(){
        List<PssUnit> pssUnits = whUnitService.listUnit();
        ResponseResult<Object> responseResult = null;
        if(pssUnits.size()>0){
            responseResult = new ResponseResult<>(200, "ok",pssUnits);
        }else {
            responseResult = new ResponseResult<>(5001, "没有数据");
        }
        return responseResult;
    }
}
