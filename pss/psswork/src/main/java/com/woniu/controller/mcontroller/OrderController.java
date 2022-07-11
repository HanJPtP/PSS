package com.woniu.controller.mcontroller;

import com.woniu.pojo.PssOrderTask;
import com.woniu.pojo.PssOrderType;
import com.woniu.pojo.PssToolsNums;
import com.woniu.pojo.PssUsers;
import com.woniu.service.mservice.impl.PssOrderProcessServiceImpl;
import com.woniu.service.mservice.impl.PssOrderTaskServiceImpl;
import com.woniu.service.mservice.impl.PssOrderTypeServiceImpl;
import com.woniu.service.mservice.impl.PssToolsNumsServiceImpl;
import com.woniu.util.PageInfo;
import com.woniu.util.ResponseResult;
import com.woniu.util.mutil.GenerateNoUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  派工单控制类
 */
@RestController
@Slf4j
@RequestMapping("/taskorder")
//必要参数构造，编译时，lombok 帮 注入final修饰的对象
@RequiredArgsConstructor
public class OrderController {

//    //注入对象
//    @Resource
//    private PssOrderTaskServiceImpl pssOrderTaskService;
    private final PssOrderTaskServiceImpl pssOrderTaskService;
    private final PssOrderTypeServiceImpl pssOrderTypeService;
    private final PssOrderProcessServiceImpl pssOrderProcessService;
    private final PssToolsNumsServiceImpl pssToolsNumsService;

    /**
     *  查询
     * @param pageNo    当前页码
     * @param pageSize  当前页数据条数
     * @param orderno   工单编号
     * @param ordertypeid   工单类型
     * @param userno    发起人工号
     * @param orderstatus   工单状态
     * @return  返回pageInfo对象
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('taskorder:list')")
    public ResponseResult<Object> listPssOrderTask(@RequestParam(value = "pageNo",required = false,defaultValue = "1") Integer pageNo,
                                                   @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "orderno",required = false,defaultValue = "") String orderno,
                                                   @RequestParam(value = "ordertypeid",required = false,defaultValue = "-1") Integer ordertypeid,
                                                   @RequestParam(value = "userno",required = false,defaultValue = "") String userno,
                                                   @RequestParam(value = "orderstatus",required = false,defaultValue = "-1") Integer orderstatus){
        PageInfo<PssOrderTask> pageInfo = pssOrderTaskService.listOrderTask(pageNo, pageSize, orderno, ordertypeid, userno, orderstatus);
        ResponseResult<Object> responseResult = null;
        log.info(orderstatus+"");
        log.info(pageInfo.getData().toString());
        //将数据存入map
        Map map = new HashMap<String,Object>();
        //订单类型
        List<PssOrderType> pssOrderTypes = pssOrderTypeService.listPssOrderType();
        //得到所有普通用户
        List<PssUsers> usersList = pssOrderProcessService.getOrderUsers();
        map.put("pssOrderTypes", pssOrderTypes);
        map.put("usersList",usersList);

        if(pageInfo.getData().size() <= 0){
            //没有查到数据
            responseResult = new ResponseResult<>(2001,"查无数据",map);
        }else{

//            //存储用户工号
//            // 集合
//            List<String> usernames = new ArrayList<>();
//            for (int i = 0; i < usersList.size(); i++) {
//                usernames.add(usersList.get(i).getJobno());
//            }
            //将数据存入map
            map.put("pageInfo", pageInfo);
            responseResult = new ResponseResult<>(200,map);
        }
        return responseResult;

    }

    /**
     * 添加派工单权限验证
     * @param
     * @return
     */
//    @PreAuthorize("hasAuthority('ordertask:add')")
    @PostMapping("showadd")
    public  ResponseResult<Void> showAdd(){
        return new ResponseResult<>(200, "OK");
    }

