package com.woniu.controller.whcontroller;


import com.woniu.pojo.PssWarehouse;
import com.woniu.service.impl.PssWarehourseService;
import com.woniu.util.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

@RestController
public class PssWareHouseController {

    @Resource
    private PssWarehourseService pssWarehourseService;

    /**
     * 四库种类列表
     * @return
     */
    @GetMapping("warehouse/list")
    public ResponseResult<Object> queryWhdata(){
        List<PssWarehouse> pssWarehouseList = pssWarehourseService.listWhtype();
        ResponseResult<Object> responseResult = null;
        if(pssWarehouseList.size()>0){
            responseResult = new ResponseResult<>(200, "ok",pssWarehouseList);
        }else {
            responseResult = new ResponseResult<>(5001, "没有数据");
        }
        return responseResult;
    }
}
