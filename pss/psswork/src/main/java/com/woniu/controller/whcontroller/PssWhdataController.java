package com.woniu.controller.whcontroller;


import com.woniu.pojo.PageInfo;
import com.woniu.pojo.PssWhdata;
import com.woniu.service.impl.Pss_whdataService;
import com.woniu.util.ResponseResult;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class PssWhdataController {

    @Resource
    private Pss_whdataService pssWhdataService;


//    /**
//     * 有无首页登录权限
//     * @return
//     */
//    @SneakyThrows
//    @GetMapping("/test/whdatalist")
////    @PreAuthorize("hasAuthority('subject:list')")
//    public ResponseResult<Void> testShowPerms(){
//        return new ResponseResult<>(200,"ok");
//    }
//
    /**
     * 有没有修改权限
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/whdataupdate")
    @PreAuthorize("hasAuthority('warehouse:update')")
    public ResponseResult<Void> testUpdatePerms(){
        return new ResponseResult<>(200,"ok");
    }

    /**
     * 有没有添加权限
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/whdataadd")
    @PreAuthorize("hasAuthority('warehouse:add')")
    public ResponseResult<Void> testAddPerms(){
        return new ResponseResult<>(200,"ok");
    }

    /**
     * 有没有删除权限
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/whdatadel")
    @PreAuthorize("hasAuthority('warehouse:delete')")
    public ResponseResult<Void> testDelPerms(){
        return new ResponseResult<>(200,"ok");
    }



    /**
     * 四库列表
     * @param dataname
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("data/list")
    public ResponseResult<Object> queryWhdata(@RequestParam(value = "dataname",required = false,defaultValue = "")String dataname,
                                              @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
                                              @RequestParam(value = "pageSize",required = false,defaultValue = "5")Integer pageSize){
        PageInfo<PssWhdata> pssWhdataPageInfo = pssWhdataService.ListWhdata(pageNo, pageSize, dataname);
        ResponseResult<Object> responseResult = null;
        if(pssWhdataPageInfo.getData().size()>0){
            responseResult = new ResponseResult<>(200, "ok",pssWhdataPageInfo);
        }else {
            responseResult = new ResponseResult<>(5001, "没有数据");
        }
        return responseResult;
    }

    /**
     * 添加四库
     * @param pssWhdata
     * @return
     */
    @PostMapping("data/add")
    public ResponseResult<Void> addWhdata(@RequestBody PssWhdata pssWhdata){
        int i = pssWhdataService.addWhdata(pssWhdata);
        ResponseResult<Void> responseResult = null;
        if(i>0){
            responseResult = new ResponseResult<>(200, "添加四库数据成功");
        }else {
            responseResult = new ResponseResult<>(5001, "添加失败");
        }
        return responseResult;
    }

    /**
     * 修改四库
     * @param pssWhdata
     * @return
     */
    @PostMapping("data/update")
    public ResponseResult<Void> updateWhdata(@RequestBody PssWhdata pssWhdata){
        System.out.println(pssWhdata.toString());
        int i = pssWhdataService.updateWhdata(pssWhdata);
        ResponseResult<Void> responseResult = null;
        if(i>0){
            responseResult = new ResponseResult<>(200, "修改四库数据成功");
        }else {
            responseResult = new ResponseResult<>(5001, "修改失败");
        }
        return responseResult;
    }


    /**
     * 删除四库
     * @param id
     * @return
     */
    @GetMapping("/data/delete")
    public ResponseResult<Void> delWhdata(@RequestParam(value = "id",required = false,defaultValue = "-1") Integer id){
        int i = pssWhdataService.deleteWhdata(id);
        ResponseResult<Void> responseResult = null;
        if(i>0){
            responseResult = new ResponseResult<>(200, "删除四库数据成功");
        }else {
            responseResult = new ResponseResult<>(5001, "删除失败");
        }
        return responseResult;
    }


    /**
     * 根据id查四库
     * @param id
     * @return
     */
    @GetMapping("/data/query")
    public ResponseResult<Void> queWhdata(@RequestParam(value = "id",required = false,defaultValue = "-1") Integer id){
        System.out.println(id);
        PssWhdata pssWhdata = pssWhdataService.queryData(id);
        ResponseResult<Void> responseResult = null;
        if(pssWhdata!=null){
            responseResult = new ResponseResult<>(200, "查询四库数据成功");
        }else {
            responseResult = new ResponseResult<>(5001, "查询失败");
        }
        return responseResult;
    }
}