    /**
     *  新增派工单
     * @param pssOrderTask
     * @return
     */
    @PostMapping("add")
    public ResponseResult<Void> addPssOrderTask(@RequestBody PssOrderTask pssOrderTask){
        //设置工单状态,初始是未发放
        pssOrderTask.setOrderstatus(0);
        //自动生成工单编号
        pssOrderTask.setOrderno(GenerateNoUtil.getOrderTaskNo());
        //调用新增方法
        int rs = pssOrderTaskService.addOrderTask(pssOrderTask);
        ResponseResult<Void> responseResult = null;
        if(rs > 0){
            responseResult = new ResponseResult<>(200,"添加派工单成功");
        }else{
            responseResult = new ResponseResult<>(2001,"添加派工单失败");
        }
        return responseResult;
    }

    /**
     * 下达 添加处理单
     * @param
     * @return
     */
//    @PreAuthorize("hasAuthority('ordertask:addprocess')")
    @PostMapping("addprocess")
    public  ResponseResult<Void> taskAddProcess(@RequestBody PssOrderTask pssOrderTask){
        int rs = pssOrderProcessService.taskAddProcess(pssOrderTask);
        ResponseResult<Void> responseResult = null;
        if(rs == 1){
            responseResult = new ResponseResult<>(200,"下达任务成功");
        }else if(rs == 0){
            responseResult = new ResponseResult<>(2001,"下达任务失败");
        }else{
            responseResult = new ResponseResult<>(2001,"任务已经下达过");
        }
        return responseResult;
    }


    /**
     * 修改派工单权限验证
     * @param
     * @return
     */
//    @PreAuthorize("hasAuthority('ordertask:update')")
    @PostMapping("showupdate")
    public  ResponseResult<Void> showUpdate(){
        return new ResponseResult<>(200, "OK");
    }

    /**
     *  修改派工单
     * @param pssOrderTask
     * @return
     */
    @PostMapping("update")
    public ResponseResult<Void> updatePssOrderTask(@RequestBody PssOrderTask pssOrderTask){

        ResponseResult<Void> responseResult = null;

        //修改工单为完成之前，需要先判断工具是否归还了，查询PssToolsNums状态
        List<PssToolsNums> listTools = pssToolsNumsService.listToolsNums(pssOrderTask.getOrderno());
        int flag = -1;
        if(pssOrderTask.getOrderstatus() == 3) { //如果修改工单为完成时，判断工具是否归还了
            for (int i = 0; i < listTools.size(); i++) {
                //1借用中
                if (listTools.get(i).getStatus() == 1) {
                    flag = 0;
                    break;
                }
            }
        }
        if(flag == 0){
            //有未还的工具
            responseResult = new ResponseResult<>(2002,"修改失败，还有未归还的工具");
        }else{
            //调用新增方法
            int rs = pssOrderTaskService.updateOrderTask(pssOrderTask);

            if(rs > 0){
                responseResult = new ResponseResult<>(200,"修改派工单成功");
            }else{
                responseResult = new ResponseResult<>(2001,"修改派工单失败");
            }
        }

        return responseResult;
    }

    /**
     *  工具归还方法
     * @param orderno
     * @return
     */
    @GetMapping("tback")
    public ResponseResult<Void> returnTools(@RequestParam("orderno") String orderno){
        //查看派工单状态是否是待校验
        Integer orderStatus = pssOrderTaskService.getOrderStatus(orderno);
        ResponseResult<Void> responseResult = null;
        if(orderStatus == 2){
            //查看工具是否已经归还
            int status = pssToolsNumsService.getStatus(orderno);

            if(status != 2){
                //没有归还
                //是待校验状态，修改工具归还状态
                int rs = pssToolsNumsService.updateStatus(orderno);
                log.info("rs=" + rs);
                if(rs > 0){
                    responseResult = new ResponseResult<>(200,"归还成功");
                }else{
                    responseResult = new ResponseResult<>(2001,"归还失败");
                }
            }else{
                responseResult = new ResponseResult<>(2002,"工具已归还过");
            }

        }else{
            responseResult = new ResponseResult<>(2003,"归还失败,当前不是待校验状态");
        }
        return responseResult;

    }
}
