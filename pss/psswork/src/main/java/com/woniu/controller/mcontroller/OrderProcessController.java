package com.woniu.controller.mcontroller;

import com.woniu.fo.Tools;
import com.woniu.pojo.PssOrderProcess;
import com.woniu.pojo.PssToolsNums;
import com.woniu.pojo.PssUsers;
import com.woniu.pojo.PssWhdata;
import com.woniu.service.impl.PssUsersService;
import com.woniu.service.impl.PssWarehourseService;
import com.woniu.service.impl.Pss_whdataService;
import com.woniu.service.mservice.impl.*;
import com.woniu.util.PageInfo;
import com.woniu.util.ResponseResult;
import com.woniu.util.mutil.GenerateNoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/processorder")
@RequiredArgsConstructor
public class OrderProcessController {
    private final PssOrderProcessServiceImpl pssOrderProcessService;

    private final PssUsersService pssUsersService;

    private final ToolsServiceImpl toolsService;

    private final PssToolsNumsServiceImpl pssToolsNumsService;

    private final PssOrderTypeServiceImpl pssOrderTypeService;

    private final PssCourtsService2Impl pssCourtsService;

    private final Pss_whdataService pss_whdataService;

    private final PssOrderTaskServiceImpl pssOrderTaskService;

    private final Pss_whdataService pssWhdataService;

    private final PssWarehourseService pssWarehourseService;


    /**
     *  处理单多条件查询
     * @param pageNo    当前页面
     * @param pageSize  当前页数据条数
     * @param orderno   处理单编号
     * @param handlerno    处理人编号字符串
     * @param ordertypeid
     * @param areano    区域编号
     * @param problemtype   问题类型
     * @return
     */
    @GetMapping("list")
    @PreAuthorize("hasAuthority('processorder:list')")
    public ResponseResult<Object> listProcessOrder(@RequestParam(value = "pageNo",required = false,defaultValue = "1") Integer pageNo,
                                                   @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "orderno",required = false,defaultValue = "") String orderno,
                                                   @RequestParam(value = "handlerno",required = false,defaultValue = "") String handlerno,
                                                   @RequestParam(value = "ordertypeid",required = false,defaultValue = "-1") Integer ordertypeid,
                                                   @RequestParam(value = "areano",required = false,defaultValue = "") String areano,
                                                   @RequestParam(value = "problemtype",required = false,defaultValue = "-1") Integer problemtype){
        //处理单列表
        PageInfo<PssOrderProcess> pageInfo = pssOrderProcessService.listPssOrderProcess(pageNo,pageSize,orderno,handlerno,ordertypeid,areano,problemtype);
        log.info(pageInfo.getData().toString());

        //得到借用工具详情集合toolsList
        List<Tools> toolsList = new ArrayList<>();

        for (PssOrderProcess p : pageInfo.getData()){
            //得到工单类型名
            p.setTypename(pssOrderTypeService.getByordertypeid(p.getOrdertypeid()).getTypename());
            //得到处理人编号字符串数组,集合
            if(p.getHandlerno() != null) {
                String[] handlerNoStr = p.getHandlerno().split(",");
                List<String> handlerNoList = Arrays.asList(handlerNoStr);
                //排除字符串数组中的 null值
                List<String> arrList = new ArrayList<>(handlerNoList);
                arrList.remove("null");
                handlerNoList = arrList;
                log.info(handlerNoList.toString());
                //得到处理人集合
                List<PssUsers> usersList = pssUsersService.getUsersByNo(handlerNoList);
                //存入PssOrderProcess对象
                p.setHandlerList(usersList);
            }

            //得到所需工具编号集合
            if(p.getTools() != null) {
                List<String> toolsNoList = Arrays.asList(p.getTools().split(","));
                //得到工具对象集合
                List<PssWhdata> toolsByNoList = toolsService.getToolsByNo(toolsNoList);
                //存入PssOrderProcess对象
                p.setPssWhdataList(toolsByNoList);
            }

            //得到工单-工具-数量 对象集合
            List<PssToolsNums> listToolsNums = pssToolsNumsService.listToolsNums(p.getOrderno());
            p.setToolsNumsList(listToolsNums);

            //得到借用工具详情集合toolsList
            for (int i = 0; i < p.getToolsNumsList().size(); i++) {
                Tools tools = new Tools();
                tools.setToolno(p.getToolsNumsList().get(i).getTools());
                //根据工具编号查到工具对象
                PssWhdata whdata = pssWhdataService.getByNo(tools.getToolno());
                tools.setToolname(whdata.getDataname());
                tools.setStyle(pssWarehourseService.getStyleName(whdata.getWhhouseid()));
                tools.setStandard(whdata.getStandard());
                tools.setNums(p.getToolsNumsList().get(i).getNums());
                tools.setUserno(p.getToolsNumsList().get(i).getUserno());
                tools.setUsername(p.getToolsNumsList().get(i).getUsername());
                tools.setBorrowtime(p.getToolsNumsList().get(i).getBorrowtime());
                tools.setReturntime(p.getToolsNumsList().get(i).getReturntime());
                tools.setStatus(p.getToolsNumsList().get(i).getStatus());
                toolsList.add(tools);
            }

        }
        //得到所有台区名称
        List<String> listAllCname = pssCourtsService.listAllCname();
        //得到所有普通用户
        List<PssUsers> usersList = pssOrderProcessService.getOrderUsers();

        log.info("-----------------");
        log.info(toolsList.toString());
        HashMap<String, Object> map = new HashMap<>();
        ResponseResult<Object> responseResult = null;
        if(pageInfo.getData().size() > 0){
            //有数据
            map.put("pageInfo",pageInfo);
            map.put("listAllCname",listAllCname);
            map.put("usersList",usersList);
            map.put("toolsList",toolsList);
            responseResult = new ResponseResult<>(200,map);
        }else{
            //没有数据
            responseResult = new ResponseResult<>(2001,"查无数据");
        }

        return responseResult;

    }

    /**
     *  查询工具 并分页
     * @param pageNo
     * @param pageSize
     * @param dataname
     * @return
     */
    @GetMapping("toolsList")
    public ResponseResult<Object> getWhdataPageInfo(@RequestParam(value = "pageNo",required = false,defaultValue = "1") Integer pageNo,
                                                    @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "dataname",required = false,defaultValue = "") String dataname){
        //工具库列表
        com.woniu.pojo.PageInfo<PssWhdata> whdataPageInfo = pss_whdataService.ListWhdata(pageNo, pageSize, dataname);
        log.info(whdataPageInfo.getData().toString());
        ResponseResult<Object> responseResult = null;
        if(whdataPageInfo.getData().size() > 0){
            //有数据
            responseResult = new ResponseResult<>(200,whdataPageInfo);
        }else{
            //没有数据
            responseResult = new ResponseResult<>(2001,"查无数据");
        }
        return responseResult;
    }

    /**
     * 添加处理工单权限验证
     * @param
     * @return
     */
