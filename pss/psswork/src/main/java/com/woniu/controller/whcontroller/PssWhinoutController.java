package com.woniu.controller.whcontroller;


import com.woniu.pojo.*;
import com.woniu.service.impl.PssCheckService;
import com.woniu.service.impl.Pss_whdataService;
import com.woniu.service.impl.PssinoutService;
import com.woniu.service.impl.PsswhtoolnumService;
import com.woniu.util.DateUtil;
import com.woniu.util.ResponseResult;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@RestController
public class PssWhinoutController {

    @Resource
    private PssinoutService pssinoutService;

    @Resource
    private PsswhtoolnumService psswhtoolnumService;

    @Resource
    private Pss_whdataService pssWhdataService;

    @Resource
    private PssCheckService pssCheckService;

    /**
     * 出入库列表
     *
     * @param intime
     * @param outtime
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/inout/list")
    public ResponseResult<Object> queryWhinout(@RequestParam(value = "intime", required = false, defaultValue = "") String intime,
                                               @RequestParam(value = "outtime", required = false, defaultValue = "") String outtime,
                                               @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                               @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) throws ParseException {
        Date intime1 = null;
        Date outtime1 = null;
        if(!intime.equals("")) {
            intime1 = DateUtil.getDateToString(intime);
        }
        if(!outtime.equals("")){
            outtime1 = DateUtil.getDateToString(outtime);
        }
//        System.out.println("-----");
//        System.out.println(intime);
//        System.out.println(outtime);

        PageInfo<PssWhInout> pageInfo = pssinoutService.ListWhinout(pageNo, pageSize, intime1, outtime1);
        ResponseResult<Object> responseResult = null;
        System.out.println(pageInfo.getData().toString());

        if (pageInfo.getData().size() > 0) {
            responseResult = new ResponseResult<>(200, "ok", pageInfo);
        } else {
            responseResult = new ResponseResult<>(5001, "没有数据");
        }
        return responseResult;
    }

    /**
     * 出入库详情
     *
     * @param orderno
     * @return
     */
    @GetMapping("inout/detail")
    public ResponseResult<Object> psstoolnumdetail(@RequestParam(value = "orderno", required = false, defaultValue = "") String orderno) {
        List<PssWhInout> pssinoutInfor = pssinoutService.pssinoutInfor(orderno);
        ResponseResult<Object> responseResult = null;
        if (pssinoutInfor.size() > 0) {
            responseResult = new ResponseResult<>(200, "ok", pssinoutInfor);
        } else {
            responseResult = new ResponseResult<>(5001, "没有数据");
        }
        return responseResult;
    }


    /**
     * 添加出入库
     *
     * @param pssWhInout
     * @return
     */
    @PostMapping("inout/add")
    public ResponseResult<Void> addInout(@RequestBody PssWhInout pssWhInout) {
        System.out.println(pssWhInout.toString());
        int i = pssinoutService.addInout(pssWhInout);
        ResponseResult<Void> responseResult = null;
        if (i > 0) {
            responseResult = new ResponseResult<>(200, "添加出入库数据成功");
        } else {
            responseResult = new ResponseResult<>(5001, "添加失败");
        }
        return responseResult;
    }

    /**
     * 修改出入库
     *
     * @param pssWhInout
     * @return
     */
    @PostMapping("inout/update")
    public ResponseResult<Void> updateInout(@RequestBody PssWhInout pssWhInout) {
        int i = pssinoutService.updateInout(pssWhInout);
        ResponseResult<Void> responseResult = null;
        if (i > 0) {
            responseResult = new ResponseResult<>(200, "修改出入库数据成功");
        } else {
            responseResult = new ResponseResult<>(5001, "修改失败");
        }
        return responseResult;
    }


    /**
     * 删除出入库
     *
     * @param id
     * @return
     */
    @GetMapping("/inout/delete")
    public ResponseResult<Void> delInout(@RequestParam(value = "id", required = false, defaultValue = "-1") Integer id) {
        int i = pssinoutService.delInout(id);
        ResponseResult<Void> responseResult = null;
        if (i > 0) {
            responseResult = new ResponseResult<>(200, "删除出入库数据成功");
        } else {
            responseResult = new ResponseResult<>(5001, "删除失败");
        }
        return responseResult;
    }


