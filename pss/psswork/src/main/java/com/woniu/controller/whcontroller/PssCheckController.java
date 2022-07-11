package com.woniu.controller.whcontroller;


import com.woniu.pojo.PageInfo;

import com.woniu.pojo.PssWhCheck;

import com.woniu.service.impl.PssCheckService;
import com.woniu.util.ResponseResult;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class PssCheckController {

    @Resource
    private PssCheckService pssCheckService;

    /**
     * 校验列表
     *
     * @param account
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("check/list")
    public ResponseResult<Object> queryWhinout(@RequestParam(value = "account", required = false, defaultValue = "") String account,
                                               @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                               @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {


//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date checktime1;
//
//        try {
//            checktime1=sdf.parse("");
//
//        }catch (Exception e){
//            checktime1=null;
//
//        }
        PageInfo<PssWhCheck> pageInfo = pssCheckService.listCheck(pageNo, pageSize,account);
        ResponseResult<Object> responseResult = null;
        if (pageInfo.getData().size() > 0) {
            responseResult = new ResponseResult<>(200, "ok", pageInfo);
        } else {
            responseResult = new ResponseResult<>(5001, "没有数据");
        }
        return responseResult;
    }


    /**
     * 校验详情
     * @param orderno
     * @return
     */
    @GetMapping("psscheck/detail")
    public ResponseResult<Object> psscheckdetail(@RequestParam(value = "orderno", required = false, defaultValue = "")String orderno){
        List<PssWhCheck> pssWhChecks = pssCheckService.checkInfor(orderno);
        ResponseResult<Object> responseResult = null;
        if(pssWhChecks.size()>0){
            responseResult = new ResponseResult<>(200, "ok",pssWhChecks);
        }else {
            responseResult = new ResponseResult<>(5001, "没有数据");
        }
        return responseResult;
    }


    /**
     * 添加校验
     * @param pssWhCheck
     * @return
     */
    @PostMapping("psscheck/add")
    public ResponseResult<Void> addWhcheck(@RequestBody PssWhCheck pssWhCheck){
        int i = pssCheckService.addWhcheck(pssWhCheck);
        ResponseResult<Void> responseResult = null;
        if(i>0){
            responseResult = new ResponseResult<>(200, "添加校验数据成功");
        }else {
            responseResult = new ResponseResult<>(5001, "添加失败");
        }
        return responseResult;
    }

    /**
     * 修改校验
     * @param pssWhCheck
     * @return
     */
    @PostMapping("check/update")
    public ResponseResult<Void> updateWhcheck(@RequestBody PssWhCheck pssWhCheck){

        int i = pssCheckService.updateWhcheck(pssWhCheck);
        ResponseResult<Void> responseResult = null;
        if(i>0){
            responseResult = new ResponseResult<>(200, "修改校验数据成功");
        }else {
            responseResult = new ResponseResult<>(5001, "修改失败");
        }
        return responseResult;
    }



    /**
     * 删除校验信息
     * @param id
     * @return
     */
    @GetMapping("/check/delete")
    public ResponseResult<Void> delInout(@RequestParam(value = "id",required = false,defaultValue = "-1") Integer id){
        int i = pssCheckService.checkDel(id);
        ResponseResult<Void> responseResult = null;
        if(i>0){
            responseResult = new ResponseResult<>(200, "删除校验数据成功");
        }else {
            responseResult = new ResponseResult<>(5001, "删除失败");
        }
        return responseResult;
    }

    /**
     * 有没有修改权限
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/checkupdate")
    @PreAuthorize("hasAuthority('check:update')")
    public ResponseResult<Void> testUpdatePerms(){
        return new ResponseResult<>(200,"ok");
    }

//    /**
//     * 有没有添加权限
//     * @return
//     */
//    @SneakyThrows
//    @GetMapping("/test/checkadd")
//    @PreAuthorize("hasAuthority('check:add')")
//    public ResponseResult<Void> testAddPerms(){
//        return new ResponseResult<>(200,"ok");
//    }

    /**
     * 有没有删除权限
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/checkdel")
    @PreAuthorize("hasAuthority('check:delete')")
    public ResponseResult<Void> testDelPerms(){
        return new ResponseResult<>(200,"ok");
    }


}