//    @PreAuthorize("hasAuthority('orderprocess:add')")
    @PostMapping("showadd")
    public  ResponseResult<Void> showAdd(){
        return new ResponseResult<>(200, "OK");
    }

    /**
     * 添加派工单
     * @param
     * @return
     */
//    @PreAuthorize("hasAuthority('orderprocess:add')")
    @PostMapping("add")
    public  ResponseResult<Void> addOrderProcess(@RequestBody PssOrderProcess pssOrderProcess){

        //得到pss_tools_nums表数据
        for (int i = 0; i < pssOrderProcess.getToolsNumsList().size(); i++) {
            //userno 根据派工表查询
            String userno = pssOrderTaskService.getusernoByorderno(pssOrderProcess.getOrderno());
            pssOrderProcess.getToolsNumsList().get(i).setUserno(userno);
                //username
            pssOrderProcess.getToolsNumsList().get(i).setUsername(pssUsersService.getUsernameByNo(userno));
            //time
            pssOrderProcess.getToolsNumsList().get(i).setBorrowtime(pssOrderProcess.getStarttime());
            pssOrderProcess.getToolsNumsList().get(i).setReturntime(pssOrderProcess.getEndtime());
            //借用中
            pssOrderProcess.getToolsNumsList().get(i).setStatus(1);
        }

        //循环新增pss_tools_nums表数据
        for (int i = 0; i < pssOrderProcess.getToolsNumsList().size(); i++) {
            pssToolsNumsService.addToolsNums(pssOrderProcess.getToolsNumsList().get(i));
        }

        //更新派工单
        int status = pssOrderProcess.getProblemtype();
        if(status==0){
            //正常情况,改为待校验
            pssOrderTaskService.updateStatus(2, pssOrderProcess.getOrderno());
        }else if(status==1){
            //维护维修，，改为未发放，等待重新下达任务，改变工单类型
            pssOrderTaskService.updateStatus(0, pssOrderProcess.getOrderno());
            //更新工单任务
            //得到新的工单编号
            String neworderno = GenerateNoUtil.getOrderTaskNo();
            pssOrderTaskService.updateOrdertypeid(2,pssOrderProcess.getTrouble(),neworderno, pssOrderProcess.getOrderno());

        }else if(status==2){
            //抢修装拆，，改为未发放，等待重新下达任务，改变工单类型
            pssOrderTaskService.updateStatus(0, pssOrderProcess.getOrderno());
            //更新工单任务
            //得到新的工单编号
            String neworderno = GenerateNoUtil.getOrderTaskNo();
            pssOrderTaskService.updateOrdertypeid(3,pssOrderProcess.getTrouble(),neworderno,pssOrderProcess.getOrderno());
        }



        //修改处理单处理单方法
        log.info(pssOrderProcess.toString());
        int rs = pssOrderProcessService.updateProcess(pssOrderProcess);
        //修改PssToolsNums表数据
        //gettools
        ResponseResult<Void> responseResult = null;
        if(rs > 0){
            responseResult = new ResponseResult<>(200,"提交成功");
        }else{
            responseResult = new ResponseResult<>(2001,"提交失败");
        }
        return responseResult;
    }


}