    /**
     * 根据工单自动生成出入库
     *
     * @param orderno
     * @return
     */
    @GetMapping("inout/auto")
    public ResponseResult<Void> pssauto(@RequestParam(value = "orderno", required = false, defaultValue = "") String orderno) {

        System.out.println("1-----------1");
        ResponseResult<Void> responseResult = null;
        List<PssToolsNums> pssToolsNumsList = psswhtoolnumService.toolnumInfor(orderno);
        for (int i = 0; i < pssToolsNumsList.size(); i++) {

            PssToolsNums pssToolsNums = pssToolsNumsList.get(i);
            Integer whhouseid = pssToolsNums.getWhhouseid();

            Integer status = pssToolsNums.getStatus();
            System.out.println("11111");
            System.out.println(orderno);
            System.out.println(status);
            System.out.println(whhouseid);
            if (status == 2 ) {
                System.out.println("2222");
                System.out.println(orderno);
                System.out.println(status);
                System.out.println(whhouseid);
                String tools = pssToolsNums.getTools();
                Integer nums = pssToolsNums.getNums();
                pssWhdataService.UpdateNums(tools, nums);
                System.out.println("入库成功");
                //添加出入库记录
                PssWhInout pssWhInout = new PssWhInout();
                pssWhInout.setOrderno(pssToolsNums.getOrderno());
                pssWhInout.setIntime(pssToolsNums.getReturntime());
                pssWhInout.setOuttime(pssToolsNums.getBorrowtime());
                pssWhInout.setInouttype(pssToolsNums.getStatus());
                int i1 = pssinoutService.addInout(pssWhInout);
                //添加校验记录
                int i2 = -1;
                if(whhouseid==3||whhouseid==4){
                    PssWhCheck pssWhCheck = new PssWhCheck();
                    pssWhCheck.setAccount(pssToolsNums.getUsername());
                    pssWhCheck.setOrderno(pssToolsNums.getOrderno());
                    pssWhCheck.setCHECKstatus(2);
                    i2 = pssCheckService.addWhcheck(pssWhCheck);
                }
                if (i1 > 0) {
                    if(i2 > 0){
                        responseResult = new ResponseResult<>(200, "添加出入库数据,校验数据成功");
                    }else{
                        responseResult = new ResponseResult<>(200, "添加出入库数据");
                    }

                } else {
                    responseResult = new ResponseResult<>(5001, "添加失败");
                }

            } else if (status == 1) {
                System.out.println("2_____________2");
                String tools = pssToolsNums.getTools();
                Integer nums = pssToolsNums.getNums();
                pssWhdataService.delNums(tools, nums);
                PssWhInout pssWhInout = new PssWhInout();
                pssWhInout.setOrderno(pssToolsNums.getOrderno());
                pssWhInout.setIntime(pssToolsNums.getReturntime());
                pssWhInout.setOuttime(pssToolsNums.getBorrowtime());
                pssWhInout.setInouttype(pssToolsNums.getStatus());
                int i1 = pssinoutService.addInout(pssWhInout);
                if (i1 > 0) {
                    responseResult = new ResponseResult<>(200, "添加出入库数据成功");

                } else {
                    responseResult = new ResponseResult<>(5001, "添加失败");
                }
            }
        }
        return responseResult;
    }


    /**
     * 有没有修改权限
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/inoutupdate")
    @PreAuthorize("hasAuthority('inout:update')")
    public ResponseResult<Void> testUpdatePerms(){
        return new ResponseResult<>(200,"ok");
    }

    /**
     * 有没有添加权限
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/inoutadd")
    @PreAuthorize("hasAuthority('inout:add')")
    public ResponseResult<Void> testAddPerms(){
        return new ResponseResult<>(200,"ok");
    }

    /**
     * 有没有删除权限
     * @return
     */
    @SneakyThrows
    @GetMapping("/test/inoutdel")
    @PreAuthorize("hasAuthority('inout:delete')")
    public ResponseResult<Void> testDelPerms(){
        return new ResponseResult<>(200,"ok");
    }


}
